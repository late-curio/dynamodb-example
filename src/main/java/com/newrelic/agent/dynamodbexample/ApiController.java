package com.newrelic.agent.dynamodbexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ApiController {

    private final ProductInfoRepository repository;

    public ApiController(ProductInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductInfo> getAll() {
        Iterable<ProductInfo> all = repository.findAll();

        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductInfo> save(@RequestBody ProductInfo productInfo) {
        ProductInfo saved = repository.save(productInfo);

        return new ResponseEntity<ProductInfo>(saved, HttpStatus.CREATED);
    }
}
