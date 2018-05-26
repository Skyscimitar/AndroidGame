package com.example.danieltaylor.androidgame.SingleGame;

import com.example.danieltaylor.androidgame.GameElements.Character;
import com.example.danieltaylor.androidgame.GameElements.Player;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.icu.text.UnicodeSet.CASE;

public class SinglePlayerGame {

    private boolean running;
    private int turnCounter = 0;
    private boolean isGameOver = false;
    private int currentPlayerTurnPosition = 0;
    private ArrayList<Player> playerList;
    private Player winner;

    public SinglePlayerGame(ArrayList<Player> playerList){
        this.playerList = playerList;
    }

    public Player getWinner(){
        return winner;
    }


    //starts the game, decides which player starts
    public void startGame(){
        this.running = true;
    }


    // user informs the game he has taken his turn
    public boolean takeTurn(Player player, String action){
        //make sure it's the player updating the data turn
        if (playerList.get(currentPlayerTurnPosition).getPlayerID().equals(player.getPlayerID())){
            updateGameData(player, action);
            return true;
        }
        return false;
    }


    //updates the game's data
    private void updateGameData(Player player, String action){
        takeAction(player, action);
        checkGameStatus();
        incrementTurn();
    }

    private void takeAction(Player player, String action) {
        switch (action){
            case "ATTACK":
                for (Player p:playerList) {
                    if (!p.getPlayerID().equals(player.getPlayerID())) {
                        p.character.takeDamage(player.character);
                    }
                    player.increasedDefense = false;
                }
            case "DEFEND":
                if (!player.increasedDefense) {
                    player.increasedDefense = true;
                    player.character.setDefense(player.character.getDefense() + 3);
                } else {
                    player.increasedDefense = false;
                }
            case "HEAL":
                player.character.setHealth(player.character.getHealth() + 10);
                player.increasedDefense = false;
        }

    }


    //increments turn
    public void incrementTurn(){
        if (currentPlayerTurnPosition < playerList.size()) {
            currentPlayerTurnPosition += 1;
        }
        else {
            currentPlayerTurnPosition = 0;
        }
    }

    // tells the gameThread if it is the user's turn or not
    public boolean isPlayerTurn(Player player){
        return playerList.get(currentPlayerTurnPosition).getPlayerID().equals(player.getPlayerID());
    }

    public boolean isGameRunning() {
        return running && !isGameOver;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private void checkGameStatus(){
        //used to track if someone lost this turn
        boolean playerLost = false;
        for (Player player:playerList) {
            if (player.character.isDead()) {
                player.loose();
                playerLost = true;
            }
        }
    }

    public Player updatePlayer(Player player) {
        Player updatedPlayer = new Player(new Character());
        for (Player p:playerList) {
            if (p.getPlayerID().equals(player.getPlayerID())){
                updatedPlayer = p;
            }
        }
        return updatedPlayer;
    }


}
