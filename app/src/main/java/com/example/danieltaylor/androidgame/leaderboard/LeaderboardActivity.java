package com.example.danieltaylor.androidgame.leaderboard;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.danieltaylor.androidgame.R;

public class LeaderboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        SingleLeaderboardFrament fragment = new SingleLeaderboardFrament();
        loadFragment(fragment);

        BottomNavigationView navigationView = findViewById(R.id.leaderboard_btm_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
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
}
