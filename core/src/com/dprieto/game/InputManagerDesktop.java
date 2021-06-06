package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManagerDesktop implements InputProcessor {

    private Level level;
    Vector2 point;
    boolean isDragging;
    Vector2 screenPoint;

    public InputManagerDesktop(Level level)
    {
        this.level = level;
        point = new Vector2();
        screenPoint = new Vector2();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        screenPoint.x = screenX;
        screenPoint.y = screenY;

        Vector3 position = level.cardsCamera.orthographicCamera.unproject(new Vector3(screenX, screenY, 0));
        point.x = position.x;
        point.y = position.y;


        for ( int i = 0; i < level.cardsButtons.size(); i++)
        {
            level.cardsButtons.get(i).checkClicked(point);
        }

        for (int i = 0; i < level.eventsButtons.size(); i++)
        {
            level.eventsButtons.get(i).checkClicked(point);
        }

        Vector3 pos = level.tableCamera.orthographicCamera.unproject(new Vector3(screenX,screenY,0));
        point.x = pos.x;
        point.y = pos.y;


        if (level.dice.isActive() && level.gameStarted)
        {
             level.dice.checkClicked(point);

            for (int i = 0; i < level.mainButtons.size(); i++)
            {
                level.mainButtons.get(i).checkClicked(point);
            }
        }

        for(int i = 0; i < level.buyBargainsButtons.size(); i++)
        {
            level.buyBargainsButtons.get(i).checkClicked(point);
        }

        if (level.usingEvent)
        {
            for (int i = 0; i < level.players.size(); i++)
            {
                level.players.get(i).checkClicked(point);
            }
        }

        if (level.exitStartMonth.isActive)
        {
            level.exitStartMonth.checkClicked(point);
        }
        if (level.cardAnimation)
        {
            level.cardToDisplay.checkClicked(point);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {


        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        return false;
    }
}
