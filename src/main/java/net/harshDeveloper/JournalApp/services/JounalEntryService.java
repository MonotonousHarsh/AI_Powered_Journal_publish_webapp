package net.harshDeveloper.JournalApp.services;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.JounalEntryRepositopry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JounalEntryService {

    @Autowired
    private  JounalEntryRepositopry jounalEntryRepository ;

    @Autowired
    private UserService userService;




    @Transactional
   public JounalEntry SaveEntry(JounalEntry jounalEntry , String username){
    try {
        User user = userService.findByUsername(username);
        jounalEntry.setdate(LocalDateTime.now());
        JounalEntry saved = jounalEntryRepository.save(jounalEntry);
        user.getJounalEntries().add(saved); //System.out.println("→ [Service] saved with ID: " + saved.getId());
        userService.saveUser(user);
        return saved;
    } catch (Exception e) {

        throw new RuntimeException("An error occur while saving the entry. " , e);
    }
    }


    public List<JounalEntry> getAllEntry(){
        return jounalEntryRepository.findAll();
    }

    public Optional<JounalEntry> findJounalEntryById(ObjectId id){
        return    jounalEntryRepository.findById(id);
    }



    public boolean deleteElemetbyId(ObjectId myid , String username) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJounalEntries().removeIf(x -> x.getId().equals(myid));
            if (removed) {
                userService.saveUser(user);
                jounalEntryRepository.deleteById(myid);
            }

        }catch(Exception e){
            System.out.println(e);
            throw  new RuntimeException("an error occured while detecting the entry" , e);
        }
        return removed;

    }

}


 //   controller call ----> service ----> repository