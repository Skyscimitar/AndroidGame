package com.example.danieltaylor.androidgame.GameElements;

import android.graphics.Bitmap;

public class Character {

    private double attack;
    private double defense;
    private double speed;
    private double health;

    private Bitmap frontappearance;
    private Bitmap backappearance;
    private Bitmap characterSelectionappearance;


    public Character(double attack, double defense, double speed, Bitmap frontappearance, Bitmap backappearance, Bitmap characterSelectionappearance) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.frontappearance = frontappearance;
        this.backappearance = backappearance;
        this.characterSelectionappearance = characterSelectionappearance;
        this.health = 100;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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
