package net.harshDeveloper.JournalApp.controllers;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.services.JounalEntryService;
import net.harshDeveloper.JournalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jounal")
public class JounalEntryController{


    @Autowired
    private JounalEntryService jounalEntryService;

   @Autowired
   private UserService userService;

    @PostMapping("{username}")
     public ResponseEntity<JounalEntry> CreateEntry(@RequestBody JounalEntry jounalEntry , @PathVariable String username){
      //  System.out.println("Received payload: " + jounalEntry);

        try {
        //    jounalEntry.setdate(LocalDateTime.now());
            jounalEntryService.SaveEntry(jounalEntry,username);
            return new ResponseEntity<>(jounalEntry,HttpStatus.CREATED)                    ;
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
     }



     @GetMapping("/id/{myid}")
     public ResponseEntity<JounalEntry> getJounalEntrybyId(@PathVariable ObjectId myid){
        Optional<JounalEntry> jounalEntry =  jounalEntryService.findJounalEntryById(myid);
        if(jounalEntry.isPresent()){
            return new ResponseEntity<>(jounalEntry.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> DeleteJounalEntrybyId ( @PathVariable ObjectId myid){
        jounalEntryService.deleteElemetbyId(myid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJounalById(@PathVariable ObjectId id , @RequestBody JounalEntry newEntry){
//        JounalEntry old = jounalEntryService.findJounalEntryById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle(): old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//    jounalEntryService.SaveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK) ;
      //  }
        Optional<JounalEntry> optionalOld = jounalEntryService
                .findJounalEntryById(id);

        if (!optionalOld.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JounalEntry oldEntry = optionalOld.get();

        // Patch fields
        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
            oldEntry.setTitle(newEntry.getTitle());
        }
        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
            oldEntry.setContent(newEntry.getContent());
        }
        // …and so on for other fields like date, tags, etc.

        // Save and return
        JounalEntry updated = jounalEntryService.SaveEntry(oldEntry,oldEntry.getUser().getUsername());
        return new ResponseEntity<>(updated, HttpStatus.OK) ;
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJounalEntriesOfUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null && user.getJounalEntries() != null && !user.getJounalEntries().isEmpty()) {
            return new ResponseEntity<>(user.getJounalEntries(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
