/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.server;

import com.epic.movethis.entities.Player;
import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;

/**
 *
 * @author Epic
 */
public class UpdateScore extends APacket {

    private final int kills, death;

    public UpdateScore(Player player) {
        super(Packets.PACKET_UPDATE_SCORE.getPacketID());
        this.kills = player.getKills();
        this.death = player.getDeath();
    }
}
