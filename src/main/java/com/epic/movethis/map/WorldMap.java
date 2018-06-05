/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.movethis.map;

import java.io.File;
import java.util.List;

/**
 *
 * @author Epic
 *
 */
// example map :
//
// 1 = non walkable
// 0 = walkable
// 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
// 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
public class WorldMap {

    private String name;
    private File background;
    private List<String> areas;

    public static final int spawnX = 100;
    public static final int spawnY = 450;
}
