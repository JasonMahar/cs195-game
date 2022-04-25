package com.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.BaseActor;

public class Pie extends BaseActor {

    public Pie(float x, float y, Stage stage) {
        super(x, y, stage);
        float random = MathUtils.random(1);
        loadTexture("pie.png");

        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.02f)));
        addAction(Actions.forever(Actions.rotateBy(30 + random, 1)));

        setSpeed(400);
        setMaxSpeed(400);
        setDeceleration(0);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        wrapAroundWorld();
    }


}
