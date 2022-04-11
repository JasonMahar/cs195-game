package com.wickedgames.cs195.model;

import java.util.Collection;
import java.util.HashMap;

/*  Class GameInstance
 * 
 *  
 * 
 *  @author Jason Mahar
 *  @created 3/21/2022
 *  
 */
public class GameInstance {

	/*
	 *  State diagram of the game generally goes in order:
	 *  PLAYER_JOIN --> GAME_STARTING --> ENTERING_GAME --> IN_GAME --> CLOSING
	 *       ^                                                |
	 *       +-----------<-------------<-------------<--------+
	 */
	private enum GameState { PLAYER_JOIN, GAME_STARTING, ENTERING_GAME, IN_GAME, CLOSING }; 
	
	// current Game State
	private GameState gameState;
	
	private Integer ID;
	private HashMap<Integer,PlayerSprite> players = new HashMap<Integer,PlayerSprite>();
	
	/**
	 * 
	 */
	public GameInstance( Integer ID ) {
		System.out.println("GameInstance() constructor. ID = " + ID);
		
		this.ID = ID;
		setGameState( GameState.PLAYER_JOIN );
	}


	
	// Add/Update/Remove Players:
	
	public boolean addPlayer(PlayerSprite player) {
		
		if( player == null || players.containsKey(player.getPublicID()) ) 
			return false;
		
		return players.put(player.getPublicID(), player) == null;
	}
	
	public boolean updatePlayer(PlayerSprite player) {

		if( player == null || ! players.containsKey(player.getPublicID()) ) 
			return false;
		
		return players.replace(player.getPublicID(), player) != null;
	}
	
	public boolean removePlayer(Integer playerPublicID) {

		if( ! players.containsKey(playerPublicID) ) 
			return false;
		
		return players.remove(playerPublicID) != null;
	}


	public boolean isEmpty() {
		return players.isEmpty();
	}
	
	
	
	// Game State changes:
	
	public boolean join() {
		System.out.println("\nGameInstance.join() called ");
		setGameState( GameState.PLAYER_JOIN );
		return true;
	}

	public boolean start() {
		System.out.println("\nGameInstance.start() called ");
		setGameState( GameState.GAME_STARTING );
		return true;
	}
	public boolean enter() {
		System.out.println("\nGameInstance.entering() called ");
		setGameState( GameState.ENTERING_GAME );
		return true;
	}
	
	public boolean run() {
		System.out.println("\nGameInstance.run() called ");
		setGameState( GameState.IN_GAME );
		return true;
	}

	public boolean close() {
		System.out.println("\nGameInstance.close() called ");
		setGameState( GameState.CLOSING );
		return true;
	}

	
	
	//////////////////////////////////////////
	//
	// Private Methods:
	//
	

	//////////////////////////////////////////
	//
	// getters & setters:
	//

	public Integer getID() {
		return ID;
	}

	protected void setID(Integer iD) {
		ID = iD;
	}

	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState( GameState newState ) {
		System.out.println("GameInstance.setGameState() newState = " + newState);
		gameState = newState;
	}
	

	public Collection<PlayerSprite> getPlayers() {
		return players.values();
	}
	



	@Override
	public String toString() {
		return "{ "
				+ "gameState=" + gameState + ","
				+ getPlayers()
				+ " }";
	}
	
	

	//////////////////////////////////////////////////////////////////////////
	// static main()
	//
	
	/**
	 *  Create and run the game
	 */
	public static void main(String[] args) {
		System.out.println("Running GameInstance.main()");
		System.out.println("End GameInstance.main()");
	}


}
