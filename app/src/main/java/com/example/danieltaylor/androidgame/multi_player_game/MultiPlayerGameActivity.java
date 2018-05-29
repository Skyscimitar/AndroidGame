package com.example.danieltaylor.androidgame.multi_player_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    public TurnBasedMatch mMatch;
    public Turn mTurnData;
    ConstraintLayout layout;
    ProgressBar spinner;
    TextView searchForOpponentText;
    Button btnStartMatch;

    private String mMyPlayerId; // TODO set ce truc, je sais pas trop ou
    private AlertDialog mAlertDialog;

    //TODO make sure the game only ever has 2 players in it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_game);
        spinner = findViewById(R.id.matchmaking_spinner);
        searchForOpponentText = findViewById(R.id.search_for_opponent_text);
        btnStartMatch = findViewById(R.id.start_match);
        btnStartMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartMatchClicked(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled or other unrecoverable error
                // TODO update the ui accordingly (error message) remove the spinner and text, redisplay the button
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
                            // TODO implement this -> set characters for each player initializeGameData(match);

                        }
                        // Show turn UI
                        // TODO showTurnUI(match);
                    } else {
                        // Error encountered
                        int status = CommonStatusCodes.DEVELOPER_ERROR;
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            status = apiException.getStatusCode();
                        }
                        // Handle exception

                    }
                }
            });
        }
    }

    public void startMatch(TurnBasedMatch match) {
        mTurnData = new Turn();
        // Some basic turn data
        mTurnData.data = "First turn";

        mMatch = match;

        String myParticipantId = mMatch.getParticipantId(mMyPlayerId);

        Games.getTurnBasedMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this)).takeTurn(match.getMatchId(),
                mTurnData.persist(), myParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        updateMatch(turnBasedMatch);
                    }
                });
                // Handle exception?
    }

    private void playTurn(TurnBasedMatch match) {
        //String nextParticipantId = getNextParticipantId(mMyPlayerId, match);

        // This calls a game specific method to get the bytes that represent the game state
        // including the current player's turn.

        // byte[] gameData = serializeGameData(); j'ai pas compris ce bail

        /*Games.getTurnBasedMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .takeTurn(match.getMatchId(), mTurnData.persist(), nextParticipantId)
                .addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
                    @Override
                    public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                        if (task.isSuccessful()) {
                            TurnBasedMatch match = task.getResult();
                        } else {
                            // Handle exceptions
                        }
                    }
                });*/
    }

    public void onStartMatchClicked(View view) {
        btnStartMatch.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        searchForOpponentText.setVisibility(View.VISIBLE);
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

    public void updateMatch(TurnBasedMatch match) {
        mMatch = match;

        int status = match.getStatus();
        int turnStatus = match.getTurnStatus();

        switch (status) {
            case TurnBasedMatch.MATCH_STATUS_CANCELED:
                showWarning("Canceled!", "This game was canceled!");
                return;
            case TurnBasedMatch.MATCH_STATUS_EXPIRED:
                showWarning("Expired!", "This game is expired.  So sad!");
                return;
            case TurnBasedMatch.MATCH_STATUS_AUTO_MATCHING:
                showWarning("Waiting for auto-match...",
                        "We're still waiting for an automatch partner.");
                return;
            case TurnBasedMatch.MATCH_STATUS_COMPLETE:
                if (turnStatus == TurnBasedMatch.MATCH_TURN_STATUS_COMPLETE) {
                    showWarning("Complete!",
                            "This game is over; someone finished it, and so did you!  " +
                                    "There is nothing to be done.");
                    break;
                }
                // Note that in this state, you must still call "Finish" yourself,
                // so we allow this to continue.
                showWarning("Complete!",
                        "This game is over; someone finished it!  You can only finish it now.");
        }
        // OK, it's active. Check on turn status.
        switch (turnStatus) {
            case TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN:
                mTurnData = Turn.unpersist(mMatch.getData());
                // TODO showTurnUI(mMatch);
                return;
            case TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN:
                // Should return results.
                showWarning("Alas...", "It's not your turn.");
                break;
            case TurnBasedMatch.MATCH_TURN_STATUS_INVITED:
                showWarning("Good inititative!",
                        "Still waiting for invitations.\n\nBe patient!");
        }

        mTurnData = null;
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

    public void showWarning(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(title).setMessage(message);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                    }
                });

        // create alert dialog
        mAlertDialog = alertDialogBuilder.create();

        // show it
        mAlertDialog.show();
    }
}
