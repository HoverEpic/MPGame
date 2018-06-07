/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.network.packets.player;

import com.epic.mpgame.network.packets.APacket;
import com.epic.mpgame.network.packets.Packets;
import com.google.gson.JsonObject;

/**
 *
 * @author Epic
 */
public class PlayerAttack extends APacket {

    private String attack;
    private double x, y;
    private double velocityX, velocityY;

    public PlayerAttack() {
        super(Packets.PACKET_PLAYER_ATTACK.getPacketID());
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @return the attack
     */
    public String getAttack() {
        return attack;
    }

    /**
     * @return the velocityX
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * @return the velocityY
     */
    public double getVelocityY() {
        return velocityY;
    }

    @Override
    public void decode(JsonObject contentObj) {
        this.attack = contentObj.get("attack").getAsString();
        this.x = contentObj.get("x").getAsDouble();
        this.y = contentObj.get("y").getAsDouble();
        this.velocityX = contentObj.get("velocityX").getAsDouble();
        this.velocityY = contentObj.get("velocityY").getAsDouble();
    }
}
