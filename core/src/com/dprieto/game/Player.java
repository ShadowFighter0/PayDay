package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player extends GameObject{

    Level level;

    int numPlayer;
    int money;
    int currentSquare;
    Vector2 tokenPosition;
    Vector2 tokenDimension;

    boolean mustGiveBargain;

    //MovementAnim
    boolean inMovement;
    Vector2 newTokenPosition;

    ArrayList<BargainCard> bargains;
    ArrayList<MailCard> mailCards;
    ArrayList<EventCard> events;

    TextureRegion playerCardTexture;
    TextureRegion tokenTexture;

    public Player (int numPlayer, int initialMoney, Vector2 cardPosition, Vector2 tokenPosition, Level level) {

        this.level = level;

        position = cardPosition.cpy();
        this.tokenPosition = tokenPosition.cpy();

        playerCardTexture = AssetManager.getInstance().getTexture("PlayerCard");

        setDimension(playerCardTexture);
        dimension.x *= 4;
        dimension.y *= 4;

        this.numPlayer = numPlayer;

        tokenTexture = AssetManager.getInstance().getTexture("Piece" + numPlayer);
        currentSquare = 0;

        tokenDimension = new Vector2(tokenTexture.getRegionWidth(), tokenTexture.getRegionHeight());

        money = initialMoney;

        bargains = new ArrayList<BargainCard>();
        mailCards = new ArrayList<MailCard>();
        events = new ArrayList<EventCard>();
    }

    public void MoveTo(Vector2 newTokenPosition) {

        this.newTokenPosition = newTokenPosition;
        inMovement = true;
    }

    @Override
    public void update(float delta) {

        if (inMovement)
        {
            Vector2 movement = newTokenPosition.cpy().sub(tokenPosition.cpy());

            tokenPosition.add(movement.nor().scl(Constants.PLAYER_SPEED * delta));

            if (tokenPosition.cpy().dst(newTokenPosition.cpy()) <= 0.5f)
            {
                currentSquare++;
                inMovement = false;
                level.OnPlayerArrived();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(playerCardTexture, position.x - dimension.x/2, position.y - dimension.y/2, dimension.x, dimension.y);
        batch.draw(tokenTexture, this.tokenPosition.x - tokenDimension.x/2, this.tokenPosition.y - tokenDimension.y/2);
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }
}
