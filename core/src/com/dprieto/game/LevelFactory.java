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
            case 1:


                return new Level(

                );
        }
        return null;
    }


}
