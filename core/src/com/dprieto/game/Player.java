package com.dprieto.game;

import java.util.ArrayList;

public class Player {

    int numPlayer;
    int money;

    boolean mustGiveBargain;

    ArrayList<BargainCard> bargains;
    ArrayList<MailCard> mailcards;
    ArrayList<EventCard> events;

    public Player (int numPlayer, int initialMoney)
    {
        this.numPlayer = numPlayer;
        money = initialMoney;

        bargains = new ArrayList<BargainCard>();
        mailcards = new ArrayList<MailCard>();
        events = new ArrayList<EventCard>();
    }

}
