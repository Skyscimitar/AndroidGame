package com.example.danieltaylor.androidgame.MultiPlayerGame;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.danieltaylor.androidgame.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MultiPlayerGameActivity extends AppCompatActivity {

    private static final int RC_SELECT_PLAYERS = 9010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_game);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled or other unrecoverable error
                return;
            }
            ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get autoMatch criteria
            Bundle autoMatchCriteria = null;
            int minAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            TurnBasedMatchConfig.Builder builder = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees);
            if (minAutoPlayers > 0) {
                builder.setAutoMatchCriteria(
                        RoomConfig.createAutoMatchCriteria(minAutoPlayers, maxAutoPlayers, 0));
            }
            Games.getTurnBasedMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .createMatch(builder.build()).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
                @Override
                public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                    if (task.isSuccessful()) {
                        TurnBasedMatch match = task.getResult();
                        if (match.getData() == null) {
                            // First turn, initialize game data
                            // TODO implement this -> set characters for each player
                            initializeGameData(match);
                        }
                        // Show turn UI
                        showTurnUI(match);
                    } else {
                        // Error encountered
                        int status = CommonStatusCodes.DEVELOPER_ERROR;
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            status = apiException.getStatusCode();
                        }
                        handleError(status, exception);
                    }
                }
            });
        }
    }

    private void playTurn(TurnBasedMatch match) {
        String nextParticipantId = getNextParticipantId(mMyPlayerId, match);

        // This calls a game specific method to get the bytes that represent the game state
        // including the current player's turn.
        byte[] gameData = serializeGameData();

        Games.getTurnBasedMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .takeTurn(match.getMatchId(), gameData, nextParticipantId)
                .addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
                    @Override
                    public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                        if (task.isSuccessful()) {
                            TurnBasedMatch match = task.getResult();
                        } else {
                            // Handle exceptions.
                        }
                    }
                });
    }

    public void onStartMatchClicked(View view) {
        boolean allowAutoMatch = true;
        Games.getTurnBasedMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getSelectOpponentsIntent(1, 2, allowAutoMatch)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_SELECT_PLAYERS);
                    }
                });
    }

    public String getNextParticipantId(String myPlayerId, TurnBasedMatch match) {
        String myParticipantId = match.getParticipantId(myPlayerId);

        ArrayList<String> participantIds = match.getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (match.getAvailableAutoMatchSlots() <= 0) {
            // You've run out of automatch slots, so we start over.
            return participantIds.get(0);
        } else {
            // You have not yet fully automatched, so null will find a new
            // person to play against.
            return null;
        }
    }
}
