package com.game.screen;



import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.*;
import com.game.entities.*;

import static com.badlogic.gdx.Gdx.input;


public class LevelScreen extends BaseScreen {

    Hero hero;

    Treasure treasure;


    int health;
    int coins;
    int arrows;
    boolean gameOver;

    Label healthLabel;
    Label coinLabel;
    Label arrowLabel;
    Label messageLabel;


    public void initialize() {

        TilemapActor tma = new TilemapActor("map.tmx", mainStage);




        for (MapObject obj : tma.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }



        for (MapObject obj : tma.getTileList("Flyer")) {
            MapProperties props = obj.getProperties();
            new Flyer((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        MapObject treasureTile = tma.getTileList("Treasure").get(0);
        MapProperties treasureProps = treasureTile.getProperties();
        treasure = new Treasure((float) treasureProps.get("x"), (float) treasureProps.get("y"), mainStage);





        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);


        // User Interface
        health = 3;
        coins = 5;
        arrows = 3000;
        gameOver = false;

        healthLabel = new Label(" x " + health, BaseGame.labelStyle);
        healthLabel.setColor(Color.PINK);

        coinLabel = new Label(" x " + coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);

        arrowLabel = new Label(" x " + arrows, BaseGame.labelStyle);
        arrowLabel.setColor(Color.TAN);

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setVisible(false);



        BaseActor healthIcon = new BaseActor(0, 0, uiStage);
        healthIcon.loadTexture("heart-icon.png");

        BaseActor arrowIcon = new BaseActor(0, 0, uiStage);
        arrowIcon.loadTexture("pie.png");

        uiTable.pad(20);
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.add().expandX();
        uiTable.add(arrowIcon);
        uiTable.add(arrowLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(8).expandX().expandY();
        uiTable.row();

    }

    // GAME LOOP
    public void update(float deltaTime) {

        if (gameOver) return;

        // hero ans sword movement controls
        if (hero.isVisible()) {
            if (input.isKeyPressed(Input.Keys.LEFT)) hero.accelerateAtAngle(180);
            if (input.isKeyPressed(Input.Keys.RIGHT)) hero.accelerateAtAngle(0);
            if (input.isKeyPressed(Input.Keys.UP)) hero.accelerateAtAngle(90);
            if (input.isKeyPressed(Input.Keys.DOWN)) hero.accelerateAtAngle(270);
        }

        for (BaseActor solid : BaseActor.getList(mainStage, "com.game.entities.Solid")) {
            hero.preventOverlap(solid);

            for (BaseActor flyer : BaseActor.getList(mainStage, "com.game.entities.Flyer")) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid);
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                }
            }
        }

        for (BaseActor flyer : BaseActor.getList(mainStage, "com.game.entities.Flyer")) {
            if (hero.overlaps(flyer)) {
                hero.preventOverlap(flyer);
                flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                Vector2 heroPosition = new Vector2(hero.getX(), hero.getY());
                Vector2 flyerPosition = new Vector2(flyer.getX(), flyer.getY());
                Vector2 hitVector = heroPosition.sub(flyerPosition);
                hero.setMotionAngle(hitVector.angleDeg());
                hero.setSpeed(100);
                health--;
            }

        }

        for (BaseActor arrow : BaseActor.getList(mainStage, "com.game.entities.Arrow")) {
            for (BaseActor flyer : BaseActor.getList(mainStage, "com.game.entities.Flyer")) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove();
                    arrow.remove();
                    Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }

            for (BaseActor solid : BaseActor.getList(mainStage, "com.game.entities.Solid")) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid);
                    arrow.setSpeed(0);
                    arrow.addAction(Actions.fadeOut(0.5f));
                    arrow.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }




        if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            treasure.remove();
            gameOver = true;
        }

        if (health <= 0) {
            messageLabel.setText("Game over...");
            messageLabel.setColor(Color.RED);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            hero.remove();
            gameOver = true;
        }

        healthLabel.setText(" x " + health);
        arrowLabel.setText(" x " + arrows);
    }



    public void shootArrow() {
        if (arrows <= 0) return;
        arrows--;
        Arrow arrow = new Arrow(0, 0, mainStage);
        arrow.centerAtActor(hero);
        arrow.setRotation(hero.getMotionAngle());
        arrow.setMotionAngle(hero.getMotionAngle());
    }

    public boolean keyDown(int keycode) {

        if (gameOver) return false;



        if (keycode == Input.Keys.SPACE) {
            shootArrow();
        }


        return false;
    }
}
