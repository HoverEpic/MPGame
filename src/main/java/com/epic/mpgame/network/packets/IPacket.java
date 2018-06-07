/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.network.packets;

import com.google.gson.JsonObject;

/**
 *
 * @author Epic
 */
public interface IPacket {

    public String encode();

    public void decode(JsonObject contentObj);
}
