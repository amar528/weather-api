package au.com.vanguard.demo.weatherapi.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "openWeatherClient", url = "${client.open-weather.url}")
public interface OpenWeatherMapClient {
}
