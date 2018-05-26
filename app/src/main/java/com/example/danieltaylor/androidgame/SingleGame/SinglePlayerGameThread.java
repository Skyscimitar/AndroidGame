package com.example.danieltaylor.androidgame.SingleGame;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.danieltaylor.androidgame.GameElements.Player;
import com.example.danieltaylor.androidgame.GameElements.Character;

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

    public SinglePlayerGameThread(SinglePlayerGameActivity activity){

        super();

        this.activity = activity;

        actions.add("ATTACK");
        actions.add("DEFEND");
        actions.add("HEAL");

        Character playerCharacter = new Character();
        Character aiCharacter = new Character();

        aiPlayer = new Player(playerCharacter);
        userPlayer = new Player(aiCharacter);


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
