package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username);
  //  User findByUsername (String username);
    void deleteByUsername(String username);
}
