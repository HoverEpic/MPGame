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
public class PlayerMove extends APacket {

    private double x;
    private double y;

    public PlayerMove() {
        super(Packets.PACKET_PLAYER_MOVE.getPacketID());
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

    @Override
    public void decode(JsonObject contentObj) {
        this.x = contentObj.get("x").getAsDouble();
        this.y = contentObj.get("y").getAsDouble();
    }
}
