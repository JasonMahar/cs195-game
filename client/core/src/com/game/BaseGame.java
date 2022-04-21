package com.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public abstract class BaseGame extends Game {
    /**
     * Stores reference to game; used when calling setActiveScreen method.
     */
    private static BaseGame game;
    public static Label.LabelStyle labelStyle; // BitmapFont + Color
    public static TextButton.TextButtonStyle textButtonStyle;

    /**
     * Called when game is initialized; stores global reference to game object.
     */
    public BaseGame() {
        game = this;
    }

    /**
     * Called when game is initialized,
     * after Gdx.input and other objects have been initialized.
     */
    public void create() {
        // prepare for multiple classes/stages/actors to receive discrete input
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 48;
        fontParameters.color = WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Linear;
        fontParameters.magFilter = Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameters);
        labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;

        textButtonStyle = new TextButton.TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        NinePatch buttonPatch = new NinePatch(buttonTex, 24, 24, 24, 24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = customFont;
        textButtonStyle.fontColor = GRAY;
    }

    /**
     * Used to switch screens while game is running.
     * Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s) {
        game.setScreen(s);
    }
}