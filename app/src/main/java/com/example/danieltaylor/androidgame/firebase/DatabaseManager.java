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
    public void incrementSingleScore(final boolean victory, FirebaseUser user) {
        ref = mDatabase.getReference("User");
        Query query = ref.child(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                u = updateSingleScore(u, victory);
                ref.child(u.getId()).setValue(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * increment the multiplayer score
     * only need to handle the user's score as the other user's app will handle their score for them
     * @param victory
     * @param user
     */
    public void incrementMultiScore(final boolean victory, FirebaseUser user) {
        ref = mDatabase.getReference("User");
        Query query = ref.child(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                u = updateMultiScore(u, victory);
                ref.child(u.getId()).setValue(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private User updateSingleScore(User u, Boolean victory){
        if (victory) {
            u.setSinglePlayerScore(u.getMultiPlayerScore() + 20);
        } else {
            u.setSinglePlayerScore(max(0, u.getMultiPlayerScore() - 10));
        }
        return u;
    }

    private User updateMultiScore(User u, Boolean victory){
        if (victory) {
            u.setMultiPlayerScore(u.getMultiPlayerScore() + 20);
        } else {
            u.setMultiPlayerScore(max(0, u.getMultiPlayerScore() - 10));
        }
        return u;
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
