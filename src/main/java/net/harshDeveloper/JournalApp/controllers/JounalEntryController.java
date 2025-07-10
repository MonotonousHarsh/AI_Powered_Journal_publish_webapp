package net.harshDeveloper.JournalApp.controllers;

import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.services.JounalEntryService;
import net.harshDeveloper.JournalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/entry")
public class JounalEntryController{


    @Autowired
    private JounalEntryService jounalEntryService;

   @Autowired
   private UserService userService;

    @PostMapping("/create-entry")
    public ResponseEntity<JounalEntry> createEntry(@RequestBody JounalEntry journalEntry) {
        try {
            // Get authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Log for debugging
            System.out.println("Creating entry for user: " + username);

            // Create and save the entry
            JounalEntry savedEntry = jounalEntryService.SaveEntry(journalEntry, username);

            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error creating entry", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



     @GetMapping("/id/{myid}")
     public ResponseEntity<JounalEntry> getJounalEntrybyId(@PathVariable ObjectId userId) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();

         Optional<User> userOptional = userService.findByUsername(username);

         if (userOptional.isEmpty()) {
             throw new RuntimeException("User not found: " + username);
         }

         User user = userOptional.get();
         List<JounalEntry> collect = user.getJounalEntries().stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
         if (!collect.isEmpty()) {
             Optional<JounalEntry> jounalEntry = jounalEntryService.findJounalEntryById(userId);


             if (jounalEntry.isPresent()) {
                 return new ResponseEntity<>(jounalEntry.get(), HttpStatus.OK);
             }
         }
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }


    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> DeleteJounalEntrybyId ( @PathVariable ObjectId myid){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed =    jounalEntryService.deleteElemetbyId(myid,username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateJounalById(@PathVariable ObjectId userId , @RequestBody JounalEntry newEntry){
//        JounalEntry old = jounalEntryService.findJounalEntryById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle(): old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//    jounalEntryService.SaveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK) ;
      //  }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       // Optional<User> user = userService.findByUsername(username);
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }

        User user = userOptional.get();
        List<JounalEntry> collect = user.getJounalEntries().stream().filter(x->x.getUserId().equals(userId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JounalEntry> optionalOld = jounalEntryService
                    .findJounalEntryById(userId);
            if(optionalOld.isPresent()){
                JounalEntry old = optionalOld.get();
                old.setTitle(newEntry.getTitle() != null && ! newEntry.getTitle().equals(" ")
                        ? newEntry.getTitle()
                        : old.getTitle());
                old.setContent(
                        newEntry.getContent() != null && !newEntry.getContent().isBlank()
                                ? newEntry.getContent()
                                : old.getContent()
                );

                jounalEntryService.SaveEntry(old , username);
                return new ResponseEntity<>(old  , HttpStatus.OK);
            }
        }





        // Save and return

        return new ResponseEntity<>( HttpStatus.NOT_FOUND) ;
    }

    @GetMapping("/all-entry")
    public ResponseEntity<List<JounalEntry>> getAllJounalEntries() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("this is Auth" + auth);
            if (auth == null || !auth.isAuthenticated()) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }


            // Extract UserDetails from authentication principal
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = userDetails.getUsername();

          //  String username = auth.getName();
            System.out.println("Fetching entries for: " + username); // Log username

            List<JounalEntry> entries = jounalEntryService.findEntriesByUsername(username);
            System.out.println("Found entries: " + entries.size()); // Log count

            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            e.printStackTrace(); // This will show in your server console
            return ResponseEntity.status(500).build();
        }
    }

}






