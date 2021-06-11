package com.newrelic.agent.dynamodbexample.async;

import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.newrelic.agent.dynamodbexample.ProductInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AsyncApiController {

    private final AsyncProductInfoRepository repository;

    public AsyncApiController(AsyncProductInfoRepository repository) {
        this.repository = repository;
    }

//    @GetMapping(value = "/async/products", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ProductInfo> getAll() {
//        Iterable<ProductInfo> all = repository.findAll();
//
//        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
//    }

    @GetMapping(value = "/async/products")
    public List<ProductInfo> getAllById(@RequestParam(name = "ids") List<String> ids) {
        return repository.findAllByIds(ids).stream()
                .map(this::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ProductInfo from(Future<GetItemResult> future) {
        try {
            return ProductInfo.from(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/async/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductInfo> getById(@PathVariable String id) throws ExecutionException, InterruptedException {
        ProductInfo productInfo = ProductInfo.from(repository.findById(id).get());
        if (productInfo.equals(ProductInfo.NOT_FOUND)) {
            return notFound().build();
        }
        return ok(productInfo);
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
