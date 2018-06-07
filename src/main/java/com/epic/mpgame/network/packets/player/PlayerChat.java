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
public class PlayerChat extends APacket {

    private String message;

    public PlayerChat() {
        super(Packets.PACKET_PLAYER_CHAT.getPacketID());
    }

    public PlayerChat(String message) {
        super(Packets.PACKET_PLAYER_CHAT.getPacketID());
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void decode(JsonObject contentObj) {
        this.message = contentObj.get("message").getAsString();
    }
}
