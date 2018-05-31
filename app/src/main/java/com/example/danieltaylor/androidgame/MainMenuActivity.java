package com.example.danieltaylor.androidgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.firebase.User;
import com.example.danieltaylor.androidgame.leaderboard.LeaderboardActivity;
import com.example.danieltaylor.androidgame.profile.ProfileActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {

    Button btnSinglePlayer;
    Button btnMultiPlayer;
    Button btnLeaderBoard;
    Button btnSignOut;
    Button btnProfile;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase mDatabase;
    DatabaseManager dm;
    FirebaseUser mUser;
    ListView languageList;
    ImageButton languageButton;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dm = new DatabaseManager(mDatabase);
        mUser = mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        btnSinglePlayer = findViewById(R.id.btn_singleplayer);
        btnMultiPlayer = findViewById(R.id.btn_multiplayer);

        for (UserInfo user: mAuth.getCurrentUser().getProviderData()) {
            if (user.getProviderId().equals("google.com")) {
                btnMultiPlayer.setVisibility(View.VISIBLE);
            } else {
                btnMultiPlayer.setVisibility(View.GONE);
            }
        }

        btnLeaderBoard = findViewById(R.id.btn_leaderboard);
        btnSignOut = findViewById(R.id.btn_signout);
        btnProfile = findViewById(R.id.btn_profile);

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
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
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

        languageButton = findViewById(R.id.language_button);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        dm.registerUser(mUser);
    }

    private void goToLeaderboard(){
        Intent intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
        startActivity(intent);
    }

    private void goToSinglePlayerGame(){
        Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("KEY", "single");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //TODO mulitgame activity
    private void goToMultiPlayerGame(){
        Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("KEY", "multi");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToProfile(){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }


    /**
     * Allow the user to manually change language if they don't want to use the default locale of
     * their device
     * @param lang the new language (for now only fr & en supported)
     */
    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainMenuActivity.class);
        finish();
        startActivity(refresh);
    }


    private void showLanguageDialog(){
        final Dialog dialog = new Dialog(MainMenuActivity.this);
        dialog.setContentView(R.layout.language_selection_dialog);
        TextView fr = dialog.findViewById(R.id.fr);
        TextView en = dialog.findViewById(R.id.en);
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("fr");
                dialog.dismiss();
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
