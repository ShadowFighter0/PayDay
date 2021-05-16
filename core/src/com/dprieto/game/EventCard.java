package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EventCard extends Card {

    int amount;
    Constants.EventType type;

    public EventCard(String name, String description, Level level, int amount, Constants.EventType type) {
        super(name, description, level);

        visualCard = AssetManager.getInstance().getTexture("EventCard");

        dimension.x = visualCard.getRegionWidth() * 2.5f;
        dimension.y = visualCard.getRegionHeight() * 2.5f;

        this.amount = amount;
    }
}
