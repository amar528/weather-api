package au.com.vanguard.demo.weatherapi.model;

import au.com.vanguard.demo.weatherapi.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherDataRequestTest {

    @Test
    void given_nullArguments_should_throwInvalidRequest() {
        assertThrows(InvalidRequestException.class, () -> new WeatherDataRequest(null, null));
    }

    @Test
    void given_emptyArguments_should_throwInvalidRequest() {
        assertThrows(InvalidRequestException.class, () -> new WeatherDataRequest("", ""));
    }

    @Test
    void given_nullAndEmptyArguments_should_throwInvalidRequest() {
        assertThrows(InvalidRequestException.class, () -> new WeatherDataRequest(null, ""));
    }

    @Test
    void given_nullCity_validCountry_should_constructRequest() {
        new WeatherDataRequest(null, "UK");
    }

    @Test
    void given_validCity_andNullCountry_should_constructRequest() {
        new WeatherDataRequest("Cardiff", null);
    }

}