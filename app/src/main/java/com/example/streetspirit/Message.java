package com.example.streetspirit;

import com.google.firebase.database.Query;

import java.util.Date;

public class Message {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String id, time, text, email;

    public Message() {
    }

    public Message(String id, String time, String text, String email) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.email = email;
    }
}
