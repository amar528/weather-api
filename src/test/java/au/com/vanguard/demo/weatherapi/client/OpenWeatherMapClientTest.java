package au.com.vanguard.demo.weatherapi.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableFeignClients
@ContextConfiguration(classes = {WireMockConfig.class})
class OpenWeatherMapClientTest {

    @Autowired
    private WireMockServer mockOpenWeatherService;

    @Autowired
    private OpenWeatherMapClient underTest;

    static void setupMockOpenWeatherResponse(WireMockServer mockService, String filePath) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/books"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OpenWeatherMapClientTest.class.getClassLoader().getResourceAsStream(filePath),
                                        defaultCharset()))));
    }

    @Test
    void shouldReturnValidWeatherData_givenValidOpenWeatherResponse() throws Exception {

    }

}