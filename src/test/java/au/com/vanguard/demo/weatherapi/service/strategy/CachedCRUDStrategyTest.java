package au.com.vanguard.demo.weatherapi.service.strategy;

import au.com.vanguard.demo.weatherapi.client.OpenWeatherMapClient;
import au.com.vanguard.demo.weatherapi.client.key.ClientAPIKeyStrategy;
import au.com.vanguard.demo.weatherapi.model.WeatherDataBuilder;
import au.com.vanguard.demo.weatherapi.model.WeatherDataRequest;
import au.com.vanguard.demo.weatherapi.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CachedCRUDStrategyTest {

    private CachedCRUDStrategy underTest;

    private int cacheSeconds = 600;

    @Mock
    private WeatherDataRepository mockWeatherDataRepository;

    @Mock
    private ClientAPIKeyStrategy mockClientAPIKeyStrategy;

    @Mock
    private OpenWeatherMapClient mockOpenWeatherClient;

    @BeforeEach
    void beforeEach() {
        underTest = new CachedCRUDStrategy(cacheSeconds, mockWeatherDataRepository, mockClientAPIKeyStrategy, mockOpenWeatherClient);
    }

    @Test
    void given_persistedDataIsWithinTTL_when_getWeatherData_then_theDataIsReturned() {

        // given
        var city = "Melbourne";
        var country = "AUS";
        var request = new WeatherDataRequest(city, country);
        var persistedData = new WeatherDataBuilder().city(city).country(country).build();
        persistedData.setCreatedDate(Instant.now().minusSeconds(200)); // within cache range
        given(mockWeatherDataRepository.findByCityAndCountry(city, country)).willReturn(Optional.of(persistedData));

        // when
        var result = underTest.getWeatherData(request);

        // then
        assertNotNull(result);
        assertEquals(persistedData, result);
        verify(mockWeatherDataRepository).findByCityAndCountry(city, country);
        verifyNoInteractions(mockOpenWeatherClient);
    }

}