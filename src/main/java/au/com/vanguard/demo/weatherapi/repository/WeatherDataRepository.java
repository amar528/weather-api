package au.com.vanguard.demo.weatherapi.repository;

import au.com.vanguard.demo.weatherapi.model.WeatherData;
import au.com.vanguard.demo.weatherapi.model.WeatherDataBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    Optional<WeatherData> findByCity(String city);

    Optional<WeatherData> findByCountry(String country);

    Optional<WeatherData> findByCityAndCountry(String city, String country);
}
