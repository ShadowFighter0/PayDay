package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BargainCard extends Card {

    int buyAmount;
    int sellAmount;

    public BargainCard(String name, String description, Level level, int buyAmount, int sellAmount) {
        super(name, description, level);

        visualCard = AssetManager.getInstance().getTexture("BargainCard");

        dimension.x = visualCard.getRegionWidth() * 2.5f;
        dimension.y = visualCard.getRegionHeight() * 2.5f;

        this.buyAmount = buyAmount;
        this.sellAmount = sellAmount;
    }

    @Override
    public void OnClicked() {

    }
}
