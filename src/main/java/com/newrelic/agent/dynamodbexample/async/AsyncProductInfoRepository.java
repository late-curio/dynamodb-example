package com.newrelic.agent.dynamodbexample.async;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.*;
import com.newrelic.agent.dynamodbexample.ProductInfo;
import com.newrelic.agent.dynamodbexample.reactive.FutureTransformer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Repository
public class AsyncProductInfoRepository {

    private final AmazonDynamoDBAsync client;

    public AsyncProductInfoRepository(AmazonDynamoDBAsync client) {
        this.client = client;
    }

    public Future<GetItemResult> findById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        AttributeValue idValue = new AttributeValue(id);
        key.put("id", idValue);
        GetItemRequest request = new GetItemRequest("ProductInfo", key);
        return client.getItemAsync(request);
    }

    public List<Future<GetItemResult>> findAllByIds(List<String> ids) {
        return ids.stream().map(this::findById).collect(Collectors.toList());
    }

    public Iterable<ProductInfo> findAll() {

        return List.of();
    }

    public ProductInfo save(ProductInfo productInfo) {
        return productInfo;
    }

    public void deleteById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        AttributeValue idValue = new AttributeValue(id);
        key.put("id", idValue);
        client.deleteItemAsync("ProductInfo", key);
    }
}