package com.example.danieltaylor.androidgame.SingleGame;

import android.content.Context;

import com.example.danieltaylor.androidgame.GameElements.Player;

import org.json.JSONObject;

public class SinglePlayerGameThread implements Runnable {

    private Player aiPlayer;
    private Player userPlayer;
    private boolean isRunning;
    private JSONObject gameData;
    private SinglePlayerGame game;
    private Thread gameThread;
    private boolean userPlayerActed;
    private boolean aiPlayerActed;

    public SinglePlayerGameThread(){
        super();
    }


    public void run(){
        while (isRunning) {
            update();
            draw();
            control();
        }
    }

    //allows player turns to be taken
    private void update(){

        if (game.isGameOver()) {
            onPause();
            endGame(game.getWinner());
        }
        // if the player inputted an action and it is his turn, update the gameData;
        if (userPlayerActed && game.isPlayerTurn(userPlayer)) {
            game.takeTurn(userPlayer, gameData);
        }

        //if the aiplayer inputted an action and it is his turn, update the gameData
        if (aiPlayerActed && game.isPlayerTurn(aiPlayer)) {
            game.takeTurn(aiPlayer, gameData);
        }
    }

    //updates the game view
    private void draw(){}

    //registers player controls
    private void control(){}


    public void onPause(){
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO handle interruption exception correctly
        }
    }


    private void endGame(Player winner){}

    public void onResume() {
        isRunning = game.isGameRunning();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void onStart() {
        game.startGame();
        gameThread = new Thread(this);
        gameThread.start();
    }

}
