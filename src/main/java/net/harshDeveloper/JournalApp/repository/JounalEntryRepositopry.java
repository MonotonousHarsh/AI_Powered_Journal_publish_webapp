package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.JounalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JounalEntryRepositopry extends MongoRepository <JounalEntry, ObjectId > {


}
