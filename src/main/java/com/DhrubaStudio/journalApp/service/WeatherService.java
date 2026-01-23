package com.DhrubaStudio.journalApp.service;

import com.DhrubaStudio.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String weatherstackApiKey;

    @Autowired
    public WeatherService(@Value("${api.weatherstack.key}") String weatherstackApiKey) {
        this.weatherstackApiKey = weatherstackApiKey;
    }

    private static final String API_Url ="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeatherByCity(String city){
        String API = API_Url.replace("CITY", city).replace("API_KEY", weatherstackApiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(API, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }


}
