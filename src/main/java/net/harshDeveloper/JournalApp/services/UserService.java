package net.harshDeveloper.JournalApp.services;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {



    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserRepository userRepository;

    public List<User> getAll(){
         return userRepository.findAll();

    }



    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User saveNewUser(User user) {
        // Ensure bidirectional consistency
        if (user.getJounalEntries() != null) {
            for (JounalEntry entry : user.getJounalEntries()) {
                entry.setUser(user);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    public void  saveUser(User user){

        if(user.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
          //  user.setPassword(passwordEncoder().encode(rawPassword));

        }
        userRepository.save(user);
    }



    public User saveUser_as_Admin(User user) {



        // Ensure bidirectional consistency
        if (user.getJounalEntries() != null) {
            for (JounalEntry entry : user.getJounalEntries()) {
                entry.setUser(user);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER,ADMIN"));
        return userRepository.save(user);

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

}
