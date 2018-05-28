package com.example.danieltaylor.androidgame.firebase;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Math.max;

public class DatabaseManager {

    private FirebaseDatabase mDatabase;
    private DatabaseReference ref;
    private User user;
    private ArrayList<User> singleLeaderBoard = new ArrayList<>();
    private ArrayList<User> multiLeaderBoard = new ArrayList<>();

    public DatabaseManager(FirebaseDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

    /**
     * change the users ranking in the single player leaderboard
     * @param victory
     * @param user
     */
    public void incrementSingleScore(boolean victory, FirebaseUser user) {
        ref = mDatabase.getReference("User");
        preparpeUser(user.getUid());
        User u = getUser();
        if (victory) {
            u.setSinglePlayerScore(u.getSinglePlayerScore() + 20);
        } else {
            u.setSinglePlayerScore(max(0, u.getSinglePlayerScore() - 10));
        }
        ref.child(user.getUid()).setValue(u);
        preparpeUser(user.getUid());
    }

    /**
     * increment the multiplayer score
     * only need to handle the user's score as the other user's app will handle their score for them
     * @param victory
     * @param user
     */
    public void incrementMultiScore(boolean victory, FirebaseUser user) {
        ref = mDatabase.getReference("User");
        preparpeUser(user.getUid());
        User u = getUser();
        if (victory) {
            u.setMultiPlayerScore(u.getMultiPlayerScore() + 20);
        } else {
            u.setMultiPlayerScore(max(0, u.getMultiPlayerScore() - 10));
        }
    }


    public void preparpeUser(String id) {
        ref = mDatabase.getReference("User");
        Query query = ref.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * update the user in the db when the user signs in
     * @param u
     */
    public void updateUserOnSignIn(User u) {
        ref = mDatabase.getReference("User");
        ref.child(u.getId()).setValue(u);
    }

    public ArrayList<User> getSingleLeaderBoard() {
        return singleLeaderBoard;
    }

    public ArrayList<User> getMultiLeaderBoard() {
        return multiLeaderBoard;
    }

    public User getUser() {
        return user;
    }
}
