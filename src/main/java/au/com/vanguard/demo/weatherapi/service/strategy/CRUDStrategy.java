package au.com.vanguard.demo.weatherapi.service.strategy;

import au.com.vanguard.demo.weatherapi.model.WeatherData;
import au.com.vanguard.demo.weatherapi.model.WeatherDataRequest;
import jakarta.validation.Valid;

public interface CRUDStrategy {
    WeatherData getWeatherData(@Valid WeatherDataRequest request);
}
