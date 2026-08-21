package com.lovecult.developmentAidChallenge;

import com.lovecult.developmentAidChallenge.console.TableFormatter;
import com.lovecult.developmentAidChallenge.model.AppWeatherResult;
import com.lovecult.developmentAidChallenge.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WeatherApplication implements CommandLineRunner {

    private final WeatherService weatherService;
    private final TableFormatter tableFormatter;

    @Autowired
    public WeatherApplication(WeatherService weatherService, TableFormatter tableFormatter) {
        this.weatherService = weatherService;
        this.tableFormatter = tableFormatter;
    }

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class,args);
    }

    @Override
    public void run(String... args) {
        List<String> cities = List.of("Chisinau", "Madrid", "Kyiv", "Amsterdam");
        List<AppWeatherResult> finalResults = new ArrayList<>();

        for (String city : cities) {
            AppWeatherResult result = weatherService.getTomorrowForecast(city);
            if (result != null) finalResults.add(result);
        }

        System.out.println();
        tableFormatter.print(finalResults);
        System.exit(0);
    }
}