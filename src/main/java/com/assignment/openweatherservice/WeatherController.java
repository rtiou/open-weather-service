package com.assignment.openweatherservice;

import com.assignment.openweatherservice.weatherstack.WeatherStackResponse;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    public String URL = "http://api.weatherstack.com/current?access_key=f3c5eb6c866588cb74270f8dd&query=$CITY";

    @Autowired
    public WeatherRepository weatherRepository;

    @GetMapping("/api/getWeather/{country}")
    public String getWeather(@PathParam("country") String country) {
        RestTemplate restTemplate = new RestTemplate();

        String url = StringUtils.replace(URL, "$CITY", country);
        WeatherStackResponse response = restTemplate.getForObject(url, WeatherStackResponse.class);

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName(country);
        weatherEntity.setTemperature(response.getCurrent().getTemperature());
        weatherRepository.save(weatherEntity);

        return response.getCurrent().getTemperature();

    }
}
