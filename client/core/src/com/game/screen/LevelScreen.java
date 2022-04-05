package com.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.*;
import com.game.entities.*;
import com.game.entities.ui.DialogBox;


public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;
    private Label starfishLabel;

    private DialogBox dialogBox;

    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;

    public void initialize() {

        TilemapActor tilemapActor = new TilemapActor("map.tmx", mainStage);

        for (MapObject obj : tilemapActor.getTileList("Starfish")) {
            MapProperties props = obj.getProperties();
            new Starfish((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tilemapActor.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tilemapActor.getTileList("Sign")) {
            MapProperties props = obj.getProperties();
            Sign sign = new Sign((float) props.get("x"), (float) props.get("y"), mainStage);
            sign.setText((String) props.get("message"));
        }

        MapObject startPoint = tilemapActor.getRectangleList("Start").get(0);
        MapProperties props = startPoint.getProperties();
        turtle = new Turtle((float) props.get("x"), (float) props.get("y"), mainStage);

        win = false;

        // user interface code

        starfishLabel = new Label("Starfish Left:", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);

        ButtonStyle buttonStyle = new ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

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

        ButtonStyle buttonStyle2 = new ButtonStyle();

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
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(muteButton).top();
        uiTable.add(restartButton).top();

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.row();
        uiTable.add(dialogBox).colspan(4);

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

        for (BaseActor rockActor : BaseActor.getList(mainStage, "org.seariver.actor.Rock"))
            turtle.preventOverlap(rockActor);

        for (BaseActor starfishActor : BaseActor.getList(mainStage, "org.seariver.actor.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.collected) {
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

        if (BaseActor.count(mainStage, "org.seariver.actor.Starfish") == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        starfishLabel.setText("Starfish Left: " + BaseActor.count(mainStage, "org.seariver.actor.Starfish"));

        for (BaseActor signActor : BaseActor.getList(mainStage, "org.seariver.actor.Sign")) {
            Sign sign = (Sign) signActor;

            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);

            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }
    }
}
