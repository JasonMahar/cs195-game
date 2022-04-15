package admin_tool.transport;

import java.util.Collection;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;

public interface GameSessionInterface {

	// This is a convenience method for creating a basic PlayerData object 
	// 		with only the playerName, so that it can be passed into 
	//		createNewGame() or joinGame()
	PlayerData createNewPlayer(String playerName);
	

	// These next 3 methods are the main commands used for the CS195 Game Prototype
	
	GameInstance joinGame(PlayerData userPlayer);

	GameInstance updatePlayerData(PlayerData userPlayer);
	
	boolean quitGame(PlayerData userPlayer);


	
	
	Collection<GameInstance> getAllGames();

	GameInstance getGameData(Integer gameID);
	
	GameInstance createNewGame(PlayerData userPlayer);
	
	Collection<PlayerData> getAllPlayersData();

	PlayerData getPlayerData(int ID);
}