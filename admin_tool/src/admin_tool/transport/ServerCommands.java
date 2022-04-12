/**
 * 
 */
package admin_tool.transport;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;

/**
 * @author Jason
 *
 */
public enum ServerCommands {


	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			return session.getGameData();
		}
	},

	CREATE_GAME{

		@Override
		public String toString() {	return "Create Game";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			if(player == null && params != null && !params.isBlank()) {
				player = session.createNewPlayer();
			}
			return session.createNewGame(player);
		}
	},

	LIST_GAMES{

		@Override
		public String toString() {	return "List Games";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

// TODO: 
			return session.getGameData();
		}
	},

	GET_GAME_INFO{

		@Override
		public String toString() {	return "Get Game Info";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			return session.getGameData();
		}
	},

/*
    private static final String SERVER_COMMAND_STRINGS[] = { 
    		"List Games", 
    		"Get Game Info", 
    		"Leave Game",
    		"List All Players", 
    		"Get Player Info", 
    		"Update Player"};
    
	
	GameInstance getGameData();

	boolean joinGame(PlayerSprite userPlayer);

	boolean quitGame();

	//TODO: implement!
	boolean updatePlayerData(PlayerSprite userPlayer);

	boolean getPlayerData(int ID);
	*/
	
	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			return session.getGameData();
		}
	},

	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			return session.getGameData();
		}
	},

	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID) {

			return session.getGameData();
		}
	};

	
	public String toString();
	public GameInstance send(GameSession session, String params, PlayerSprite player, Integer gameID);

}
