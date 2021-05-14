package com.dprieto.game;

public class EventCard extends Card {

    int amount;
    Constants.EventType type;

    public EventCard(String name, String description, int amount, Constants.EventType type) {
        super(name, description);
    }
}
