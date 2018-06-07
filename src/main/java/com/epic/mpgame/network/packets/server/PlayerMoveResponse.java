/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.network.packets.server;

import com.epic.mpgame.network.packets.APacket;

/**
 *
 * @author Epic
 */
public class PlayerMoveResponse extends APacket {

    private int moveId;
    private boolean isAccepted;

    public PlayerMoveResponse() {
        super(0);
    }

}
