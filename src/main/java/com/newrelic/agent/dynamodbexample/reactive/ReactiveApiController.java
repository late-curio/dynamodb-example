package com.newrelic.agent.dynamodbexample.reactive;

import com.newrelic.agent.dynamodbexample.ProductInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ReactiveApiController {

    private final ReactiveProductInfoRepository repository;

    public ReactiveApiController(ReactiveProductInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/reactive/products")
    public Flux<ProductInfo> getAllById(@RequestParam(name = "ids") List<String> ids) {
        return repository.findAllByIds(ids);
    }

    @GetMapping(value = "/reactive/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductInfo> getById(@PathVariable String id) {
        return repository.findById(id);
    }
}
