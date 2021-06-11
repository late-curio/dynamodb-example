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
    public Flux<ProductInfo> getAllById(@RequestParam("ids") List<String> ids) {
        return repository.findAllByIds(ids);
    }

    @GetMapping(value = "/reactive/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductInfo> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping(value = "/reactive/product/{id}")
    public ResponseEntity<ProductInfo> deleteById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/reactive/products")
    public ResponseEntity<ProductInfo> save(@RequestBody ProductInfo productInfo) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
