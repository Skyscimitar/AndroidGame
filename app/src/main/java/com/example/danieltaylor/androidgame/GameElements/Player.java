package com.example.danieltaylor.androidgame.GameElements;

public class Player {

    public Character character;
    private boolean hasLost = false;

    public Player(Character character){
        this.character = character;
    }

    public boolean hasLost() {
        return hasLost;
    }


    public void loose() {
        hasLost = true;
    }
}
