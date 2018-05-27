package com.example.danieltaylor.androidgame.SingleGame;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.R;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;
    public Button btnAttack;
    public Button btnDefend;
    public Button btnHeal;
    public SurfaceView playerHealth;
    public SurfaceView enemyHealth;
    public TextView playerHealthText;
    public TextView enemyHealthText;
    public ImageView playerCharacter;
    public ImageView enemyCharacter;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO change the GameThread to a view
        setContentView(R.layout.activity_single_player_game);

        res = getResources();

        btnAttack = findViewById(R.id.btn_attack);
        btnDefend = findViewById(R.id.btn_defend);
        btnHeal = findViewById(R.id.btn_heal);
        playerHealth = findViewById(R.id.player_character_health);
        enemyHealth = findViewById(R.id.enemy_character_health);
        playerHealthText = findViewById(R.id.player_character_health_text);
        enemyHealthText = findViewById(R.id.enemy_character_health_text);

        gameThread = new SinglePlayerGameThread(SinglePlayerGameActivity.this, res);

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
