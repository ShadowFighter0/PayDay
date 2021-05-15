package com.dprieto.game;

import com.badlogic.gdx.math.Vector2;

public class TableSquare {

    int index;
    Constants.SquareType type;
    Vector2 position;

    public TableSquare(int index, Constants.SquareType type, Vector2 position)
    {
        this.index = index;
        this.type = type;
        this.position = position;
    }

}
