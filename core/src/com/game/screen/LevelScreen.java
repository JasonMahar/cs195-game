package com.game.screen;



import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.game.*;
import com.game.entities.*;


public class LevelScreen extends BaseScreen {

    private Ninja ninja;



    public void initialize() {

        TilemapActor tma = new TilemapActor("grass.tmx", mainStage);

        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"),
                    mainStage);
        }

        ninja = new Ninja(0, 0, mainStage);

        //iPosition in the stage to add other character
       // ninja = new Ninja(400, 300, mainStage);
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
