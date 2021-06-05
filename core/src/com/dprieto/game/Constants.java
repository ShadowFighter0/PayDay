package com.dprieto.game;

import java.util.HashMap;

public class Constants {

    static Constants instance;

    public static final float ZOOM_MOBILE_SENSITIVITY = 0.001f;
    public static final float PAN_MOBILE_SENSITIVITY = 0.25f;
    public static final float PLAYER_SPEED = 200;
    public static final float EXTRA_HUD = 250f;
    public static final float DISTANCE = 5;

    public static final float MAILSHOWNS = 10;
    public static final float BARGAINSHOWNS = 10;


    Constants()
    {


    }

    enum EventType {EarnMoney, PlayersEarnMoney, PlayerLoseMoney, StealBargain }
    enum SquareType {Mail, Event, Lottery, Bargain, StartMonth}

    public static Constants getInstance()
    {
        if (instance == null)
        {
            instance = new Constants();
        }
        return instance;
    }

}



