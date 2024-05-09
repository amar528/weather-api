package au.com.vanguard.demo.weatherapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherDataTest {

    @Test
    void shouldEquals() {
        var weatherData = new WeatherDataBuilder().city("city").country("country").description("Hazy sunshine").build();
        var weatherData2 = new WeatherDataBuilder().city("city").country("country").description("Hazy sunshine").build();

        assertEquals(weatherData, weatherData2);
    }

    @Test
    void shouldHash() {
        var weatherData = new WeatherDataBuilder().city("city").country("country").description("Hazy sunshine").build();
        var weatherData2 = new WeatherDataBuilder().city("city").country("country").description("Hazy sunshine").build();

        assertEquals(weatherData.hashCode(), weatherData2.hashCode());
    }

}