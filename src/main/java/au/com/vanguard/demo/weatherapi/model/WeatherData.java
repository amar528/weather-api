package au.com.vanguard.demo.weatherapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class WeatherData {

    @Id
    @GeneratedValue
    private Long id;

    private String city;

    private String country;

    private String description;

    /**
     * For JPA
     */
    WeatherData() {

    }

    public WeatherData(String city, String country, String description) {
        this.city = city;
        this.country = country;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return Objects.equals(city, that.city) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country);
    }
}
