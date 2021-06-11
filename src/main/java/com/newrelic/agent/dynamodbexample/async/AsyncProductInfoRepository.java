package com.newrelic.agent.dynamodbexample.async;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.newrelic.agent.dynamodbexample.ProductInfo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Repository
public class AsyncProductInfoRepository {

    private final AmazonDynamoDBAsync client;

    public AsyncProductInfoRepository(AmazonDynamoDBAsync client) {
        this.client = client;
    }

    public Mono<ProductInfo> findById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        AttributeValue idValue = new AttributeValue(id);
        key.put("id", idValue);
        GetItemRequest request = new GetItemRequest("ProductInfo", key);
        ProductInfoAsyncResultHandler handler = new ProductInfoAsyncResultHandler();
        Future<GetItemResult> future = client.getItemAsync(request, handler);
        CompletableFuture<GetItemResult> completableFuture = FutureTransformer.makeCompletableFuture(future);
        //GetItemResult result = future.get();
        //System.out.println("Result is " + result);
        return Mono.fromFuture(completableFuture).map(ProductInfo::from);
    }



    public Iterable<ProductInfo> findAll() {

        return List.of();
    }

    public ProductInfo save(ProductInfo productInfo) {
        return productInfo;
    }

    public void deleteById(String id) {

    }
}