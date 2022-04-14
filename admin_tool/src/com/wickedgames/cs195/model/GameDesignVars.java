package com.wickedgames.cs195.model;


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
	public static final Integer MAX_GAME_FPS = 30;	
	public static final Integer MAX_PLAYERS_PER_GAME = 4;	
	
	public static final Integer MAX_SIMULTANEOUS_GAMES = 1;	
	public static final Integer DEFAULT_GAME_ID = 1;	

	public static final Integer	MAX_UPDATE_RATE = 5; 			// updates per second
	public static final Float   TIME_BETWEEN_UPDATES = 1.0f/MAX_UPDATE_RATE; 	
	
	
	// Player constants:
	public static final int 	STARTING_LIVES = 3;	
	public static final int 	MAX_LIVES = 3;	
	public static final Float 	MAX_PLAYER_SPEED = 2.0f;		// in virtual meters/second

	
	// Projectile constants:
	public static final int 	STARTING_AMMO = 1;
	public static final int 	MAX_AMMO = 1;
	public static final Float 	DAMAGE_FROM_HIT = 1.0f;
	
	
}
