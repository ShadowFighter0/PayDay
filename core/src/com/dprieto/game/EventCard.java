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

    public void Use (Player caster, Player objective)
    {
        switch (type)
        {
            case EarnMoney:

                caster.money += amount;
                break;

            case PlayersEarnMoney:

                caster.money += amount;
                objective.money += amount;
                break;

            case PlayerLoseMoney:

                objective.money -= amount;
                caster.money += amount;
                break;

            case StealBargain:

                objective.MustGiveBargain(caster);

                break;
        }

        level.usingEvent = false;
        level.playerObjective = -1;
    }
}
