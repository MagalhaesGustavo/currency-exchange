# Introduction

This API has as objective to perfom transactions between two registers accounts using exchange rate for diferentts currencies using an external API to covert currencies

External API utilised: https://exchangerate.host/#/#docs

## Tecnologies:

Java 17

Spring Framework

Maven

MongoDB

FeignClient

## How to run

Utilise the docker-compose file to up the MongoDB database 

```$ docker-compose up -d```

Run the Application.class via IDE

## Swagger API Documentation

Run the Application.class and access the URL: http://localhost:8015/currencyexchange/documentation to get all the APIs informations (Methods, Status Code and Payloads)

## Collection Test

There is a Postman Collection commited at the root path named "Currency Exchange.postman_collection.json" to import at the Postman enviroment to test the methods

## Unit Tests

To check de tests coverage access the Jacoco report at path: target -> jacoco-report -> index.html

To check the mutation tests access the Pitest report at path: target -> pit-reports -> index.html

## Metrics

To watch the metrics, actuator is configured and the metrics can be accessed at the URL: http://localhost:8015/currencyexchange/actuator 





