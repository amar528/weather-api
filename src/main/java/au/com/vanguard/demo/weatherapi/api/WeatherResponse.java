package au.com.vanguard.demo.weatherapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherResponse {
    private String description;
}
