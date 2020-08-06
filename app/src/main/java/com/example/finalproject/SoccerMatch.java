package com.example.finalproject;

public class SoccerMatch {

    String title, date, URL;
    long id = -1;

    public SoccerMatch() {}

    public SoccerMatch (long id, String title, String date, String URL) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
