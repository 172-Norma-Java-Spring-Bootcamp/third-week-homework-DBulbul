package com.example.restservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeather {

    @NotBlank(message = "Bu kısım boş olamaz")
    private String description;

    @Min(value = -70, message = "-70 dan küçük olamaz")
    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private BigDecimal windSpeed;

    public String toString() {
        return "Weather{" + "Genel Durum: " + description +
                "Sıcaklık: " + temperature +
                "Hissedilen: " + feelsLike +
                "Rüzgar Hızı: " + windSpeed + "}";
    }
}