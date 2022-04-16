package com.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.*;
import com.game.entities.*;
import com.game.entities.ui.DialogBox;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {
    private Ninja ninja;
    private boolean win;
    ArrayList<Pie> pies;
    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;

    public void initialize() {

        TilemapActor tilemapActor = new TilemapActor("map.tmx", mainStage);
        pies = new ArrayList<Pie>();

        for (MapObject obj : tilemapActor.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }



        MapObject startPoint = tilemapActor.getRectangleList("Start").get(0);
        MapProperties props = startPoint.getProperties();
        ninja = new Ninja((float) props.get("x"), (float) props.get("y"), mainStage);

        win = false;

        // user interface code



        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();



        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!LevelScreen.this.isTouchDownEvent(e)) return false;

                instrumental.dispose();
                oceanSurf.dispose();

                NinjaPie.setActiveScreen(new LevelScreen());
                return true;
            }
        });

        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture(Gdx.files.internal("audio.png"));
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);

        Button muteButton = new Button(buttonStyle2);
        muteButton.setColor(Color.CYAN);

        muteButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!LevelScreen.this.isTouchDownEvent(e)) return false;

                audioVolume = 1 - audioVolume;
                instrumental.setVolume(audioVolume);
                oceanSurf.setVolume(audioVolume);

                return true;
            }
        });

        uiTable.pad(10);

        uiTable.add().expandX().expandY();
        uiTable.add(muteButton).top();
        uiTable.add(restartButton).top();



        waterDrop = Gdx.audio.newSound(Gdx.files.internal("Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("Ocean_Waves.ogg"));

        audioVolume = 1.00f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();
    }

    public void update(float deltaTime) {

        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.game.entities.Rock"))
            ninja.preventOverlap(rockActor);

        for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.game.entities.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (ninja.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true;
                waterDrop.play(audioVolume);
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }
/*
        for(Pie pie: pies){


        }

        private void updatePie(float delta) {


        }
 */
    }


}
