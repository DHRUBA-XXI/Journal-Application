package com.DhrubaStudio.journalApp.repository;
import com.DhrubaStudio.journalApp.entity.ConfigJournalAppEntity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
