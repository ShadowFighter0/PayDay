package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;
import java.util.ArrayList;


public class Level {

    CameraHelper cameraHelper;
    BitmapFont font;
    FileReader reader;

    ArrayList<MailCard> mailCards;
    ArrayList<BargainCard> bargainCards;
    ArrayList<EventCard> eventCards;

    ArrayList<TableSquare> tableSquares;

    TextureRegion map;
    ArrayList<Player> players;

    public Level(int playersNum)
    {
        map = AssetManager.getInstance().getTexture("ProvisionalMap");

        //Camera stuff
        cameraHelper = new CameraHelper(map.getRegionWidth()/2,map.getRegionHeight()/2,1500,1000); //map.getWidth(),map.getHeight());

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
        batch.draw(map, 0, 0);

        //font.getData().setScale(cameraHelper.currentZoom);
        //font.draw(batch,
        //        "",
        //        cameraHelper.position.x + cameraHelper.currentWidth/2 - (100 * cameraHelper.currentZoom),
        //        cameraHelper.position.y + (cameraHelper.currentHeight/2));
    }
}
