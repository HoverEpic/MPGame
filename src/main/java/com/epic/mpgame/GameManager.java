/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.mpgame;

import com.epic.mpgame.managers.EntitiesManager;

/**
 *
 * @author Epic
 */
public class GameManager {

    private final EntitiesManager entitiesManager = new EntitiesManager();

    /**
     * @return the entitiesManager
     */
    public EntitiesManager getEntitiesManager() {
        return entitiesManager;
    }

}
