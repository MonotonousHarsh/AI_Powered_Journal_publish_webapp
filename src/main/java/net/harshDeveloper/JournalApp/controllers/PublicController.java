package net.harshDeveloper.JournalApp.controllers;

import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    public UserService userService;
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }
}
