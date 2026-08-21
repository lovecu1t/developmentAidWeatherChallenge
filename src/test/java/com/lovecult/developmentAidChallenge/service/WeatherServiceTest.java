package com.lovecult.developmentAidChallenge.service;

import com.lovecult.developmentAidChallenge.api.WeatherApi;
import com.lovecult.developmentAidChallenge.model.AppWeatherResult;
import com.lovecult.developmentAidChallenge.model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherApi weatherApi;

    @Mock
    private Call<WeatherResponse> mockCall;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(weatherApi);
        ReflectionTestUtils.setField(weatherService,"apiKey", "someKey");
    }

    @Test
    void getTomorrowForecast_ShouldReturnCorrectData_AndCalculateWindDirection() throws IOException {
        List<WeatherResponse.Hour> hours = List.of(
                new WeatherResponse.Hour("NW"),
                new WeatherResponse.Hour("NW"),
                new WeatherResponse.Hour("S")
        );

        WeatherResponse.Day day = new WeatherResponse
                .Day(10.0, 20.0, 50.0, 15.0);
        WeatherResponse.ForecastDay today = new WeatherResponse
                .ForecastDay(LocalDate.now(), day, hours);
        WeatherResponse.ForecastDay tomorrow = new WeatherResponse
                .ForecastDay(LocalDate.now().plusDays(1), day, hours);

        WeatherResponse.Forecast forecast = new WeatherResponse
                .Forecast(List.of(today, tomorrow));
        WeatherResponse.Location location = new WeatherResponse
                .Location("Madrid");
        WeatherResponse fakeResponse = new WeatherResponse(location, forecast);

        when(weatherApi.getForecast(anyString(), anyString(), anyInt())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(Response.success(fakeResponse));

        AppWeatherResult result = weatherService.getTomorrowForecast("Madrid");

        assertNotNull(result);
        assertEquals("Madrid", result.city());
        assertEquals(10.0, result.minTempC());
        assertEquals("NW", result.windDirection(), "The wind direction should be NW");
    }

    @Test
    void getTomorrowForecast_ShouldReturnNull_WhenApiFails() throws IOException {
        when(weatherApi.getForecast(anyString(), anyString(), anyInt())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(Response.error(404, okhttp3.ResponseBody.create(null, "")));

        AppWeatherResult result = weatherService.getTomorrowForecast("UnknownCity");

        assertNull(result, "The service should return null");
    }
}
