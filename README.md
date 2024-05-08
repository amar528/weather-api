# Weather API

## Welcome

Welcome to the Weather API !  This simple service provides a simple wrapper API
around the Open Weather Map service.

## Running

### Standalone

### Docker

## Design Considerations
The layering of the application is as follows:


## Approach

The Open Weather API was investigated using a Rest Client (Rapid API on Mac OS)
API keys were generated, and added to the Spring Config.
I explored calling the API with different parameters, and discovered that
the Country was mandatory, so this was mirrored in this API design.
I looked at the error cases and possible HTTP responses that can result from calling this API.
These were modelled and handled in a ErrorDecoder instance, which plugs into the OpenFeign (REST client) framework.


## Design Patterns Used

Builder Pattern - The WeatherDataBuilder handles construction of WeatherData model instances.
Strategy - We have strategies for the CRUD layer, and the API key handling for the service and client.
Delegate - API delegates to adapter and service. The service delegates to the CRUD strategy.
Facade - The service layer provides a facade to the persistence and client layers, abstracting all the detail.
Adapter - An adapter class handles construction of API requests and responses from/to API arguments and model
data respectively.

# Testing
The solution was implemented using TDD, using Mockito for mocking during Unit Tests, and also 
Spring Data JPA tests for integration testing the JPA layer, MWeb MVC testing for testing the Controller
layer, and also Wiremock was used to integration test the Feign Client, which utilises mocked HTTP
Testing a specific component involves mocking the collaborating classes or infrastructure that the
component depends upon. For example, 

## Enhancements

Given further time, I would have liked to investigate and improve:

- Spring Integration. Is there a nicer way of implementing proxy-type
  microservices such as this? e.g. similar to Camel routes, transformation and persistence.
- Fallback approaches - Feign has some nice fallback patterns that can be used
  to provide default responses in case of certain error conditions.
- Hystrix - add circuit breaker in case of repeated errors from the downstream service
- Investigate newer Spring Cloud components that I am not yet aware of.
- Investigate and learn Cloud deployment integration options.
- Investigate new Jenkins build pipeline options.
