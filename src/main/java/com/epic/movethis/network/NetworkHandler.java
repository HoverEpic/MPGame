/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.network;

import com.epic.movethis.App;
import com.epic.movethis.entities.Player;
import com.epic.movethis.map.WorldMap;
import com.epic.movethis.network.packets.IPacket;
import com.epic.movethis.network.packets.Packets;
import com.epic.movethis.network.packets.player.PlayerAttack;
import com.epic.movethis.network.packets.player.PlayerChat;
import com.epic.movethis.network.packets.player.PlayerDamage;
import com.epic.movethis.network.packets.player.PlayerJoin;
import com.epic.movethis.network.packets.player.PlayerMove;
import com.epic.movethis.network.packets.player.PlayerReady;
import com.epic.movethis.network.packets.server.ChatMessage;
import com.epic.movethis.network.packets.server.EntityAdd;
import com.epic.movethis.network.packets.server.EntityMove;
import com.epic.movethis.network.packets.server.EntityRemove;
import com.epic.movethis.network.packets.server.EntityUpdate;
import com.epic.movethis.network.packets.server.JoinResponse;
import com.epic.movethis.network.packets.server.PlayAttack;
import com.epic.movethis.network.packets.server.StartGame;
import com.epic.movethis.network.packets.server.UpdateScore;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Epic
 */
public class NetworkHandler extends TextWebSocketHandler {

    public static Pattern playerNamePattern = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // The WebSocket has been opened
        System.out.println("Session " + session.getId() + " opened!");
        // create the player object
        Player player = App.getInstance().getGameManager().getEntitiesManager().createNewPlayer(session);
        player.sendChatMessage(null, "You are now connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // The WebSocket has been closed
        System.out.println("Session " + session.getId() + " closed!");
        // remove the player object
        Player player = App.getInstance().getGameManager().getEntitiesManager().removePlayer(session);
        if (player != null) {
            //remove entity on clients
            App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new EntityRemove(player.getId()));
            //send disconnect message to other players
            App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new ChatMessage(player.getName() + " leaved the game !"));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // A message has been received

        //get the player object
        Player player = App.getInstance().getGameManager().getEntitiesManager().getPlayer(session);
        if (player == null)
        {
            System.out.println("Player is null, ending session !");
            session.close();
            return;
        }

        // handle packet
        IPacket packet = Packets.decodeClientPacket(textMessage.getPayload());
        if (packet != null) {
            if (packet instanceof PlayerJoin) {

                // check if the playername is correct (not empty/only alphanumerical chars)
                String playername = ((PlayerJoin) packet).getName();
                if (playername.length() == 0) {
                    player.sendWebSocketMessage(new JoinResponse(JoinResponse.JOIN_STATUS_ERROR, "Username cannot be empty !"));
                    session.close();
                    return;
                }
                if (!playerNamePattern.matcher(playername).matches()) {
                    player.sendWebSocketMessage(new JoinResponse(JoinResponse.JOIN_STATUS_ERROR, "Username must contains only letters and numbers !"));
                    session.close();
                    return;
                }

                // check if the playername already exist
                if (App.getInstance().getGameManager().getEntitiesManager().getPlayerByName(playername) != null) {
                    player.sendWebSocketMessage(new JoinResponse(JoinResponse.JOIN_STATUS_ERROR, "A player with this name is already logged in !"));
                    session.close();
                    return;
                }

                //success login, fill player data
                player.setIsLoggedIn(true);
                player.setName(((PlayerJoin) packet).getName());
                player.sendWebSocketMessage(new JoinResponse(JoinResponse.JOIN_STATUS_SUCCESS));
                StartGame startGamePacket = new StartGame(player.getId(), "Death by Tomatos !");
                player.setX(WorldMap.spawnX);
                player.setY(WorldMap.spawnY);
                startGamePacket.setSpawnPoint(WorldMap.spawnX, WorldMap.spawnY);
                player.sendWebSocketMessage(startGamePacket);
                // broadcast join message
                App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new ChatMessage(player.getName() + " joined the game !"));
            }
            if (packet instanceof PlayerReady) {

                //hackfix wait for the game start on client
//                Thread.sleep(1000);

                // send already loggedin players list to new player
                for (Player alreadyLoggedInPlayer : App.getInstance().getGameManager().getEntitiesManager().getPlayers()) {
                    if (!alreadyLoggedInPlayer.equals(player)) {
                        player.sendWebSocketMessage(new EntityAdd(alreadyLoggedInPlayer));
                    }
                }

                // send already loggedin players list
                for (Player alreadyLoggedInPlayer : App.getInstance().getGameManager().getEntitiesManager().getPlayers()) {
//                    if (!alreadyLoggedInPlayer.equals(player)) {
                        alreadyLoggedInPlayer.sendWebSocketMessage(new EntityAdd(player));
//                    }
                }
            }
            if (packet instanceof PlayerChat) {
                //broadcast chat message
                App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new ChatMessage(player.getName(), ((PlayerChat) packet).getMessage()));
            }
            if (packet instanceof PlayerAttack) {
                //broadcast attack
                App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new PlayAttack(player, (PlayerAttack) packet));
            }
            if (packet instanceof PlayerDamage) {
                PlayerDamage playerDamagePacket = (PlayerDamage) packet;
                // TODO calculate damage for diferent bullets
                player.setLife(player.getLife() - 1);
                if (player.getLife() <= 0) {
                    //player is dead !
                    player.setDeath(player.getDeath() + 1);
                    player.setX(WorldMap.spawnX);
                    player.setY(WorldMap.spawnY);
                    player.move();
                    int idKilledBy = playerDamagePacket.getFrom();
                    Player killedBy = App.getInstance().getGameManager().getEntitiesManager().getPlayerById(idKilledBy);
                    if (killedBy != null) {
                        killedBy.setKills(killedBy.getKills() + 1);
                        killedBy.updayeScore();
                    }
                    player.setLife(player.getMaxLife());
                }
                player.updayeScore();
                player.updateLife();
            }
            if (packet instanceof PlayerMove) {
                player.setX(((PlayerMove) packet).getX());
                player.setY(((PlayerMove) packet).getY());
                //broadcast move
                App.getInstance().getGameManager().getEntitiesManager().broadcastToPlayers(new EntityMove(player.getId(), player.getX(), player.getY()), Collections.singletonList(player));
//                System.out.println("Player " + player.getName() +" move " + ((PlayerMove) packet).getX() + "/" + player.getY());
            }
        } else {
//            System.out.println("Unhandled packet : " + textMessage.getPayload());
        }
//        session.sendMessage(new TextMessage("COUCOU " + new String(textMessage.asBytes())));
    }
}
