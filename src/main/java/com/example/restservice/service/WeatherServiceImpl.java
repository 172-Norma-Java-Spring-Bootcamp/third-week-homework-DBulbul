package com.example.restservice.service;

import com.example.restservice.model.CurrentWeather;
import com.example.restservice.model.ForecastWeather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=metric";
    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q={city}&APPID={key}&units=metric";
    private static final String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=%s,%s&APPID=%s&units=metric";

    private final String apiKey = "fd03d96f0df600554ec5234788a97d98";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherServiceImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public CurrentWeather getCurrentWeatherStub(String city) {
        return new CurrentWeather("Clear", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
    }
@Override
    public CurrentWeather getCurrentWeather(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //String strUri = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + apiKey + "&units=imperial";
        String strUri = String.format(baseUrl, city,apiKey);

        ResponseEntity<String> res = restTemplate.exchange(strUri, HttpMethod.GET, entity, String.class);
          return convertToCurrentWeather(res);
    }



    public CurrentWeather getCurrentWeatherWithOtoUrl(String city){
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertToCurrentWeather(response);
    }

    public ForecastWeather getForecastWeatherWithOtoUrl(String city){
        URI url = new UriTemplate(FORECAST_URL).expand(city, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertToForecastWeather(response);
    }

    public CurrentWeather convertToCurrentWeather(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new CurrentWeather(root.path("weather").get(0).path("main").asText(),
                    BigDecimal.valueOf(root.path("main").path("temp").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("feels_like").asDouble()),
                    BigDecimal.valueOf(root.path("wind").path("speed").asDouble()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    public ForecastWeather convertToForecastWeather(ResponseEntity<String> response) {
        ForecastWeather forecast = new ForecastWeather();
        List<CurrentWeather> currentWeathers = new ArrayList<CurrentWeather>();

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            for(int i = 0; i < 30; i++) {
                CurrentWeather weather = new CurrentWeather();
                String description = root.path("list").get(i).path("weather").get(0).path("main").asText();
                BigDecimal temp = BigDecimal.valueOf(root.path("list").get(i).path("main").path("temp").asDouble());
                BigDecimal feelsLike = BigDecimal.valueOf(root.path("list").get(i).path("main").path("feels_like").asDouble());
                BigDecimal windSpeed = BigDecimal.valueOf(root.path("list").get(i).path("wind").path("speed").asDouble());
                weather.setDescription(description);
                weather.setTemperature(temp);
                weather.setFeelsLike(feelsLike);
                weather.setWindSpeed(windSpeed);
                currentWeathers.add(weather);
            }

            /*forecast.setWeathers(currentWeathers);*/
            return forecast;
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}

