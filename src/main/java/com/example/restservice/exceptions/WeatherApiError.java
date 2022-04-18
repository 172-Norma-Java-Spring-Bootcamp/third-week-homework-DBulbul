package com.example.restservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class WeatherApiError {
        public String message;
        public boolean isOk;

        public WeatherApiError(String message, boolean isOk) {
            this.message = message;
            this.isOk = isOk;
        }
    }
