package com.DhrubaStudio.journalApp.repository;
import com.DhrubaStudio.journalApp.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class UserRepositoryImpl {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getUsersForSentiAnalysis() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentiAnalysis").is(true));
        List<User> Users = mongoTemplate.find(query, User.class);
        return Users;
    }
}
