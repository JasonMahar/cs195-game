package com.wickedgames.cs195;

import java.util.Collection;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.wickedgames.cs195.controller.GamesController;
import com.wickedgames.cs195.controller.PlayersController;
import com.wickedgames.cs195.model.CS195PlayerData;
import com.wickedgames.cs195.model.GameDesignVars;
import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.GameInstance.GameState;
import com.wickedgames.cs195.model.PlayerData;
import com.wickedgames.cs195.model.PlayerData.State;
import com.wickedgames.cs195.server.model.MapManager;



@SpringBootApplication
@RestController
public class ServerApplication {


// STUBS!
//	private static Integer STUB_gameID = 1;
//	private static Integer STUB_playerID = 1001;
//	private static Integer STUB_playerID2 = 1002;
//	private static Integer STUB_playerID3 = 1003;
	
	
	private static GamesController gamesController = new GamesController();
	private static PlayersController playersController = new PlayersController();
	
	public static void main(String[] args) {
		
		SpringApplication.run(ServerApplication.class, args);


		GamesController.createGame(GameDesignVars.GAME_LOBBY_ID);
		System.out.println("ServerApplication.main() creating game lobby. id = " + GameDesignVars.GAME_LOBBY_ID);
		
// STUB!
//STUB_createSampleGame();

	}

	

	/////////////////////////////////////////////
	//
	// Game calls

	
	/* createGame
	 * 
	 * creates and returns a GameInstance
	 * receives the Player's data, which is automatically added to the GameInstance
	 */
	@PostMapping("/game")
	public GameInstance createGame(@RequestBody CS195PlayerData newPlayer) {
		System.out.println("ServerApplication.createGame() called with newPlayer id: " + newPlayer.getPublicID());

		Integer ID = GamesController.createGame();
		GameInstance newGame = gamesController.getGame(ID);
		newGame.addPlayer(newPlayer);
		
		return newGame;
	}

	
	/* joinGame
	 * 
	 * add a Player's data in RequestBody to the GameInstance with gameID.
	 * 
	 * if successful return the current GameInstance
	 */
	@PostMapping("/game/{gameID}")
	public GameInstance joinGame(@PathVariable Integer gameID, @RequestBody CS195PlayerData newPlayer) {

		System.out.println("ServerApplication.joinGame() called.");

		if( newPlayer == null) {
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			return null;
		}
		System.out.println("\twith gameID: " + gameID + ", playerID: " + newPlayer.getPublicID());
		
		GameInstance game = gamesController.getGame(gameID);
		if( game == null) {
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			return null;
		}
		
		if( !game.addPlayer(newPlayer) ) {
			
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			

			// NOTE: this usually means that the player was already in the game.
			//		But because we probably won't implement leaveGame() for the 
			//		first iteration, we'll go ahead and return the game
//			return null;
		}
		newPlayer.setState(State.IN_LOBBY);
		
		return game;
	}

	
	/* leaveGame
	 * 
	 * remove a player with playerID from a GameInstance with gameID.
	 * if the GameInstance doesn't have any players remaining, destroy the GameInstance
	 * 
	 * if successful return all games (since leaving a game returns to the Multiplayer Menu that lists games)
	 */
	@DeleteMapping("/game/{gameID}/{playerID}")
	public Collection<GameInstance> leaveGame(@PathVariable Integer gameID, @PathVariable Integer playerID) {
		System.out.println("ServerApplication.leaveGame() called with gameID: " +
				gameID + ", playerID: " + playerID);
		
		GameInstance game = gamesController.getGame(gameID);
		
		game.removePlayer(playerID);
		if( game.isEmpty()) {
			gamesController.removeGame(gameID);
		}
		
		return gamesController.getAllGames();
	}


/*
	// if no gameID is supplied then return all games
	@GetMapping("/game")

		public Collection<GameInstance> game(@RequestParam(value = "id", defaultValue = "0") String id) {
			System.out.println("ServerApplication.game() called with id: " + id);

		Integer gameID = 0;
		try {
			gameID = Integer.valueOf(id);
		}
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		// TODO: probably should not allow clients to see list of games
		//		but it's handy for testing purposes.
		if( gameID == null || gameID == 0 ) {
			// TODO:
			// 	should normally return an error code for invalid param or data not found
			

			return gamesController.getAllGames();
		}
		
//
//GameInstance ps =  gamesController.getGame(gameID);
//System.out.println("ServerApplication.game() returning GameInstance: " + ps);
//return ps;

		Collection<GameInstance> c = new ArrayList<GameInstance>();
		GameInstance g = gamesController.getGame(gameID);
		c.add(g);
		return c;	
	}
*/
	
	
// TODO: this is really for debugging purposes and would be a security concern in a final product
	// return all games
	@GetMapping("/game")
	public Collection<GameInstance> getAllGames() {
		System.out.println("ServerApplication.getAllGames() called");

		return gamesController.getAllGames();
	}
	
	
	// 
	@GetMapping("/game/{id}")
	public GameInstance getGame(@PathVariable String id) {

		System.out.println("ServerApplication.getGame() called with id: " + id);

		Integer gameID = 0;
		try {
			gameID = Integer.valueOf(id);
		}
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		return gamesController.getGame(gameID);
	}


	// owner of the room clicks Start Game button or Game Ends, etc
	// 		(for this first iteration, everyone in the lobby has Start Game privileges, 
	//		since we're skipping the CreateGame step and there's only one GameID to JoinGame)
	//
	@PutMapping("/game/{id}")
	public GameInstance updateGameState(@PathVariable Integer gameID, @RequestBody GameState state) {
		System.out.println("ServerApplication.updateGameState() called with gameID: " +
				gameID + ", state: " + state);

		GameInstance game = gamesController.getGame(gameID);
		game.setGameState(state);
		

		// different game states will change Player's state as appropriate
		State playersState = null;
				
				
		switch(state) {
		
			case GAME_STARTING:
			case ENTERING_GAME:
	//NOTE:  for this first iteration, we're skipping the starting countdown 
	//		and loading phase and going directly to IN_GAME
				game.setGameState(GameState.IN_GAME);
				
				// fall through to GameState.ENTERING_GAME
			
			case IN_GAME:
				playersState =  State.RUNNING;
	
				// need to set all players spawn points
				MapManager.setPlayersStartingLocations( game.getAllPlayers() );
				break;
				
			case GAME_LOBBY:
				playersState = State.IN_LOBBY;
				break;
				
			case CLOSING:
				playersState = State.MAIN_MENU;
				gamesController.removeGame(gameID);
				break;
				
			default:
				System.out.println("\tERROR: Unknown state: " + state  + ". returning null");
				return null;
		}

		if( playersState != null) {

			for(PlayerData player : game.getAllPlayers()) {
				player.setState(playersState);
			}
		}
		
		return game;
	}
	
	/////////////////////////////////////////////
	//
	// Player calls
	

	/* createPlayer
	 * 
	 * creates and returns a PlayerData object
	 * with:
	 * 		PrivateID set for a player's own use with the server
	 * 		PublicID set for opponents to refer to the player
	 */
	@PostMapping("/player")
	public PlayerData createPlayer(@RequestParam(value = "name", defaultValue = "") String name) {
		System.out.println("ServerApplication.createPlayer() called");

		Integer ID = PlayersController.createPlayer();
		PlayerData newPlayer = PlayersController.getPlayer(ID);
		newPlayer.setName(name);
		
		return newPlayer;
	}

	/* 
	 * gets all players
	 * This really only exists for admin/debugging purposes
	 * normally the URI looks like "/player/{id}" in which getPlayer is called
	 */
	@GetMapping("/player")
	public Collection<PlayerData> player(@RequestParam(value = "id", defaultValue = "0") String id) {

		System.out.println("ServerApplication.player() called. id = " + id);
		
		Integer playerID = 0;
		try {
			playerID = Integer.valueOf(id);
		}
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		if( playerID == null || playerID == 0 ) {
			System.out.println("no playerID = " + playerID + ". getting all players.");

			// TODO:
			// 	should normally return an error code for invalid param or data not found
			
			// STUB!
//			playerID = STUB_playerID;

			return PlayersController.getAllPlayers();	
		}
		else {
			System.out.println("getPlayer playerID = " + playerID);

			Collection<PlayerData> playerList = new ArrayList<PlayerData>();
			PlayerData player = PlayersController.getPlayer(playerID);
			playerList.add(player);
			return playerList;	
			
		}

//PlayerData ps = PlayersController.getPlayer(playerID);
//
//System.out.println("ServerApplication.player() returning PlayerData: " + ps);
//		return ps;

	}

	
	/* getPlayer
	 * 
	 * gets player identified by id passed in 
	 */
	@GetMapping("/player/{id}")
	public PlayerData getPlayer(@PathVariable String id) {

		System.out.println("ServerApplication.getPlayer() called with id: " + id);

		Integer playerID = 0;
		try {
			playerID = Integer.valueOf(id);
		}
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }

		if( playerID == null || playerID == 0 ) {
			System.out.println("bad playerID = " + playerID + ". returning 1st player.");

			// TODO:
			// 	should normally return an error code for invalid param or data not found
			
//			// STUB!
//			playerID = STUB_playerID;
		}
		
		return PlayersController.getPlayer(playerID);	
	}
	
	

	/* updatePlayer
	 * 
	 * updates a player's data for the game with gameID.
	 * 
	 * if successful return GameInstance - i.e. latest updated data
	 */
	@PutMapping("/player/{gameID}")
	public GameInstance updatePlayer(@PathVariable Integer gameID, @RequestBody CS195PlayerData player) {
//		System.out.println("ServerApplication.updatePlayer() called with gameID: " +
//				gameID + ", playerID: " + player.getPublicID());
		
		GameInstance game = gamesController.getGame(gameID);
		game.updatePlayer(player);
		
		PlayersController.updatePlayer(player);
		
		if( game.isEmpty()) {
			gamesController.removeGame(gameID);
		}
		
		return game;
	}
	
	/*
	// STUB Methods for Testing!
	//
	
	private static void STUB_createSampleGame() {


		// STUBS!
		// TODO: Creating just a single game instance for early testing purposes 
		GamesController.createGame();
		//GameInstance game = gamesController.getGame(STUB_gameID);
		System.out.println("ServerApplication.STUB_createSampleGame() created demo game. id = " + GameDesignVars.THE_ONLY_GAME_ID);
		
////		STUB_playerID = PlayersController.createPlayer();
//		PlayerData player1 = new CS195PlayerData("Player1");
//		player1.setPublicID(STUB_playerID);
//		PlayersController.addPlayer(player1);
//		//System.out.println("ServerApplication.main() created PlayerData: " + player1);
//		
////		STUB_playerID2 = PlayersController.createPlayer();
//		PlayerData player2 = new CS195PlayerData("Player2");
//		player2.setPublicID(STUB_playerID2);
//		PlayersController.addPlayer(player2);
//		
//		gamesController.addPlayer(STUB_gameID, player1);
//		gamesController.addPlayer(STUB_gameID, player2);
	}
	
	
	// easy way to test server is running
	//
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
*/
}
