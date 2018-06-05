/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.player;

import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;

/**
 *
 * @author Epic
 * Sent by the client on join
 */
public class PlayerReady extends APacket {

    public PlayerReady() {
        super(Packets.PACKET_PLAYER_JOIN.getPacketID());
    }
}
