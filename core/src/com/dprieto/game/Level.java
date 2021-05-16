package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Level {

    CameraHelper cameraHelper;
    BitmapFont font;
    GlyphLayout layout;
    FileReader reader;
    Dice dice;

    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    ArrayList<TableSquare> tableSquares;

    TextureRegion map;
    Card cardToDisplay;
    ArrayList<Player> players;

    int currentPlayerIndex = 0;
    int movement = 0;

    boolean turnEnded = true;
    boolean cardAnimation = false;

    public Level (int playersNum, ArrayList<TableSquare> tableSquares, int initialMoney)
    {

        map = AssetManager.getInstance().getTexture("ProvisionalMap");
        this.tableSquares = tableSquares;

        cameraHelper = new CameraHelper(map.getRegionWidth()/2+125,map.getRegionHeight()/2, map.getRegionWidth() + Constants.EXTRA_HUD, map.getRegionHeight());
        dice = new Dice(this, new Vector2(500,2*cameraHelper.worldHeight/3));

        layout = new GlyphLayout();
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        mailCards = new ArrayList<MailCard>();
        bargainCards = new ArrayList<BargainCard>();
        eventCards = new ArrayList<EventCard>();
        players = new ArrayList<Player>();

        reader = new FileReader(this);
        reader.LoadXML();

        for (int i = 1; i <= playersNum; i++)
        {
            players.add(new Player(i, initialMoney,new Vector2(map.getRegionWidth() + Constants.EXTRA_HUD/2, ( i * cameraHelper.worldHeight/5)), tableSquares.get(0).position, this));
        }

        dice.setActive(true);

    }

    public void update(float delta)
    {
        if (!cardAnimation)
        {
            //NewPlayer => Show Options
            if (turnEnded)
            {
                ShowOptions();
            }
            //Update dice in order to get the number
            if (dice.isActive())
            {
                dice.update(delta);
            }
            else
            {
                //we have the movement
                if (movement > 0)
                {
                    //Move Lerped to next square
                    if (!players.get(currentPlayerIndex).inMovement)
                    {
                        players.get(currentPlayerIndex).MoveTo(tableSquares.get(players.get(currentPlayerIndex).currentSquare + 1).position);
                    }
                    else
                    {
                        players.get(currentPlayerIndex).update(delta);
                    }
                }
                else if (movement == 0)
                {
                    switch (tableSquares.get(players.get(currentPlayerIndex).currentSquare).type)
                    {
                        case Mail:
                            MailCard mail = mailCards.remove(0);
                            players.get(currentPlayerIndex).mailcards.add(mail);
                            mailCards.add(mail);

                            cardToDisplay = mail;
                            cardAnimation = true;
                            break;

                        case Event:
                            EventCard event = eventCards.remove(0);
                            players.get(currentPlayerIndex).events.add(event);
                            eventCards.add(event);

                            cardToDisplay = event;
                            cardAnimation = true;
                            break;

                        case Bargain:
                            BargainCard bargainCard = bargainCards.remove(0);
                            players.get(currentPlayerIndex).bargains.add(bargainCard);
                            bargainCards.add(bargainCard);

                            cardToDisplay = bargainCard;
                            cardAnimation = true;
                            break;

                        case Lottery:

                            break;
                        case StartMonth:

                            int amount = 0;

                            for (int i = 0; i < players.get(currentPlayerIndex).mailcards.size(); i++)
                            {
                                amount += players.get(currentPlayerIndex).mailcards.get(i).amount;
                            }

                            players.get(currentPlayerIndex).money -= amount;

                            if (players.get(currentPlayerIndex).money < 0)
                            {
                                Gdx.app.debug("Player" + currentPlayerIndex +1, "has been defeated");
                            }

                            break;
                    }
                }
            }
        }
        else
        {
            cardToDisplay.update(delta);
        }

        cameraHelper.update();
    }

    public void EndCardAnimation()
    {
        cardAnimation = false;
        turnEnded = true;

        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
    }

    public void OnPlayerArrived()
    {
        movement--;
    }

    void ShowOptions()
    {
        dice.setActive(true);
        dice.Reset();
        //View Events
        //View Cards

        turnEnded = false;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(map, 0, 0);

        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).render(batch);
        }

        if (cardAnimation)
            cardToDisplay.render(batch);


        dice.render(batch);
    }
}
