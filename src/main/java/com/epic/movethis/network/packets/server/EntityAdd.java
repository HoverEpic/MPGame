/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.server;

import com.epic.movethis.entities.Entity;
import com.epic.movethis.entities.Player;
import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;

/**
 *
 * @author Epic
 */
public class EntityAdd extends APacket {

    private int entityId;
    private String entityName;
    private boolean isPlayer;
    private double x, y;
    private int health;
    private int maxHealth;

    public EntityAdd() {
        super(Packets.PACKET_ADD_ENTITY.getPacketID());
    }

    public EntityAdd(Entity entity) {
        super(Packets.PACKET_ADD_ENTITY.getPacketID());
        this.entityId = entity.getId();
        this.entityName = entity.getName();
        this.isPlayer = entity instanceof Player;
        this.x = entity.getX();
        this.y = entity.getY();
        this.health = entity.getLife();
        this.maxHealth = entity.getMaxLife();
    }

    /**
     * @return the entityId
     */
    public int getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setEntity(Entity entity) {
        this.entityId = entity.getId();
        this.entityName = entity.getName();
        this.isPlayer = entity instanceof Player;
        this.x = entity.getX();
        this.y = entity.getY();
        this.health = entity.getLife();
        this.maxHealth = 20;
    }

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the isPlayer
     */
    public boolean isIsPlayer() {
        return isPlayer;
    }

    /**
     * @param isPlayer the isPlayer to set
     */
    public void setIsPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
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
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth the maxHealth to set
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

}
