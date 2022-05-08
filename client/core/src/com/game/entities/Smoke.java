package com.game.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.BaseActor;

public class Smoke extends BaseActor {

    public Smoke(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("smoke.png");
        addAction(Actions.fadeOut(0.5f));
        addAction(Actions.after(Actions.removeActor()));
    }
}
