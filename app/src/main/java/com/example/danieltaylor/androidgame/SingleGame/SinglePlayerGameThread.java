package com.example.danieltaylor.androidgame.SingleGame;

import android.content.Context;
import android.content.res.Resources;
import android.view.SurfaceView;
import android.widget.ArrayAdapter;

import com.example.danieltaylor.androidgame.GameElements.Player;
import com.example.danieltaylor.androidgame.GameElements.Character;
import com.example.danieltaylor.androidgame.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerGameThread implements Runnable {

    ArrayList<String> actions = new ArrayList<>();

    private Player aiPlayer;
    private Player userPlayer;
    private boolean isRunning;
    private JSONObject gameData;
    private SinglePlayerGame game;
    private Thread gameThread;
    private boolean userPlayerActed = false;
    private boolean aiPlayerActed = false;
    private String userPlayerAction;
    private String aiPlayerAction;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    public SinglePlayerGameActivity activity;
    SurfaceView enemyHealth;
    SurfaceView playerHealth;
    private Resources res;

    public SinglePlayerGameThread(SinglePlayerGameActivity activity, Resources res){

        super();

        this.activity = activity;
        this.res = res;

        actions.add("ATTACK");
        actions.add("DEFEND");
        actions.add("HEAL");

        Character playerCharacter = new Character();
        Character aiCharacter = new Character();

        aiPlayer = new Player(playerCharacter);
        userPlayer = new Player(aiCharacter);

        //TODO set the image views to display the correct character sprites


        playerArrayList.add(aiPlayer);
        playerArrayList.add(userPlayer);

        game = new SinglePlayerGame(playerArrayList);
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
            userPlayerActed = false;
            game.takeTurn(userPlayer, userPlayerAction);
            userPlayer = game.updatePlayer(userPlayer);
        }

        //if the aiplayer inputted an action and it is his turn, update the gameData
        if (aiPlayerActed && game.isPlayerTurn(aiPlayer)) {
            aiPlayerActed = false;
            game.takeTurn(aiPlayer, aiPlayerAction);
            aiPlayer = game.updatePlayer(aiPlayer);
        }

        //if it's the ai turn, take a random action
        if(game.isPlayerTurn(aiPlayer)) {
            int ai = new Random().nextInt(2);
            aiPlayerAction = actions.get(ai);
            aiPlayerActed = true;
        }
    }

    //updates the game view
    private void draw() {
        //update the ui based on the current game state
        //update user player health

        //TODO update surface view


        //update ai player health

        //TODO update surface view
    }

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

    public void onReceivePlayerControl(String action) {
        switch (action) {
            case "ATTACK":
                userPlayerActed = true;
                userPlayerAction = action;
            case "DEFEND":
                userPlayerActed = true;
                userPlayerAction = action;
            case "HEAL":
                userPlayerActed = true;
                userPlayerAction = action;
        }

        //TODO update ui based on the players choice
    }


}
