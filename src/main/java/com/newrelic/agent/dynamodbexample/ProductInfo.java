package com.newrelic.agent.dynamodbexample;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

import java.util.Map;

@DynamoDBTable(tableName = "ProductInfo")
public class ProductInfo {
    public final static ProductInfo NOT_FOUND = new ProductInfo();

    private String id;
    private String msrp;
    private String cost;

    public ProductInfo() {
    }

    public ProductInfo(String cost, String price) {
        this.cost = cost;
        this.msrp = price;
    }

    public static ProductInfo from(GetItemResult result) {
        Map<String, AttributeValue> item = result.getItem();
        if (item == null) {
            return NOT_FOUND;
        }
        String cost = item.get("cost").getS();
        String msrp = item.get("msrp").getS();
        String id = item.get("id").getS();
        ProductInfo product = new ProductInfo(cost, msrp);
        product.id = id;
        return product;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getMsrp() {
        return msrp;
    }

    public void setMsrp(String msrp) {
        this.msrp = msrp;
    }

    @DynamoDBAttribute
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}