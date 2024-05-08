package au.com.vanguard.demo.weatherapi.api;

import au.com.vanguard.demo.weatherapi.model.WeatherDataRequest;
import au.com.vanguard.demo.weatherapi.service.WeatherDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/1.0")
public class WeatherController {

    private final WeatherDataService weatherDataService;

    public WeatherController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping("/weather/{city}/{country}")
    public WeatherResponse getWeather(@PathVariable(name = "city", required = true) String city,
                                      @PathVariable(name = "country", required = false) String country) {
        var request = new WeatherDataRequest(city, country);
        var weatherData = weatherDataService.getWeatherData(request);
        return new WeatherResponse(weatherData.getDescription());
    }


}
