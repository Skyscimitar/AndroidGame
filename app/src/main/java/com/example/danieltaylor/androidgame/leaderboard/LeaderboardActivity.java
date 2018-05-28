package com.example.danieltaylor.androidgame.leaderboard;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.danieltaylor.androidgame.R;

public class LeaderboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        SingleLeaderboardFrament fragment = new SingleLeaderboardFrament();
        loadFragment(fragment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.leaderboard_title));

        BottomNavigationView navigationView = findViewById(R.id.leaderboard_btm_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        music = MediaPlayer.create(getApplicationContext(), R.raw.leaderboard);
        music.setLooping(true);
        music.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()) {
            case R.id.single_leaderboard_nav_btn:
                fragment = new SingleLeaderboardFrament();
                break;
            case R.id.multi_leaderboard_nav_btn:
                fragment = new MultiLeaderboardFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.leaderboard_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        music.start();
    }
}
