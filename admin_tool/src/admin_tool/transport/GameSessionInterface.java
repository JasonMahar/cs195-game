package admin_tool.transport;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;

public interface GameSessionInterface {

	GameInstance createNewGame(PlayerData userPlayer);

	GameInstance getGameData();

	boolean joinGame(PlayerData userPlayer);

	boolean quitGame();

	//TODO: implement!
	boolean updatePlayerData(PlayerData userPlayer);

	boolean getPlayerData(int ID);

}