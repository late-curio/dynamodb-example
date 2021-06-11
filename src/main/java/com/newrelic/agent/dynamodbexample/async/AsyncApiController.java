package com.newrelic.agent.dynamodbexample.async;

import com.newrelic.agent.dynamodbexample.ProductInfo;
import com.newrelic.agent.dynamodbexample.ProductInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AsyncApiController {

    private final AsyncProductInfoRepository repository;

    public AsyncApiController(AsyncProductInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/async/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductInfo> getAll() {
        Iterable<ProductInfo> all = repository.findAll();

        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping(value = "/async/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductInfo> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping(value = "/async/product/{id}")
    public ResponseEntity<ProductInfo> deleteById(@PathVariable String id) {
        repository.deleteById(id);
        return ok().build();
    }

    @PostMapping(value = "/async/products")
    public ResponseEntity<ProductInfo> save(@RequestBody ProductInfo productInfo) {
        ProductInfo saved = repository.save(productInfo);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
