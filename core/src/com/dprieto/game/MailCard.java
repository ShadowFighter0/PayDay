package com.dprieto.game;

public class MailCard extends Card{

    int amount;

    public MailCard(String name, String description, Level level, int amount) {
        super(name, description, level);

        visualCard = AssetManager.getInstance().getTexture("MailCard");

        dimension.x = visualCard.getRegionWidth() * 2.5f;
        dimension.y = visualCard.getRegionHeight() * 2.5f;

        this.amount = amount;
    }

}
