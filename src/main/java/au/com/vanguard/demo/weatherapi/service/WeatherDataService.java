package au.com.vanguard.demo.weatherapi.service;

import au.com.vanguard.demo.weatherapi.client.APIKey;
import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherDataService {

    private final APIKey apiKey;
    private final OpenWeatherMapClient openWeatherClient;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataService(APIKey apiKey, OpenWeatherMapClient openWeatherClient, WeatherDataRepository weatherDataRepository) {
        this.apiKey = apiKey;
        this.openWeatherClient = openWeatherClient;
        this.weatherDataRepository = weatherDataRepository;
    }
}
