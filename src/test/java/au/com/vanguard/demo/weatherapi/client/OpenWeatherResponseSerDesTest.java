package au.com.vanguard.demo.weatherapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.InputStream;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherResponseSerDesTest {

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void shouldSerialize() throws JsonProcessingException, JSONException {
        var underTest = new OpenWeatherResponse();


        var weather = new Weather();
        underTest.getWeather().add(weather);
        weather.setId("1");
        weather.setIcon("09D");
        weather.setMain("Drizzle");
        weather.setDescription("Light intensity drizzle");

        var json = mapper.writeValueAsString(underTest);
        assertNotNull(json);
    }

    @Test
    void shouldDeserialize() throws Exception {

        var result = mapper.readValue(Paths.get("target/test-classes/open-weather-valid-response.json").toFile(), OpenWeatherResponse.class);
        assertNotNull(result);
    }

}