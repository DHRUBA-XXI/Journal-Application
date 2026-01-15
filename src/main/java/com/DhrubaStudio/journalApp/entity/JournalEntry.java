package com.DhrubaStudio.journalApp.entity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private LocalDateTime date;

    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
