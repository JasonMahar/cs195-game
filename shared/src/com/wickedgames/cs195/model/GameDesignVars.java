package com.wickedgames.cs195.model;
/**
 * @author Jason Mahar
 *
 */


/* GameDesignVars
 * 
 * This class acts as a single source for finding Game Design elements 
 * that will likely need to be tweaked during or even after development.
 * 
 */
public final class GameDesignVars {

	// there are no instances of this class. all data is static
	private GameDesignVars() {}
	
	
	// Game constants:

// STUB:
	public static final boolean USE_STUB_IN_PLACE_OF_SERVER = false;
// STUB:
	public static final boolean ONLY_ONE_GAME = true;
	
	public static final Integer GAME_LOBBY_ID = 1;	
	public static final Integer BAD_GAME_ID = 0;	
	
	public static final boolean START_WITH_SOUND_ENABLED = false;	
	
	public static final Integer MAX_GAME_FPS = 30;	
	public static final Integer MAX_PLAYERS_PER_GAME = 4;	

	public static final float   LOBBY_SCREEN_TIME_BETWEEN_UPDATES = 3.0f; 	// seconds per update
	public static final float	LOBBY_SCREEN_UPDATE_RATE = 1.0f/LOBBY_SCREEN_TIME_BETWEEN_UPDATES; 	// updates per second
	
	public static final float	GAMEPLAY_MAX_SERVER_UPDATE_RATE = 5.0f; 			// updates per second
	public static final float   GAMEPLAY_TIME_BETWEEN_UPDATES = 1.0f/GAMEPLAY_MAX_SERVER_UPDATE_RATE; 	// seconds per update
	
	
	
	// Player constants:
	public static final int 	DEFAULT_PLAYER_ID = 0;	
	public static final String DEFAULT_PLAYER_NAME = "";
	
	public static final int 	STARTING_LIVES = 3;	
	public static final int 	MAX_LIVES = 3;	
	public static final float 	MAX_PLAYER_SPEED = 2.0f;		// in virtual meters/second

	
	// Projectile constants:
	public static final int 	STARTING_AMMO = 1;
	public static final int 	MAX_AMMO = 1;
	public static final float 	DAMAGE_FROM_HIT = 1.0f;
	public static final float 	MAX_DISTANCE = 30.0f;

	
	
}
