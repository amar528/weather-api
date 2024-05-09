# Weather API

## Welcome
Welcome to the Weather API !  This service provides a simple wrapper API
around the Open Weather Map service (https://api.openweathermap.org)

## Build
The project was built with Java 17 (LTS), using maven, and developed in Intellij (community edition).

To build the service:
From the root of the project directory :

`mvnw` / `mvnw.cmd`

or

Run maven from the command line :
`mvn clean install`

### Standalone
To run the service as a standalone java process :

`java -jar target/weather-api-0.0.1-SNAPSHOT.jar`

You can test the service via:
`GET /api/1.0/weather/London/UK HTTP/1.1
api-key: abcdefg0123456
Host: localhost:8080`

### Docker
To build the docker image, execute the following maven goal:

`mvn spring-boot:build-image`

To start the Docker container :

`docker run -d -p 8081:8080 weather-api:0.0.1-SNAPSHOT`

To test the container :

`GET /api/1.0/weather/London/UK HTTP/1.1
api-key: abcdefg0123456
Host: localhost:8081`

(refer to api-tests for example requests)

## Design Considerations

### URL Scheme
The weather resource is defined as follows:

`GET /api/1.0/weather/{city}/{country}`

Where city is mandatory, whilst country is optional.

### API Key
The application expects a HTTP request header to validate client requests :
`api-key = abcdefg0123456`

This key is stored in the application configuration, via `application.properties`

### Persistence
The service by default uses an in-memory H2 instance.  To use persistent file storage, we can alter the 
`spring.datasource.url` property at deployment time, for example this can be passed to a docker container
at startup (suppose we want to mount a /db directory and store the data there:
`docker run -v /weather-db -e JAVA_TOOL_OPTIONS='-Dspring.datasource.url=jdbc:h2:file:/weather-db;AUTO_SERVER=TRUE;`

### Layering
The layering of the application is as follows:

`Controller -> Adapter
            -> Service -> CRUD Strategy -> Repository
                                        -> Open Weather Feign Client`

We can replace the CRUD strategy implementation without affecting the upper layers, as long as the
interface specification would hold for future requirements.

Additionally, the API Key Interceptor utilises the APIKeyValidator.
A simple configuration injected approach is used to verify the request header api-key value.
This could be replaced with Spring Security, or another implementation of the strategy interface.

## Approach
The Open Weather API was investigated using a Rest Client (Rapid API on Mac OS)
API keys were generated, and added to the Spring Boot configuration (application.properties)

This API was explored calling the API with different parameters, and I discovered that
the City was mandatory, whilst Country is not. So this was mirrored in this API design.
I looked at the error cases and possible HTTP responses that can result from calling this API.
These were modelled and handled in a ErrorDecoder instance, which throws the appropriate Exception.
The ErrorDecoder plugs into the OpenFeign (REST client) framework.
The exceptions are handled via a @ControllerAdvice exception handler, which maps each exception 
to a corresponding HTTP/REST response.

## Design Patterns Used

Builder Pattern - The WeatherDataBuilder handles construction of WeatherData model instances.
Strategy - We have strategies for the CRUD layer, and the API key handling for the service and client.
Delegate - API delegates to adapter and service. The service delegates to the CRUD strategy.
Facade - The service layer provides a facade to the persistence and client layers, abstracting all the detail.
Adapter - An adapter class handles construction of API requests and responses from/to API arguments and model
data respectively.

## Testing
The solution was implemented using TDD, using :
- Mockito for mocking during Unit Tests
- Spring Data JPA tests for integration testing the JPA layer / Spring Data repositories
- Web MVC testing for testing the Controller and Exception handling, JSON Ser/Des
- Wiremock was used to integration test the Feign Client, which provides mocked HTTP responses

Testing a specific component involves mocking the collaborating classes or infrastructure that the
component directly depends upon, for example collaborating classes, or a mock HTTP server, or in-memory H2 database.

End-to-end testing was performed using RapidAPI, with test cases for happy path, as well as error cases
`{404, 400, 401, 429}`

These tests are available in HTTP Archive format under:
`/api-tests/weather-api.har`
(please note these are set to test against localhost:8081)

## Code Coverage
We use JaCoCo (maven plugin) to generate our test coverage report.
The report is available under target/site/jacoco/index.html
(Coverage 90%/70%)

## Enhancements

Given further time, I would have liked to investigate and improve:

- API Key authentication could be achieved using Spring Security
- Spring Integration. Is there a nicer way of implementing proxy-type
  microservices such as this? e.g. similar to Camel routes, transformation and persistence.
- Fallback approaches - Feign has some nice fallback patterns that can be used
  to provide default responses in case of certain error conditions.
- Hystrix - add circuit breaker in case of repeated errors from the downstream service
- Investigate newer Spring Cloud components that I am not yet aware of.
- Investigate and learn Cloud deployment integration options.
- Investigate build packs, for building Docker image.
- Investigate new Jenkins build pipeline options.
- How would you implement a similar solution using Python and modern frameworks ? Is it easier?
