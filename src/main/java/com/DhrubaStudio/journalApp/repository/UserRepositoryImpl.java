package com.DhrubaStudio.journalApp.repository;
import com.DhrubaStudio.journalApp.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getUsersForSentiAnalysis() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysisConsent").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
