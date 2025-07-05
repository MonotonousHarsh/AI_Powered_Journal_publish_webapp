package net.harshDeveloper.JournalApp.controllers;


import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.api.response.WeatherResponse;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import net.harshDeveloper.JournalApp.services.UserService;
import net.harshDeveloper.JournalApp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Chromaticity;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> findAll(){
         return userService .getAll();
    }

   // @PostMapping
                      // maps to POST /users


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);
        if (userInDb != null) {
            // Preserve journal entries
            user.setJounalEntries(userInDb.getJounalEntries());

            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> DeleteUserName (@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
            userRepository.deleteByUsername(username);
          return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<?> weatherGreetings(@PathVariable String city ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {


            WeatherResponse weatherResponse = weatherService.getWeather(city);
            // String greeting= "";
            if (weatherResponse != null) {
                String greeting = "hii"+" " + authentication.getName()+" "+"weather feels like " + weatherResponse.getCurrent().getFeelslike();

                return new ResponseEntity<>(greeting, HttpStatus.OK);
            }


            // log.error("this error comes" + );
            return new ResponseEntity<>("hi" + authentication.getName(), HttpStatus.OK);
        }
        catch(Exception e){
            log.error("this error comes ",e);


        }
        return new ResponseEntity<>("hii " + authentication.getName()  , HttpStatus.INTERNAL_SERVER_ERROR );


    }

}
