package au.com.vanguard.demo.weatherapi.model;

import au.com.vanguard.demo.weatherapi.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class WeatherDataRequest {
    private final String city;
    private final String country;

    public WeatherDataRequest(String city, String country) {
        if (!StringUtils.hasText(city) && !StringUtils.hasText(country)) {
            throw new InvalidRequestException();
        }
        this.city = city;
        this.country = country;
    }

    public String toArguments() {
        var buffer = new StringBuilder();

        if (StringUtils.hasText(city)) {
            buffer.append(city);
        }

        if (StringUtils.hasText(country)) {
            if (!buffer.isEmpty()) {
                buffer.append(",");
            }
            buffer.append(country);
        }

        return buffer.toString();
    }
}
