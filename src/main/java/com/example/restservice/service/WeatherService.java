package com.example.restservice.service;

import com.example.restservice.model.CurrentWeather;
import com.example.restservice.model.ForecastWeather;
import org.springframework.http.ResponseEntity;

public interface WeatherService {
    public abstract CurrentWeather getCurrentWeather(String city);

    public abstract CurrentWeather getCurrentWeatherWithOtoUrl(String city);
    public abstract CurrentWeather getCurrentWeatherStub(String city);

    public abstract CurrentWeather convertToCurrentWeather(ResponseEntity<String> response);

    public abstract ForecastWeather convertToForecastWeather(ResponseEntity<String> response);

    public abstract ForecastWeather getForecastWeatherWithOtoUrl(String city);
}

