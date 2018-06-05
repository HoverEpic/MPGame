/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 *                       Version 3, 29 June 2007
 *
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 *
 * You can view LICENCE file for details.
 *
 * @author The Dragonet Team
 */
package com.epic.movethis;

import com.epic.movethis.managers.EntitiesManager;
//import com.epic.movethis.network.NetworkHandler;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Gson gson = new Gson();

    private final GameManager gameManager = new GameManager();

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public App() {
        instance = this;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public Gson getGson() {
        return gson;
    }
//
//    @Autowired
//    private NetworkHandler networkHandler;

    /**
     * @return the gameManager
     */
    public GameManager getGameManager() {
        return gameManager;
    }

}
