package com.DhrubaStudio.journalApp.repository;
import com.DhrubaStudio.journalApp.entity.JournalEntry;
import com.DhrubaStudio.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
