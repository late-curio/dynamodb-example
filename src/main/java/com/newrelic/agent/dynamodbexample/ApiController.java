package com.newrelic.agent.dynamodbexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductInfo> getById(@PathVariable String id) {
        Optional<ProductInfo> found = repository.findById(id);

        if(found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<ProductInfo> deleteById(@PathVariable String id) {
        Optional<ProductInfo> found = repository.findById(id);

        if(found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductInfo> save(@RequestBody ProductInfo productInfo) {
        ProductInfo saved = repository.save(productInfo);

        return new ResponseEntity<ProductInfo>(saved, HttpStatus.CREATED);
    }
}
