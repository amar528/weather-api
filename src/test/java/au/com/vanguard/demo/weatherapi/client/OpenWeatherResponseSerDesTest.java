package au.com.vanguard.demo.weatherapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherResponseSerDesTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        var underTest = new OpenWeatherResponse();


        var weather = new Weather();
        weather.setId("1");
        weather.setIcon("09D");
        weather.setMain("Drizzle");
        weather.setDescription("Light intensity drizzle");

        var json = mapper.writeValueAsString(underTest);
        assertNotNull(json);
    }

}