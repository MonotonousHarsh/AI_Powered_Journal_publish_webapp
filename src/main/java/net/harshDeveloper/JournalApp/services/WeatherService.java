package net.harshDeveloper.JournalApp.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.api.response.WeatherResponse;
import net.harshDeveloper.JournalApp.cache.AppCache;
import net.harshDeveloper.JournalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherService {

    @Autowired
   private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private  String apiKey ;

    @Autowired
    private RedisService redisService;



   @Autowired
   private AppCache appCache;



    public WeatherResponse getWeather (String city) {
        WeatherResponse weatherResponse = redisService.get("weather of"+city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }
        else {

            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, "apiKey");
            log.debug("Calling weather API URL: {}", finalAPI);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body !=null ){
                redisService.set("weather of " + city , body,300l);
            }
            return body;
        }

    }


}
