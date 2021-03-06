package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class HUDElement {

    enum Anchor {UpperLeft, UpperRight, MiddleScreen}

    Anchor myAnchor;
    Vector2 anchorPos;

    String name;
    TextureRegion image;

    Vector2 offsetPosition;
    Vector2 currentPosition;
    Vector2 dimension;
    float rotation = 0;

    Camera camera;
    boolean isActive;


    public HUDElement()
    {

    }

    public HUDElement(String imageName, Vector2 position, Anchor anchor, Camera camera)
    {
        this.name = imageName;
        this.image = AssetManager.getInstance().getTexture(imageName);

        this.offsetPosition = position;
        this.currentPosition = position.cpy();
        this.myAnchor = anchor;

        anchorPos = new Vector2();

        dimension = new Vector2();
        dimension.x = image.getRegionWidth();
        dimension.y = image.getRegionHeight();
        
        this.camera = camera;
        isActive = true;

    }

    public HUDElement (String imageName, Vector2 position, Vector2 dimensionMultiplier, Anchor anchor, Camera camera)
    {
        this.name = imageName;
        this.image = AssetManager.getInstance().getTexture(imageName);

        this.offsetPosition = position;
        this.currentPosition = position.cpy();

        this.myAnchor = anchor;

        anchorPos = new Vector2();

        dimension = new Vector2();
        dimension.x = image.getRegionWidth() * dimensionMultiplier.x;
        dimension.y = image.getRegionHeight() * dimensionMultiplier.y;

        this.camera = camera;
        isActive = true;
    }

    public void SetRotation (float rotation){this.rotation = rotation;}
    public void setActive(boolean active) {
        isActive = active;
    }
    public void SetPosition(Vector2 newPositions) {offsetPosition = newPositions;}
    public void SetPosition(float x, float y) {offsetPosition.x = x; offsetPosition.y = y;}

    public void render (SpriteBatch batch)
    {
        if (isActive)
        {
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

            batch.draw(image, currentPosition.x - dimension.x/2, currentPosition.y - dimension.y/2,
                    dimension.x/2, dimension.y/2, dimension.x, dimension.y,1, 1, rotation);
        }
    }
}