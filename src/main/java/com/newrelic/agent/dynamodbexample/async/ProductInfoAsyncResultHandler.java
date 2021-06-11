package com.newrelic.agent.dynamodbexample.async;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.newrelic.agent.dynamodbexample.ProductInfo;

import java.util.Map;

public class ProductInfoAsyncResultHandler implements AsyncHandler<GetItemRequest, GetItemResult> {

    private ProductInfo result;

    public ProductInfo getResult() {
        return result;
    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onSuccess(GetItemRequest request, GetItemResult getItemResult) {
        System.out.println("Setting result...");
        result = ProductInfo.from(getItemResult);
    }
}
