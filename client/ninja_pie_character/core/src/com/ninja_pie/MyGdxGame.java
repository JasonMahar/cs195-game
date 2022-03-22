package com.ninja_pie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
    SpriteBatch batch;
	Texture img;
	public Screen menuScreen, mainScreen;

	@Override
	public void create() {
		MenuScreen menuScreen = new MenuScreen();
		setScreen(menuScreen);
	}
}
