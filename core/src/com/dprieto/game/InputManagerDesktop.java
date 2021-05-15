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
        Vector3 pos = level.cameraHelper.camera.unproject(new Vector3(screenX,screenY,0));

        screenPoint.x = screenX;
        screenPoint.y = screenY;

        point.x = pos.x;
        point.y = pos.y;

        boolean clicked = false;

        if (level.dice.isActive())
        {
            clicked = level.dice.checkClicked(point);
        }

        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(isDragging)
        {
            isDragging = false;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(isDragging)
        {
            float cameraMovementX = screenX - screenPoint.x;
            float cameraMovementY = screenY - screenPoint.y;

            level.cameraHelper.moveCamera(- cameraMovementX, cameraMovementY);
            screenPoint.x = screenX;
            screenPoint.y = screenY;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        level.cameraHelper.changeZoom(amountY);

        return false;
    }
}
