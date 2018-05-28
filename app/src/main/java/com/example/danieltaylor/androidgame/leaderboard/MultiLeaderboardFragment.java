package com.example.danieltaylor.androidgame.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danieltaylor.androidgame.R;
import com.google.firebase.database.FirebaseDatabase;

public class MultiLeaderboardFragment extends Fragment {

    FirebaseDatabase mDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.multi_leaderboard_fragment, null);
    }
}
