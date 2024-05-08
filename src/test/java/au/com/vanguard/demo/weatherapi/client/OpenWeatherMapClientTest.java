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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    static void setupMockOpenWeatherResponse(WireMockServer mockService, HttpStatus status, String filePath, String appId, String queryParams) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlPathTemplate("/data/2.5/weather")).withQueryParam("appid", havingExactly(equalTo(appId))).withQueryParam("q", havingExactly(containing(queryParams))).willReturn(WireMock.aResponse().withStatus(status.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody(copyToString(OpenWeatherMapClientTest.class.getClassLoader().getResourceAsStream(filePath), defaultCharset()))));
    }

    @Test
    void givenValidOpenWeatherResponse_shouldReturnValidWeatherData() throws Exception {

        // given valid request and valid response and body (200 OK with valid json body)
        var appId = "abcde01234";
        var queryParams = "Melbourne, AUS";

        setupMockOpenWeatherResponse(mockOpenWeatherService, HttpStatus.OK, "open-weather-response-1.json", appId, queryParams);

        // when
        OpenWeatherResponse openWeatherResponse = underTest.findWeatherData(appId, queryParams);

        // then
        assertNotNull(openWeatherResponse);
        assertNotNull(openWeatherResponse.getWeather());
        assertEquals(1, openWeatherResponse.getWeather().size());
        var weatherResponse = openWeatherResponse.getWeather().get(0);
        assertEquals("300", weatherResponse.getId());
        assertEquals("Drizzle", weatherResponse.getMain());
        assertEquals("light intensity drizzle", weatherResponse.getDescription());

        mockOpenWeatherService.verify(exactly(1),
                getRequestedFor(urlPathMatching("/data/2.5/weather/*"))
                        .withQueryParam("appid", equalTo(appId))
                        .withQueryParam("q", havingExactly("Melbourne, AUS")));

    }

}