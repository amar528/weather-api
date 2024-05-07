package au.com.vanguard.demo.weatherapi.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {

    private List<Weather> weather = new ArrayList<>();
}
