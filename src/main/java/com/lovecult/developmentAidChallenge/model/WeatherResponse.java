package com.lovecult.developmentAidChallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(Location location,Forecast forecast) {

    public record Location(String name) {}

    public record Forecast(
            @JsonProperty("forecastday") List<ForecastDay> forecastDay) {
    }

    public record ForecastDay(LocalDate date, Day day, List<Hour> hour) {
    }

    public record Day(
            @JsonProperty("mintemp_c") double minTempC,
            @JsonProperty("maxtemp_c") double maxTempC,
            @JsonProperty("avghumidity") double avgHumidity,
            @JsonProperty("maxwind_kph") double maxWindKph
    ) {}

    public record Hour(@JsonProperty("wind_dir") String windDirection) {}
}
