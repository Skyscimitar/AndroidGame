package com.example.danieltaylor.androidgame.game_elements;

import java.util.UUID;

public class Player {

    public Character character;
    private boolean hasLost = false;
    public boolean increasedDefense = false;
    private String playerID;

    public Player(Character character){
        this.character = character;
        playerID = UUID.randomUUID().toString();
    }

    public boolean hasLost() {
        return hasLost;
    }

    public String getPlayerID() {
        return playerID;
    }


    public void loose() {
        hasLost = true;
    }
}
