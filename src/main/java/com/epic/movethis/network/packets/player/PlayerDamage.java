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
 */
public class PlayerDamage extends APacket {

    private String attack;
    private int from;

    public PlayerDamage() {
        super(Packets.PACKET_PLAYER_DAMAGE.getPacketID());
    }

    /**
     * @return the attack
     */
    public String getAttack() {
        return attack;
    }

    /**
     * @return the from
     */
    public int getFrom() {
        return from;
    }

    @Override
    public void decode(JsonObject contentObj) {
        this.attack = contentObj.get("attack").getAsString();
        this.from = contentObj.get("from").getAsInt();
    }
}
