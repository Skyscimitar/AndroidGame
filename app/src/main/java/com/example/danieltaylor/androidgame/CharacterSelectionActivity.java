package com.example.danieltaylor.androidgame;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.SingleGame.SinglePlayerGameActivity;

public class CharacterSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    int characterSelected;
    View character1;
    View character2;
    View character3;
    View character4;
    Button characterSelectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        character1 = findViewById(R.id.character1);
        character2 = findViewById(R.id.character2);
        character3 = findViewById(R.id.character3);
        character4 = findViewById(R.id.character4);
        characterSelectionButton = findViewById(R.id.btn_confirm_character);

        character1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected == 1) {
                    unSelectCharacter(1);
                } else {
                    selectCharacter(3);
                }
            }
        });

        character2.setOnClickListener(this);
        character3.setOnClickListener(this);
        character4.setOnClickListener(this);
        characterSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterSelected != 0) {
                    Intent intent = new Intent(CharacterSelectionActivity.this, SinglePlayerGameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("character", characterSelected);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

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


    public void onClick(View v){
        switch (v.getId()) {

            case R.id.character1:
                if (characterSelected == 1) {
                    unSelectCharacter(2);
                } else {
                    selectCharacter(1);
                }

            case R.id.character2:
                if (characterSelected == 2) {
                    unSelectCharacter(2);
                } else {
                    selectCharacter(2);
                }

            case R.id.character3:
                if (characterSelected == 3) {
                    unSelectCharacter(3);
                } else {
                    selectCharacter(3);
                }

            case R.id.character4:
                if (characterSelected == 4) {
                    unSelectCharacter(4);
                } else {
                    selectCharacter(4);
                }
        }
    }

    private void selectCharacter(int number) {
        this.characterSelected = number;
        Toast.makeText(getApplicationContext(),
                "selected character " + Integer.toString(characterSelected),
                Toast.LENGTH_SHORT).show();
        //TODO update UI with the number
    }

    private void unSelectCharacter(int number) {
        this.characterSelected = 0;
        //TODO update UI with the number
    }
}
