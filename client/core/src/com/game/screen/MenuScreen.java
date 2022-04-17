package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.game.BaseActor;
import com.game.BaseGame;
import com.game.BaseScreen;
import com.game.NinjaPie;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchDown;


public class MenuScreen extends BaseScreen {

	private String userName;
	
	
    public MenuScreen() {
    	super();
    	
//TODO: There should be a text entry field for user to enter their name
    	
//    	userName = "";
    	userName = "Default Player Name";	// STUB
    	
	}
    public MenuScreen(String userName) {
    	super();
    	
		this.userName = userName;
	}

	public void initialize() {

        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("dojo.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("title.png");

        TextButton startButton = new TextButton("Join Game", BaseGame.textButtonStyle);
        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
            	
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
	    		
            	System.out.println("Running MenuScreen startButton handler called. userName: " + userName);
                if( userName == null || userName.isBlank() ) {
                	return false;
                }
                
                NinjaPie.setActiveScreen(new LobbyScreen(userName));
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

        uiTable.add(title).colspan(2).padBottom(50);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);
    }

    @Override
    public void update(float deltaTime) {
    }

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {

        	System.out.println("Running MenuScreen keyDown handler called "
        			+ "with isKeyPressed(Keys.ENTER). userName: " + userName);
            if( userName == null || userName.isBlank() ) {
            	return false;
            }
            
            NinjaPie.setActiveScreen(new LobbyScreen(userName));
        }

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
