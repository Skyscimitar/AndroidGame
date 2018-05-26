package com.example.danieltaylor.androidgame.SingleGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.danieltaylor.androidgame.R;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO change the GameThread to a view
        setContentView(R.layout.activity_single_player_game);
        gameThread = new SinglePlayerGameThread();

        //start the game
        gameThread.onStart();
    }


    //ensures the game is correctly resumed with the activity
    @Override
    protected void onResume() {
        super.onResume();
        gameThread.onResume();
    }


    //ensures the game is correctly paused with the activity
    @Override
    protected void onPause() {
        super.onPause();
        gameThread.onPause();
    }
}
