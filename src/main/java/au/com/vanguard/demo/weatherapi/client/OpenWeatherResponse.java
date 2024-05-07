package au.com.vanguard.demo.weatherapi.client;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
public class OpenWeatherResponse {

    private List<Weather> weather = new ArrayList<>();
}
