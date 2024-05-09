package au.com.vanguard.demo.weatherapi.controller;

import au.com.vanguard.demo.weatherapi.exception.InvalidAPIKeyException;
import au.com.vanguard.demo.weatherapi.model.WeatherAdapter;
import au.com.vanguard.demo.weatherapi.model.WeatherDataBuilder;
import au.com.vanguard.demo.weatherapi.model.WeatherRequest;
import au.com.vanguard.demo.weatherapi.model.WeatherResponse;
import au.com.vanguard.demo.weatherapi.service.WeatherDataService;
import au.com.vanguard.demo.weatherapi.service.key.APIKeyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WeatherController.class)
@ExtendWith(SpringExtension.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherDataService mockWeatherDataService;

    @MockBean
    private WeatherAdapter mockWeatherAdapter;

    @MockBean
    private APIKeyValidator mockApiKeyValidator;

    @Test
    void given_cityAndCountry_when_getWeather_should_returnJSONWeatherResponse() throws Exception {

        // given
        var city = "London";
        var country = "UK";

        var apiKey = "abcd1234";
        var request = new WeatherRequest(city, country);
        var weatherData = new WeatherDataBuilder().city(city).country(country).description("Hazy sunshine").build();
        var response = new WeatherResponse(weatherData.getDescription());

        doNothing().when(mockApiKeyValidator).validate(apiKey);
        given(mockWeatherAdapter.toRequest(city, country)).willReturn(request);
        given(mockWeatherDataService.getWeatherData(request)).willReturn(weatherData);
        given(mockWeatherAdapter.toResponse(weatherData)).willReturn(response);

        // when / then
        mvc.perform(get("/api/1.0/weather/{city}/{country}", city, country)
                        .header(APIKeyValidator.HEADER_NAME, apiKey)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(weatherData.getDescription())));

        verify(mockApiKeyValidator).validate(apiKey);
        verify(mockWeatherAdapter).toRequest(city, country);
        verify(mockWeatherDataService).getWeatherData(request);

    }

    @Test
    void given_city_when_getWeather_should_returnJSONWeatherResponse() throws Exception {

        // given
        var city = "London";

        var apiKey = "abcd1234";
        var request = new WeatherRequest(city, null);
        var weatherData = new WeatherDataBuilder().city(city).description("Hazy sunshine").build();
        var response = new WeatherResponse(weatherData.getDescription());

        doNothing().when(mockApiKeyValidator).validate(apiKey);
        given(mockWeatherAdapter.toRequest(city, null)).willReturn(request);
        given(mockWeatherDataService.getWeatherData(request)).willReturn(weatherData);
        given(mockWeatherAdapter.toResponse(weatherData)).willReturn(response);

        // when / then
        mvc.perform(get("/api/1.0/weather/{city}", city)
                        .header(APIKeyValidator.HEADER_NAME, apiKey)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(weatherData.getDescription())));

        verify(mockApiKeyValidator).validate(apiKey);
        verify(mockWeatherAdapter).toRequest(city, null);
        verify(mockWeatherDataService).getWeatherData(request);

    }

    @Test
    void given_invalidAPIKey__when_getWeather_should_return401() throws Exception {

        // given
        var city = "London";
        var country = "UK";

        String apiKey = "invalid-api-key";
        var request = new WeatherRequest(city, country);
        var weatherData = new WeatherDataBuilder().city(city).country(country).description("Hazy sunshine").build();
        var response = new WeatherResponse(weatherData.getDescription());

        doThrow(InvalidAPIKeyException.class).when(mockApiKeyValidator).validate(apiKey);
        given(mockWeatherAdapter.toRequest(city, country)).willReturn(request);
        given(mockWeatherDataService.getWeatherData(request)).willReturn(weatherData);
        given(mockWeatherAdapter.toResponse(weatherData)).willReturn(response);

        // when / then
        mvc.perform(get("/api/1.0/weather/{city}/{country}", city, country)
                        .header(APIKeyValidator.HEADER_NAME, apiKey)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(HttpStatus.UNAUTHORIZED.value())));

        verify(mockApiKeyValidator).validate(apiKey);
        verifyNoInteractions(mockWeatherAdapter);
        verifyNoInteractions(mockWeatherDataService);

    }
}