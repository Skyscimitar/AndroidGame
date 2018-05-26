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
    private void update(){}

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


    public void onResume() {
        isRunning = game.isGameRunning();
        gameThread = new Thread(this);
        gameThread.start();
    }

}
