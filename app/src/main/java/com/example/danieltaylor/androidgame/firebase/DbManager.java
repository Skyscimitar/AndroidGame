package com.example.danieltaylor.androidgame.firebase;

import com.example.danieltaylor.androidgame.LeaderboardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DbManager {
    private FirebaseDatabase mDatabase;
    private ArrayList<UserMarker> users = new ArrayList<>();
    private ArrayList<UserMarker> leaderboard = new ArrayList<>();

    public DbManager(FirebaseDatabase db) {
        this.mDatabase = db;
    }

    private static int indexOfName(ArrayList<UserMarker> users, String name) {
        for (UserMarker user:users) {
            if (user.getLogin() != null && user.getLogin().equals(name)) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

    public void addUserToDb(UserMarker user) {
        DatabaseReference mReference = mDatabase.getReference("Markers_Users");
        mReference.child(user.getId()).setValue(user);
    }

    public void updateSinglePlayerWins(UserMarker user) {
        int userWins = user.getSinglePlayerWins();
        user.setSinglePlayerWins(userWins + 1);
    }

    public void updateElo(UserMarker winner, UserMarker loser) {
        int winnerElo = winner.getElo();
        int loserElo = loser.getElo();

        Double qWinner = Math.pow(10, ((double) winnerElo)/400);
        Double qLoser = Math.pow(10, ((double) loserElo)/400);

        Double winnerExpectancy = qWinner/(qWinner + qLoser);
        Double loserExpectancy = qLoser/(qWinner + qLoser);

        int newWinnerElo = (int) Math.floor(winnerElo + 24 * (1 - winnerExpectancy));
        int newLoserElo = (int) Math.floor(loserElo + 24 * (0 - loserExpectancy));

        winner.setElo(newWinnerElo);
        loser.setElo(newLoserElo);
    }

    public void getLeaderboard(final String login, final LeaderboardActivity activity) {
        DatabaseReference mReference = mDatabase.getReference();
        Query usersByEloQuery = mReference.child("users").orderByChild("elo");
        usersByEloQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    users.add(snapshot.getValue(UserMarker.class));
                }
                int i = indexOfName(users, login);
                if (i < 0) {
                    // TODO some sort of error message
                } else if (i < 10) {
                    int m = Math.max(10, users.size());
                    leaderboard = new ArrayList<>(users.subList(0, m));
                } else if (users.size() - i < 4) {
                    leaderboard = new ArrayList<>(users.subList(users.size() -10, users.size()));
                } else {
                    leaderboard = new ArrayList<>(users.subList(0, 2));
                    leaderboard.addAll(users.subList(i - 2, i + 3));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Exception e = databaseError.toException();
                e.printStackTrace();
            }
        });
    }
}
