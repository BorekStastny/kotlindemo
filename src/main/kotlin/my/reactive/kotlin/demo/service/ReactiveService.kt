package my.reactive.kotlin.demo.service


import my.reactive.kotlin.demo.client.DemoClient
import my.reactive.kotlin.demo.client.model.GetUserProfileRequest
import my.reactive.kotlin.demo.client.model.GetUserProfileResponse
import my.reactive.kotlin.demo.enrich
import my.reactive.kotlin.demo.notify
import my.reactive.kotlin.demo.service.model.GreetCustomerModel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import reactor.core.publisher.Mono

@Service
class ReactiveService {

    private val LOG = LoggerFactory.getLogger(ReactiveService::class.java)

    @Autowired
    lateinit var client: DemoClient

    fun greetCustomer(userId: Int): Mono<String> {
        return initModel(userId).log()
                .enrich({ getUserProfile(it) }, { response, model -> combine(response, model) })
                .enrich({ getGreeting(it) }, { response, model -> combine(response, model) })
                .notify { storeGreeting(it) }
                .map { createResponse(it) }
    }

    /**
     * Converts model to GetUserProfileRequest and obtains GetUserProfileResponse.
     */
    private fun getUserProfile(model: GreetCustomerModel): Mono<GetUserProfileResponse> {
        Assert.notNull(model.userId, "userId cannot be empty")
        val request = GetUserProfileRequest(model.userId)
        return client.getUserProfile(request)
    }

    /**
     * Incorporates GetUserProfileResponse into model.
     */
    private fun combine(response: GetUserProfileResponse, model: GreetCustomerModel): GreetCustomerModel {
        model.name = response.name
        return model
    }

    /**
     * Converts model to String getGreeting request.
     */
    private fun getGreeting(model: GreetCustomerModel): Mono<String> {
        Assert.notNull(model.name, "user name cannot be empty")
        return client.getGreeting(model.name)
    }

    /**
     * Incorporates String getGreeting response into model.
     */
    private fun combine(response: String, model: GreetCustomerModel): GreetCustomerModel {
        model.greeting = response
        return model
    }

    /**
     * Logs resulting greeting from model.
     */
    private fun storeGreeting(model: GreetCustomerModel): Mono<Any> {
        LOG.info("Storing greeting: ${model.greeting} for user ${model.userId}")
        return Mono.empty()
    }

    /**
     * Creates operation response.
     */
    private fun createResponse(model: GreetCustomerModel): String {
        return "${model.greeting} !!!"
    }

    /**
     * Initializes model with data from operation request.
     */
    private fun initModel(userId: Int?): Mono<GreetCustomerModel> {
        Assert.notNull(userId, "userId cannot be empty")
        return Mono.just(GreetCustomerModel(userId))
    }
}