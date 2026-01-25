package com.DhrubaStudio.journalApp.service;
import com.DhrubaStudio.journalApp.api.response.WeatherResponse;
import com.DhrubaStudio.journalApp.cache.AppCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String weatherstackApiKey;
    private final AppCache appCache;
    private final RestTemplate restTemplate;
    private final String API_URL;

    @Autowired
    public WeatherService(@Value("${api.weatherstack.key}") String weatherstackApiKey,
                          AppCache appCache,
                          RestTemplate restTemplate) {
        this.weatherstackApiKey = weatherstackApiKey;
        this.appCache = appCache;
        this.restTemplate = restTemplate;
        this.API_URL = appCache.apiData.get("WeatherStack_API");
    }


    public WeatherResponse getWeatherByCity(String city){
        String API = API_URL.replace("<CITY>", city).replace("<API_KEY>", weatherstackApiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(API, HttpMethod.GET,null, WeatherResponse.class);
        return response.getBody();
    }


}
