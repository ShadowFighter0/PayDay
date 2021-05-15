package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


public class Level {

    CameraHelper cameraHelper;
    BitmapFont font;
    FileReader reader;
    Dice dice;

    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    ArrayList<TableSquare> tableSquares;

    TextureRegion map;
    ArrayList<Player> players;

    int currentPlayerIndex;

    public Level (int playersNum, ArrayList<TableSquare> tableSquares, int initialMoney)
    {

        map = AssetManager.getInstance().getTexture("ProvisionalMap");
        this.tableSquares = tableSquares;

        cameraHelper = new CameraHelper(map.getRegionWidth()/2,map.getRegionHeight()/2, map.getRegionWidth() + 500, map.getRegionHeight());
        dice = new Dice(this, new Vector2(500,2*cameraHelper.worldHeight/3));
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        mailCards = new ArrayList<MailCard>();
        bargainCards = new ArrayList<BargainCard>();
        eventCards = new ArrayList<EventCard>();
        players = new ArrayList<Player>();

        reader = new FileReader(this);
        reader.LoadXML();

        for (int i = 0; i < playersNum; i++)
        {
            players.add(new Player(i,initialMoney));
        }

    }

    public void update(float delta)
    {
        //Show Things
        //Select Things
        //Do The thing
        //End Turn

        cameraHelper.update();
    }

    void ShowOptions()
    {
        dice.setActive(true);
        //View Events
        //View Cards
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(map, 0, 0);
        dice.render(batch);

        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).render(batch);
        }

    }
}
