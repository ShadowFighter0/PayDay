package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GestureListener implements GestureDetector.GestureListener {

    private Level level;
    Vector2 point;
    boolean isDragging;
    Vector2 screenPoint;

    public GestureListener (Level level)
    {
        this.level = level;
        point = new Vector2();
        screenPoint = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        screenPoint.x = x;
        screenPoint.y = y;

        Vector3 position = level.cardsCamera.orthographicCamera.unproject(new Vector3(x, y, 0));
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

        Vector3 pos = level.tableCamera.orthographicCamera.unproject(new Vector3(x,y,0));
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
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
