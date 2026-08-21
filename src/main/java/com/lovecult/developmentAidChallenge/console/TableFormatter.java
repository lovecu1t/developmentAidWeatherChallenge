package com.lovecult.developmentAidChallenge.console;

import com.lovecult.developmentAidChallenge.model.AppWeatherResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TableFormatter {
    public void print(List<AppWeatherResult> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("There is no data to display");
            return;
        }

        String format = "| %-12s | %-12s | %-10s | %-10s | %-10s | %-12s | %-9s |%n";
        String separator = "-----------------------------------------------------------";

        System.out.println(separator+separator);
        System.out.printf(format, "City", "Date", "Min Temp", "Max Temp", "Humidity", "Wind Speed", "Wind Direction");
        System.out.println(separator+separator);

        for(AppWeatherResult result : results) {
            System.out.printf(format,
                    result.city(),
                    result.date(),
                    result.minTempC() + " Celsius",
                    result.maxTempC() + " Celsius",
                    result.humidity() + " %",
                    result.windSpeedKph() + " kph",
                    result.windDirection());
        }
        System.out.println(separator+separator);
    }
}
