package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.*;
import com.game.entities.*;
import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.transport.*;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchDown;


public class MenuScreen extends BaseScreen {

	public static final int MAX_CHARACTERS = 20;
	
// STUB		
//	private String userName = "Default Player Name";	// STUB
	
	
	private TextField nameField;
	
    public MenuScreen() {
    	super();
	}

	public void initialize() {

        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("dojo.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("title.png");


    	// create Font for Label and TextField
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
        		Gdx.files.internal("OpenSans.ttf") );
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 40;
        fontParameters.borderWidth = 1;
//      fontParameters.color = Color.WHITE;
//      fontParameters.color = Color.GOLD;
        BitmapFont font = fontGenerator.generateFont(fontParameters);
//        font.setColor(Color.BLACK);
        
        LabelStyle labelStyle = new LabelStyle(font, Color.GOLD);
        Label nameLabel = new Label("Name:", labelStyle);

    	// create Font for text
        TextFieldStyle fieldStyle = new TextFieldStyle();
        fieldStyle.font = font;
        fieldStyle.fontColor = Color.GOLD;

        Texture fieldBackground = new Texture(Gdx.files.internal("dialog-translucent.png"));
//        Texture fieldBackground = new Texture(Gdx.files.internal("dialog.png"));  // also tried  button.png 
        
//        TextureRegion fieldRegion = new TextureRegion(fieldBackground);
//        fieldRegion.setRegionWidth(600);
//        fieldRegion.setRegionHeight(400);
//        fieldRegion.setTexture( new Texture(Gdx.files.internal("button.png")) );
        Drawable fieldBackgroundDrawable = new TextureRegionDrawable(fieldBackground);
        fieldBackgroundDrawable.setMinWidth(200);
        fieldBackgroundDrawable.setMinHeight(60);
        fieldStyle.background = fieldBackgroundDrawable;
        
        nameField = new TextField(Ninja.getPlayerName(), fieldStyle);
        nameField.setMaxLength(MAX_CHARACTERS);
        nameField.setColor(Color.LIGHT_GRAY);
        
        TextButton startButton = new TextButton("Join Game", BaseGame.textButtonStyle);
        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
            	
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;

            	
                return gotoLobby();
            }
        });


        TextButton rulesButton = new TextButton("Rules", BaseGame.textButtonStyle);
        rulesButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;

// TODO: make a rules/credits dialog box pop up
                
                return true;
            }
        });
        
        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        quitButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                Gdx.app.exit();
                return false;
            }
        });

        uiTable.add(title).colspan(4).padTop(40).padBottom(40);
        uiTable.row();
        uiTable.add(nameLabel);
//        uiTable.add(nameField).colspan(2).fillX(); 
        uiTable.add(nameField).colspan(3).width(400).height(60).left();
        uiTable.row().padTop(30);
        uiTable.add(startButton).colspan(2).right();
        uiTable.add(rulesButton);
        uiTable.add(quitButton);

        mainStage.setScrollFocus(nameField);
        mainStage.setKeyboardFocus(nameField);
        nameField.getOnscreenKeyboard().show(true);
    }

    @Override
    public void update(float deltaTime) {
    }

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {

//        	System.out.println("Running MenuScreen keyDown handler called "
//        			+ "with isKeyPressed(Keys.ENTER). userName: " + userName);
        	return gotoLobby();
        }

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();

        return false;
    }

    
    private boolean gotoLobby() {
    	
        String userName = nameField.getText();
//    	System.out.println("MenuScreen.gotoLobby(). userName: " + userName);
    	if( userName == null || userName.isBlank() ) {
// TODO: popup error message about must enter user name
        	return false;
        }
        
    	//NOTE: this can return a STUB_GameSession if GameDesignVars.USE_STUB_IN_PLACE_OF_SERVER = true       	
    	GameSessionInterface serverSession = GameSession.getGameSession();
    	
        PlayerData userPlayer = serverSession.createNewPlayer(userName);
		if(userPlayer == null) {
// TODO: popup error message about server not responding or could not create game instance
			return false;
		}
		
		
        Ninja.setPlayerData(userPlayer);
		Ninja.setPlayerName(userName);
		Ninja.setPlayerID(userPlayer.getPublicID());
        
        NinjaPie.setActiveScreen(new LobbyScreen());
        return true;
    }
    
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
