package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Level {

    //cameras
    Camera tableCamera;
    Camera cardsCamera;

    BitmapFont font;
    GlyphLayout layout;
    FileReader reader;

    //Buttons
    Dice dice;
    ArrayList<HUDButton> cardsButtons;
    ArrayList<HUDButton> mainButtons;
    ArrayList<HUDText> texts;

    //Arrays
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
    boolean showCards = false;
    boolean showEvents = false;


    public Level (int playersNum, ArrayList<TableSquare> tableSquares, int initialMoney)
    {

        map = AssetManager.getInstance().getTexture("ProvisionalMap");
        this.tableSquares = tableSquares;

        tableCamera = new Camera (map.getRegionWidth() + Constants.EXTRA_HUD, map.getRegionHeight());
        cardsCamera = new Camera (map.getRegionWidth() + Constants.EXTRA_HUD, map.getRegionHeight());

        CreateHUD();

        layout = new GlyphLayout();

        mailCards = new ArrayList<MailCard>();
        bargainCards = new ArrayList<BargainCard>();
        eventCards = new ArrayList<EventCard>();
        players = new ArrayList<Player>();

        reader = new FileReader(this);
        reader.LoadXML();

        //Create Players
        for (int i = 1; i <= playersNum; i++)
        {
            players.add(new Player(i, initialMoney,new Vector2(map.getRegionWidth() + Constants.EXTRA_HUD/2, ( i * tableCamera.viewportHeight/5)), tableSquares.get(0).position, this));
        }
    }

    void CreateHUD()
    {
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        dice = new Dice(this, new Vector2(500,2 * tableCamera.currentHeight/3));
        dice.setActive(true);

        mainButtons = new ArrayList<HUDButton>();
        cardsButtons = new ArrayList<HUDButton>();
        texts = new ArrayList<HUDText>();

        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( 0,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailLeft, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowRight", new Vector2(0,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailRight, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( 0,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.BargainLeft, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowRight", new Vector2( 0,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.BargainRight, this, cardsCamera));

        mainButtons.add(new HUDButton("EventCard", new Vector2( -300,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.EventCard, this, tableCamera));
        mainButtons.add(new HUDButton("MailCard", new Vector2( 20,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ShowCard, this, tableCamera));

        texts.add(new HUDText(new Vector2(-350, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        texts.get(0).setText("Events");
        texts.add(new HUDText(new Vector2(-50, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        texts.get(1).setText("My Cards");
        texts.add(new HUDText(new Vector2(-170, 130), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        texts.get(2).setText("Dice");
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
                    CardUpdate();
                }
            }
        }
        else
        {
            cardToDisplay.update(delta);
        }

        tableCamera.update();
    }

    public void DiceEnd(){
        dice.setActive(false);

        for (int i = 0 ; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).setActive(false);
        }
        for (int i = 0 ; i < texts.size(); i++)
        {
            texts.get(i).setActive(false);
        }
    }

    private void CardUpdate() {
        switch (tableSquares.get(players.get(currentPlayerIndex).currentSquare).type)
        {
            case Mail:
                MailCard mail = mailCards.remove(0);
                players.get(currentPlayerIndex).mailCards.add(mail);
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
                for (int i = 0; i < players.size(); i++) {
                    if(!players.get(i).equals(currentPlayerIndex) && players.get(i).money >= 100)
                    {
                        players.get(i).money -= 100;
                    }
                }
                int rand = MathUtils.random(0, players.size() - 1);
                players.get(rand).money += 100 * (players.size() - 1);

                turnEnded = true;
                break;

            case StartMonth:

                int mailAmount = 0;
                int bargainAmount = 0;

                for (int i = 0; i < players.get(currentPlayerIndex).bargains.size(); i++)
                {
                    bargainAmount += players.get(currentPlayerIndex).bargains.get(i).sellAmount;
                }
                for (int i = 0; i < players.get(currentPlayerIndex).mailCards.size(); i++)
                {
                    mailAmount += players.get(currentPlayerIndex).mailCards.get(i).amount;
                }

                //Display bargain



                //
                players.get(currentPlayerIndex).money -= mailAmount;

                if (players.get(currentPlayerIndex).money < 0)
                {
                    Gdx.app.debug("Player" + currentPlayerIndex + 1, "has been defeated");
                }

                break;
        }
    }

    public void EndCardAnimation() {
        cardAnimation = false;
        turnEnded = true;

        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
    }

    public void OnPlayerArrived() {
        movement--;
    }

    void ShowOptions() {
        dice.setActive(true);
        dice.Reset();

        for (int i = 0 ; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).setActive(true);
        }
        for (int i = 0 ; i < texts.size(); i++)
        {
            texts.get(i).setActive(true);
        }

        turnEnded = false;
    }

    public void tableRender (SpriteBatch batch) {
        batch.draw(map, 0, 0);

        for (int i = 0; i < players.size(); i++)
        {
            players.get(i).render(batch);
        }

        for (int i = 0; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).render(batch);
        }
        for (int i = 0; i < texts.size(); i++)
        {
            texts.get(i).render(batch);
        }

        if (cardAnimation)
            cardToDisplay.render(batch);


        dice.render(batch);
    }

    public void cardsRender (SpriteBatch batch) {
        if (showCards)
        {
            for (int i = 0; i < cardsButtons.size(); i++)
            {
                cardsButtons.get(i).render(batch);
            }
        }
        else if (showEvents)
        {

        }

    }
}
