package com.DhrubaStudio.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class QuotesResponse {

    @JsonProperty("quote")
    private String quote;

    @JsonProperty("author")
    private String author;

    @JsonProperty("work")
    private String work;

    @JsonProperty("categories")
    private List<String> categories = new ArrayList<>();


    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getWork() {
        return work;
    }

    public List<String> getCategories() {
        return categories;
    }

    // Setters (optional – needed only if you manually create objects)
    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ", work='" + work + '\'' +
                ", categories=" + categories +
                '}';
    }
}