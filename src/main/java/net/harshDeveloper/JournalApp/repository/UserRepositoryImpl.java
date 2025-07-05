package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
   private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){

        Query query = new Query();
        query.addCriteria(Criteria.where("Email").regex("/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$/."));
      //  query.addCriteria(Criteria.where("Email").exists(true));
 //       query.addCriteria((Criteria.where("username").is("vipul")));
        query.addCriteria(Criteria.where("sentimentsAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
