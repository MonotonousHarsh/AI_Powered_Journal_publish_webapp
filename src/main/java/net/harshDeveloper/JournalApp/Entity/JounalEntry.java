package net.harshDeveloper.JournalApp.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.harshDeveloper.JournalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@Getter
@Setter
@Component
public class JounalEntry {


    @Id
    private ObjectId id;

    private String title;

    private String content;

    private ObjectId userId; // Stores user's ID (ObjectId)


    private Sentiment sentiment;

    // In JounalEntry.java

    // Add a proper getter
    // Fix the date field
    @Getter
    private LocalDateTime date;

    // Fix the setter method
    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    @DBRef(lazy = true)
    @JsonIgnore // Prevent infinite recursion in JSON serialization
    private User user;


    // FIXED toString() - Only use user ID instead of full object
    @Override
    public String toString() {
        return "JournalEntry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + (user != null ? user.getId() : null) + '\'' + // Only ID!
                '}';
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }



}



