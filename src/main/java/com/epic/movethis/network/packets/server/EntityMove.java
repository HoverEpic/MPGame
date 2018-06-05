/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.server;

import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;

/**
 *
 * @author Epic
 */
public class EntityMove extends APacket {

    private final int entityId;
    private final double x;
    private final double y;

    public EntityMove(int entityId, double x, double y) {
        super(Packets.PACKET_MOVE_ENTITY.getPacketID());
        this.entityId = entityId;
        this.x = x;
        this.y = y;
    }

}
