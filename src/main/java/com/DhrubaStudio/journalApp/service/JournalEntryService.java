package com.DhrubaStudio.journalApp.service;
import com.DhrubaStudio.journalApp.entity.JournalEntry;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.repository.JournalEntryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public Optional<JournalEntry> findbyId(ObjectId Id){
        return journalEntryRepository.findById(Id);
    }

    @Transactional
    public void saveEntry(JournalEntry entry, String userName){
            try {
                User foundUser = userService.findbyUserName(userName);
                entry.setDate(LocalDateTime.now());
                JournalEntry savedEntry = journalEntryRepository.save(entry);
                foundUser.getJournalEntries().add(savedEntry);
                userService.saveUser(foundUser);
            }catch (Exception e){
                log.error(e.getMessage());
                throw new RuntimeException("Error occurred during saving: ",e);
            }
    }

    @Transactional
    public boolean deletebyId(String userName, ObjectId Id){
        boolean forRemoval = false;
        try{
            User foundUser = userService.findbyUserName(userName);
            forRemoval = foundUser.getJournalEntries().removeIf(x -> x.getId().equals(Id));
            if(forRemoval){
                userService.saveUser(foundUser);
                journalEntryRepository.deleteById(Id);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error occurred during deleting: ",e);
        }
        return forRemoval;
    }
    /*
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public void saveEntry(JournalEntry entry){
            entry.setDate(LocalDateTime.now());
            journalEntryRepository.save(entry);
    }*/

}
