/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network.packets.server;

import com.epic.movethis.entities.Player;
import com.epic.movethis.network.packets.APacket;
import com.epic.movethis.network.packets.Packets;
import com.epic.movethis.network.packets.player.PlayerAttack;

/**
 *
 * @author Epic
 */
public class PlayAttack extends APacket {

    private final int attackerId;
    private final String attack;
    private final double x, y;
    private double velocityX, velocityY;

    public PlayAttack(int attackerId, String attack, double x, double y, double velocityX, double velocityY) {
        super(Packets.PACKET_PLAY_ATTACK.getPacketID());
        this.attackerId = attackerId;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public PlayAttack(Player player, PlayerAttack playerAttack) {
        super(Packets.PACKET_PLAY_ATTACK.getPacketID());
        this.attackerId = player.getId();
        this.attack = playerAttack.getAttack();
        this.x = playerAttack.getX();
        this.y = playerAttack.getY();
        this.velocityX = playerAttack.getVelocityX();
        this.velocityY = playerAttack.getVelocityY();
    }

}
