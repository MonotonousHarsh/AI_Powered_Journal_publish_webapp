package net.harshDeveloper.JournalApp.Entity;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    @DBRef
    private List<JounalEntry> jounalEntries = new ArrayList<>();

    private List<String> roles;


    // Add helper methods for bidirectional relationship
    public void addJournalEntry(JounalEntry entry) {
        jounalEntries.add(entry);
        entry.setUser(this);
    }

    public void removeJournalEntry(JounalEntry entry) {
        jounalEntries.remove(entry);
        entry.setUser(null);
    }
}
