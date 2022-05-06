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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.*;
import com.game.entities.*;
import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.transport.*;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchDown;

import java.util.Collection;

//import org.json.JSONException;

public class LobbyScreen extends BaseScreen {

	
	private static final String TITLE_TEXT = "GAME LOBBY";


	private static final CharSequence PLAYERS_HEADING_TEXT = "Players:";
	
	
	private GameSessionInterface serverSession;
	private GameInstance gameData;
	

    
    public LobbyScreen() {
    	super();
    	System.out.println("Creating LobbyScreen ");
	}

	public void initializeGameData() {
    	System.out.println("Running LobbyScreen.initializeGameData()");

    	

    	//NOTE: this can return a STUB_GameSession if GameDesignVars.USE_STUB_IN_PLACE_OF_SERVER = true       	
    	serverSession = GameSession.getGameSession();
    	
// TODO: gameID shouldn't be hardcoded. Normally would do a createGame first and get the ID from there
//    			GameInstance gameData = serverSession.createNewGame(userPlayer);

// TODO: probably should move joinGame to MainScreen and going back from the LobbyScreen should
//		send a leaveGame.  Even if game crashes we should still do a leaveGame
    	// Don't joinGame if player is already in it 
    	// This happens when the player refreshes the screen
    	if( gameData == null || gameData.getPlayer(Ninja.getPlayerID()) == null ) {
    		gameData = serverSession.joinGame(GameDesignVars.GAME_LOBBY_ID, Ninja.getPlayerData());	
    	}
    	else {
            gameData = serverSession.getGameData(GameDesignVars.GAME_LOBBY_ID);
    		
    	}
        
		if(gameData == null) {
	    	System.out.println("ERROR: LobbyScreen.initializeGameData gameData is null");
			
// TODO: popup error message about server not responding or could not create game instance
			return;
//			return false;
		}
		

    	// this try shouldn't be here. 
    	// TODO: figure out which method is not catching its JSONException
//    	try { // JSONException

//    		PlayerData userPlayer = serverSession.createNewPlayer(Ninja.getPlayerName());
	    	
// TODO: gameID shouldn't be hardcoded. Normally would do a createGame first and get the ID from there
//			gameData = serverSession.getGameData(GameDesignVars.GAME_LOBBY_ID);

	    	Collection<PlayerData> players = gameData.getAllPlayers();
	    	for(PlayerData player : players) {
	    		System.out.println("found player: " + player);	
	    	}
	    	
//		    	
//    	} catch (Exception e) {
//
//			System.out.println("LobbyScreen.initialize() caught JSONException = " + e);
//			e.printStackTrace();
//    	}
    	
	}

	
	
	public void initialize() {
    	System.out.println("Running LobbyScreen.initialize()");

    	
    	initializeGameData();
    	
// TODO: all of these fonts and styles should really be moved to a skin.json

    	// create Font for text
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
        		Gdx.files.internal("OpenSans.ttf") );
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 40;
        BitmapFont font = fontGenerator.generateFont(fontParameters);
    	
        // background image
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("dojo.jpg");
        background.setSize(800, 600);
//        background.setOpacity(0.75f); 		// darken background
        

        

//        Texture titleBackground = new Texture(Gdx.files.internal("dialog.png")); 
//        TextureRegion titleRegion = new TextureRegion(titleBackground);;
//        Drawable titleDrawable = new TextureRegionDrawable(titleRegion);
        
        // Screen Title
        LabelStyle titleStyle = new LabelStyle(font, Color.GOLD);
//        titleStyle.background = titleDrawable;
        Label titleLabel = new Label(TITLE_TEXT, titleStyle);
        
        
//        // Players List grouping
//        WidgetGroup playersListGroup = new WidgetGroup();
//        playersListGroup.setColor(Color.FOREST);
//        playersListGroup.setSize(500, 300);

//        BaseActor playersListBackground = new BaseActor(0, 0, mainStage);
//        playersListBackground.loadTexture("dialog.png");
//        playersListBackground.setSize(500, 300);
//        playersListBackground.setColor(Color.RED);
        
        LabelStyle playersHeadingStyle = new LabelStyle(font, Color.GOLD);
        Label playersHeading = new Label(PLAYERS_HEADING_TEXT, playersHeadingStyle);
        

        Texture listSelection = new Texture(Gdx.files.internal("sign.png"));
//        Texture listSelection = new Texture(Gdx.files.internal("dialog.png")); // was  button.png 
        TextureRegion listSelectionRegion = new TextureRegion(listSelection);
        Drawable listSelectionDrawable = new TextureRegionDrawable(listSelectionRegion);
        ListStyle listStyle = new ListStyle( font, 
        		Color.GOLD, Color.WHITE, listSelectionDrawable );
        
//        listRegion.setRegionWidth(600);
//        listRegion.setRegionHeight(400);
//        listRegion.setTexture( new Texture(Gdx.files.internal("button.png")) );
        Texture listBackground = new Texture(Gdx.files.internal("dialog-translucent.png")); // was  button.png 
        TextureRegion listBackgroundRegion = new TextureRegion(listBackground);
        Drawable listBackgroundDrawable = new TextureRegionDrawable(listBackgroundRegion);
        listStyle.background = listBackgroundDrawable;
        
        List<String> list = new List<String>(listStyle);
        list.clearItems();
//        list.setColor(Color.CYAN);
        list.setColor(Color.LIGHT_GRAY);
        
        Collection<PlayerData> playersList = gameData.getAllPlayers();
        
        if( playersList.isEmpty() ) {

        	System.out.println("LobbyScreen.initialize(). No players found. ");
        }
        else {
        	
            String[] nameStrings = new String[playersList.size()];
        	int i=0;
            for( PlayerData player : playersList) {
            	nameStrings[i++] = "   " + player.getName();
            	System.out.println("LobbyScreen.initialize adding player to list: " + player.getName());
            }
            list.setItems( nameStrings );
            list.setSize(440, 222);
            list.setPosition(150, 140);
            list.setOrigin(55, 88);
        }

//        playersListGroup.addActor(playersListBackground);
//        playersListGroup.addActor(playersHeading);
//        playersListGroup.addActor(list);
        
        
        // Now lay them all out in the uiTable
        
        uiTable.add(titleLabel).colspan(4).padBottom(40);
        uiTable.row();
        
//      uiTable.add(playersListBackground);
      uiTable.add(playersHeading).colspan(4).row();
      uiTable.add(list).colspan(4).width(480).height(240).padBottom(40);
//        uiTable.add(playersListGroup).colspan(2).padBottom(50);
        uiTable.row();
        
        
        createAndAddButtons();
        uiTable.top();
        
        mainStage.setScrollFocus(list);
        mainStage.setKeyboardFocus(list);
    }

    private void createAndAddButtons() {

    	// can't get "Start Game" to fit on screen. don't know how to resize it
        TextButton startButton = new TextButton("Start Game", BaseGame.textButtonStyle);
//        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
//        TextButtonStyle style = startButton.getStyle(); 
//        startButton.setWidth(2.0f);
//        startButton.scaleBy(-0.6f, 1.0f); - doesn't change anything :(
//        startButton.setSize(startButton.getWidth()/2, startButton.getHeight());
        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                
// TODO: need to send game state change to Server so that it will set starting locations
                
                NinjaPie.setActiveScreen( new LevelScreen() );
                return true;
            }
        });

        TextButton refreshButton = new TextButton("Refresh", BaseGame.textButtonStyle);
        refreshButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                NinjaPie.setActiveScreen(new LobbyScreen());
                return true;
            }
        });

        TextButton backButton = new TextButton("Back", BaseGame.textButtonStyle);
        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(touchDown)) return false;
                NinjaPie.setActiveScreen(new MenuScreen());
                return true;
            }
        });
        
        
//      uiTable.add(startButton).right();
      uiTable.add(startButton).colspan(2).expand().center();
      uiTable.add(refreshButton);
      uiTable.add(backButton).left();
	
	}

	@Override
    public void update(float deltaTime) {
    }

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
        	NinjaPie.setActiveScreen( new LevelScreen() );

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
        	NinjaPie.setActiveScreen( new MenuScreen() );

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
