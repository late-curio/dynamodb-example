package com.newrelic.agent.dynamodbexample.reactive;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.newrelic.agent.dynamodbexample.ProductInfo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Repository
public class ReactiveProductInfoRepository {

    private final AmazonDynamoDBAsync client;

    public ReactiveProductInfoRepository(AmazonDynamoDBAsync client) {
        this.client = client;
    }

    public Mono<ProductInfo> findById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        AttributeValue idValue = new AttributeValue(id);
        key.put("id", idValue);
        GetItemRequest request = new GetItemRequest("ProductInfo", key);
        Future<GetItemResult> future = client.getItemAsync(request);
        CompletableFuture<GetItemResult> completableFuture = FutureTransformer.makeCompletableFuture(future);
        return Mono.fromFuture(completableFuture).map(ProductInfo::from);
    }

    public Flux<ProductInfo> findAllByIds(List<String> ids) {
        return Flux.fromIterable(ids).flatMap(this::findById);
    }
}