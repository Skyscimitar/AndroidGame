package com.example.danieltaylor.androidgame.firebase;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.danieltaylor.androidgame.firebase.User;

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
            u.setSinglePlayerScore(u.getSinglePlayerScore() + 20);
        } else {
            u.setSinglePlayerScore(max(0, u.getSinglePlayerScore() - 10));
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


    /**
     * register a new user if it doesn't exist or do nothing
     * @param mUser
     */
    public void registerUser(final FirebaseUser mUser) {
        ref = mDatabase.getReference("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(mUser.getUid())) {
                    User u = new User();
                    u.setMultiPlayerScore(0);
                    u.setSinglePlayerScore(0);
                    u.setEmail(mUser.getEmail());
                    u.setName(mUser.getDisplayName());
                    u.setId(mUser.getUid());
                    ref.child(u.getId()).setValue(u);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * allow the user to update his name
     * @param user
     */
    public void updateUserName(final User user){
        ref = mDatabase.getReference("User");
        Query query = ref.child(user.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                u.setName(user.getName());
                ref.child(user.getId()).setValue(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
