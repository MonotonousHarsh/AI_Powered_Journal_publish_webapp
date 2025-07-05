package net.harshDeveloper.JournalApp.Scheduler;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.cache.AppCache;
import net.harshDeveloper.JournalApp.enums.Sentiment;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import net.harshDeveloper.JournalApp.repository.UserRepositoryImpl;
import net.harshDeveloper.JournalApp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppCache appCache;
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;



    @Scheduled(cron = "0 9 * * SUN")
    public void FetchUsersAndSendSaMail (){
        List<User> users =  userRepositoryImpl.getUserForSA();
        for(User user: users){
            List<JounalEntry> jounalEntries =  user.getJounalEntries();
     List<Sentiment> sentiments = jounalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment !=null){
                    sentimentCounts.put(sentiment , sentimentCounts.getOrDefault(sentiment,0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null  ;
            int maxCount = 0;
        for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount= entry.getValue();
                mostFrequentSentiment= entry.getKey();
            }

        }
        if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(),"sentiments for last 7 days" ,mostFrequentSentiment.toString());
        }
        }
    }

    @Scheduled(cron="0 */5 * ? * *")
    public void ClearAppCache(){
        appCache.init();
    }


}
