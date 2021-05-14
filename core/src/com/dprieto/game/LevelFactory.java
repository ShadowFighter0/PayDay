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

                //Tablero con fichas cartas de to do
                return new Level(                );
        }
        return null;
    }


}
