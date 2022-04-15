package com.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Pool;
import com.game.BaseActor;
import com.game.NinjaPie;


public class Pie extends BaseActor   {

    public final static int STATE_NORMAL = 0;
    public final static int STATE_HIT = 1;
    public int state;


    public final static float SPEED_X = 5.5f;
    public final static float DRAW_WIDTH = .32f;
    public final static float DRAW_HEIGHT = .32f;

    public final static float WIDTH = .31f;
    public final static float HEIGHT = .31f;



    public float stateTime;



    public Pie(float x, float y, Stage s) {
        super(x, y, s);
    }

    public void init(float x, float y) {
        stateTime = 0;
        state = STATE_NORMAL;
    }

    public void hit() {
        if (state == STATE_NORMAL) {
            state = STATE_HIT;
            stateTime = 0;
        }
    }

}
