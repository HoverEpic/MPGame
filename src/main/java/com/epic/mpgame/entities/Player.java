/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.entities;

import com.epic.mpgame.network.packets.IPacket;
import com.epic.mpgame.network.packets.Packets;
import com.epic.mpgame.network.packets.server.ChatMessage;
import com.epic.mpgame.network.packets.server.UpdateScore;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Epic
 */
public class Player extends Entity {

    private final WebSocketSession connection;
    private boolean isLoggedIn = false;
    private int kills = 0;
    private int death = 0;

    public Player(int id, WebSocketSession connection) {
        super(id, "");
        super.setLife(10);
        this.connection = connection;
    }

    public void sendChatMessage(String from, String message) {
        sendWebSocketMessage(new ChatMessage(from, message));
    }

    public void sendWebSocketMessage(IPacket packet) {
        try {
            synchronized(connection) {
                connection.sendMessage(Packets.getServerPacket(packet));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player) obj;
            return other.getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.connection);
        return hash;
    }

    /**
     * @return the isLoggedIn
     */
    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * @return the kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * @param kills the kills to set
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * @return the death
     */
    public int getDeath() {
        return death;
    }

    /**
     * @param death the death to set
     */
    public void setDeath(int death) {
        this.death = death;
    }

    public void updayeScore() {
        sendWebSocketMessage(new UpdateScore(this));
    }
}
