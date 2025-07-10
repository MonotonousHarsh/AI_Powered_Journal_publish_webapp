package net.harshDeveloper.JournalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.JounalEntryRepositopry;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Component
@Slf4j
public class JounalEntryService {

    @Autowired
    private  JounalEntryRepositopry jounalEntryRepository ;

    @Autowired
    private JounalEntry jounalEntry;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;





//
public JounalEntry SaveEntry(JounalEntry journalEntry, String username) {
    // 1. Find the user
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    // 2. Set critical fields
    journalEntry.setDate(LocalDateTime.now());
    journalEntry.setUserId(user.getId());  // Set direct user ID reference
    journalEntry.setUser(user);            // Set DBRef if needed

    // 3. Save the entry
    JounalEntry savedEntry = jounalEntryRepository.save(journalEntry);

    // 4. Update user's entries (optional but maintains bidirectional relationship)
    user.addJournalEntry(savedEntry);
    userRepository.save(user);

    // 5. Log for debugging
    System.out.println("Saved entry with ID: " + savedEntry.getId() +
            " for user ID: " + user.getId());

    return savedEntry;
}

//
//@Transactional
//public JounalEntry saveEntry(JounalEntry journalEntry, String username) {
//    try {
//        Optional<User> userOptional = userService.findByUsername(username);
//
//        if (userOptional.isEmpty()) {
//            throw new RuntimeException("User not found: " + username);
//        }
//
//        User user = userOptional.get();
//        jounalEntry.setdate(LocalDateTime.now());
//
//        // Set the user reference (important for bidirectional relationship)
//        journalEntry.setUser(user);
//
//        // Save journal entry first to generate ID
//        JounalEntry saved = jounalEntryRepository.save(journalEntry);
//
//        // Now add to user's journalEntries list
//        user.addJournalEntry(saved);
//
//        // Save updated user
//        userService.saveUser(user);
//
//        return saved;
//    } catch (Exception e) {
//        log.error("Error occurred for user: {}", username, e);
//        throw new RuntimeException("An error occurred while saving the entry.", e);
//    }
//}


    public List<JounalEntry> getAllEntry(){
        return jounalEntryRepository.findAll();
    }

    public Optional<JounalEntry> findJounalEntryById(ObjectId userId){
        return    jounalEntryRepository.findById(userId);
    }


    public List<JounalEntry> findEntriesByUsername(String username) {
//        User user = userService.findByUsername(username);
//        if (user == null) {
//            return Collections.emptyList();
//        }
        // Find user by username
        User user = userRepository.findByUsername(username)

                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("user is :::" + user);
        System.out.println("checking userid   "  + user.getId());
       List<JounalEntry> entries =  jounalEntryRepository.findByUserId(user.getId());
        System.out.println("meri Entry" + entries);
        return entries;
    }


    public boolean deleteElemetbyId(ObjectId userId , String username) {
        boolean removed = false;
        try {

            Optional<User> userOptional = userService.findByUsername(username);

            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found: " + username);
            }

            User user = userOptional.get();
           // Optional<User> user = userService.findByUsername(username);
            removed = user.getJounalEntries().removeIf(x -> x.getUserId().equals(userId));
            if (removed) {
                userService.saveUser(user);
                jounalEntryRepository.deleteById(userId);
            }

        }catch(Exception e){
            System.out.println(e);
            throw  new RuntimeException("an error occured while detecting the entry" , e);
        }
        return removed;

    }

}


 //   controller call ----> service ----> repository