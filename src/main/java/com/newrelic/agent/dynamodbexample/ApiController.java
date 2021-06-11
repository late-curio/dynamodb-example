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
    public List<ProductInfo> getAllById(@RequestParam(name = "ids", required = false) List<String> ids)  {
        Iterable<ProductInfo> all;
        if(ids == null || ids.isEmpty()) {
            all = repository.findAll();
        }
        else {
            all = repository.findAllById(ids);
        }
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductInfo> save(@RequestBody ProductInfo productInfo) {
        ProductInfo saved = repository.save(productInfo);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // same as above but not pluralized
    @PostMapping(value = "/product")
    public ResponseEntity<ProductInfo> create(@RequestBody ProductInfo productInfo) {
        ProductInfo saved = repository.save(productInfo);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductInfo> getById(@PathVariable String id) {
        Optional<ProductInfo> found = repository.findById(id);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/products")
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<ProductInfo> deleteById(@PathVariable String id) {
        Optional<ProductInfo> found = repository.findById(id);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }
}
