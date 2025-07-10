package net.harshDeveloper.JournalApp.controllers;

import lombok.extern.slf4j.Slf4j;
import net.harshDeveloper.JournalApp.Dto.UsersDto;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsersDto usersDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usersDto.getUsername(),
                            usersDto.getPassword()
                    )
            );
            System.out.println("username come from frontend" + " "  + usersDto.getUsername()+
                    "" + "password come from frontend" + usersDto.getPassword());
            UserDetails userDetails = userDetailServiceImpl
                    .loadUserByUsername(usersDto.getUsername());
            System.out.println("printing " + userDetails);
            String jwt = jwtUtils.generateToken(userDetails.getUsername());

            System.out.println("printing JWT APNA WALA"+ "" + jwt);

            Map<String, Object> payload = Map.of(
                    "token", jwt,
                    "userDetails", userDetails
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(payload);

        } catch (BadCredentialsException ex) {
            log.warn("Invalid login attempt for user {}", usersDto.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }
}
