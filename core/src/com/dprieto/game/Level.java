package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


public class Level {

    CameraHelper cameraHelper;
    BitmapFont font;
    FileReader reader;

    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    TextureRegion map;
    ArrayList<Player> players;

    public Level()
    {
        //Camera stuff
        cameraHelper = new CameraHelper(1000,1000); //map.getWidth(),map.getHeight());

        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        mailCards = new ArrayList<MailCard>();
        bargainCards = new ArrayList<BargainCard>();
        eventCards = new ArrayList<EventCard>();
        players = new ArrayList<Player>();

        reader = new FileReader(this);
        reader.LoadXML();

    }

    public void update(float delta)
    {
        cameraHelper.update();
    }

    public void render(SpriteBatch batch)
    {
        font.getData().setScale(cameraHelper.currentZoom);
        font.draw(batch,
                "",
                cameraHelper.position.x + cameraHelper.currentWidth/2 - (100 * cameraHelper.currentZoom),
                cameraHelper.position.y + (cameraHelper.currentHeight/2));
    }
}
