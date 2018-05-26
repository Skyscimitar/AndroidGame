package com.example.danieltaylor.androidgame.SingleGame;

import com.example.danieltaylor.androidgame.GameElements.Player;

import org.json.JSONObject;

import java.util.ArrayList;

public class SinglePlayerGame {

    private boolean running;
    private int turnCounter = 0;
    private boolean isGameOver = false;
    private int currentPlayerTurnPosition = 0;
    private ArrayList<Player> playerList;
    private JSONObject data;

    public SinglePlayerGame(ArrayList<Player> playerList){
        this.playerList = playerList;
    }


    //starts the game, decides which player starts
    public void startGame(){
        this.running = true;

    }


    // user informs the game he has taken his turn
    public boolean takeTurn(Player player, JSONObject data){
        //make sure it's the player updating the data turn
        if (playerList.get(currentPlayerTurnPosition) == player){
            updateGameData(data);
            return true;
        }
        return false;
    }


    //updates the game's data
    private void updateGameData(JSONObject data){
        this.data = data;
        checkGameStatus();
    }


    //increments turn
    public void incrementTurn(){}

    // tells the gameThread if it is the user's turn or not
    public boolean isPlayerTurn(Player player){
        return playerList.get(currentPlayerTurnPosition) == player;
    }

    public boolean isGameRunning() {
        return running && !isGameOver;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private void checkGameStatus(){};


}
