package com.wickedgames.cs195.transport;

import java.util.Collection;

/**
 * @author Jason
 *
 */

import java.util.HashMap;
import java.util.Iterator;

import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.model.GameInstance.GameState;

/*
 * 
 * This enum replaces the following strings as well as 
 * having a send() method for each value to make the appropriate call to the server
 * 
 * If send() returns null, you can call ServerCommands.getLastResultMessage()
 * If the server returns any data, you can call ServerCommands.getLastJSONReceived
 * 		to see the raw json data returned.
 * 
private static final String SERVER_COMMAND_STRINGS[] = { 
		"Create Game", 
		"List Games", 
		"Get Game Info", 
		"Create Player",
		"Join Game",
		"Leave Game",
		"List All Players", 
		"Get Player Info", 
		"Update Player"};
		
*/
public enum ServerCommands {


/*
	TEMPLATE{

		@Override
		public String toString() {	return "TEMPLATE";	}

		@Override
		public GameInstance send(GameSession session, String params, PlayerData player, Integer gameID) {

			return ServerCommands.getGameSession().getGameData();
		}
	},
	
	
	
    private static final String SERVER_COMMAND_STRINGS[] = { 
    		"Create Game", 
    		"List Games", 
    		"Get Game Info", 
    		"Create Player",
    		"Join Game",
    		"Leave Game",
    		"List All Players", 
    		"Get Player Info", 
    		"Update Player"};
    		
*/
	

	CREATE_PLAYER{

		@Override
		public String toString() {	return "Create Player";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			if( params == null || params.isEmpty() ) {
				resultMessage = "ERROR: CREATE_PLAYER.send() Must enter player name.";
				return null;
			}
			player = ServerCommands.getGameSession().createNewPlayer(params);
			
			if( player == null ) {
				resultMessage = "ERROR: CREATE_PLAYER.send() Could not create new player.";
				return null;
			}
			
			HashMap<Integer, PlayerData> players = new HashMap<>();
			players.put(player.getPublicID(), player);
			// since send() always returns a GameInstance, 
			//		GET_PLAYER_DATA returns the PlayerData inside a dummy GameInstance
			GameInstance dummyGame = new GameInstance(GameState.UNKNOWN, GameDesignVars.BAD_GAME_ID, players);
			
			resultMessage = SUCCESS_MESSAGE;
			
			return dummyGame;
			
		}
	},

	
	JOIN_GAME{

		@Override
		public String toString() {	return "Join Game";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			if( gameID == GameDesignVars.BAD_GAME_ID ) {
				System.out.println("\tERROR: JOIN_GAME send() Bad gameID: " + gameID);
				resultMessage = "ERROR: JOIN_GAME send() Bad gameID: " + gameID;
				return null;
			}
			
			if( player == null ) {
				System.out.println("\tERROR: JOIN_GAME send() PlayerData is null.");

				resultMessage = "ERROR: JOIN_GAME.send() PlayerData is null.";
				return null;
			}
			
			GameInstance game = ServerCommands.getGameSession().joinGame(gameID, player);
			
			if( game == null ) {
				System.out.println("\tERROR: JOIN_GAME send()  could not create GameInstance.");
				resultMessage = "ERROR: JOIN_GAME send() could not create GameInstance";
			}
			else {
				resultMessage = SUCCESS_MESSAGE;
			}
			
			return game;
		}
	},

	
	START_GAME{

		@Override
		public String toString() {	return "Start Game";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			GameInstance game = ServerCommands.getGameSession().startGame(gameID);
			if( game == null) {
				resultMessage = "ERROR: Unable to start game.";
			}
			else {
				resultMessage = SUCCESS_MESSAGE;
			}
			
			return game;
		}
	},

	
	// returns only a PlayerData. doesn't fit the normal model of 
	// returning a game. So instead we return a dummy GameInstance 
	// with just the PlayerData set
	//
	GET_PLAYER_DATA{

		@Override
		public String toString() {	return "Get Player Info";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			player = ServerCommands.getGameSession().getPlayerData(player.getPublicID());	// this won't change the original
			if( player == null ) {
				return null;
			}
			
			HashMap<Integer, PlayerData> players = new HashMap<>();
			players.put(player.getPublicID(), player);
			// since send() always returns a GameInstance, 
			//		GET_PLAYER_DATA returns the PlayerData inside a dummy GameInstance
			GameInstance dummyGame = new GameInstance(GameState.UNKNOWN, GameDesignVars.BAD_GAME_ID, players);

			resultMessage = SUCCESS_MESSAGE;
			return dummyGame;
		}
	},

	
	// returns only a PlayerData. doesn't fit the normal model of 
	// returning a game. So instead we return a dummy GameInstance 
	// with just the HashMap of PlayerData set
	//
	GET_ALL_PLAYERS_DATA{

		@Override
		public String toString() {	return "Get All Players";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			HashMap<Integer, PlayerData> players = ServerCommands.getGameSession().getAllPlayersData();

			if( players == null ) {
				resultMessage = "ERROR: GET_ALL_PLAYERS_DATA.send() PlayerData player is null.";
				return null;
			}
			
			// since send() always returns a GameInstance, 
			//		GET_ALL_PLAYERS_DATA returns the players HashMap inside a dummy GameInstance
			GameInstance dummyGame = new GameInstance(GameState.UNKNOWN, GameDesignVars.BAD_GAME_ID, players);

			resultMessage = SUCCESS_MESSAGE;
			return dummyGame;
		}
	},
	
	
	// returns only a PlayerData. doesn't fit the normal model of 
	// returning a game. So instead we return a dummy GameInstance 
	// with just the PlayerData set
	//
	UPDATE_PLAYER_DATA{

		@Override
		public String toString() {	return "Update Player";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			resultMessage = SUCCESS_MESSAGE;
			return ServerCommands.getGameSession().updatePlayerData(gameID, player);
		}
	},
	
	
	/*
	 *  to create a game, it  needs the PlayerData of who's creating it passed in
	 *  (games with no players in them will get removed by the server)
	 */
	CREATE_GAME{

		@Override
		public String toString() {	return "Create Game";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			if(player == null && params != null && !params.isBlank()) {
				player = ServerCommands.getGameSession().createNewPlayer(params);
			}
			
			GameInstance game = ServerCommands.getGameSession().createNewGame(player);
			
			if( game == null ) {
				resultMessage = "ERROR: CREATE_GAME.send() could not create GameInstance";
			}
			else {
				resultMessage = SUCCESS_MESSAGE;
			}
			
			return game;
		}
	},

	
	GET_GAME_INFO{

		@Override
		public String toString() {	return "Get Game Info";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			GameInstance game = ServerCommands.getGameSession().getGameData(gameID);
			
			if( game == null ) {
				resultMessage = "ERROR: GET_GAME_INFO.send() could not create GameInstance";
			}
			else {
				resultMessage = SUCCESS_MESSAGE;
			}
			
			return game;
		}
	},

	// returns Collection<GameInstance>, so doesn't fit the model for here
	// HACK: just returning first GameInstance
	LIST_GAMES{

		@Override
		public String toString() {	return "List Games";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			Collection<GameInstance> games = ServerCommands.getGameSession().getAllGames();
			if( games == null ) {
				resultMessage = "LIST_GAMES.send() no GameInstances returned.";
				return null;
			}
			
			
//			GameInstance[] gamesArray = (GameInstance[])games.toArray();
//			
//			if( gamesArray.length == 0 )  {
//				resultMessage = "LIST_GAMES.send() no GameInstances returned";
//				return null;
//			}
//			return gamesArray[0];

// HACK: only returning first GameInstance
			Iterator<GameInstance> it = games.iterator();
			GameInstance game = it.next();
			return game;
		}
	},

	LEAVE_GAME{

		@Override
		public String toString() {	return "Leave Game";	}

		@Override
		public GameInstance send(String params, PlayerData player, Integer gameID) {

			ServerCommands.getGameSession().quitGame(player);
			resultMessage = SUCCESS_MESSAGE;
			return null;
		}
	};
	
	////////////////////////////////////////
	// methods for each enum value:
	
	public abstract String toString();
	public abstract GameInstance send(String params, PlayerData player, Integer gameID);

	

	/////////////////////////////////////////////////////
	// PUBLIC static (class) constants and methods:
	//
	
	public static final String SUCCESS_MESSAGE = "SUCCESS";

	/**
	 * @return the ServerCommands enum value
	 */
	public static ServerCommands getServerCommandsFromString(String commandString) {
		
		if( commandString == null || commandString.isEmpty() ) {
			resultMessage = "ERROR: ServerCommands.getServerCommandsFromString() Must enter player a command string.";
			return null;
		}
		for( ServerCommands command : ServerCommands.values()) {

			if(  command.toString().equals(commandString) ) {

				resultMessage = SUCCESS_MESSAGE;
				return command;
			}
		}
		

		resultMessage = "ERROR: ServerCommands.getServerCommandsFromString() No ServerCommands found for commandString:" + commandString;
		return null;
	}

	/**
	 * @return the lastJSONReceived
	 */
	public static String getLastJSONReceived() {
		return GameSession.getLastJSONReceived();
	}
	

	/**
	 * @return the resultMessage
	 */
	public static String getLastResultMessage() {
		return resultMessage;
	}
	

	public static void clearLastResults() {

		resultMessage = "";
		GameSession.setLastJSONReceived("");
	}


	/////////////////////////////////////////////////////
	// PRIVATE static (class) constants and methods:
	//
	
	// create a Singleton GameSessionInterface
	private static GameSessionInterface gameSession = null;
	
	// create a Singleton GameSessionInterface
	private static String resultMessage = "";
	
	/**
	 * @return the GameSessionInterface Singleton
	 */
	private static GameSessionInterface getGameSession() {
		if( gameSession == null) {
			gameSession = GameSession.getGameSession();
		}
		return gameSession;
	}
	 
}
