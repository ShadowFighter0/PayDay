package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDText extends HUDElement{

    BitmapFont font;
    String text;
    private Vector2 textOffset = Vector2.Zero.cpy();

    public HUDText (Vector2 position, Anchor anchor, BitmapFont font, Camera camera) {

        this.offsetPosition = position;
        this.currentPosition = position.cpy();

        this.myAnchor = anchor;
        anchorPos = new Vector2();

        this.camera = camera;
        this.font = font;
        text = "";

        isActive = true;
    }

    public HUDText (Vector2 position, Vector2 anchor, BitmapFont font, Camera camera) {

        this.offsetPosition = position;
        this.currentPosition = position.cpy();

        this.myAnchor = null;
        anchorPos = anchor;

        this.camera = camera;
        this.font = font;
        text = "";

        isActive = true;
    }

    @Override
    public void render(SpriteBatch batch) {

        if (isActive) {
            if (myAnchor != null)
            {
                switch (myAnchor) {
                    case UpperLeft:
                        anchorPos.x = camera.position.x - camera.currentWidth / 2;
                        anchorPos.y = camera.position.y + camera.currentHeight / 2;
                        break;

                    case UpperRight:
                        anchorPos.x = camera.position.x + camera.currentWidth / 2;
                        anchorPos.y = camera.position.y + camera.currentHeight / 2;
                        break;
                    case MiddleScreen:
                        anchorPos.x = camera.position.x;
                        anchorPos.y = camera.position.y;
                        break;
                }
            }

            currentPosition.x = anchorPos.x + offsetPosition.x;
            currentPosition.y = anchorPos.y + offsetPosition.y;

            font.draw(batch,
                    "" + text,
                    currentPosition.x - textOffset.x / 2.0f,
                    currentPosition.y + textOffset.y / 2.0f);
        }
    }

    public void setText(String text)
    {
        this.text = text;
        GlyphLayout layout = new GlyphLayout(font, this.text);
        textOffset.x = layout.width;
        textOffset.y = layout.height;
    }
}