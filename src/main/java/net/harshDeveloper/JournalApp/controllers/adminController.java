package net.harshDeveloper.JournalApp.controllers;


import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.cache.AppCache;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import net.harshDeveloper.JournalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser(){
      List<User> AllUser =   userService.getAll();

      if(AllUser != null && !AllUser.isEmpty()){
          return new ResponseEntity<>(AllUser, HttpStatus.OK);
      }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.saveNewUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }
    @PostMapping("/create-Admin")
    public ResponseEntity<User> createUser_as_Admin(@RequestBody User user) {
        User saved = userService.saveUser_as_Admin(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

}
