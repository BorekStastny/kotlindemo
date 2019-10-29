package my.reactive.kotlin.demo.client;

import my.reactive.kotlin.demo.client.model.GetUserProfileRequest;
import my.reactive.kotlin.demo.client.model.GetUserProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@Service
public class DemoClient {

    public Mono<GetUserProfileResponse> getUserProfile(GetUserProfileRequest request) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(request.getUserId(), "userId cannot be null");

        //mock
        String name;
        switch (request.getUserId()) {
            case 0: name = "Borek"; break;
            case 1: name = "Pavel"; break;
            case 2: name = "Honza"; break;
            default: name = "Stranger";
        }


        return Mono.just(new GetUserProfileResponse(request.getUserId(), name));
    }

    public Mono<String> getGreeting(String name) {
        return Mono.just("Hello " + name);
    }
}
