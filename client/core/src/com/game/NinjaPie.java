package com.game;

import com.game.screen.MenuScreen;

public class NinjaPie extends BaseGame {

	public void create() {
		super.create();
		setActiveScreen(new MenuScreen());
	}
}
