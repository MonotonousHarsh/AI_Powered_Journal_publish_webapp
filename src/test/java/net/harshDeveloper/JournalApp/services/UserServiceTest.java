package net.harshDeveloper.JournalApp.services;

import net.harshDeveloper.JournalApp.Entity.User;
import net.harshDeveloper.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserRepository userRepository;

    @Test
 //   @Disabled // jb mein koe or test run karunga tb ye test nhi chalega;
    public void  testAdd(){
        assertEquals(4 , 2+2);
       assertNotNull(userRepository.findByUsername("Anjali verma"));
    }

    @ParameterizedTest
    @CsvSource({
            "Acne Vulgaris,Clogged pores, redness, swelling, raised bumps, pus-filled lesions, scarring, depending on type of lesion present.",
            "Non-inflammatory acne,Mild redness and irritation may accompany them, but major inflammation and pus-filled lesions are typically absent.",
            "Inflammatory acne,Swelling, inflammation, red/pink/purple raised bumps."

    })
    public void test( String Disease_name, String Symptoms )
    {
            assertEquals(Disease_name , Symptoms);
    }



}
