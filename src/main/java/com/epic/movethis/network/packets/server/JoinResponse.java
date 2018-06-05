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
 * Sent by the client on join
 */
public class JoinResponse extends APacket {

    public final static int JOIN_STATUS_ERROR = 0;
    public final static int JOIN_STATUS_SUCCESS = 1;

    private int status;
    private String message;

    public JoinResponse(int status) {
        super(Packets.PACKET_JOIN_RESPONSE.getPacketID());
        this.status = status;
    }

    public JoinResponse(int status, String message) {
        super(Packets.PACKET_JOIN_RESPONSE.getPacketID());
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
