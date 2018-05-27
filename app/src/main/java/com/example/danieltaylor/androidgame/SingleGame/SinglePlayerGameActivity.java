package com.example.danieltaylor.androidgame.SingleGame;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.GameElements.Character;
import com.example.danieltaylor.androidgame.GameElements.GifImageView;
import com.example.danieltaylor.androidgame.GameElements.Player;
import com.example.danieltaylor.androidgame.MainMenuActivity;
import com.example.danieltaylor.androidgame.R;

import java.util.Random;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;

    GifImageView playerCharacterGif;
    GifImageView enemyCharacterGif;

    TextView playerCharacterName;
    TextView playerCharacterHealth;

    TextView enemyCharacterName;
    TextView enemyCharacterHealth;

    Button attack_btn;
    Button defend_btn;
    Button heal_btn;



    Player userPlayer;
    Player aiPlayer;


    private MediaPlayer music;
    public final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD =1;

    private Random random = new Random();



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
            Log.e("GACT", "assigning random character to user");
            selectedCharacter = random.nextInt(4) + 1;
        }

        // create players
        userPlayer = createPlayer(selectedCharacter);
        int aiCharacter = random.nextInt(4) + 1;
        Log.e("GACT", "assigned" + Integer.toString(aiCharacter)+"To ai");
        aiPlayer = createPlayer(aiCharacter);

        music = MediaPlayer.create(getApplicationContext(), R.raw.almostdead);
        music.setLooping(true);

        playerCharacterGif = findViewById(R.id.player_character_gif);
        playerCharacterGif.setGifImageResource(userPlayer.character.getGifResourceID());

        playerCharacterName = findViewById(R.id.player_character_name);
        playerCharacterName.setText(userPlayer.character.getName());

        playerCharacterHealth = findViewById(R.id.player_character_health);
        playerCharacterHealth.setText(res.getString(R.string.character_health,
                userPlayer.character.getHealth()));

        enemyCharacterGif = findViewById(R.id.enemy_character_gif);
        enemyCharacterGif.setGifImageResource(aiPlayer.character.getGifResourceID());

        enemyCharacterName = findViewById(R.id.enemy_character_name);
        enemyCharacterName.setText(aiPlayer.character.getName());

        enemyCharacterHealth = findViewById(R.id.enemy_character_health);
        enemyCharacterHealth.setText(res.getString(R.string.character_health,
                aiPlayer.character.getHealth()));

        attack_btn = findViewById(R.id.btn_attack);
        defend_btn = findViewById(R.id.btn_defend);
        heal_btn = findViewById(R.id.btn_heal);

        gameThread = new SinglePlayerGameThread(SinglePlayerGameActivity.this, res, userPlayer,
                aiPlayer);

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
        if (characterSelected == 1) {
            c.setAttack(res.getInteger(R.integer.character1_atk));
            c.setDefense(res.getInteger(R.integer.character1_dfs));
            c.setSpeed(res.getInteger(R.integer.character1_spd));
            c.setGifResourceID(R.drawable.character1idle);
            c.setName(res.getString(R.string.character1_name));
        }
        if (characterSelected == 2) {
            c.setAttack(res.getInteger(R.integer.character2_atk));
            c.setDefense(res.getInteger(R.integer.character2_dfs));
            c.setSpeed(res.getInteger(R.integer.character2_spd));
            c.setGifResourceID(R.drawable.character2idle);
            c.setName(res.getString(R.string.character2_name));
        }
        if (characterSelected == 3) {
            c.setAttack(res.getInteger(R.integer.character3_atk));
            c.setDefense(res.getInteger(R.integer.character3_dfs));
            c.setSpeed(res.getInteger(R.integer.character3_spd));
            c.setGifResourceID(R.drawable.character3idle);
            c.setName(res.getString(R.string.character3_name));
        }
        if (characterSelected == 4){
                c.setAttack(res.getInteger(R.integer.character4_atk));
                c.setDefense(res.getInteger(R.integer.character4_dfs));
                c.setSpeed(res.getInteger(R.integer.character4_spd));
                c.setGifResourceID(R.drawable.character4idle);
                c.setName(res.getString(R.string.character4_name));
        }

        return new Player(c);
    }

    public void updateUserPlayer(Player player) {
        userPlayer = player;
    }

    public void updateEnemyPlayer(Player player) {
        aiPlayer = player;
    }

    public void updateText(){

        //only UI thread can update ui, so we change the display on the UI thread.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playerCharacterHealth.setText(res.getString(R.string.character_health,
                        userPlayer.character.getHealth()));
                enemyCharacterHealth.setText(res.getString(R.string.character_health,
                        aiPlayer.character.getHealth()));
            }
        });

    }

    public void endGame(final Player winner) {
        music.pause();
        //TODO update leaderboard here
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(SinglePlayerGameActivity.this);
                dialog.setContentView(R.layout.game_ended_dialog);
                final TextView endGameText = dialog.findViewById(R.id.game_finished_text);
                final Button restartBtn = dialog.findViewById(R.id.restart_button);
                final Button mainMenuBtn = dialog.findViewById(R.id.main_menu_btn);
                if (winner.getPlayerID().equals(userPlayer.getPlayerID())) {
                    endGameText.setText(res.getString(R.string.game_finished_victory_text));
                } else {
                    endGameText.setText(res.getString(R.string.game_finished_loose_text));
                }
                restartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        SinglePlayerGameActivity.this.restartGame();
                    }
                });

                mainMenuBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        SinglePlayerGameActivity.this.mainMenu();
                    }
                });

                dialog.show();
            }
        });
    }

    protected void restartGame() {
        //reset character health:
        userPlayer.character.setHealth(userPlayer.character.getTotalHealth());

        //update aiCharacter
        aiPlayer = createPlayer(random.nextInt(4) + 1);
        enemyCharacterGif.setGifImageResource(aiPlayer.character.getGifResourceID());
        enemyCharacterName.setText(aiPlayer.character.getName());
        enemyCharacterHealth.setText(res.getString(R.string.character_health, aiPlayer.character.getHealth()));

        //create new thread
        gameThread = new SinglePlayerGameThread(SinglePlayerGameActivity.this, res, userPlayer,
                aiPlayer);

        //start a new Game with the same character
        music.start();
        gameThread.onStart();
    }

    protected void mainMenu() {
        //return the user to the main menu
        Intent intent = new Intent(SinglePlayerGameActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }


}
