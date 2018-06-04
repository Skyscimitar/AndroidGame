package com.example.danieltaylor.androidgame.game_elements;

import android.graphics.Bitmap;

public class Character {

    private int attack;
    private int defense;
    private int speed;
    private int health;
    private int totalHealth;
    private String name;
    private boolean isDead = false;

    private Bitmap frontappearance;
    private Bitmap backappearance;
    private Bitmap characterSelectionappearance;
    private int gifResourceID;
    private int number;

    public Character() {
        this.health = 100;
        this.totalHealth = 100;
    }

    public Character(int attack, int defense, int speed,Bitmap frontappearance, Bitmap backappearance, Bitmap characterSelectionappearance) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.health = 100;
        this.totalHealth = 100;
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

    public int getTotalHealth() {
        return this.totalHealth;
    }

    public boolean isDead(){
        return isDead;
    }

    public int getGifResourceID() {
        return gifResourceID;
    }

    public void setGifResourceID(int id) {
        this.gifResourceID = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
