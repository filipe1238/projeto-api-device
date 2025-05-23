# Endpoints for DeviceDomain
this application is deployed on Render, using its free plan, and is available at the following URL: [https://projeto-api-device-1.onrender.com/](https://projeto-api-device-1.onrender.com/)
so it may be inactive at times, simply load the page and wait a few seconds for it to start.

API docs avaible at /swagger-ui

## Requirements

- Java 21
- Maven (if possible, Intellij IDEA)

## Dev

```sh
mvn clean install
```
To run the application, use the following command:

```sh
mvn spring-boot:run
```

## Run using docker
Build and run the Dockerfile

## Testing
### Unit Tests
Those tests are executed automatically when you deploy the application. To run them manually, use the following command:

```sh
mvn clean test
```