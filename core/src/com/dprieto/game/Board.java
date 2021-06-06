package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    Level level;

    int width;
    int height;

    ArrayList<String> boxNames;

    public Board(Level level)
    {
        gameBoard = new TmxMapLoader().load("Maps/Tiled_Map.tmx");

        this.level = level;

        int boardWidth = gameBoard.getProperties().get("width", Integer.class);
        int boardHeight = gameBoard.getProperties().get("height", Integer.class);

        int tileWidth = gameBoard.getProperties().get("tilewidth", Integer.class);
        int tileHeight = gameBoard.getProperties().get("tileheight", Integer.class);

        width = boardWidth * tileWidth;
        height = boardHeight * tileHeight;

        boxes = gameBoard.getLayers().get("Boxes").getObjects();

        boardRenderer = new OrthogonalTiledMapRenderer(gameBoard, 1f);

        getBoxesInformation();
    }

    public void render(SpriteBatch batch)
    {
        boardRenderer.setView(level.tableCamera.orthographicCamera);
        boardRenderer.render();
    }

    public void getBoxesInformation()
    {
        for (int i = boxes.getCount() - 1; i >= 0; i--)
        {

            Gdx.app.debug("Square", "" + boxes.get(i).getName());
            Gdx.app.debug("SquareType", "" + Constants.SquareType.valueOf(boxes.get(i).getName()));



            level.tableSquares.add(new TableSquare(Constants.SquareType.valueOf(boxes.get(i).getName()),
                    new Vector2(boxes.get(i).getProperties().get("x", Float.class), boxes.get(i).getProperties().get("y", Float.class))));

            //Vector2 position = ;new Vector2(boxes.get(i).getProperties().get("x", Float.class), boxes.get(i).getProperties().get("y", Float.class));
            //boxNames.add(new String(boxes.get(i).getProperties().get("Text", String.class)));
        }
    }
}
