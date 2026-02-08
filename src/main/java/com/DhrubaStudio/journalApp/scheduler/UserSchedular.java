package com.DhrubaStudio.journalApp.scheduler;
import com.DhrubaStudio.journalApp.entity.JournalEntry;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.enums.Sentiment;
import com.DhrubaStudio.journalApp.repository.UserRepositoryImpl;
import com.DhrubaStudio.journalApp.service.EmailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class UserSchedular {

    private final UserRepositoryImpl userRepositoryImpl;
    private final EmailService emailService;

    @Autowired
    public  UserSchedular(UserRepositoryImpl userRepositoryImpl,
                          EmailService emailService) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> users = userRepositoryImpl.getUsersForSentiAnalysis();

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().
                    filter(x -> x.getDate().isAfter(sevenDaysAgo))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(),
                        "Your mood throughout the week",
                        mostFrequentSentiment.toString());
            }
        }
    }

}
