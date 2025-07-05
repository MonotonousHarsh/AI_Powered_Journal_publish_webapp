package net.harshDeveloper.JournalApp.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.harshDeveloper.JournalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "jounal_entries")
@Data
@NoArgsConstructor
@Getter
@Setter
public class JounalEntry {


    @org.springframework.data.annotation.Id
    private ObjectId id;

    private String title;

    private String content;


    private Sentiment sentiment;

    @Setter
    private LocalDateTime date;

    public void setdate(LocalDateTime now) {
    }



    @DBRef(lazy = true)
    @JsonIgnore // Prevent infinite recursion in JSON serialization
    private User user;



}



