package com.example.danieltaylor.androidgame.SingleGame;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.GameElements.GifImageView;
import com.example.danieltaylor.androidgame.R;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;
    private GifImageView playerCharacterGif;
    private GifImageView enemyCharacterGif;
    private MediaPlayer music;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO change the GameThread to a view
        setContentView(R.layout.activity_single_player_game);

        //TODO assign the ai a character
        //TODO pass the character selected by the player through the bundle

        res = getResources();

        music = MediaPlayer.create(getApplicationContext(), R.raw.almostdead);
        music.setLooping(true);

        playerCharacterGif = findViewById(R.id.player_character_gif);
        playerCharacterGif.setGifImageResource(R.drawable.character1idle);

        enemyCharacterGif = findViewById(R.id.enemy_character_gif);
        enemyCharacterGif.setGifImageResource(R.drawable.character2idle);

        gameThread = new SinglePlayerGameThread(SinglePlayerGameActivity.this, res);

        //start the game
        music.start();
        gameThread.onStart();
    }


    //ensures the game is correctly resumed with the activity
    @Override
    protected void onResume() {
        super.onResume();
        music.start();
        gameThread.onResume();
    }


    //ensures the game is correctly paused with the activity
    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
        gameThread.onPause();
    }
}
