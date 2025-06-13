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

    @GetMapping("{username}")
     public ResponseEntity<?> getAllJounalEntriesOfUser(@PathVariable String username){
        User user =  userService.findByUsername(username) ;
       List<JounalEntry> All = user.getJounalEntries();
       if(All!=null&& !All.isEmpty()) {
           return new ResponseEntity<>(All, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PutMapping
    public ResponseEntity<?> updateJounalById(@PathVariable ObjectId id , @RequestBody JounalEntry newEntry){
//        JounalEntry old = jounalEntryService.findJounalEntryById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle(): old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//            jounalEntryService.SaveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK) ;
      //  }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }






}
