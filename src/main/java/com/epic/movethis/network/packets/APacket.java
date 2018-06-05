/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets;

import com.epic.movethis.App;
import com.google.gson.JsonObject;

/**
 *
 * @author Epic
 */
public abstract class APacket implements IPacket {

    private final int packetID;

    public APacket(int packetID) {
        this.packetID = packetID;
    }

    public int getPacketID() {
        return this.packetID;
    }

    @Override
    public String encode() {
//        System.out.println("encode " + App.getInstance().getGson().toJson(this));
        return App.getInstance().getGson().toJson(this);
    }

    @Override
    public void decode(JsonObject contentObj) {

    }
}
