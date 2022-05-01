package com.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.BaseActor;

public class Pie extends BaseActor {

    public Pie(float x, float y, Stage stage) {
        super(x, y, stage);
        
// jason's comment: the previous random rotation of 30-34 degrees/sec seemed pointless, 
// 	 so changed it to 30-359 degrees/sec
//        float random = MathUtils.random(5);
        float random = MathUtils.random(330);
        loadTexture("pie.png");

// jason's comment: not sure we want pies to disappear based on time instead of distance?
//	 Either way, if we're not allowing infinite pies, we probably need some kind of callback
//   or way to detect when Pie is removed.
        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.2f)));	// not sure fade out is desirable
        addAction(Actions.after(Actions.removeActor()));
        addAction(Actions.forever(Actions.rotateBy(30 + random, 1)));

        setSpeed(400);
        setMaxSpeed(400);
        setDeceleration(0);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        
        // NOTE: an easy way to check for pies hitting world boundary would be if we 
        //		changed boundToWorld() to return true when it tries to go past boundary
//        boolean hitBoundary = boundToWorld();
        boundToWorld();


// jason's comment: I'm assuming we don't want players or pies to wrap around to other side of screen/world
//             
//        wrapAroundWorld();
    }
}
