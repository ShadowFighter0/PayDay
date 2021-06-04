package com.dprieto.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;


public class PayDay extends Game {

	public MainMenu mainMenu;
	public GameScreen gameScreen;

	public static PayDay instance;

	@Override
	public void create() {
		instance = this;
		mainMenu = new MainMenu();
//		gameScreen = new GameScreen();
		setScreen(mainMenu);
	}
}
