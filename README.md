# Cinema API
[![Build Status](https://travis-ci.com/coelhocaique/cinema-api.svg?branch=master)](https://travis-ci.com/coelhocaique/cinema-api)
[![Code Coverage](https://codecov.io/github/coelhocaique/cinema-api/coverage.svg)](https://codecov.io/gh/coelhocaique/cinema-api)

## Technologies Used

* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Koltin](https://kotlinlang.org/)
* [Spring Web Flux](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)
* [Mongo DB](https://www.mongodb.com/)

## Run Tests

Run locally with gradle:

```
./gradlew test

```

## Run App

Run locally with gradle:

```
./gradlew bootRun -Djdk.tls.client.protocols=TLSv1.2

```
Or run with [docker](https://docs.docker.com/get-docker/):
```
cd docker
docker-compose up
```

## How to use the API

After application is started you can access [Swagger Documentation](http://localhost:8888/webjars/swagger-ui/index.html?url=/openapi.yaml)

You can also access the [Docker hub repository](https://hub.docker.com/repository/docker/coelhocaique/cinema-api)

Note: Database is already populated with available movies, so it is good to start from http://localhost:8888/movies.
## About the solution

For this project I chose to use Kotlin with Spring Reactive, because it is a stack I'm already familiar with, and provides full functional programming approach.

For the database layer, I chose to use MongoDB because of mainly these reasons: 

* Given the way I approached the problem, a relational database was not necessary. 
* NoSQL Databases are cheaper to scale and generally faster to query.
* MongoDB provides full support for Spring WebFlux.

### Why multi-module app?

I personally really like multi-module projects because it gives you flexibility to create separate docker images and deploy them separately.
I structured the project with a ***core*** and an ***api*** module. All the business rules are placed in ***core*** and the other modules must have it 
as a dependency. 
So for example if an ***event-listener*** or a ***jobs*** modules is needed we only have to create these new subprojects
and their DockerFile to generate separate images.

### Integration with IMdb API

I used the default token for integrating with OMdb API and as I noticed there are limited calls to the API, I added an in memory cache to avoid many calls.

The cache can be easily disabled by uncommenting the line ```#spring.cache.type=None``` on ```core-application.properties```.

### Hateoas pattern

I also added Hateoas pattern in the API responses, so every comes with useful links for others resources.

### The Mongo DB database

I created a free cluster on [MongoDb Website](https://www.mongodb.com/cloud) for this project and the credentials are already on the application. 

### Application Domains

I created the following domains/sub-domains for the application:

* ***Movie***: An entity that represents a movie, has only the ImdbID mapped to it, since you have a movie created you can apply the other operations provided by the system.
* ***Movie Session***: This entity is used to represent movie times and prices, so if you create a ***Movie Session*** you can already specify date, time, price and also a room if necessary. 
* ***Review***: This entity is used to create review for movies, so you can leave a rating and an optional comment.

##### The Mongo DB documents
* ***Movie***:
    Only the imdbId is stored on database document, all the other information is retrieved from [OMdb API.](http://www.omdbapi.com/)
    ```kotlin 
    val id: UUID, // the movie ID
    val imdbId: String, // IMdb API Id
    val createdAt: LocalDateTime // date the movie was created
    ```
* ***Movie Session***:
    The only note is that when a session is deleted, it is not actually deleted, the system only inactivates the session for sake of auditing and history.
    ```kotlin 
    val id: UUID, // the session ID
    val movieId: UUID, // movie Id from Movie document
    val price: BigDecimal, // the session price 
    val room: String? = null, // an optional movie room for this session
    val date: LocalDate, // the session time in format YYY-MM-DD
    val time: LocalTime, // the session time in format HH:MM:SS
    val createdAt: LocalDateTime, // date the movie session was created
    val active: Boolean // whether this session is active or not
    ```
* ***Review***:
  The entity is more generic because I thought about extending it in the future for session review for example, so instead of creating another document for the same 
purpose, we can use the same structure and just create another type.
    ```kotlin 
    val id: UUID, // the review ID
    val type: ReviewType, // the review type, available value: MOVIE
    val referenceId: UUID, // the id of the type this review is referenced, in this case movieId from movie document
    val rating: Double, // rating a value between 1 and 5
    val comment: String?, // an optional review comment
    val createdAt: LocalDateTime // date the review was created
    ```

### Beyond the requirements

Although the challenge requirement did not ask, I created extra APIs that can be useful for the user, such as:

   * ``` POST /movies``` an API to create available movies.
   * ``` GET /movies/{movieId}/sessions?dateTime=2021-09-13T14:00:00``` an API to retrieve movie session for a specific date and time greater than equal the time informed.

I also added integration with [Travis CI](https://www.travis-ci.com/) so after each commit, a pipeline runs to validate the changes and sends the tests result to [CodeCov](https://about.codecov.io/) to evaluate the test coverage of the project.


### What can be improved?

Although I did not have time to code, I wish I could've created integration test, but it would require more time to create the scenarios and apply all the necessary configurations to the application.  

Also add a TTL for caching because it is never expiring.




