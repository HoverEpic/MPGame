/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.network.packets;

import com.epic.mpgame.network.packets.player.*;
import com.epic.mpgame.network.packets.server.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.socket.TextMessage;

/**
 *
 * @author Epic
 */
public enum Packets {

    PACKET_PLAYER_JOIN(0, PlayerJoin.class, true, false),
    PACKET_JOIN_RESPONSE(1, JoinResponse.class, false, true),
    PACKET_PLAYER_CHAT(2, PlayerChat.class, true, false),
    PACKET_CHAT_MESSAGE(3, ChatMessage.class, false, true),
    PACKET_START_GAME(4, StartGame.class, false, true),
    PACKET_ADD_ENTITY(5, EntityAdd.class, false, true),
    PACKET_REMOVE_ENTITY(6, EntityRemove.class, false, true),
    PACKET_PLAYER_MOVE(7, PlayerMove.class, true, false),
    PACKET_MOVE_ENTITY(8, EntityMove.class, false, true),
    PACKET_PLAYER_ATTACK(9, PlayerAttack.class, true, false),
    PACKET_PLAY_ATTACK(10, PlayAttack.class, false, true),
    PACKET_PLAYER_DAMAGE(11, PlayerDamage.class, true, false),
    PACKET_UPDATE_ENTITY(12, EntityUpdate.class, false, true),
    PACKET_PLAYER_READY(13, PlayerReady.class, true, false),
    PACKET_UPDATE_SCORE(14, UpdateScore.class, false, true);

    // fields
    private final int packetID;
    private final Class packetClass;
    private final boolean fromClient;
    private final boolean fromServer;

    private static final JsonParser parser = new JsonParser();

    // packets maps
    private static final Map<Integer, Packets> packetsFromClient = new HashMap();
    private static final Map<Integer, Packets> packetsFromServer = new HashMap();

    Packets(int packetID, Class packetClass, boolean fromClient, boolean fromServer) {
        this.packetID = packetID;
        this.packetClass = packetClass;
        this.fromClient = fromClient;
        this.fromServer = fromServer;
    }

    public int getPacketID() {
        return this.packetID;
    }

    // fill packets maps
    static {
        for (Packets packet : Packets.values()) {
            if (packet.fromClient)
                packetsFromClient.put(packet.packetID, packet);
            if (packet.fromServer)
                packetsFromServer.put(packet.packetID, packet);
        }
    }

    public static IPacket decodeClientPacket(String data) {
//        System.out.println("Raw packet " + data);
        try {
            JsonObject contentObj = parser.parse(data).getAsJsonObject();
            int packetID = contentObj.get("packetID").getAsInt();
            if (packetsFromClient.containsKey(packetID)) {
                Class packetClass = packetsFromClient.get(packetID).packetClass;
                IPacket packet = (IPacket) packetClass.getConstructor().newInstance();
                packet.decode(contentObj);
//                System.out.println("Packet " + packet.getClass().getSimpleName() + " decoded ! " + contentObj.toString());
                return packet;
            }
        } catch (JsonSyntaxException | IllegalStateException | IllegalAccessException | InstantiationException | UnsupportedOperationException ex) {
//            Logger.getLogger(Packets.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Packets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static TextMessage getServerPacket(IPacket packet) {
        return new TextMessage(packet.encode());
    }

    /**
     * @return the packetsFromClient
     */
    public static Map<Integer, Packets> getPacketsFromClient() {
        return packetsFromClient;
    }

    /**
     * @return the packetsFromServer
     */
    public static Map<Integer, Packets> getPacketsFromServer() {
        return packetsFromServer;
    }

}
