package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDText extends HUDElement{

    BitmapFont font;
    String text;

    public HUDText (Vector2 position, Anchor anchor, BitmapFont font, Camera camera) {

        this.offsetPosition = position;
        this.currentPosition = position.cpy();

        this.myAnchor = anchor;
        anchorPos = new Vector2();

        this.camera = camera;
        this.font = font;
        text = "";
    }

    @Override
    public void render(SpriteBatch batch) {

        switch (myAnchor)
        {
            case UpperLeft:
                anchorPos.x = camera.position.x - camera.currentWidth/2;
                anchorPos.y = camera.position.y + camera.currentHeight/2;
                break;

            case UpperRight:
                anchorPos.x = camera.position.x + camera.currentWidth/2;
                anchorPos.y = camera.position.y + camera.currentHeight/2;
                break;
            case MiddleScreen:
                anchorPos.x = camera.position.x;
                anchorPos.y = camera.position.y;
                break;
        }

        currentPosition.x = anchorPos.x + offsetPosition.x;
        currentPosition.y = anchorPos.y + offsetPosition.y;

        font.draw(batch,
                "" + text,
                currentPosition.x ,
                currentPosition.y );
    }

    public void setText(String text)
    {
        this.text = text;
    }
}