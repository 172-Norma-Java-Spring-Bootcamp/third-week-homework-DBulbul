package com.example.restservice.controller;

import com.example.restservice.model.CurrentWeather;
import com.example.restservice.model.ForecastWeather;
import com.example.restservice.service.WeatherService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

    @RestController
    @RequestMapping(path = "/api/weathers/")
    @Validated
    public class WeatherController {


        private final WeatherService weatherService;

        public WeatherController(WeatherService weatherService) {
            super();
            this.weatherService = weatherService;
        }

        @GetMapping("")
        public CurrentWeather getCurrentWeather(
                @RequestParam @Length(min = 3,message = "city name should be at least 3 characters") String city){
                /*@RequestParam @Length(min = 3,message = "country name should be at least 3 characters") String country)*/
            return weatherService.getCurrentWeather(city);
        }

        @GetMapping("current")
        public CurrentWeather getCurrentWeatherWithOtoUrl(
                @RequestParam @Length(min = 3, message = "must be at least 3 characters") String city){
                /*@RequestParam @Length(min = 3, message = "must be at least 3 characters") String country) */
            return weatherService.getCurrentWeatherWithOtoUrl(city);
        }

        @GetMapping("forecast")
        public ForecastWeather getForecastWeatherWithOtoUrl(
                @RequestParam @Length(min = 3, max = 30, message = "must be at least 3, at most 30 characters") String city)
                /*@RequestParam @Length(min = 3, max = 30, message = "must be at least 3,at most 30 characters") String country)*/ {
            return weatherService.getForecastWeatherWithOtoUrl(city);
        }

        @PutMapping(path = "search/{weatherId}")
        public ResponseEntity<Object> update(@PathVariable @Positive(message = "0 dan küçük olamaz") int weatherId) {
            return ResponseEntity.ok().build();
        }

        @PostMapping(path = "add")
        public ResponseEntity<Object> addWeather(@RequestBody @Valid CurrentWeather weather) {
            return ResponseEntity.ok(weather.getDescription());
        }

        @GetMapping(path = "getWeathers")
        public ResponseEntity<Object> getWeathers(@RequestParam("id") int id, @RequestParam("info") String info) throws Exception {

            throw new NullPointerException();
        }

    }

 /*String key = "fd03d96f0df600554ec5234788a97d98";
    @GetMapping("/weather/current")
    public String current(@RequestParam(value = "location") String location) {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + key;
            ResponseEntity<String> exchange
                    = restTemplate.getForEntity(fooResourceUrl, String.class);
            System.out.println(exchange.getBody());
            return exchange.toString();

        }
    }*/