package com.example.danieltaylor.androidgame;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.danieltaylor.androidgame.Firebase.DbManager;
import com.example.danieltaylor.androidgame.Firebase.UserMarker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private ArrayList<UserMarker> leaderboard;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    DbManager mDbManager = new DbManager(mDb);
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String login = bundle.getString("login");

        listView = findViewById(R.id.leaderboard);

        mDbManager.getLeaderboard(login, LeaderboardActivity.this);
    }

    public void setListViewAdapter(ArrayList<UserMarker> userList) {

    }
}
