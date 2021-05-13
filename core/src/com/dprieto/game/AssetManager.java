package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
        //TextureRegion region;
        //
        //
        ////Load BuildingPlace
        //Texture texture = new Texture(Gdx.files.internal("Towers/BuildingPlace.png"));
        //region = new TextureRegion(texture, 0, 0, 135, 90);
        //textures.put("sign",region);
        //region = new TextureRegion(texture, 0, 90, 135, 90);
        //textures.put("towerField",region);
    }
}
