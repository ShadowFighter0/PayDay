package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player extends GameObject{

    Level level;

    boolean firstMovement = true;

    int numPlayer;
    int money;
    int currentSquare;
    Vector2 tokenPosition;
    Vector2 tokenDimension;

    boolean mustGiveBargain;
    Player playerToGiveBargain;


    boolean myTurn;

    //MovementAnim
    boolean inMovement;
    Vector2 newTokenPosition;

    ArrayList<BargainCard> bargains;
    ArrayList<MailCard> mailCards;
    ArrayList<EventCard> events;

    TextureRegion playerCardTexture;
    TextureRegion tokenTexture;

    HUDText nameText;
    HUDText moneyText;

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

        nameText = new HUDText(new Vector2(-25,50), position, level.font, level.tableCamera);
        nameText.setText("Jugador "+ numPlayer);
        moneyText = new HUDText(new Vector2(0,-30), position, level.font, level.tableCamera);
    }

    public void MoveTo(Vector2 newTokenPosition) {

        this.newTokenPosition = newTokenPosition;
        inMovement = true;
    }

    public void Turn (boolean myTurn)
    {
        this.myTurn = myTurn;

        if (myTurn)
        {
            position.x -= 25;
        }
        else
        {
            position.x += 25;
        }
    }

    public void MustGiveBargain(Player giveTo)
    {
        mustGiveBargain = true;
        playerToGiveBargain = giveTo;

    }

    @Override
    public void update(float delta) {

        if (inMovement)
        {
            Vector2 movement = newTokenPosition.cpy().sub(tokenPosition.cpy());

            tokenPosition.add(movement.nor().scl(Constants.PLAYER_SPEED * delta));

            if (tokenPosition.cpy().dst(newTokenPosition.cpy()) <= Constants.DISTANCE)
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

        batch.draw(tokenTexture, position.x + dimension.x / 4 , position.y - tokenDimension.y/2);
        nameText.render(batch);
        moneyText.setText("Money: " + money );
        moneyText.font.getData().setScale(0.9f);
        moneyText.render(batch);
        moneyText.font.getData().setScale(1);
    }

    @Override
    public void OnClicked() {

        level.playerObjective = numPlayer;
    }

    @Override
    public void OnNotClicked() {

    }
}
