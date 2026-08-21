# Weather Forecast Console Application
A Spring Boot console application that retrieves weather forecasts via WeatherAPI and outputs a formatted table to the terminal. 
This project was developed as part of a test assignment.

## Functionality
* Get tomorrow's weather forecast for a list of cities (Chisinau, Madrid, Kyiv, Amsterdam).
* Automatic calculation of the most common wind direction for the day.
* Formatted data output (min/max temperature, humidity, wind speed, and prevailing wind direction) as an ASCII table.
* Use of Retrofit 2 for type-safe interaction with the REST API.
* Coverage of business logic with unit tests using JUnit 5 and Mockito.

## Technologies
* **Java 21**
* **Spring Boot**
* **Retrofit 2** (HTTP Client)
* **Jackson** (JSON Parsing)
* **JUnit 5 & Mockito** (Unit Testing)
* **Gradle** (Build Tool)

## Requirements
1. Java 21 installed.
2. An active API key from [WeatherAPI.com](https://www.weatherapi.com/).

## Launch instructions
1. **Clone the repository:**
```bash
git clone <https://github.com/lovecu1t/developmentAidWeatherChallenge>
cd developmentAidWeatherChallenge
```
2. **Specify your API key:**
Open the src/main/resources/application.properties file and insert your key:

``` 
weather.api.key=YOUR_REAL_API_KEY
```

3. **Launch the application:**
``` bash
./gradlew bootRun
```

4. **Run the tests:**
```bash
./gradlew test
```

## Project structure
* api/ — definition of REST interfaces for Retrofit
* config/ — Spring configuration beans (Retrofit, ObjectMapper)
* model/ — data structures (Java Records) for JSON mapping
* service/ — business logic for weather aggregation and wind direction calculation
* console/ — console table rendering logic
