package au.com.vanguard.demo.weatherapi.service.strategy;

import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.model.WeatherData;
import au.com.vanguard.demo.weatherapi.repository.WeatherDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CachedCRUDStrategy implements CRUDStrategy {

    private final int cacheSeconds;
    private final WeatherDataRepository weatherDataRepository;
    private final OpenWeatherMapClient openWeatherClient;

    public CachedCRUDStrategy(@Value("${cache.ttl.seconds}") int cacheSeconds,
                              WeatherDataRepository weatherDataRepository,
                              OpenWeatherMapClient openWeatherClient) {
        this.cacheSeconds = cacheSeconds;
        this.weatherDataRepository = weatherDataRepository;
        this.openWeatherClient = openWeatherClient;
    }


    @Transactional
    @Override
    public WeatherData getWeatherData(String city, String country) {
        var now = Instant.now();
        var maybeCached = weatherDataRepository.findByCityAndCountry(city, country);

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

        return null;
    }
}
