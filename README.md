# Simple listener of feed of shares market

## Technologies stack:

- Java 21
- Spring Boot
- Spring Data JPA
- RabbitMQ
- JUnit5

## How to launch
First option:
1. Run `mvn clean package`
2. Run `docker-compose up`

Second option:
1. Use Makefile commands and run `make up`. Also, you can use `make down` command to stop Docker containers.

## How to test
To test application you should use these two requests:
1. Create shares market event:
   <br>POST http://localhost:8080/shares/event
   <br>Example of body:
   ````
    {
        "username": "john",
        "operation": "BUY",
        "ticker": "APPL",
        "amount": 500
    }
   ````
2. Get all shares of user:
   <br>GET http://localhost:8080/shares?username=<username>

