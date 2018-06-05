/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.player;

import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;
import com.google.gson.JsonObject;

/**
 *
 * @author Epic
 * Sent by the client on join
 */
public class PlayerJoin extends APacket {

    private String playername;

    public PlayerJoin() {
        super(Packets.PACKET_PLAYER_JOIN.getPacketID());
    }

    public String getName() {
        return playername;
    }

    public void setName(String name) {
        this.playername = name;
    }

    @Override
    public void decode(JsonObject contentObj) {
        this.playername = contentObj.get("playername").getAsString();
    }
}
