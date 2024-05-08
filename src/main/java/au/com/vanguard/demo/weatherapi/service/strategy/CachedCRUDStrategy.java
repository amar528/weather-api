package au.com.vanguard.demo.weatherapi.service.strategy;

import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.client.key.ClientAPIKeyStrategy;
import au.com.vanguard.demo.weatherapi.exception.InvalidRequestException;
import au.com.vanguard.demo.weatherapi.model.WeatherData;
import au.com.vanguard.demo.weatherapi.model.WeatherDataRequest;
import au.com.vanguard.demo.weatherapi.repository.WeatherDataRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;


@Component
@Validated
public class CachedCRUDStrategy implements CRUDStrategy {

    private final int cacheSeconds;
    private final WeatherDataRepository weatherDataRepository;
    private final ClientAPIKeyStrategy clientAPIKeyStrategy;
    private final OpenWeatherMapClient openWeatherClient;

    public CachedCRUDStrategy(@Value("${cache.ttl.seconds}") int cacheSeconds,
                              WeatherDataRepository weatherDataRepository,
                              ClientAPIKeyStrategy clientAPIKeyStrategy,
                              OpenWeatherMapClient openWeatherClient) {
        this.cacheSeconds = cacheSeconds;
        this.weatherDataRepository = weatherDataRepository;
        this.clientAPIKeyStrategy = clientAPIKeyStrategy;
        this.openWeatherClient = openWeatherClient;
    }


    @Transactional
    @Override
    public WeatherData getWeatherData(@Valid WeatherDataRequest request) {

            var now = Instant.now();
        var maybeCached = weatherDataRepository.findByCityAndCountry(request.getCity(), request.getCountry());

        if (maybeCached.isPresent()) {
            // we have persisted data.  test the cache validity / TTL
            var persisted = maybeCached.get();

            var limit = now.plusSeconds(cacheSeconds);
            var expireAt = persisted.getCreatedDate().plusSeconds(cacheSeconds);

            if (expireAt.isBefore(limit)) {
                // we are OK to return the cached data
                return persisted;
            }
        }

        // we need to call the open weather service

        // the api key for the service
        var apiKey = clientAPIKeyStrategy.getNext();

        var arguments = request.toArguments();

        var weatherResponse = openWeatherClient.findWeatherData(apiKey, arguments);

        return null;
    }

    private String getArguments(String city, String country) {
        return null;
    }
}
