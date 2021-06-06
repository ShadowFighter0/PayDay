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

    int number = 1; //number between 1 and 6

    float secsUntilResult = 2f;
    float currentSecsUntilResult;
    float secsChangeTexture = 0.1f;
    float currentSecsChangeTexture;
    float secsUntilTurnOff = 1f;
    float currentSecsUntilTurnOff;

    boolean throwed = false;

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

        setDimension(diceTextures.get(0));

        setActive(true);
    }

    void ThrowDice()
    {
        throwed = true;
        inAnimation = true;
    }

    void Reset()
    {
        currentSecsUntilResult = secsUntilResult;
        currentSecsChangeTexture = secsChangeTexture;
        currentSecsUntilTurnOff = secsUntilTurnOff;
    }

    @Override
    public void update(float delta) {

        if (throwed)
        {
            if (inAnimation)
            {
                DoDiceAnimation(delta);
            }
            else
            {
                DiceResult(delta);
            }
        }
    }

    private void DiceResult(float delta) {

        currentSecsUntilTurnOff-= delta;

        if (currentSecsUntilTurnOff <= 0)
        {
            throwed = false;
            level.DiceEnd();
            level.movement = number;
        }
    }

    private void DoDiceAnimation(float delta) {

        if (currentSecsUntilResult <= 0)
        {
            inAnimation = false;
        }
        else
        {
            currentSecsUntilResult -= delta;

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

    @Override
    public void render(SpriteBatch batch) {

        if (isActive())
        {
            if (throwed)
            {
                batch.draw(diceTextures.get(number-1),position.x - dimension.x/2, position.y - dimension.y/2);
            }
            else
            {
                batch.draw(diceTextures.get(0),position.x - dimension.x/2, position.y - dimension.y/2);
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
