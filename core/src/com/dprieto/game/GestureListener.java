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
        Vector3 pos = level.tableCamera.orthographicCamera.unproject(new Vector3(x,y,0));

        screenPoint.x = x;
        screenPoint.y = y;

        point.x = pos.x;
        point.y = pos.y;

        boolean clicked = false;


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

        float cameraMovementX = x - screenPoint.x;
        float cameraMovementY = y - screenPoint.y;

        level.tableCamera.moveCamera(- cameraMovementX * Constants.PAN_MOBILE_SENSITIVITY, cameraMovementY * Constants.PAN_MOBILE_SENSITIVITY);
        screenPoint.x = x;
        screenPoint.y = y;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        float changeDistance = initialDistance - distance;
        changeDistance *= Constants.ZOOM_MOBILE_SENSITIVITY * level.tableCamera.currentZoom;
        level.tableCamera.changeZoom(changeDistance);
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
