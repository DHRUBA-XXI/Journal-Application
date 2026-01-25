package com.DhrubaStudio.journalApp.cache;

import com.DhrubaStudio.journalApp.entity.ConfigJournalAppEntity;
import com.DhrubaStudio.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> apiData;

    @PostConstruct
    public void ApiInilization(){
        apiData =  new HashMap<>();
        List<ConfigJournalAppEntity> apis = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity api : apis){
            apiData.put(api.getApi_name(),api.getApi_url());
        }
    }
}
