# Getting Started

## Downloading DynamoDB Locally

https://s3.us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz

## Run AWS DynamoDB locally

`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`

## Run tests (creates database)

`./gradlew test`

## Run app

`./gradlew bootRun`

## Synchronous API

### List all products in database

`curl http://localhost:8080/products`

### Add product

`curl --header "Content-Type: application/json" -d "{\"msrp\":\"\$15.00\", \"cost\":\"\$5.00\"}" http://localhost:8080/product`

### Get product

`curl http://localhost:8080/product/<product-id>`

### Get products

`curl http://localhost:8080/products?ids=<product-id>,<product-id>,<product-id>`

### Delete product

`curl -X "DELETE" http://localhost:8080/product/<product-id>`

### Delete ALL products

`curl -X "DELETE" http://localhost:8080/products`


## Asynchronous API

### Get products (async)

`curl http://localhost:8080/async/products?ids=<product-id>,<product-id>,<product-id>`

### Get product (async)

`curl http://localhost:8080/async/product/<product-id>`

### Delete product (async)

`curl -X "DELETE" http://localhost:8080/async/product/<product-id>`


## "Reactive" API

### Get products (async)

`curl http://localhost:8080/reactive/products?ids=<product-id>,<product-id>,<product-id>`

### Get product (async)

`curl http://localhost:8080/reactive/product/<product-id>`


## Base example code derived from Baeldung

https://www.baeldung.com/spring-data-dynamodb