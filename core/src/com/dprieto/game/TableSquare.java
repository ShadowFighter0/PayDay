package com.dprieto.game;

import com.badlogic.gdx.math.Vector2;

public class TableSquare {

    Constants.SquareType type;
    Vector2 position;

    public TableSquare(Constants.SquareType type, Vector2 position)
    {
        this.type = type;
        this.position = position;
    }

}
