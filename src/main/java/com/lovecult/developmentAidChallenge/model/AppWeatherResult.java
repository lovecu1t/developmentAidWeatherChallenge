package com.lovecult.developmentAidChallenge.model;

import java.time.LocalDate;

public record AppWeatherResult(
        String city,
        LocalDate date,
        double minTempC,
        double maxTempC,
        double humidity,
        double windSpeedKph,
        String windDirection
) {}

