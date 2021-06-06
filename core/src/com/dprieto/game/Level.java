package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Level {

    //Cameras
    Camera tableCamera;
    Camera cardsCamera;

    BitmapFont font;
    GlyphLayout layout;
    FileReader reader;

    //Buttons
    Dice dice;
    ArrayList<HUDButton> cardsButtons;
    ArrayList<HUDButton> eventsButtons;
    ArrayList<HUDButton> mainButtons;
    ArrayList<HUDText> mainTexts;

    ArrayList<HUDButton> buyBargainsButtons;
    ArrayList<HUDText> buyBargainsText;

    HUDText noCards;
    HUDText selectPlayerText;

    HUDText mailMoneyText;
    HUDText bargainMoneyText;
    HUDText currentMoneyText;
    HUDText substractText;
    HUDText resultText;
    HUDButton exitStartMonth;

    //Arrays
    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    //Squares
    ArrayList<TableSquare> tableSquares;

    Board board;
    Card cardToDisplay;
    ArrayList<Player> players;

    int currentPlayerIndex = 0;
    int movement = 0;

    boolean turnEnded = true;
    boolean cardAnimation = false;
    boolean showCards = false;
    boolean showEvents = false;
    boolean usingEvent = false;

    boolean endStartMonth = false;
    boolean disableStartMonth = false;

    //Buy Bargain
    boolean bargainShowed = false;
    BargainCard bargainCard;

    //Events
    int cardShowed = 0;
    int eventShowed = 0;
    int playerObjective = 0;

    //Start Game
    HUDButton addPlayerButton;
    HUDButton startButton;
    private boolean gameStarted = false;

    LotteryCard lotteryCard = null;
    private int initialMoney = 0;

    public Level (int initialMoney)
    {
        tableSquares = new ArrayList<TableSquare>();
        board = new Board(this);

        tableCamera = new Camera (board.width + Constants.EXTRA_HUD, board.height);
        cardsCamera = new Camera (board.width + Constants.EXTRA_HUD, board.height);

        CreateHUD();

        layout = new GlyphLayout();

        mailCards = new ArrayList<MailCard>();
        bargainCards = new ArrayList<BargainCard>();
        eventCards = new ArrayList<EventCard>();
        players = new ArrayList<Player>();

        reader = new FileReader(this);
        reader.LoadXML();

        //Create Players
        this.initialMoney = initialMoney;
        lotteryCard = new LotteryCard(this);
    }

    private void startGame()
    {
        if (players.size() >= 2)
        {
            players.get(currentPlayerIndex).Turn(true);
            gameStarted = true;
        }
    }

    void CreateHUD() {

        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        dice = new Dice(this, new Vector2(500,2 * tableCamera.currentHeight/3));
        dice.setActive(true);

        mainButtons = new ArrayList<HUDButton>();
        cardsButtons = new ArrayList<HUDButton>();
        mainTexts = new ArrayList<HUDText>();
        eventsButtons = new ArrayList<HUDButton>();

        buyBargainsButtons = new ArrayList<HUDButton>();
        buyBargainsText = new ArrayList<HUDText>();

        //Main Buttons
        mainButtons.add(new HUDButton("EventCard", new Vector2( -300,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ShowEvents, this, tableCamera));
        mainButtons.add(new HUDButton("MailCard", new Vector2( 20,-50), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ShowCards, this, tableCamera));

        buyBargainsButtons.add(new HUDButton("ArrowRight", new Vector2( 150,-300), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.BuyBargain, this, tableCamera));
        buyBargainsButtons.add(new HUDButton("ArrowLeft", new Vector2( -475,-300), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.NotBuyBargain, this, tableCamera));

        buyBargainsButtons.get(0).setActive(false);
        buyBargainsButtons.get(1).setActive(false);

        mainTexts.add(new HUDText(new Vector2(-350, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(0).setText("Events");
        mainTexts.add(new HUDText(new Vector2(-50, -100), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(1).setText("My Cards");
        mainTexts.add(new HUDText(new Vector2(-170, 130), HUDElement.Anchor.MiddleScreen, font, tableCamera));
        mainTexts.get(2).setText("Dice");

        buyBargainsText.add(new HUDText(new Vector2(-350,-300),HUDElement.Anchor.MiddleScreen, font, tableCamera));
        buyBargainsText.get(0).setText("Not Buy");
        buyBargainsText.get(0).setActive(false);
        buyBargainsText.add(new HUDText(new Vector2(75,-300),HUDElement.Anchor.MiddleScreen, font, tableCamera));
        buyBargainsText.get(1).setText("Buy");
        buyBargainsText.get(1).setActive(false);
        buyBargainsText.add(new HUDText(new Vector2(-150,300),HUDElement.Anchor.MiddleScreen, font, tableCamera));
        buyBargainsText.get(2).setText("Do you want to buy this bargain?");
        buyBargainsText.get(2).setActive(false);

        noCards = new HUDText(new Vector2(-170, 0), HUDElement.Anchor.MiddleScreen, font, cardsCamera );

        //Show Cards
        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( -400,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailLeft, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowRight", new Vector2(150,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MailRight, this, cardsCamera));
        cardsButtons.add(new HUDButton("ArrowLeft", new Vector2( -375,-250), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ExitShowCard, this, cardsCamera));


        //Events Button
        eventsButtons.add(new HUDButton("ArrowLeft", new Vector2( -400,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.EventLeft, this, cardsCamera));
        eventsButtons.add(new HUDButton("ArrowRight", new Vector2(150,0), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.EventRight, this, cardsCamera));
        eventsButtons.add(new HUDButton("ArrowLeft", new Vector2(-375,-250), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ExitShowEvents, this, cardsCamera));
        eventsButtons.add(new HUDButton("ArrowLeft", new Vector2(-125,250), new Vector2( 0.5f, 0.5f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.UseEvent, this, cardsCamera));
        eventsButtons.get(3).SetRotation(-90);

        selectPlayerText = new HUDText(new Vector2(-125,200), HUDElement.Anchor.MiddleScreen, font, tableCamera);


        addPlayerButton = new HUDButton("button", new Vector2(500, 450),
                HUDElement.Anchor.MiddleScreen, cardsCamera, "Add player", font);
        startButton = new HUDButton("button", new Vector2(-150, -200),
                HUDElement.Anchor.MiddleScreen, cardsCamera, "Start game", font);

        //Start Money
        mailMoneyText = new HUDText(new Vector2(-250,250), HUDElement.Anchor.MiddleScreen, font, tableCamera);
        bargainMoneyText = new HUDText(new Vector2(-250,200), HUDElement.Anchor.MiddleScreen, font, tableCamera);
        currentMoneyText = new HUDText(new Vector2(-250,125), HUDElement.Anchor.MiddleScreen, font, tableCamera);
        substractText = new HUDText(new Vector2(-250,75), HUDElement.Anchor.MiddleScreen, font, tableCamera);
        resultText = new HUDText(new Vector2(-250,0), HUDElement.Anchor.MiddleScreen, font, tableCamera);
        exitStartMonth = new HUDButton("ArrowLeft", new Vector2(-250,-100), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.EndStartMonth, this, tableCamera);
        exitStartMonth.setActive(false);
    }

    public void ShowCards() {

        cardShowed = 0;
        eventShowed = 0;

        dice.setActive(false);

        for (int i = 0; i < mainButtons.size(); i++)
        {
            mainButtons.get(i).setActive(false);
        }

        for (int i = 0; i < mainTexts.size(); i++)
        {
            mainTexts.get(i).setActive(false);
        }
    }

    public void BuyBargain (boolean buy)
    {
        if (buy)
        {
            if (players.get(currentPlayerIndex).mustGiveBargain)
            {
                players.get(currentPlayerIndex).playerToGiveBargain.bargains.add(bargainCard);
                players.get(currentPlayerIndex).mustGiveBargain = false;
                players.get(currentPlayerIndex).playerToGiveBargain = null;
            }
            else
            {
                players.get(currentPlayerIndex).bargains.add(bargainCard);
            }

            players.get(currentPlayerIndex).money += bargainCard.buyAmount;
        }

        EndCardAnimation();
    }

    public void HideShowCards() {

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

    public void UseEvent()
    {
        usingEvent = true;
        eventShowed = 0;
    }

    public void update(float delta) {

        if (usingEvent)
        {
            if (players.get(currentPlayerIndex).events.size() == 0)
            {
                return;
            }

            eventShowed %= players.get(currentPlayerIndex).events.size();

            EventCard event = players.get(currentPlayerIndex).events.get(eventShowed);

            switch (event.type)
            {
                case EarnMoney:

                    event.Use(players.get(currentPlayerIndex), null);
                    players.get(currentPlayerIndex).events.remove(eventShowed);
                    usingEvent = false;
                    break;

                case PlayersEarnMoney:
                case PlayerLoseMoney:
                case StealBargain:

                    selectPlayerText.setActive(true);
                    selectPlayerText.setText("Please select a player");

                    if (playerObjective > 0)
                    {
                        event.Use(players.get(currentPlayerIndex), players.get(playerObjective));
                        players.get(currentPlayerIndex).events.remove(eventShowed);
                        usingEvent = false;
                        selectPlayerText.setActive(false);

                        playerObjective = -1;
                    }
                    break;
            }
        }
        else if (gameStarted)
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
                        PLayerMovement(delta);
                    }
                    else if (movement == 0)
                    {
                        players.get(currentPlayerIndex).currentSquare %= tableSquares.size();
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
        else
        {
            if (Gdx.input.justTouched())
            {
                Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
                Vector3 worldPosition = tableCamera.orthographicCamera.unproject(mousePosition);
                Vector2 position = new Vector2(worldPosition.x, worldPosition.y);

                if (players.size() < 4 && addPlayerButton.checkClicked(position))
                {
                    addPlayer();
                }
                if (players.size() >= 2 && startButton.checkClicked(position))
                {
                    startGame();
                }
            }
        }
    }

    private void PLayerMovement(float delta) {

        //Move Lerped to next square
        if (!players.get(currentPlayerIndex).inMovement)
        {
            players.get(currentPlayerIndex).currentSquare %= tableSquares.size();
            if (tableSquares.get(players.get(currentPlayerIndex).currentSquare).type == Constants.SquareType.Start && !endStartMonth && !disableStartMonth)
            {
                if (players.get(currentPlayerIndex).firstMovement)
                {
                    ExitStartMonth();
                    players.get(currentPlayerIndex).firstMovement = false;
                }
                else
                {
                    StartMonth();
                }
            }
            else if (endStartMonth && !disableStartMonth)
            {
                ExitStartMonth();
            }
            else
            {
                endStartMonth = false;
                disableStartMonth = false;

                int nextSquare = players.get(currentPlayerIndex).currentSquare + 1;
                nextSquare %= tableSquares.size();

                players.get(currentPlayerIndex).MoveTo(tableSquares.get(nextSquare).position);
            }
        }
        else
        {
            players.get(currentPlayerIndex).update(delta);
        }
    }

    void StartMonth() {

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

        Gdx.app.debug("MailAmount", "" + mailAmount);
        Gdx.app.debug("BargainAmount", "" + bargainAmount);

        //Display bargain
        mailMoneyText.setText("Mails:   " + mailAmount);
        mailMoneyText.setActive(true);

        bargainMoneyText.setText("Bargains:   " + bargainAmount);
        bargainMoneyText.setActive(true);

        currentMoneyText.setText("My Money:   " + players.get(currentPlayerIndex).money);
        currentMoneyText.setActive(true);

        substractText.setText("              " + (bargainAmount - mailAmount));
        substractText.setActive(true);

        int aux = players.get(currentPlayerIndex).money;
        resultText.setText("             " +  aux + (bargainAmount + mailAmount));
        resultText.setActive(true);

        exitStartMonth.setActive(true);
    }

    public void ExitStartMonth() {

        //Turn end
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

        players.get(currentPlayerIndex).money += (bargainAmount + mailAmount);
        players.get(currentPlayerIndex).bargains.clear();
        players.get(currentPlayerIndex).mailCards.clear();

        if (players.get(currentPlayerIndex).money < 0)
        {
            Gdx.app.debug("Player" + currentPlayerIndex + 1, "has been defeated");
        }

        mailMoneyText.setActive(false);
        bargainMoneyText.setActive(false);
        currentMoneyText.setActive(false);
        substractText.setActive(false);
        resultText.setActive(false);

        exitStartMonth.setActive(false);

        endStartMonth = false;
        disableStartMonth = true;
    }

    public void addPlayer() {

        if (players.size() < 4)
        {
            players.add(new Player(players.size() + 1, initialMoney,new Vector2(board.width + Constants.EXTRA_HUD/2,
                    ((players.size() + 1) * tableCamera.viewportHeight/5)), tableSquares.get(0).position, this));
        }
    }

    public void DiceEnd() {

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

                if (!bargainShowed)
                {
                    bargainShowed = true;
                    bargainCard = bargainCards.remove(0);
                    bargainCards.add(bargainCard);
                    cardToDisplay = bargainCard;
                    cardAnimation = true;

                    //Enable buttons and texts
                    buyBargainsText.get(0).setActive(true);
                    if (players.get(currentPlayerIndex).money > bargainCard.buyAmount)
                    {
                        buyBargainsButtons.get(0).setActive(true);
                        buyBargainsText.get(1).setActive(true);
                    }
                    buyBargainsButtons.get(1).setActive(true);
                    buyBargainsText.get(2).setActive(true);
                }
                break;


            case Lottery:
                for (int i = 0; i < players.size(); i++) {
                    if(!players.get(i).equals(currentPlayerIndex) && players.get(i).money >= 100)
                    {
                        players.get(i).money -= 100;
                    }
                }
                int rand = MathUtils.random(0, players.size() - 1);
                players.get(rand).money += 100 * (players.size());

                lotteryCard.setDescription("El jugador " + (rand + 1) + " ha ganado " + (100 * (players.size())) + "!");
                cardToDisplay = lotteryCard;
                cardAnimation = true;

                break;


            case Start:

                StartMonth();
                TurnEnd();

                break;
        }
    }

    void TurnEnd() {

        players.get(currentPlayerIndex).Turn(false);
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
        players.get(currentPlayerIndex).Turn(true);
    }

    public void EndCardAnimation() {

        //Enable buttons and texts
        for (int i = 0; i < buyBargainsButtons.size(); i++)
        {
            buyBargainsButtons.get(i).setActive(false);
        }
        for (int i = 0; i < buyBargainsText.size(); i++)
        {
            buyBargainsText.get(i).setActive(false);
        }

        bargainShowed = false;


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

        board.render(batch);

        for(int i = 0; i < tableSquares.size(); i++)
        {
            tableSquares.get(i).render(batch,font);
        }

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

        for (int i = 0; i < buyBargainsText.size(); i++)
        {
            buyBargainsText.get(i).render(batch);
        }
        for (int i = 0; i < buyBargainsButtons.size(); i++)
        {
            buyBargainsButtons.get(i).render(batch);
        }

        if (cardAnimation)
        {
            cardToDisplay.render(batch);
        }

        dice.render(batch);

        exitStartMonth.render(batch);
        mailMoneyText.render(batch);
        bargainMoneyText.render(batch);
        currentMoneyText.render(batch);
        substractText.render(batch);
        resultText.render(batch);

        if(!gameStarted)
        {
            if(players.size() < 4)
            {
                addPlayerButton.render(batch);
            }
            if(players.size() >= 2)
            {
                startButton.render(batch);
            }
        }
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
            for (int i = 0; i < eventsButtons.size(); i++)
            {
                eventsButtons.get(i).render(batch);
            }

            if (players.get(currentPlayerIndex).events.size() == 0)
            {
                noCards.setText("No Cards");
                noCards.render(batch);
                return;
            }

            if (eventShowed < 0)
            {
                eventShowed = players.get(currentPlayerIndex).events.size() - 1;
            }
            else if (eventShowed == players.get(currentPlayerIndex).events.size())
            {
                eventShowed = 0;
            }

            players.get(currentPlayerIndex).events.get(eventShowed).render(batch);

            selectPlayerText.render(batch);
        }
    }
}
