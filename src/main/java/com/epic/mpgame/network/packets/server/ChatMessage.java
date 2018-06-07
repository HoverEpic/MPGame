/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.network.packets.server;

import com.epic.mpgame.network.packets.APacket;
import com.epic.mpgame.network.packets.Packets;

/**
 *
 * @author Epic
 */
public class ChatMessage extends APacket {

    private String player;
    private String message;

    public ChatMessage() {
        super(Packets.PACKET_CHAT_MESSAGE.getPacketID());
    }

    public ChatMessage(String player, String message) {
        super(Packets.PACKET_CHAT_MESSAGE.getPacketID());
        this.player = player;
        this.message = message;
    }

    public ChatMessage(String message) {
        super(Packets.PACKET_CHAT_MESSAGE.getPacketID());
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
