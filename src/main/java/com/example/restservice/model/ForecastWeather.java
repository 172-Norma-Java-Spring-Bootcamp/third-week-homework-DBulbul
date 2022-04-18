package com.example.restservice.model;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
public class ForecastWeather {


    @Getter
    @Setter
    @AllArgsConstructor
    public class WeatherForecast {
        private Collection<CurrentWeather> weathers;

        public WeatherForecast() {
            weathers = new ArrayList<CurrentWeather>();
        }
    }
}
