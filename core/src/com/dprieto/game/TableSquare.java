package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TableSquare {

    Constants.SquareType type;
    Vector2 position;

    public TableSquare(Constants.SquareType type, Vector2 position)
    {
        this.type = type;
        this.position = position;
    }

    public void render (SpriteBatch batch, BitmapFont font)
    {
        font.getData().setScale(0.5f);
        font.draw(batch, "" + type, position.x - 50 , position.y);
        font.getData().setScale(1);
    }

}
