package com.dprieto.game;

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
                ArrayList<TableSquare> squares = new ArrayList<TableSquare>();

                squares.add(new TableSquare(0, Constants.SquareType.StartMonth));
                squares.add(new TableSquare(1, Constants.SquareType.Mail));
                squares.add(new TableSquare(2, Constants.SquareType.Bargain));
                squares.add(new TableSquare(3, Constants.SquareType.Lottery));
                squares.add(new TableSquare(4, Constants.SquareType.Event));
                squares.add(new TableSquare(5, Constants.SquareType.Mail));
                squares.add(new TableSquare(6, Constants.SquareType.Bargain));
                squares.add(new TableSquare(7, Constants.SquareType.Lottery));
                squares.add(new TableSquare(8, Constants.SquareType.Event));
                squares.add(new TableSquare(9, Constants.SquareType.Mail));
                squares.add(new TableSquare(10, Constants.SquareType.Bargain));
                squares.add(new TableSquare(11, Constants.SquareType.Lottery));
                squares.add(new TableSquare(12, Constants.SquareType.Event));
                squares.add(new TableSquare(13, Constants.SquareType.Mail));
                squares.add(new TableSquare(14, Constants.SquareType.Bargain));
                squares.add(new TableSquare(15, Constants.SquareType.Lottery));


                return new Level(4, squares, 1000);
        }
        return null;
    }
}
