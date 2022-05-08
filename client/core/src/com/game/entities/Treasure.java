package com.game.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.BaseActor;

public class Treasure extends BaseActor {

    public Treasure(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("ninja_star.png");
    }
}
