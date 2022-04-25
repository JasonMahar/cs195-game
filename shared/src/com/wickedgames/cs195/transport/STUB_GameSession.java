package com.wickedgames.cs195.transport;

import java.util.ArrayList;
import java.util.Collection;

import com.wickedgames.cs195.model.CS195PlayerData;
import com.wickedgames.cs195.model.GameDesignVars;
import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;
import com.wickedgames.cs195.model.Projectile;
import com.wickedgames.cs195.model.PlayerData.Facing;
import com.wickedgames.cs195.model.PlayerData.State;

public class STUB_GameSession implements GameSessionInterface {

	public static final int DEFAULT_GAME_ID = 1;	
	public static final int STUB_DEFAULT_PLAYER_ID = 1;
	private static final float INITIAL_MOVEMENT = 2.0f;
	private static final float INITIAL_FACING = 40.0f;
	
	
	// This is a singleton to take the place of the server preserving the
	//		GameInstance even though a GameSession is supposed to be stateless
	private static GameInstance game = null;
	
	
	// this should be all 4 locations - the player and 3 opponents
	private float[][] startingLocations = { 
			{64.0f, 896.0f}, 
			{536.0f, 64.0f}, 
			{64.0f, 64.0f}, 
			{536.0f, 896.0f} 
		};
	
	
	public STUB_GameSession() {}


	
	public void STUB_add3DummyPlayers() { 
		
		// I'm going to assume startingLocations[0] is for the player, 
		// so looping through playerIDs 2 to 4 and giving them startingLocations 1 to 3
		for(int playerID = 2; playerID<5; ++playerID) {

			// replacing initial speed of PlayerData.STOPPED with SLOW_MOVEMENT
			// replacing initial PlayerData.Facing.LEFT with 30.0
			PlayerData.Facing direction = PlayerData.Facing.LEFT;
			direction.setDirection(INITIAL_FACING);
			PlayerData dummyPlayer = new CS195PlayerData( playerID, playerID, 
					"Player " + playerID, PlayerData.State.RUNNING, 
					startingLocations[playerID-1][0], startingLocations[playerID-1][1], 
					/*PlayerData.Facing.LEFT*/ direction, /*PlayerData.STOPPED */ INITIAL_MOVEMENT * playerID, 
					null /*Projectile[] projectiles*/ );
			game.addPlayer( dummyPlayer );
		}
	}
	
	@Override
	public PlayerData createNewPlayer(String playerName) {

		if( playerName == null || playerName.isBlank() ) {
			return null;
		}
		// TODO: should give more details to the player
		return new CS195PlayerData(playerName);
	}

	@Override
	public GameInstance joinGame(Integer gameId, PlayerData userPlayer) {

		game.addPlayer(userPlayer);
		return game;
	}

	@Override
	public GameInstance updatePlayerData(PlayerData userPlayer) {
		// TODO Auto-generated method stub
		return game;
	}

	@Override
	public boolean quitGame(PlayerData userPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<GameInstance> getAllGames() {
		
		Collection<GameInstance>list =  new ArrayList<GameInstance>();
		list.add(game);
		return list;
	}

	@Override
	public GameInstance getGameData(Integer gameID) {

		return game;
	}

	@Override
	public GameInstance createNewGame(PlayerData userPlayer) {

		// only construct the singleton once
		if( game == null) {
			 game = new GameInstance(GameInstance.GameState.GAME_LOBBY, 
					 DEFAULT_GAME_ID, null);
		}
		
		// update player with data that would normally come from  server
		userPlayer.setPublicID(STUB_DEFAULT_PLAYER_ID);
		userPlayer.setPrivateID(STUB_DEFAULT_PLAYER_ID);
		userPlayer.setX(startingLocations[0][0]);
		userPlayer.setY(startingLocations[0][1]);
		game.addPlayer(userPlayer);

		STUB_add3DummyPlayers();
		 
		return game;
	}

	@Override
	public Collection<PlayerData> getAllPlayersData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerData getPlayerData(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

}
