package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class LevelFactory {

    static LevelFactory instance;

    LevelFactory()
    {

    }

    public static LevelFactory getInstance()
    {
        if(instance == null)
        {
            instance = new LevelFactory();
        }
        return instance;
    }

    public Level getLevel(int index)
    {
        switch (index)
        {
            case 0:
                //Main Menu cuantos jugadores van a jugar?
            case 1:

                Board gameMap = new Board();

                ArrayList<TableSquare> squares = new ArrayList<TableSquare>();

                squares.add(new TableSquare(Constants.SquareType.StartMonth, new Vector2(100, 100)));
                squares.add(new TableSquare(Constants.SquareType.Mail, new Vector2(100, 300)));
                squares.add(new TableSquare(Constants.SquareType.Bargain, new Vector2(100, 500)));
                squares.add(new TableSquare(Constants.SquareType.Lottery, new Vector2(100, 700)));
                squares.add(new TableSquare(Constants.SquareType.Event, new Vector2(100, 900)));

                squares.add(new TableSquare(Constants.SquareType.Mail, new Vector2(300, 900)));
                squares.add(new TableSquare(Constants.SquareType.Bargain, new Vector2(500, 900)));
                squares.add(new TableSquare(Constants.SquareType.Lottery, new Vector2(700, 900)));
                squares.add(new TableSquare(Constants.SquareType.Event, new Vector2(900, 900)));

                squares.add(new TableSquare(Constants.SquareType.Mail, new Vector2(900, 700)));
                squares.add(new TableSquare( Constants.SquareType.Bargain, new Vector2(900, 500)));
                squares.add(new TableSquare( Constants.SquareType.Lottery, new Vector2(900, 300)));
                squares.add(new TableSquare( Constants.SquareType.Event, new Vector2(900, 100)));

                squares.add(new TableSquare( Constants.SquareType.Mail, new Vector2(700, 100)));
                squares.add(new TableSquare( Constants.SquareType.Bargain, new Vector2(500, 100)));
                squares.add(new TableSquare( Constants.SquareType.Lottery, new Vector2(300, 100)));


                return new Level(4, squares, 1000);
        }
        return null;
    }
}
