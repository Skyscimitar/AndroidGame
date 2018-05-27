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
    private Player userPlayer;
    private Player aiPlayer;

    public SinglePlayerGame(ArrayList<Player> playerList,Player userPlayer, Player aiPlayer){
        this.playerList = playerList;
        this.userPlayer = userPlayer;
        this.aiPlayer = aiPlayer;

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
        //TODO optimize the code -> the same code should be used for the aiPlayer and the userPlayer updates
        switch (action){
            case "ATTACK":
                if (player.getPlayerID().equals(userPlayer.getPlayerID())) {
                    aiPlayer = takeDamage(aiPlayer, player);
                    if (userPlayer.increasedDefense) {
                        userPlayer.increasedDefense = false;
                        userPlayer.character.setDefense(player.character.getDefense() - 3);
                    }

                } else {
                    userPlayer = takeDamage(userPlayer, player);
                    if (aiPlayer.increasedDefense) {
                        aiPlayer.increasedDefense = false;
                        aiPlayer.character.setDefense(player.character.getDefense() - 3);
                    }
                }
            case "DEFEND":
                if (player.getPlayerID().equals(userPlayer.getPlayerID())) {
                    if (!userPlayer.increasedDefense) {
                        userPlayer.increasedDefense = true;
                        userPlayer.character.setDefense(player.character.getDefense() + 3);
                    }
                } else {
                    if (!aiPlayer.increasedDefense) {
                        aiPlayer.increasedDefense = true;
                        aiPlayer.character.setDefense(player.character.getDefense() + 3);
                    }
                }
            case "HEAL":
                if (player.getPlayerID().equals(userPlayer.getPlayerID())) {
                    userPlayer = healCharacter(userPlayer);
                    if (userPlayer.increasedDefense) {
                        userPlayer.increasedDefense = false;
                        userPlayer.character.setDefense(player.character.getDefense() - 3);
                    }

                } else {
                    aiPlayer = healCharacter(aiPlayer);
                    if (aiPlayer.increasedDefense) {
                        aiPlayer.increasedDefense = false;
                        aiPlayer.character.setDefense(player.character.getDefense() - 3);
                    }
                }
        }

    }


    //increments turn
    public void incrementTurn(){
        if (currentPlayerTurnPosition < playerList.size()-1) {
            currentPlayerTurnPosition += 1;
        }
        else {
            currentPlayerTurnPosition = 0;
        }
        turnCounter += 1;
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
        if (aiPlayer.character.getHealth() <= 0) {
            winner = userPlayer;
            isGameOver = true;
            running = false;
        }
        if (userPlayer.character.getHealth() <= 0) {
            winner = aiPlayer;
            isGameOver = true;
            running = false;
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

    private Player takeDamage(Player victim, Player attacker) {
        int damage = attacker.character.getAttack() - victim.character.getDefense();
        //in certain conditions if a player has increased his defense this can happen
        if (damage <= 0) {
            damage = 1;
        }
        victim.character.setHealth(victim.character.getHealth() - damage);
        return victim;
    }

    private Player healCharacter(Player player) {
        int newHealth = player.character.getHealth() + 10;
        if (newHealth >= player.character.getTotalHealth()) {
            newHealth = player.character.getTotalHealth();
        }
        player.character.setHealth(newHealth);
        return player;
    }

}
