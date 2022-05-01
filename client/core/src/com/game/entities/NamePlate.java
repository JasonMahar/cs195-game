package com.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.BaseActor;
import com.game.BaseGame;


public class NamePlate extends BaseActor {

	private static final String DEFAULT_NAME = "Enemy";
	
	private Label name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name.getText().toString();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name.setText(name);
	}

	public NamePlate(float x, float y, Stage stage) {
		super(x, y, stage);

		name = new Label(DEFAULT_NAME, BaseGame.labelStyle);
		name.setColor(Color.RED);
	}

}
