package com.example.danieltaylor.androidgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.SingleGame.SinglePlayerGameActivity;

public class CharacterSelectionActivity extends AppCompatActivity {

    int characterSelected;
    View character1;
    View character2;
    View character3;
    View character4;
    Button characterSelectionButton;
    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        Resources res = getResources();
        //find the views
        character1 = findViewById(R.id.character1);
        character2 = findViewById(R.id.character2);
        character3 = findViewById(R.id.character3);
        character4 = findViewById(R.id.character4);

        TextView character1name = character1.findViewById(R.id.characeter1_name);
        character1name.setText(getString(R.string.character_name, res.getString(R.string.character1_name)));

        TextView character1atk = character1.findViewById(R.id.characeter1_atk);
        character1atk.setText(getString(R.string.character_attack, res.getInteger(R.integer.character1_atk)));

        TextView character1dfs = character1.findViewById(R.id.characeter1_dfs);
        character1dfs.setText(getString(R.string.character_defense, res.getInteger(R.integer.character1_dfs)));

        TextView character1spd = character1.findViewById(R.id.characeter1_spd);
        character1spd.setText(getString(R.string.character_speed, res.getInteger(R.integer.character1_spd)));

        TextView character2name = character2.findViewById(R.id.characeter2_name);
        character2name.setText(getString(R.string.character_name, res.getString(R.string.character2_name)));

        TextView character2atk = character2.findViewById(R.id.characeter2_atk);
        character2atk.setText(getString(R.string.character_attack, res.getInteger(R.integer.character2_atk)));

        TextView character2dfs = character2.findViewById(R.id.characeter2_dfs);
        character2dfs.setText(getString(R.string.character_defense, res.getInteger(R.integer.character2_dfs)));

        TextView character2spd = character2.findViewById(R.id.characeter2_spd);
        character2spd.setText(getString(R.string.character_speed, res.getInteger(R.integer.character2_spd)));

        TextView character3name = character3.findViewById(R.id.characeter3_name);
        character3name.setText(getString(R.string.character_name, res.getString(R.string.character3_name)));

        TextView character3atk = character3.findViewById(R.id.characeter3_atk);
        character3atk.setText(getString(R.string.character_attack, res.getInteger(R.integer.character3_atk)));

        TextView character3dfs = character3.findViewById(R.id.characeter3_dfs);
        character3dfs.setText(getString(R.string.character_defense, res.getInteger(R.integer.character3_dfs)));

        TextView character3spd = character3.findViewById(R.id.characeter3_spd);
        character3spd.setText(getString(R.string.character_speed, res.getInteger(R.integer.character3_spd)));

        TextView character4name = character4.findViewById(R.id.characeter4_name);
        character4name.setText(getString(R.string.character_name, res.getString(R.string.character4_name)));

        TextView character4atk = character4.findViewById(R.id.characeter4_atk);
        character4atk.setText(getString(R.string.character_attack, res.getInteger(R.integer.character4_atk)));

        TextView character4dfs = character4.findViewById(R.id.characeter4_dfs);
        character4dfs.setText(getString(R.string.character_defense, res.getInteger(R.integer.character4_dfs)));

        TextView character4spd = character4.findViewById(R.id.characeter4_spd);
        character4spd.setText(getString(R.string.character_speed, res.getInteger(R.integer.character4_spd)));

        characterSelectionButton = findViewById(R.id.btn_confirm_character);
        music = MediaPlayer.create(CharacterSelectionActivity.this, R.raw.battle);

        //TODO fix onClickListeners activiting multiple views at the same time

        character1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected == 1) {
                    unSelectCharacter(1);
                } else {
                    unSelectCharacter(characterSelected);
                    selectCharacter(3);
                }
            }
        });

        character2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected == 2) {
                    unSelectCharacter(2);
                } else {
                    unSelectCharacter(characterSelected);
                    selectCharacter(2);
                }
            }
        });

        character3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected == 3) {
                    unSelectCharacter(3);
                } else {
                    unSelectCharacter(characterSelected);
                    selectCharacter(3);
                }
            }
        });

        character4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected == 4) {
                    unSelectCharacter(4);
                } else {
                    unSelectCharacter(characterSelected);
                    selectCharacter(4);
                }
            }
        });

        characterSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected != 0) {
                    Intent intent = new Intent(CharacterSelectionActivity.this, SinglePlayerGameActivity.class);
                    intent.putExtra("character", characterSelected);
                    startActivity(intent);
                }
            }
        });

        music.setLooping(true);
        music.start();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    //provide correct up navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectCharacter(int number) {

        this.characterSelected = number;
        Toast.makeText(getApplicationContext(),
                "selected character " + Integer.toString(characterSelected),
                Toast.LENGTH_SHORT).show();

        switch(number) {
            case 1:
                character1.setBackgroundResource(R.drawable.character_selected_character_background_border);
            case 2:
                character2.setBackgroundResource(R.drawable.character_selected_character_background_border);
            case 3:
                character3.setBackgroundResource(R.drawable.character_selected_character_background_border);
            case 4:
                character4.setBackgroundResource(R.drawable.character_selected_character_background_border);
        }
    }

    private void unSelectCharacter(int number) {
        this.characterSelected = 0;
        switch(number) {
            case 1:
                character1.setBackgroundResource(R.drawable.character_selection_background_border);
            case 2:
                character2.setBackgroundResource(R.drawable.character_selection_background_border);
            case 3:
                character3.setBackgroundResource(R.drawable.character_selection_background_border);
            case 4:
                character4.setBackgroundResource(R.drawable.character_selection_background_border);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        music.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        music.start();
    }

}
