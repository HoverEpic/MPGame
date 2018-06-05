/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.server;

import com.epic.movethis.entities.Entity;
import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;

/**
 *
 * @author Epic
 */
public class EntityUpdate extends APacket {

    private int entityId;
    private String entityName;
    private int health;
    private int maxHealth;

    public EntityUpdate() {
        super(Packets.PACKET_UPDATE_ENTITY.getPacketID());
    }

    public EntityUpdate(Entity entity) {
        super(Packets.PACKET_UPDATE_ENTITY.getPacketID());
        this.entityId = entity.getId();
        this.entityName = entity.getName();
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
        this.health = entity.getLife();
        this.maxHealth = entity.getMaxLife();
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
