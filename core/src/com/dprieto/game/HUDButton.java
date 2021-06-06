package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDButton extends HUDElement{

    enum ButtonType {Quit, ShowEvents, EventLeft, EventRight, UseEvent, ExitShowEvents, ShowCards, MailLeft, MailRight, EndStartMonth, ExitShowCard}
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

        if(type != null)
        {
            switch (type)
            {
                //region ShowCards
                case ShowCards:
                    level.cardShowed = 0;
                    level.showCards = true;
                    level.showEvents = false;

                    level.ShowCards();
                    break;

                case ExitShowEvents:
                case ExitShowCard:

                    level.showEvents = false;
                    level.showCards = false;

                    level.HideShowCards();
                    break;

                case MailLeft:

                    level.cardShowed--;
                    break;

                case MailRight:

                    level.cardShowed++;
                    break;

                //endregion

                //region ShowEvents

                case ShowEvents:
                    level.eventShowed = 0;
                    level.showEvents = true;
                    level.showCards = false;

                    level.ShowCards();

                    break;


                case EventLeft:
                    level.eventShowed--;

                    break;

                case EventRight:
                    level.eventShowed++;

                    break;

                case UseEvent:
                    level.UseEvent();

                    break;


                //endregion

                case EndStartMonth:

                    level.endStartMonth = true;
                    break;


                case Quit:
                    Gdx.app.exit();
                    break;
            }
        }
    }

    public void OnNotClicked() {

    }

}