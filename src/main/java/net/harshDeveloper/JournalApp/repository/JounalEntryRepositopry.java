package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JounalEntryRepositopry extends MongoRepository <JounalEntry, ObjectId > {
   // List<JounalEntry> findAllByUserUsername(String username);
 List<JounalEntry> findByUserId(ObjectId userId);

    // Corrected method using nested path
 //  List<JounalEntry> findByUser_UserId(ObjectId userId);



}
