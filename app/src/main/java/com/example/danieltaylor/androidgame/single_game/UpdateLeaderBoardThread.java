package com.example.danieltaylor.androidgame.single_game;

import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.firebase.User;
import com.google.firebase.auth.FirebaseUser;

public class UpdateLeaderBoardThread implements Runnable{

    DatabaseManager dm;
    FirebaseUser mUser;
    Boolean victory;

    public UpdateLeaderBoardThread(FirebaseUser mUser, DatabaseManager dm) {
        this.mUser = mUser;
        this.dm = dm;
    }

    public void setWinner(Boolean winner) {
        this.victory = winner;
    }

    public void run(){
        dm.incrementSingleScore(victory, mUser);
    }
}
