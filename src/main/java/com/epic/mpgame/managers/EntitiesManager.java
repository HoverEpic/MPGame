/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame.managers;

import com.epic.mpgame.entities.Entity;
import com.epic.mpgame.entities.Player;
import com.epic.mpgame.network.packets.IPacket;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Epic
 */
public class EntitiesManager {

    private final AtomicInteger entityIds = new AtomicInteger();

    private final Map<WebSocketSession, Integer> connections = new HashMap<>();
    private final Map<Integer, Entity> entities = new HashMap<>();

    /**
     * @return the entities
     */
    public Map<Integer, Entity> getEntities() {
        return entities;
    }

    /**
     * @return the players
     */
    public Map<WebSocketSession, Integer> getConnections() {
        return connections;
    }

    public List<Player> getPlayers() {
        return entities.values().stream().filter(Player.class::isInstance).map(player -> (Player) player).collect(Collectors.toList());
    }

    public Player createNewPlayer(WebSocketSession connection) {
        Player player = new Player(entityIds.getAndIncrement(), connection);
        connections.put(connection, player.getId());
        entities.put(player.getId(), player);
        return player;
    }

    public Player getPlayer(WebSocketSession connection) {
        return (Player) entities.get(connections.get(connection));
    }

    public Player getPlayerByName(String name) {
        Optional<Player> opt = getPlayers().stream().filter(player -> player.getName().equals(name)).findFirst();
        if (opt.isPresent())
            return opt.get();
        return null;
    }

    public Player getPlayerById(int id) {
        Optional<Player> opt = getPlayers().stream().filter(player -> player.getId() == id).findFirst();
        if (opt.isPresent())
            return opt.get();
        return null;
    }

    public Player removePlayer(WebSocketSession connection) {
        int entity = connections.get(connection);
        connections.remove(connection);
        Player player = (Player) entities.remove(entity);
        return player;
    }

    public void broadcastToPlayers(IPacket packet) {
        getPlayers().stream()
                .filter(player -> player.isIsLoggedIn())
                .forEach(player -> {
                    player.sendWebSocketMessage(packet);
                });
    }

    public void broadcastToPlayers(IPacket packet, Collection<Player> notSendPlayers) {
        getPlayers().stream()
                .filter(player -> player.isIsLoggedIn() && !notSendPlayers.contains(player))
                .forEach(player -> {
                    player.sendWebSocketMessage(packet);
                });
    }

    public boolean isPlayerConnected(String playername) {
        return connections.containsKey(playername);
    }

}
