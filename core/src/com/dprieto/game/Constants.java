package com.dprieto.game;

public class Constants {

    static Constants instance;

    public static final float ZOOM_MOBILE_SENSITIVITY = 0.001f;
    public static final float PAN_MOBILE_SENSITIVITY = 0.25f;
    public static final float PLAYER_SPEED = 300;
    public static final float EXTRA_HUD = 290;
    public static final float DISTANCE = 5;


    enum EventType {EarnMoney, PlayersEarnMoney, PlayerLoseMoney, StealBargain }
    enum SquareType {Mail, Event, Lottery, Bargain, StartMonth}


    Constants() {
    }


    public static Constants getInstance()
    {
        if (instance == null)
        {
            instance = new Constants();
        }
        return instance;
    }

}



