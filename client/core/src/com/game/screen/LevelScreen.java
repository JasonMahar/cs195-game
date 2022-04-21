package com.game.screen;



import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.game.*;
import com.game.entities.*;


public  class LevelScreen extends BaseScreen {

    private Ninja ninja;



    public void initialize() {
        BaseActor space = new BaseActor(0, 0, mainStage);
        space.loadTexture("grass.jpg");
        space.setSize(800, 600);
        BaseActor.setWorldBounds(space);

        ninja = new Ninja(400, 300, mainStage);




    }

    public void update(float dt) {




    }

    // override default InputProcessor methods
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE)
            ninja.shoot();



        return false;
    }
}
