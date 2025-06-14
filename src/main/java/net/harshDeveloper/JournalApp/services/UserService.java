package net.harshDeveloper.JournalApp.services;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> getAll(){
         return userRepository.findAll();

    }


    public  User findByUsername( String username){
        return userRepository.findByUsername(username);
    }
    public User saveUser(User user) {
        // Ensure bidirectional consistency
        if (user.getJounalEntries() != null) {
            for (JounalEntry entry : user.getJounalEntries()) {
                entry.setUser(user);
            }
        }
        return userRepository.save(user);
    }



}
