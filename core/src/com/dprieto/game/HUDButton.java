package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDButton extends HUDElement{

    enum ButtonType {Quit, ShowEvents, ShowCards, MailLeft, MailRight, ExitShowCard, ExitShowEvents}
    ButtonType type;

    Level level;
    HUDText text = null;

    public HUDButton (String imageName, Vector2 position, Anchor anchor, ButtonType buttonType, Level level, Camera camera) {
        super(imageName, position, anchor, camera);

        this.type = buttonType;
        this.level = level;
    }

    public HUDButton (String imageName, Vector2 position, Vector2 dimensionModifier, Anchor anchor, ButtonType buttonType, Level level, Camera camera, String content, BitmapFont font) {
        super(imageName, position, dimensionModifier, anchor, camera );

        this.type = buttonType;
        this.level = level;

        this.text = new HUDText(position, anchor, font, camera);
        this.text.setText(content);
    }
    public HUDButton (String imageName, Vector2 position, Vector2 dimensionModifier, Anchor anchor, ButtonType buttonType, Level level, Camera camera) {
        super(imageName, position, dimensionModifier, anchor, camera );

        this.type = buttonType;
        this.level = level;
    }

    public HUDButton (String imageName, Vector2 position, Anchor anchor, Camera camera, String content, BitmapFont font) {
        super(imageName, position, anchor, camera);
        this.text = new HUDText(position, anchor, font, camera);
        this.text.setText(content);
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
        if(text != null)
        {
            text.render(batch);
        }
   }

    public boolean checkClicked (Vector2 point) {
        if (isActive)
        {
            if (point.x > currentPosition.x - dimension.x / 2 && point.x < currentPosition.x + dimension.x / 2
                    && point.y > currentPosition.y - dimension.y / 2 && point.y < currentPosition.y + dimension.y / 2)
            {
                OnClicked();
                return true;
            }
            else
            {
                OnNotClicked();
            }
        }
        return false;
    }

    public void OnClicked(){
        switch (type)
        {

            case ShowEvents:
                level.showEvents = true;

                break;

            case ShowCards:
                level.ShowCards();
                break;

            case MailLeft:

                level.cardShowed--;
                break;

            case MailRight:

                level.cardShowed++;
                break;

            case ExitShowCard:

                level.HideShowCards();
                break;

            case ExitShowEvents:

                break;

            case Quit:
                Gdx.app.exit();
                break;
        }
    }

    public void OnNotClicked() {

    }

}