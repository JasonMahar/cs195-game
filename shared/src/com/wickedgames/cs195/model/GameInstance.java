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
	 *  GAME_LOBBY --> GAME_STARTING --> ENTERING_GAME --> IN_GAME --> CLOSING
	 *       ^                                                |
	 *       +-----------<-------------<-------------<--------+
	 */
	public enum GameState { GAME_LOBBY, GAME_STARTING, ENTERING_GAME, IN_GAME, CLOSING }; 
	
	// current Game State
	private GameState gameState;
	
	private Integer ID;
	private HashMap<Integer,PlayerData> players = new HashMap<Integer,PlayerData>();
	

	public GameInstance(GameState gameState, Integer ID, HashMap<Integer, PlayerData> players) {
		super();
		
		this.gameState = gameState;
		this.ID = ID;
		if( players == null ) {
			players = new HashMap<Integer, PlayerData>();
		}
		this.players = players;
	}

	
	// Get/Add/Update/Remove Players:
	
	public boolean addPlayer(PlayerData player) {
		
		if( player == null || players.containsKey(player.getPublicID()) ) 
			return false;
		
		return players.put(player.getPublicID(), player) == null;
	}
	
	public boolean updatePlayer(PlayerData player) {

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
		setGameState( GameState.GAME_LOBBY );
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
	

	public Collection<PlayerData> getAllPlayers() {
		return players.values();
	}

	public PlayerData getPlayer(Integer playerID) {
		return players.get(playerID);
	}



	@Override
	public String toString() {
		return "{ "
				+ "gameState=" + gameState + ","
				+ getAllPlayers()
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
