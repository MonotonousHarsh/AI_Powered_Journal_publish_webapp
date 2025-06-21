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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jounal")
public class JounalEntryController{


    @Autowired
    private JounalEntryService jounalEntryService;

   @Autowired
   private UserService userService;

    @PostMapping()
     public ResponseEntity<JounalEntry> CreateEntry(@RequestBody JounalEntry jounalEntry ){
      //  System.out.println("Received payload: " + jounalEntry);

        try {
        //    jounalEntry.setdate(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            jounalEntryService.SaveEntry(jounalEntry,username);
            return new ResponseEntity<>(jounalEntry,HttpStatus.CREATED)                    ;
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
     }



     @GetMapping("/id/{myid}")
     public ResponseEntity<JounalEntry> getJounalEntrybyId(@PathVariable ObjectId myid) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
         User user = userService.findByUsername(username);
         List<JounalEntry> collect = user.getJounalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
         if (!collect.isEmpty()) {
             Optional<JounalEntry> jounalEntry = jounalEntryService.findJounalEntryById(myid);


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
    public ResponseEntity<?> updateJounalById(@PathVariable ObjectId myid , @RequestBody JounalEntry newEntry){
//        JounalEntry old = jounalEntryService.findJounalEntryById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle(): old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//    jounalEntryService.SaveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK) ;
      //  }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JounalEntry> collect = user.getJounalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JounalEntry> optionalOld = jounalEntryService
                    .findJounalEntryById(myid);
            if(optionalOld.isPresent()){
                JounalEntry old = optionalOld.get();
                old.setTitle(newEntry.getTitle() != null && ! newEntry.getTitle().equals(" ") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getTitle() : old.getTitle());
                jounalEntryService.SaveEntry(old , username);
                return new ResponseEntity<>(old  , HttpStatus.OK);
            }
        }





        // Save and return

        return new ResponseEntity<>( HttpStatus.NOT_FOUND) ;
    }

    @GetMapping()
    public ResponseEntity<?> getAllJounalEntriesOfUser( ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user != null && user.getJounalEntries() != null && !user.getJounalEntries().isEmpty()) {
            return new ResponseEntity<>(user.getJounalEntries(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
