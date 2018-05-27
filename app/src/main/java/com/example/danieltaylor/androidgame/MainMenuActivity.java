package com.example.danieltaylor.androidgame;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {

    Button btnSinglePlayer;
    Button btnMultiPlayer;
    Button btnLeaderBoard;
    Button btnSignOut;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        btnSinglePlayer = findViewById(R.id.btn_singleplayer);
        btnMultiPlayer = findViewById(R.id.btn_multiplayer);
        btnLeaderBoard = findViewById(R.id.btn_leaderboard);
        btnSignOut = findViewById(R.id.btn_signout);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSinglePlayerGame();
            }
        });
        btnMultiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMultiPlayerGame();
            }
        });
        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLeaderboard();
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign out from google and firebase
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //TODO leaderboard activity
    private void goToLeaderboard(){}

    //TODO singlegame activity
    private void goToSinglePlayerGame(){
        Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", "single");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //TODO mulitgame activity
    private void goToMultiPlayerGame(){
        Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", "multi");
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
