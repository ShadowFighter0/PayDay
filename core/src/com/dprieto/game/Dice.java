package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jdk.nashorn.internal.runtime.Debug;

import java.util.ArrayList;

public class Dice extends GameObject{

    ArrayList<TextureRegion> diceTextures;

    int number;

    float secsUntilResult = 5f;
    float currentSecsUntilResult;
    float secsChangeTexture = 0.5f;
    float currentSecsChangeTexture;

    boolean throwed = true;
    boolean inAnimation = false;

    Level level;

    public Dice (Level level, Vector2 position)
    {
        this.level = level;

        this.position = position;

        diceTextures = new ArrayList<TextureRegion>();

        for (int i = 1; i <= 6; i++)
        {
            diceTextures.add(AssetManager.getInstance().getTexture(i + ""));
        }
        Gdx.app.debug(diceTextures.size() + "", "");

        dimension.x = diceTextures.get(0).getRegionWidth();
        dimension.y = diceTextures.get(0).getRegionHeight();

        setActive(false);
    }

    void ThrowDice()
    {
        throwed = true;
        inAnimation = true;
        currentSecsUntilResult = secsUntilResult;
        currentSecsChangeTexture = secsChangeTexture;
    }

    @Override
    public void update(float delta) {

        if (throwed)
        {
            if (inAnimation)
            {
                if (currentSecsUntilResult <= 0)
                {
                    inAnimation = false;
                }
                else
                {
                    currentSecsChangeTexture -= delta;

                    if (currentSecsChangeTexture <= 0)
                    {
                        currentSecsChangeTexture = secsChangeTexture;
                        number = MathUtils.random(1,6);
                    }
                    else
                    {
                        currentSecsChangeTexture -= delta;
                    }
                }
            }
            else
            {

            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.app.debug("OJO", "" + position + " " + dimension);
        batch.draw(diceTextures.get(number),position.x - dimension.x/2, position.y - dimension.y/2);

        if (isActive())
        {
            if (throwed)
            {

            }
        }
    }

    @Override
    public void OnClicked() {
        ThrowDice();
    }

    @Override
    public void OnNotClicked() {

    }
}
