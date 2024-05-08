package au.com.vanguard.demo.weatherapi.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {


    private List<Weather> weather = new ArrayList<>();
}
