package com.example.danieltaylor.androidgame.firebase;

public class UserMarker {
    private String id;
    private String login;
    private String password;
    private String email;
    private int elo;
    private int singlePlayerWins;

    public UserMarker() {}

    public UserMarker(String id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.elo = 1500;
        this.singlePlayerWins = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getSinglePlayerWins() {
        return singlePlayerWins;
    }

    public void setSinglePlayerWins(int wins) {
        this.singlePlayerWins = wins;
    }
}
