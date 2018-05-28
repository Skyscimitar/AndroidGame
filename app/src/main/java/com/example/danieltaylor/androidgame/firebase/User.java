package com.example.danieltaylor.androidgame.firebase;

public class User {

    private String id;
    private String email;
    private int singlePlayerScore;
    private int multiPlayerScore;
    private String name;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSinglePlayerScore() {
        return singlePlayerScore;
    }

    public void setSinglePlayerScore(int singlePlayerScore) {
        this.singlePlayerScore = singlePlayerScore;
    }

    public int getMultiPlayerScore() {
        return multiPlayerScore;
    }

    public void setMultiPlayerScore(int multiPlayerScore) {
        this.multiPlayerScore = multiPlayerScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
