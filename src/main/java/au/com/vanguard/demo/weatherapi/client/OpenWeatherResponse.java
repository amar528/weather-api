package au.com.vanguard.demo.weatherapi.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {

    private List<Weather> weather = new ArrayList<>();

    public String getDescription() {
        if (weather.isEmpty()) {
            throw new IllegalStateException("no weather was returned");
        }

        if (weather.size() > 1) {
            throw new IllegalStateException("multiple weathers were returned");
        }
        return weather.get(0).getDescription();
    }
}
