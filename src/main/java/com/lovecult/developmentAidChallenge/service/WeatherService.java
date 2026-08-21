package com.lovecult.developmentAidChallenge.service;

import com.lovecult.developmentAidChallenge.api.WeatherApi;
import com.lovecult.developmentAidChallenge.model.AppWeatherResult;
import com.lovecult.developmentAidChallenge.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WeatherService {

    private final WeatherApi weatherApi;
    private int days = 2;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public AppWeatherResult getTomorrowForecast(String city) {
        try {
            Response<WeatherResponse> response = weatherApi
                    .getForecast(apiKey,city,days).execute();

            if (response.isSuccessful() && response.body() != null) {
                return extractTomorrowData(response.body());
            }
            else System.out.println("Error when querying a city" + city + ": " + response.code());

        } catch (IOException e) {
            System.out.println("Network error " + e.getMessage());
        }
        return null;
    }

    public AppWeatherResult extractTomorrowData(WeatherResponse response) {
        WeatherResponse.ForecastDay tomorrow = response.forecast().forecastDay().get(1); // 1 bcs 2 days | 0 - today 1 - tomorrow

        String dominantWindDir = getDominantWindDirection(tomorrow.hour());

        return new AppWeatherResult(
                response.location().name(),
                tomorrow.date(),
                tomorrow.day().minTempC(),
                tomorrow.day().maxTempC(),
                tomorrow.day().avgHumidity(),
                tomorrow.day().maxWindKph(),
                dominantWindDir
        );
    }

    private String getDominantWindDirection(List<WeatherResponse.Hour> hours) {
        Map<String,Integer> frequencyMap = new HashMap<>();

        for (WeatherResponse.Hour hour : hours) {
            frequencyMap.put(
                    hour.windDirection(),
                    frequencyMap.getOrDefault(hour.windDirection(),0) +1);
        }

        String dominantDir = "";
        int maxCount = 0;

        for (Map.Entry<String,Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                dominantDir = entry.getKey();
            }
        }
        return dominantDir;
    }
}
