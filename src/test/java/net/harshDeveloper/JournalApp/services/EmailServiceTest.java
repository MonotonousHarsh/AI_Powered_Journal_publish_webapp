package net.harshDeveloper.JournalApp.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void emailServiceTest(){
        emailService.sendEmail("monotonousharsh@gmail.com","Message for Saving Journal Entries"
               , "Hii MonotonousHarsh your application is ready to go we  are saving your all journal entries that you have done till now.  Thank YOU");
    }
}
