package au.com.vanguard.demo.weatherapi.service;

import au.com.vanguard.demo.weatherapi.client.key.RoundRobinClientAPIKeyStrategy;
import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherDataService {

    private final RoundRobinClientAPIKeyStrategy roundRobinClientApiKeyStrategy;
    private final OpenWeatherMapClient openWeatherClient;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataService(RoundRobinClientAPIKeyStrategy roundRobinClientApiKeyStrategy, OpenWeatherMapClient openWeatherClient, WeatherDataRepository weatherDataRepository) {
        this.roundRobinClientApiKeyStrategy = roundRobinClientApiKeyStrategy;
        this.openWeatherClient = openWeatherClient;
        this.weatherDataRepository = weatherDataRepository;
    }
}
