package com.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.*;
import com.game.entities.*;
import com.game.entities.ui.*;
import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.transport.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;



public class LevelScreen extends BaseScreen {
    private Ninja ninja;
    private Hashtable<Integer,Opponent> opponents;
    private boolean win;
//    ArrayList<Pie> pies;
    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;

    private GameSessionInterface serverSession;
	private GameInstance gameData;
	private PlayerData	playerData = null;
	
	// when timeSinceLastServerUpdate > GameDesignVars.GAMEPLAY_TIME_BETWEEN_UPDATES, 
	//		get update from server
	private float timeSinceLastServerUpdate = GameDesignVars.GAMEPLAY_TIME_BETWEEN_UPDATES + 1.0f;	
	
    
    public LevelScreen() {
    	super();
	}

	public void initializeDataFromServer() {

		opponents = new Hashtable<Integer, Opponent>();
		
// STUB:       	//        	serverSession = new GameSession();
		serverSession = new STUB_GameSession();
		gameData = serverSession.getGameData(STUB_GameSession.DEFAULT_GAME_ID);
				
	    // Create Ninja's and Opponents' data 
	    for(PlayerData player: gameData.getAllPlayers()){
	    	
	    	// Create Ninja's data 
	    	if( player.getPublicID() == Ninja.getPlayerID() ) {	
	    		playerData = player;	
	    		continue;
	    	}

		    // Create an Opponent's data 
	    	opponents.put( player.getPublicID(), new Opponent(0, 0, mainStage, player) );
	    }
	    
		sendAndReceiveServerUpdate();
	    
	}
	
	public void initialize() {

		initializeDataFromServer();
		
        TilemapActor tilemapActor = new TilemapActor("map.tmx", mainStage);
//        pies = new ArrayList<Pie>();

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
        muteButton.setChecked( GameDesignVars.START_WITH_SOUND_ENABLED );

        muteButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!LevelScreen.this.isTouchDownEvent(e)) return false;

                audioVolume = 1 - audioVolume;
                instrumental.setVolume(audioVolume);
                oceanSurf.setVolume(audioVolume);
//                muteButton.setChecked( !muteButton.isChecked() );
                
                return true;
            }
        });

        uiTable.pad(10);

        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
        uiTable.add(muteButton).top();
        uiTable.row();



        waterDrop = Gdx.audio.newSound(Gdx.files.internal("Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("Ocean_Waves.ogg"));

        audioVolume = GameDesignVars.START_WITH_SOUND_ENABLED ? 1.00f : 0.0f;
        instrumental.setVolume(audioVolume);
        instrumental.setLooping(true);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();
    }

	
    public void update(float deltaTime) {

    	// update player before sending its data to the server. 
    	// there probably should be this call:
    	// player.update();
    	// but player.act(); is doing double duty and updating before this method is called.
        

        // TODO: pie.update() should actually be done inside player.update() or at least player.act();
    	//		but currently Ninja does not have a pies attribute.
    	//		So potentially every player's and opponent's pies are owned by the LevelScreen.
    	//			Note if this remains true, then the pie data needs to be sent and received by server
    	//			somewhere other than PlayerData. Maybe GameData?
        /*
         *		// Player's pies
                for(Pie pie: pies){

        			pie.update(deltaTime);
                }
         */
    	
    	timeSinceLastServerUpdate += deltaTime;
    	
// TODO: server updates almost certainly need to be moved into their own asynchronous thread
//	 this is just quick & dirty try 
    	if( timeSinceLastServerUpdate > GameDesignVars.GAMEPLAY_TIME_BETWEEN_UPDATES) {
    		
    		sendAndReceiveServerUpdate();
    		timeSinceLastServerUpdate = 0.0f;
    	}
    	
    	
        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.game.entities.Rock"))
            ninja.preventOverlap(rockActor);

        /*
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
        */
            
                    
        // Update Opponents' data, positions & actions
        for(PlayerData opponent: gameData.getAllPlayers()){
        	
        	if( opponent == playerData ) {	continue;	}
//                    	
//                    	opponent.update(deltaTime);

// TODO: this should actually be inside the PlayerData's update()        
        	
//            	     // Opponents' pies
//            	        for(Projectile pie: opponent.getAllProjectiles()){
//	
//            				pie.update(deltaTime);
//            	        }
        }
        
    
    
    } // END update()

	// updating the server with the players recent data has the side effect 
	//		of returning the current state of the game, including opponents' data
	private void sendAndReceiveServerUpdate() {
		// TODO Auto-generated method stub

		gameData = serverSession.updatePlayerData(playerData);
		

	    // Update Opponents' data, positions & actions
		// 		This may be unnecessary and just automatically update itself since 
		//		we're reusing the same PlayerData instances
		// 	
	    for(PlayerData player: gameData.getAllPlayers()){
	    	
	    	if( player.getPublicID() == Ninja.getPlayerID() ) {	continue;	}

		    // Find and update Opponent's data 
	    	Opponent opponent = opponents.get(player.getPublicID());
	    	opponent.setPlayerData( player );
	    }
		
	} 

    public boolean keyDown(int keyCode) {

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();

        return false;
    }


}
