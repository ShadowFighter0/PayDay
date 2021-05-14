package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.util.HashMap;

public class AssetManager {

    public static AssetManager instance;

    HashMap<String,TextureRegion> textures;

    private AssetManager()
    {
        textures = new HashMap<String,TextureRegion>();
        LoadTextures();
    }

    public static AssetManager getInstance()
    {
        if(instance == null)
        {
            instance = new AssetManager();
        }
        return instance;
    }

    public TextureRegion getTexture(String textureName)
    {
        return textures.get(textureName);
    }

    void LoadTextures()
    {
       TextureRegion region;

       //Load Cards
        Texture texture = new Texture(Gdx.files.internal("Cards/Cards.png"));
        region = new TextureRegion(texture, 0, 0, 190, 140);
        textures.put("RedFront",region);
        region = new TextureRegion(texture, 380, 0, 190, 140);
        textures.put("RedBack",region);
        region = new TextureRegion(texture, 0, 140, 190, 140);
        textures.put("GreenFront",region);
        region = new TextureRegion(texture, 0, 280, 190, 140);
        textures.put("GreenBack",region);
        region = new TextureRegion(texture, 380, 140, 190, 140);
        textures.put("BlueFront",region);
        region = new TextureRegion(texture, 380, 280, 190, 140);
        textures.put("BlueBack",region);

        texture = new Texture(Gdx.files.internal("Cards/PlayerCard.png"));
        region = new TextureRegion(texture, 0, 0, 64, 64);
        textures.put("PlayerCard",region);

        //Lottery
        texture = new Texture(Gdx.files.internal("Cards/Lottery.png"));
        region = new TextureRegion(texture, 0, 0, 68, 68);
        textures.put("Lottery",region);

        //Dice
        texture = new Texture(Gdx.files.internal("Dice/Dice.png"));
        region = new TextureRegion(texture, 0, 136, 68, 68);
        textures.put("1",region);
        region = new TextureRegion(texture, 68, 68, 68, 68);
        textures.put("2",region);
        region = new TextureRegion(texture, 68, 0, 68, 68);
        textures.put("3",region);
        region = new TextureRegion(texture, 68, 136, 68, 68);
        textures.put("4",region);
        region = new TextureRegion(texture, 0, 68, 68, 68);
        textures.put("5",region);
        region = new TextureRegion(texture, 0, 0, 68, 68);
        textures.put("6",region);

        //Pieces
        //Blue
        texture = new Texture(Gdx.files.internal("Pieces/BluePiece.png"));
        region = new TextureRegion(texture, 0, 0, 64, 64);
        textures.put("BluePiece",region);
        //Red
        texture = new Texture(Gdx.files.internal("Pieces/RedPiece.png"));
        region = new TextureRegion(texture, 0, 0, 68, 68);
        textures.put("RedPiece",region);
        //Green
        texture = new Texture(Gdx.files.internal("Pieces/GreenPiece.png"));
        region = new TextureRegion(texture, 0, 0, 68, 68);
        textures.put("GreenPiece",region);
        //Yellow
        texture = new Texture(Gdx.files.internal("Pieces/YellowPiece.png"));
        region = new TextureRegion(texture, 0, 0, 68, 68);
        textures.put("YellowPiece",region);

        //Map
        texture = new Texture(Gdx.files.internal("Maps/ProvisionalMap.png"));
        region = new TextureRegion(texture, 0, 0, 1000, 1000);
        textures.put("ProvisionalMap",region);

    }
}
