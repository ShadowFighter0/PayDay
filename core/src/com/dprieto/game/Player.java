package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Player extends GameObject{


    int numPlayer;
    int money;
    int currentSquare;

    boolean mustGiveBargain;

    ArrayList<BargainCard> bargains;
    ArrayList<MailCard> mailcards;
    ArrayList<EventCard> events;

    TextureRegion playerCard;

    public Player (int numPlayer, int initialMoney)
    {
        this.numPlayer = numPlayer;
        money = initialMoney;

        bargains = new ArrayList<BargainCard>();
        mailcards = new ArrayList<MailCard>();
        events = new ArrayList<EventCard>();

        playerCard = AssetManager.getInstance().getTexture("PlayerCard");
        setDimension(playerCard);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(playerCard, position.x - playerCard.getRegionWidth()/2, position.y - playerCard.getRegionHeight()/2);
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }
}
