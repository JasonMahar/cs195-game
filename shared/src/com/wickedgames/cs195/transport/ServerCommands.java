/**
 * 
 */
package com.wickedgames.cs195.transport;


import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerData;

/**
 * @author Jason
 *
 */
public enum ServerCommands {

/*
	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerData player, Integer gameID) {

			return session.getGameData();
		}
	},
*/
	CREATE_GAME{

		@Override
		public String toString() {	return "Create Game";	}

		@Override
		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {

			if(player == null && params != null && !params.isBlank()) {
				player = session.createNewPlayer(params);
			}
			return session.createNewGame(player);
		}
	},

	// returns Collection<GameInstance>, so doesn't fit the model for here
//	LIST_GAMES{
//
//		@Override
//		public String toString() {	return "List Games";	}
//
//		@Override
//		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {
//
//			return session.getAllGames();
//		}
//	},

	GET_GAME_INFO{

		@Override
		public String toString() {	return "Get Game Info";	}

		@Override
		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {

			return session.getGameData(gameID);
		}
	},

	JOIN_GAME{

		@Override
		public String toString() {	return "Join Game";	}

		@Override
		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {

			return session.joinGame(gameID, player);
		}
	},

	LEAVE_GAME{

		@Override
		public String toString() {	return "Leave Game";	}

		@Override
		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {

			session.quitGame(player);
			return null;
		}
	},


	// returns a Collection of PlayerData, so doesn't fit the model for here
//	GET_ALL_PLAYERS_DATA{
//
//		@Override
//		public String toString() {	return "List All Players";	}
//
//		@Override
//		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {
//
//			return session.getAllPlayersData();
//		}
//	},
	
	
	// returns only a PlayerData, so doesn't fit the model for here
	
//	GET_PLAYER_DATA{
//
//		@Override
//		public String toString() {	return "Get Player Info";	}
//
//		@Override
//		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {
//
//			session.getPlayerData(player.getPublicID());
//			
//			return session.getPlayerData(player.getPublicID());
//		}
//	},

	UPDATE_PLAYER_DATA{

		@Override
		public String toString() {	return "Update Player";	}

		@Override
		public GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID) {

			return session.updatePlayerData(gameID, player);
		}
	};

	
	public abstract String toString();
	public abstract GameInstance send(GameSessionInterface session, String params, PlayerData player, Integer gameID);

}
