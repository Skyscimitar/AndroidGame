package com.example.danieltaylor.androidgame.SingleGame;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.GameElements.Player;
import com.example.danieltaylor.androidgame.GameElements.Character;
import com.example.danieltaylor.androidgame.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Because the thread isn't also a view, can't update the ui, a solution would be
 * to run it on the uiThread but that isn't great
 * instead we pass the activity to the thread, we then let the activity handle all ui updates
 * on the ui thread and keep the logic seperate
 */
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
    ArrayList<Player> playerArrayList = new ArrayList<>();
    private SinglePlayerGameActivity activity;
    SurfaceView enemyHealth;
    SurfaceView playerHealth;
    private Resources res;

    public Player winner;
    private Button attack_btn;
    private Button defend_btn;
    private Button heal_btn;
    private Random random = new Random();

    public SinglePlayerGameThread(SinglePlayerGameActivity activity, Resources res, Player userPlayer,
                                  Player aiPlayer){

        super();

        this.activity = activity;
        this.res = res;

        actions.add("ATTACK");
        actions.add("DEFEND");
        actions.add("HEAL");

        this.aiPlayer = aiPlayer;
        this.userPlayer = userPlayer;

        attack_btn = activity.attack_btn;
        defend_btn = activity.defend_btn;
        heal_btn = activity.heal_btn;

        //set onClickListeners to register the user's controls.
        attack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPlayerActed = true;
                userPlayerAction = "ATTACK";
                disableInput();
            }
        });

        defend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPlayerActed = true;
                userPlayerAction = "DEFEND";
                disableInput();
            }
        });

        heal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPlayerActed = true;
                userPlayerAction = "HEAL";
                disableInput();
            }
        });


        playerArrayList.add(aiPlayer);
        playerArrayList.add(userPlayer);

        game = new SinglePlayerGame(playerArrayList, userPlayer, aiPlayer);
    }


    public void run(){
        while (isRunning) {
            update();
            draw();
            control();
        }
        return;
    }

    //allows player turns to be taken
    private void update(){

        if (game.isGameOver()) {
            isRunning = false;
            endGame(game.getWinner());
        } else {
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
            if (game.isPlayerTurn(aiPlayer)) {
                int ai = random.nextInt(3);
                aiPlayerAction = actions.get(ai);
                aiPlayerActed = true;
            }
        }
    }

    //updates the game view
    private void draw() {
        //update user player
        activity.updateUserPlayer(userPlayer);

        //update ai player
        activity.updateEnemyPlayer(aiPlayer);

        activity.updateText();
    }

    //registers player controls
    private void control(){
        try {
            //wait so the user can see the cpu's activity
            gameThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (game.isPlayerTurn(userPlayer)) {
            //enable the input if it is the player's turn
            enableInput();
        }
    }


    public void onPause(){
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void endGame(Player winner){
        Looper.prepare();
        activity.endGame(winner);
        isRunning = false;

    }

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


    //prevent the user from acting
    private void disableInput() {
        activity.disableInput();
    }

    private void enableInput() {
        activity.enableInput();
    }

}
