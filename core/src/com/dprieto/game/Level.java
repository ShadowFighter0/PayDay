package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
    ArrayList<HUDText> mainTexts;
    HUDText noCards;

    //Arrays
    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    //Squares
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

    int cardShowed = 0;

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
        players.get(currentPlayerIndex).Turn(true);
    }

    void CreateHUD()
    {
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        dice = new Dice(this, new Vector2(500,2 * tableCamera.currentHeight/3));
        dice.setActive(true);

        mainButtons = new ArrayList<HUDButton>();
        cardsButtons = new ArrayList<HUDButton>();
        mainTexts = new ArrayList<HUDText>();

        //Main Buttons
        mainButtons.add(new HUDButton("EventCard", new Vector2( -300,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ShowEvents, this, tableCamera));
        mainButtons.add(new HUDButton("MailCard", new Vector2( 20,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ShowCards, this, tableCamera));

        mainTexts.add(new HUDText(new Vector2(-350, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(0).setText("Events");
        mainTexts.add(new HUDText(new Vector2(-50, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(1).setText("My Cards");
        mainTexts.add(new HUDText(new Vector2(-170, 130), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(2).setText("Dice");

        noCards = new HUDText(new Vector2(-170, 0), HUDElement.Anchor.MiddleScreen, font, cardsCamera );


        //Show Cards
        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( -400,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailLeft, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowRight", new Vector2(125,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailRight, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( -375,-250), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ExitShowCard, this, cardsCamera));
    }

    public void ShowCards()
    {
        cardShowed = 0;

        showCards = true;

        dice.setActive(false);

        for (int i = 0 ; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).setActive(false);
        }
        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).setActive(false);
        }
    }

    public void HideShowCards()
    {
        cardShowed = 0;

        showCards = false;

        dice.setActive(true);

        for (int i = 0 ; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).setActive(true);
        }
        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).setActive(true);
        }
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
                if (dice.throwed)
                {
                    for (int i = 0 ; i < mainButtons.size(); i++)
                    {
                        mainButtons.get(i).setActive(false);
                    }
                    for (int i = 0; i < mainTexts.size()-1; i++)
                    {
                        mainTexts.get(i).setActive(false);
                    }
                }
            }
            else if (!showCards && !showEvents)
            {
                //we have the movement
                if (movement > 0)
                {
                    //Move Lerped to next square
                    if (!players.get(currentPlayerIndex).inMovement)
                    {
                        int nextSquare = players.get(currentPlayerIndex).currentSquare + 1;
                        nextSquare %= tableSquares.size();

                        players.get(currentPlayerIndex).MoveTo(tableSquares.get(nextSquare).position);
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
        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).setActive(false);
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

                cardAnimation = false;
                turnEnded = true;

                TurnEnd();
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

                TurnEnd();
                break;
        }
    }

    void TurnEnd()
    {
        players.get(currentPlayerIndex).Turn(false);
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
        players.get(currentPlayerIndex).Turn(true);
    }

    public void EndCardAnimation() {
        cardAnimation = false;
        turnEnded = true;

        TurnEnd();
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
        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).setActive(true);
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
        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).render(batch);
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

            if (players.get(currentPlayerIndex).mailCards.size() + players.get(currentPlayerIndex).bargains.size() == 0)
            {
                noCards.setText("No Cards");
                noCards.render(batch);
                return;
            }

            ArrayList<Card> cards = new ArrayList<Card>();

            for(int i = 0; i < players.get(currentPlayerIndex).mailCards.size(); i++)
            {
                cards.add(players.get(currentPlayerIndex).mailCards.get(i));
            }
            for (int i = 0; i < players.get(currentPlayerIndex).bargains.size(); i++)
            {
                cards.add(players.get(currentPlayerIndex).bargains.get(i));
            }

            if (cardShowed < 0 )
            {
                cardShowed = cards.size() - 1;
            }
            else if (cardShowed == cards.size())
            {
                cardShowed = 0;
            }

            cards.get(cardShowed).render(batch);

        }
        else if (showEvents)
        {

        }

    }
}
