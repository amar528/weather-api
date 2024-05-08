package au.com.vanguard.demo.weatherapi.api;

import au.com.vanguard.demo.weatherapi.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WeatherController.class)
@ExtendWith(SpringExtension.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherDataService mockWeatherDataService;

    @Test
    void shouldGetWeather_happyPath() throws Exception {

    }
}