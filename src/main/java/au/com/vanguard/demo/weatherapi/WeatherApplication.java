package au.com.vanguard.demo.weatherapi;

import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.client.key.ClientAPIKeyStrategy;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
public class WeatherApplication {

	static final Logger LOG = LoggerFactory.getLogger(WeatherApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

//	@Autowired
//	ClientAPIKeyStrategy clientAPIKeyStrategy;
//	@Autowired OpenWeatherMapClient client;
//
//	@PostConstruct
//	void testHarness() throws Exception {
//
//		var apiKey = clientAPIKeyStrategy.getNext();
//		var response = client.findWeatherData(apiKey, "AUS");
//		LOG.debug("" + response);
//	}

}
