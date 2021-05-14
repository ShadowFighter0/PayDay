package com.dprieto.game;

public class BargainCard extends Card {

    int buyAmount;
    int sellAmount;

    public BargainCard(String name, String description, int buyAmount, int sellAmount) {
        super(name, description);

        this.buyAmount = buyAmount;
        this.sellAmount = sellAmount;
    }
}
