package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class FileReader {

    Level level;

    public FileReader(Level level)
    {
        this.level = level;
    }


    public void LoadXML()
    {
        XmlReader reader = new XmlReader();
        XmlReader.Element root = reader.parse(Gdx.files.internal("Cards.xml"));

        //Mail
        Array<XmlReader.Element> folder = root.getChildrenByName("mail");
        Array<XmlReader.Element> cards;

        for (int i = 0; i < folder.size; i++)
        {
            cards = folder.get(i).getChildrenByName("card");
            for (int j = 0; j < cards.size; j++)
            {
                XmlReader.Element card = cards.get(j);

                String name = card.get("name");
                String description = card.get("description");
                int amount = Integer.parseInt(card.get("amount"));

                Gdx.app.debug("Mail " + j, "name: " + name + " description: " + description + " amount: " + amount);
                level.mailCards.add(new MailCard(name, description, level, amount));
            }
        }

        //Event
        folder = root.getChildrenByName("event");

        for (int i = 0; i < folder.size; i++)
        {
            cards = folder.get(i).getChildrenByName("card");
            for (int j = 0; j < cards.size; j++)
            {
                XmlReader.Element card = cards.get(j);

                String name = card.get("name");
                String description = card.get("description");
                int amount = Integer.parseInt(card.get("amount"));
                Constants.EventType type = null;

                switch (card.get("type"))
                {
                    case "EarnMoney":
                        type = Constants.EventType.EarnMoney;
                        break;
                    case "PlayersEarnMoney":
                        type = Constants.EventType.PlayersEarnMoney;
                        break;
                    case "PlayerLoseMoney":
                        type = Constants.EventType.PlayerLoseMoney;
                        break;
                    case "StealBargain":
                        type = Constants.EventType.StealBargain;
                        break;
                }
                Gdx.app.debug("Event " + j, "name: " + name + " description: " + description + " amount: " + amount + " Type: " + type);
                level.eventCards.add(new EventCard(name, description, level, amount, type));
            }
        }


        //Bargain
        folder = root.getChildrenByName("bargain");

        for (int i = 0; i < folder.size; i++)
        {
            cards = folder.get(i).getChildrenByName("card");
            for (int j = 0; j < cards.size; j++)
            {
                XmlReader.Element card = cards.get(j);

                String name = card.get("name");
                String description = card.get("description");
                int buyAmount = Integer.parseInt(card.get("buyAmount"));
                int sellAmount = Integer.parseInt(card.get("sellAmount"));

                Gdx.app.debug("Bargain " + j, "name: " + name + " description: " + description + " buy amount: " + buyAmount + " sell amount: " + sellAmount);
                level.bargainCards.add(new BargainCard(name, description, level, buyAmount, sellAmount));
            }
        }
    }
}
