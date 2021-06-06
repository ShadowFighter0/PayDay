package com.dprieto.game;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Board {

    TiledMap gameBoard;
    MapObjects boxes;
    OrthogonalTiledMapRenderer boardRenderer;

    int width;
    int height;

    ArrayList<String> boxNames;


    public Board()
    {
        gameBoard = new TmxMapLoader().load("Maps/Tiled_Map.tmx");

        int boardWidth = gameBoard.getProperties().get("width", Integer.class);
        int boardHeight = gameBoard.getProperties().get("height", Integer.class);

        int tileWidth = gameBoard.getProperties().get("tileWidth", Integer.class);
        int tileHeight = gameBoard.getProperties().get("tileHeight", Integer.class);

        width = boardWidth * tileWidth;
        height = boardHeight * tileHeight;

        boxes = gameBoard.getLayers().get("Boxes").getObjects();

        boardRenderer = new OrthogonalTiledMapRenderer(gameBoard, 1f);

        boxNames = new ArrayList<String>();
    }

    public void getBoxesInformation()
    {
        for (int i = 0; i < boxes.getCount(); i++)
        {
            Vector2 position = new Vector2(boxes.get(i).getProperties().get("x", Float.class), boxes.get(i).getProperties().get("y", Float.class));
            boxNames.add(new String(boxes.get(i).getProperties().get("Text", String.class)));
        }
    }
}
