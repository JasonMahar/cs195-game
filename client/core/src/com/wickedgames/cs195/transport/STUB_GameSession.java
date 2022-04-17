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

	private GameInstance game;
	
	
	public STUB_GameSession() {

		 game = new GameInstance(GameInstance.GameState.GAME_LOBBY, 
				 GameDesignVars.DEFAULT_GAME_ID, null);
		 STUB_add3DummyPlayers();
	}

	
	public void STUB_add3DummyPlayers() { 

		// this should be all 4 locations - the player and 3 opponents
		float[][] startingLocations = { 
				{64.0f, 896.0f}, 
				{536.0f, 64.0f}, 
				{64.0f, 64.0f}, 
				{536.0f, 896.0f} 
			};
		
		// I'm going to assume startingLocations[0] is for the player, 
		// so looping through playerIDs 2 to 4 and giving them startingLocations 1 to 3
		for(int playerID = 2; playerID<5; ++playerID) {

			PlayerData dummyPlayer = new CS195PlayerData( playerID, playerID, 
					"Player " + playerID, PlayerData.State.RUNNING, 
					startingLocations[playerID-1][0], startingLocations[playerID-1][1], 
					PlayerData.Facing.LEFT, PlayerData.STOPPED, 
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
		// TODO Auto-generated method stub
		return game;
	}

	@Override
	public GameInstance createNewGame(PlayerData userPlayer) {
		// TODO Auto-generated method stub
		return null;
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
