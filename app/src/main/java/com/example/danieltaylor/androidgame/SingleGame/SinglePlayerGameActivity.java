package com.example.danieltaylor.androidgame.SingleGame;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.GameElements.Character;
import com.example.danieltaylor.androidgame.GameElements.GifImageView;
import com.example.danieltaylor.androidgame.GameElements.Player;
import com.example.danieltaylor.androidgame.R;

import java.util.Random;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;
    GifImageView playerCharacterGif;
    GifImageView enemyCharacterGif;
    private Player userPlayer;
    private Player aiPlayer;
    private MediaPlayer music;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int selectedCharacter = intent.getIntExtra("character", 0);

        //TODO change the GameThread to a view ?not sure if necessary at the moment?
        setContentView(R.layout.activity_single_player_game);



        res = getResources();

        if (selectedCharacter == 0) {
            Toast.makeText(getApplicationContext(), "assigning randomCharacter",
                    Toast.LENGTH_LONG).show();
            selectedCharacter = new Random().nextInt(4) + 1;
        }

        userPlayer = createPlayer(selectedCharacter);

        aiPlayer = createPlayer(new Random().nextInt(4) + 1);

        music = MediaPlayer.create(getApplicationContext(), R.raw.almostdead);
        music.setLooping(true);

        playerCharacterGif = findViewById(R.id.player_character_gif);
        playerCharacterGif.setGifImageResource(userPlayer.character.getGifResourceID());

        enemyCharacterGif = findViewById(R.id.enemy_character_gif);
        enemyCharacterGif.setGifImageResource(aiPlayer.character.getGifResourceID());

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

    private Player createPlayer(int characterSelected) {

        Character c = new Character();
        switch(characterSelected) {
            case 1:
                c.setAttack(res.getInteger(R.integer.character1_atk));
                c.setDefense(res.getInteger(R.integer.character1_dfs));
                c.setSpeed(res.getInteger(R.integer.character1_spd));
                c.setGifResourceID(R.drawable.character1idle);
                c.setName(res.getString(R.string.character1_name));
            case 2:
                c.setAttack(res.getInteger(R.integer.character2_atk));
                c.setDefense(res.getInteger(R.integer.character2_dfs));
                c.setSpeed(res.getInteger(R.integer.character2_spd));
                c.setGifResourceID(R.drawable.character2idle);
                c.setName(res.getString(R.string.character2_name));
            case 3:
                c.setAttack(res.getInteger(R.integer.character3_atk));
                c.setDefense(res.getInteger(R.integer.character3_dfs));
                c.setSpeed(res.getInteger(R.integer.character3_spd));
                c.setGifResourceID(R.drawable.character3idle);
                c.setName(res.getString(R.string.character3_name));
            case 4:
                c.setAttack(res.getInteger(R.integer.character4_atk));
                c.setDefense(res.getInteger(R.integer.character4_dfs));
                c.setSpeed(res.getInteger(R.integer.character4_spd));
                c.setGifResourceID(R.drawable.character4idle);
                c.setName(res.getString(R.string.character4_name));
        }

        return new Player(c);
    }
}
