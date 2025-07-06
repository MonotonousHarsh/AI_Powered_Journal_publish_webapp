package net.harshDeveloper.JournalApp.controllers;

import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.config.SpringSecurity;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import net.harshDeveloper.JournalApp.services.UserService;
import net.harshDeveloper.JournalApp.services.UserDetailServiceImpl;
import net.harshDeveloper.JournalApp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    public UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SpringSecurity springSecurity;


    @PostMapping("/signup")
    public ResponseEntity<User> SignUp(@RequestBody User user) {
        User saved = userService.saveNewUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUsername());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());


            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Exception occured while creatAuthentication token", e);
            return new ResponseEntity<>( "Incorrect username and password",HttpStatus.BAD_REQUEST);

        }
    }
}
