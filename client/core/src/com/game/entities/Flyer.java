package com.game.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.BaseActor;

public class Flyer extends BaseActor {

    public Flyer(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("bomb.png");
        setSize(48, 48);
        setBoundaryPolygon(6);

    }

    public void act(float dt) {
        super.act(dt);

        applyPhysics(dt);
        boundToWorld();
    }
}