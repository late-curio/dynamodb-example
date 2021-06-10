# Getting Started

## Downloading DynamoDB Locally

https://s3.us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz


## Run AWS DynamoDB locally

`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`


## Run tests (creates database)

`./gradlew test`


## Run app

`./gradlew bootRun`


## Exercise API

### List all products in database
`curl http://localhost:8080/products`


### Add product
`curl --header "Content-Type: application/json" -d "{\"msrp\":\"\$15.00\", \"cost\":\"\$5.00\"}" http://localhost:8080/products`

### Get product
`curl http://localhost:8080/products/<product-id>`

### Delete product
`curl -X "DELETE" http://localhost:8080/product/<product-id>`


## Base example code derived from Baeldung

https://www.baeldung.com/spring-data-dynamodb