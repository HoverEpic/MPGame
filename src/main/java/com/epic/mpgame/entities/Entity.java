/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.entities;

import com.epic.mpgame.App;
import com.epic.mpgame.network.packets.server.EntityMove;
import com.epic.mpgame.network.packets.server.EntityUpdate;

/**
 *
 * @author Epic
 */
public abstract class Entity {

    private final int id;
    private String name;
    private int life, maxLife = 10;
    private double x;
    private double y;

    public Entity(int id) {
        this.id = id;
    }

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the maxLife
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * @param maxLife the maxLife to set
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public void updateLife() {
        App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new EntityUpdate(this));
    }

    public void move() {
        App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new EntityMove(getId(), getX(), getY()));
    }
}
