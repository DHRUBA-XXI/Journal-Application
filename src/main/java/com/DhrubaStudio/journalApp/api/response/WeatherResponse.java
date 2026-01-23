package com.DhrubaStudio.journalApp.api.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {

    @JsonProperty("current")
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public class Current {

        @JsonProperty("temperature")
        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions = new ArrayList<>();

        @JsonProperty("feelslike")
        private int feelslike;

        @JsonProperty("is_day")
        private String isDay;

        public List<String> getWeatherDescriptions() {
            return weatherDescriptions;
        }

        public void setWeatherDescriptions(List<String> weatherDescriptions) {
            this.weatherDescriptions = weatherDescriptions;
        }

        public String getIsDay() {
            return isDay;
        }

        public void setIsDay(String isDay) {
            this.isDay = isDay;
        }

        public int getFeelslike() {
            return feelslike;
        }

        public void setFeelslike(int feelslike) {
            this.feelslike = feelslike;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }
    }
}
