package com.DhrubaStudio.journalApp.controller;
import com.DhrubaStudio.journalApp.entity.JournalEntry;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.JournalEntryService;
import com.DhrubaStudio.journalApp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService,
                                  UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User foundUser = userService.findbyUserName(username);
        List<JournalEntry> entries = foundUser.getJournalEntries();
        if(entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-JournalEntry-by-Id/{EntryId}")
    public ResponseEntity<?> getJournalEntrybyId(@PathVariable ObjectId EntryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User foundUser = userService.findbyUserName(username);
        List<JournalEntry> entries = new ArrayList<>();
        for(JournalEntry x : foundUser.getJournalEntries()){
            if(x.getId().equals(EntryId)){
                entries.add(x);
            }
        }
        if(!entries.isEmpty()){
            Optional<JournalEntry> foundEntry= journalEntryService.findbyId(EntryId);
            if(foundEntry.isPresent()){
                return new ResponseEntity<>(foundEntry.get(), HttpStatus.OK);}
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("Delete-Entry-By-Id/{EntryId}")
    public ResponseEntity<?> deleteJournalEntrybyId(@PathVariable ObjectId EntryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deletebyId(username, EntryId);
        if(removed == true){
            return new ResponseEntity(HttpStatus.OK);
        } else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("Update-Entry-by-Id/{userName}/{EntryId}")
    public ResponseEntity<?> updateJournalEntrybyId(@PathVariable ObjectId EntryId, @RequestBody JournalEntry NewEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User foundUser = userService.findbyUserName(username);
        List<JournalEntry> entry = new ArrayList<>();
        for(JournalEntry x : foundUser.getJournalEntries()){
            if(x.getId().equals(EntryId)){
                entry.add(x);}
        }
        if(!entry.isEmpty()){
            Optional<JournalEntry> oldJournalEntry = journalEntryService.findbyId(EntryId);
            if(oldJournalEntry.isPresent()){
                JournalEntry foundEntry = oldJournalEntry.get();
                foundEntry.setTitle(NewEntry.getTitle() != null && !NewEntry.getTitle().equals("") ? NewEntry.getTitle() : foundEntry.getTitle());
                foundEntry.setContent(NewEntry.getContent() != null && !NewEntry.getContent().equals("") ? NewEntry.getContent() : foundEntry.getContent());
                journalEntryService.saveEntry(foundEntry,username);
                return new ResponseEntity<>(foundEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
