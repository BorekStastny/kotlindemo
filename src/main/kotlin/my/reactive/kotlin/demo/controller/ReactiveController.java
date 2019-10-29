package my.reactive.kotlin.demo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import my.reactive.kotlin.demo.service.ReactiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ReactiveController {

    private Logger LOG = LoggerFactory.getLogger(ReactiveController.class);

    private ReactiveService reactiveService;

    @Autowired
    public ReactiveController(ReactiveService reactiveService) {
        this.reactiveService = reactiveService;
    }

    @GetMapping("/hello")
    @ApiOperation("Greet Customer By userID, supported values: 0 - Borek, 1 - Pavel, 2 - Honza")
    public Mono<String> hello(Integer userId)  {
        return reactiveService.greetCustomer(userId);
    }
}
