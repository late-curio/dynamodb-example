# Getting Started

## Deploying AWS DynamoDB Locally on Your Computer

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html


## Downloading Locally

https://s3.us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz


## Run AWS DynamoDB locally

`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`


## Base example code derived from Baeldung

https://www.baeldung.com/spring-data-dynamodb