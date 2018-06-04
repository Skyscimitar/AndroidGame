package com.example.danieltaylor.androidgame.single_game;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.CharacterSelectionActivity;
import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.game_elements.Character;
import com.example.danieltaylor.androidgame.game_elements.GifImageView;
import com.example.danieltaylor.androidgame.game_elements.Player;
import com.example.danieltaylor.androidgame.MainMenuActivity;
import com.example.danieltaylor.androidgame.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SinglePlayerGameActivity extends AppCompatActivity {

    private SinglePlayerGameThread gameThread;
    private UpdateLeaderBoardThread ult;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseManager dm;
    FirebaseUser mUser;

    GifImageView playerCharacterGif;
    GifImageView enemyCharacterGif;

    TextView playerCharacterName;
    TextView playerCharacterHealth;

    TextView enemyCharacterName;
    TextView enemyCharacterHealth;

    Button attack_btn;
    Button defend_btn;
    Button heal_btn;

    RelativeLayout dialogLayout;
    Button btnRestart;
    Button btnMainMenu;
    TextView gameEndText;



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
        setContentView(R.layout.activity_single_player_game);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dm = new DatabaseManager(mDatabase);
        mUser = mAuth.getCurrentUser();

        ult = new UpdateLeaderBoardThread(mUser, dm);
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

        dialogLayout = findViewById(R.id.dialog_layout);
        btnRestart = dialogLayout.findViewById(R.id.restart_button);
        btnMainMenu = dialogLayout.findViewById(R.id.main_menu_btn);
        gameEndText = dialogLayout.findViewById(R.id.game_finished_text);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenu();
            }
        });

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

    /**
     * Creates a new player object with the character selected
     * @param characterSelected
     * @return
     */
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
        Boolean victory = userPlayer.getPlayerID().equals(winner.getPlayerID());
        ult.setWinner(victory);
        ult.run();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userPlayer.getPlayerID().equals(winner.getPlayerID())) {
                    gameEndText.setText(res.getString(R.string.game_finished_victory_text));
                } else {
                    gameEndText.setText(res.getString(R.string.game_finished_loose_text));
                }
                dialogLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    protected void restartGame() {
        //starting a new game, we want the user to be able to change character if they want to
        Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
        //hide the dialog
        dialogLayout.setVisibility(View.GONE);
        startActivity(intent);

    }

    protected void mainMenu() {
        //return the user to the main menu
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        //hide the dialog window
        dialogLayout.setVisibility(View.GONE);
        startActivity(intent);
    }

    //enable the input for the user
    protected void enableInput() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attack_btn.setClickable(true);
                attack_btn.setEnabled(true);
                defend_btn.setClickable(true);
                defend_btn.setEnabled(true);
                heal_btn.setClickable(true);
                heal_btn.setEnabled(true);
            }
        });
    }

    //disable the input for the user
    protected void disableInput() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attack_btn.setClickable(false);
                attack_btn.setEnabled(false);
                defend_btn.setClickable(false);
                defend_btn.setEnabled(false);
                heal_btn.setClickable(false);
                heal_btn.setEnabled(false);
            }
        });
    }


}
