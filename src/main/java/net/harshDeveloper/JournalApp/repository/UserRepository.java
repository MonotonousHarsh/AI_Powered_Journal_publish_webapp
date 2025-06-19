package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername (String username);
    void DeleteByUserName(String username);
}
