package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card extends GameObject{

    String name;
    String description;

    public Card (String name, String description )
    {
        this.name = name;
        this.description = description;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }
}
