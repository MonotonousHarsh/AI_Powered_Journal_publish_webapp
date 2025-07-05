package net.harshDeveloper.JournalApp.repository;

import net.harshDeveloper.JournalApp.Entity.configJounalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConfigJounalAppRepository extends MongoRepository<configJounalAppEntity, ObjectId> {





}
