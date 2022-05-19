package com.wickedgames.cs195.transport;

import java.util.Collection;
import java.util.HashMap;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;

public interface GameSessionInterface {
	
	Collection<GameInstance> getAllGames();

	GameInstance getGameData(Integer gameID);

	GameInstance createNewGame(PlayerData userPlayer);
	
	GameInstance startGame(Integer gameID);
	

	// creates a basic PlayerData object 
	// 		with only the playerName, so that it can be passed into 
	//		createNewGame() or joinGame()
	PlayerData createNewPlayer(String playerName);
	
	GameInstance joinGame(Integer gameId, PlayerData userPlayer);

	GameInstance updatePlayerData(Integer gameID, PlayerData userPlayer);
	
	boolean quitGame(PlayerData userPlayer);

	HashMap<Integer, PlayerData> getAllPlayersData();

	PlayerData getPlayerData(int ID);

}