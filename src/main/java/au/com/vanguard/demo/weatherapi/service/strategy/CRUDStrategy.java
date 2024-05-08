package au.com.vanguard.demo.weatherapi.service.strategy;

import au.com.vanguard.demo.weatherapi.model.WeatherData;

public interface CRUDStrategy {
    WeatherData getWeatherData(String city, String country);
}
