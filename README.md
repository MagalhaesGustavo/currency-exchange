# Introduction

This API has as objective to perform transactions between two registered accounts using exchange rate for differents currencies, communicating to an external API to convert currencies

External API utilised: https://exchangerate.host/#/#docs

## Tecnologies:

Java 17

Spring Framework

Maven

MongoDB

FeignClient

## How to run

Utilise the docker-compose file to up the MongoDB database, access the "docker" folder and run the command bellow 

``` docker-compose up -d```

Run the Application.class via IDE

## Swagger API Documentation

Run the Application.class and access the URL: http://localhost:8015/currencyexchange/documentation to get all the APIs informations (Methods, Status Code and Payloads)

## Collection Test

There is a Postman Collection commited at the "postman" folder named "Currency Exchange.postman_collection.json" to import at the Postman environment to execute the requests

## Unit Tests
To run and collect the tests reports run the command bellow

`` mvn clean test``

To check the unit tests coverage access the Jacoco report at path: target -> jacoco-report -> index.html

To check the mutation tests report access the Pitest report at path: target -> pit-reports -> index.html

## Metrics

To watch the metrics, actuator is configured in the project and the metrics can be accessed in the URL: http://localhost:8015/currencyexchange/actuator 





