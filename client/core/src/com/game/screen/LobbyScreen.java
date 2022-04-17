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


import com.wickedgames.cs195.model.GameDesignVars;
import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;
import com.wickedgames.cs195.transport.GameSessionInterface;
import com.wickedgames.cs195.transport.STUB_GameSession;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchDown;

import java.util.Collection;

//import org.json.JSONException;

public class LobbyScreen extends BaseScreen {

	private String userName;
	private Integer userID;
	private GameSessionInterface serverSession;
	private GameInstance gameData;
	
	
    public LobbyScreen(String userName) {
    	System.out.println("Creating LobbyScreen ");

    	this.userName = userName;
    	
    	// this try shouldn't be here. 
    	// TODO: figure out which method is not catching its JSONException
    	try { // JSONException

// STUB:       	//        	serverSession = new GameSession();
			serverSession = new STUB_GameSession();

    		PlayerData userPlayer = serverSession.createNewPlayer(userName);
	    	userID = userPlayer.getPublicID();
	    	
// TODO: gameID shouldn't be hardcoded. Normally would do a createGame first and get the ID from there
	    	gameData = serverSession.joinGame(GameDesignVars.DEFAULT_GAME_ID, userPlayer);


	    	System.out.println("Running LobbyScreen.initialize() 2");
	    	
	    	Collection<PlayerData> players = gameData.getAllPlayers();
	    	for(PlayerData player : players) {
	    		System.out.println("found player: " + player);	
	    	}
	    	
	    	System.out.println("Running LobbyScreen.initialize() 3");
		    	
    	} catch (Exception e) {

			System.out.println("LobbyScreen.initialize() caught JSONException = " + e);
			e.printStackTrace();
    	}
    	
	}

	public void initialize() {
    	System.out.println("Running LobbyScreen.initialize()");

    	
    	
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("dojo.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("title.png");

        TextButton startButton = new TextButton("Start Game", BaseGame.textButtonStyle);
        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                NinjaPie.setActiveScreen( new LevelScreen(serverSession, gameData.getID(), userID) );
                return true;
            }
        });

        TextButton quitButton = new TextButton("Back", BaseGame.textButtonStyle);
        quitButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                NinjaPie.setActiveScreen(new MenuScreen(userName));
                return true;
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
        if (Gdx.input.isKeyPressed(Keys.ENTER))
        	NinjaPie.setActiveScreen( new LevelScreen(serverSession, gameData.getID(), userID) );

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
        	NinjaPie.setActiveScreen( new MenuScreen(userName) );

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
