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
 * @author Epic Sent by the server on join
 */
public class StartGame extends APacket {

    private final int playerId;
    private final String gameName;
    private double x, y;
    private int maxPlayers;

    public StartGame(int playerId, String gameName) {
        super(Packets.PACKET_START_GAME.getPacketID());
        this.playerId = playerId;
        this.gameName = gameName;
    }

    public void setSpawnPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
