package com.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.BaseActor;

public class Ninja extends BaseActor {


    private Pie pie;

    Animation north;
    Animation south;
    Animation east;
    Animation west;

    public Ninja(float x, float y, Stage stage) {
        super(x, y, stage);
        String[] filenames = {"run/ninja-run_00.png", "run/ninja-run_01.png", "run/ninja-run_02.png", "run/ninja-run_03.png", "run/ninja-run_04.png", "run/ninja-run_05.png"};


        loadAnimationFromFiles(filenames, 0.1f, true);


        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        setBoundaryPolygon(8);

    }

    public void shoot() {
        if (getStage() == null)
            return;

        Pie pie = new Pie(0, 0, this.getStage());
        pie.centerAtActor(this);
        pie.setRotation(this.getRotation());
        pie.setMotionAngle(this.getRotation());
    }


    public void act(float deltaTime) {
        super.act(deltaTime);

        // pause animation when character not moving
        if (getSpeed() == 0) {
            setAnimationPaused(true);
        } else {
            setAnimationPaused(false);

            // set direction animation
            float angle = getMotionAngle();

            if (angle >= 45 && angle <= 135) {
                setAnimation(north);
            } else if (angle > 135 && angle < 225) {
                setAnimation(west);
            } else if (angle >= 225 && angle <= 315) {
                setAnimation(south);
            } else {
                setAnimation(east);
            }

            float degreesPerSecond = 120; // degrees per second
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                rotateBy(degreesPerSecond * deltaTime);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                rotateBy(-degreesPerSecond * deltaTime);

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                accelerateAtAngle(getRotation());


            }

            applyPhysics(deltaTime);

            wrapAroundWorld();


        }

    }
}