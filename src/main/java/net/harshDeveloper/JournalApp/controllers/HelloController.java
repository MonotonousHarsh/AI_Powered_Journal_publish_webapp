package net.harshDeveloper.JournalApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @GetMapping("/hello")
    public  String Hi (){
        return "Heloo Postman Whatuppp!!!";

    }
}
