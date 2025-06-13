package net.harshDeveloper.JournalApp.services;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.JounalEntryRepositopry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JounalEntryService {

    @Autowired
    private  JounalEntryRepositopry jounalEntryRepository ;

    @Autowired
    private UserService userService;




    public  void SaveEntry(JounalEntry jounalEntry , String username){

        User user =  userService.findByUsername(username) ;
        jounalEntry.setdate(LocalDateTime.now());
        JounalEntry saved = jounalEntryRepository.save(jounalEntry);
        user.getJounalEntries().add(saved); //System.out.println("→ [Service] saved with ID: " + saved.getId());
       userService.saveUser(user);
    }


    public List<JounalEntry> getAllEntry(){
        return jounalEntryRepository.findAll();
    }

    public Optional<JounalEntry> findJounalEntryById(ObjectId id){
       return    jounalEntryRepository.findById(id);
    }


    public JounalEntry deleteElemetbyId(ObjectId myid){
        jounalEntryRepository.deleteById(myid);
        return  null;
    }

}


 //   controller call ----> service ----> repository