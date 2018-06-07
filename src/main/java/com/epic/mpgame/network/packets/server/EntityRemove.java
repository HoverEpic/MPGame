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
public class EntityRemove extends APacket {

    private int entityId;

    public EntityRemove(int entityId) {
        super(Packets.PACKET_REMOVE_ENTITY.getPacketID());
        this.entityId = entityId;
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

}
