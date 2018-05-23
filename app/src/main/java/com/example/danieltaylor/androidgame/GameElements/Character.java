package com.example.danieltaylor.androidgame.GameElements;

import android.graphics.Bitmap;

public class Character {

    private int attack;
    private int defense;
    private int speed;
    private int health;

    private Bitmap frontappearance;
    private Bitmap backappearance;
    private Bitmap characterSelectionappearance;

    public Character(int attack, int defense, int speed,Bitmap frontappearance, Bitmap backappearance, Bitmap characterSelectionappearance) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.health = 100;
        this.frontappearance = frontappearance;
        this.backappearance = backappearance;
        this.characterSelectionappearance = characterSelectionappearance;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Bitmap getFrontappearance() {
        return frontappearance;
    }

    public void setFrontappearance(Bitmap frontappearance) {
        this.frontappearance = frontappearance;
    }

    public Bitmap getBackappearance() {
        return backappearance;
    }

    public void setBackappearance(Bitmap backappearance) {
        this.backappearance = backappearance;
    }

    public Bitmap getCharacterSelectionappearance() {
        return characterSelectionappearance;
    }

    public void setCharacterSelectionappearance(Bitmap characterSelectionappearance) {
        this.characterSelectionappearance = characterSelectionappearance;
    }
}
