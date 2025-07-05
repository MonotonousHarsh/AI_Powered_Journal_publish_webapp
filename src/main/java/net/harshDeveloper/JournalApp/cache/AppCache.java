package net.harshDeveloper.JournalApp.cache;

import jakarta.annotation.PostConstruct;
import net.harshDeveloper.JournalApp.Entity.configJounalAppEntity;
import net.harshDeveloper.JournalApp.repository.ConfigJounalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJounalAppRepository configJounalAppRepository;

    public Map<String,String> appCache ;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
       List<configJounalAppEntity> all = configJounalAppRepository.findAll();
       for(configJounalAppEntity configJounalAppEntity:all){
           appCache.put(configJounalAppEntity.getKey(),configJounalAppEntity.getValue());
       }
    }
}
