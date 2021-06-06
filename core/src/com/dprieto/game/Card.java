package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Card extends GameObject{

    String name;
    String description;

    TextureRegion visualCard;

    float secsUntilTurnOf = 10f;
    float currentSecsUntilTurnOff;

    Level level;

    GlyphLayout layout;
    BitmapFont font;

    public Card (String name, String description, Level level)
    {
        this.name = name;
        this.description = description;

        this.level = level;

        position = new Vector2(level.board.width/2, level.board.height/2);

        layout = level.layout;
        font = level.font;
        currentSecsUntilTurnOff = secsUntilTurnOf;
    }

    @Override
    public void update(float delta) {

        if (currentSecsUntilTurnOff <= 0)
        {
            currentSecsUntilTurnOff = secsUntilTurnOf;
            level.EndCardAnimation();
        }
        else
        {
            currentSecsUntilTurnOff -= delta;
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(visualCard, position.x - dimension.x/2, position.y - dimension.y/2, dimension.x, dimension.y);

        layout.setText(font, ""+name);
        font.draw(batch, layout, position.x - layout.width / 2, position.y + dimension.y/3);

        layout.setText(font, ""+description);
        font.draw(batch, layout, position.x - layout.width / 2, position.y);
    }

    public void renderInPosition(SpriteBatch batch, Vector2 newPosition, Vector2 dimensionModifier)
    {
        batch.draw(visualCard, newPosition.x - dimension.x/2, newPosition.y - dimension.y/2, dimension.x , dimension.y );

        layout.setText(font, ""+name);
        font.draw(batch, layout, newPosition.x - layout.width / 2, newPosition.y + dimension.y /3);

        layout.setText(font, ""+description);
        font.draw(batch, layout, newPosition.x - layout.width / 2, newPosition.y + dimension.y );
    }

    @Override
    public void OnClicked() {

        level.EndCardAnimation();
    }

    @Override
    public void OnNotClicked() {


    }
}
