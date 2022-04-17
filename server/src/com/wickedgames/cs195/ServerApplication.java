package com.wickedgames.cs195;

import java.util.Collection;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.wickedgames.cs195.controller.GamesController;
import com.wickedgames.cs195.controller.PlayersController;
import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;

//import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@RestController
public class ServerApplication {


// STUBS!
	private static Integer STUB_gameID = 1;
	private static Integer STUB_playerID = 1001;
	private static Integer STUB_playerID2 = 1002;
	private static Integer STUB_playerID3 = 1003;
	
	
	private static GamesController gamesController = new GamesController();
	private static PlayersController playersController = new PlayersController();
	
	public static void main(String[] args) {
		
		SpringApplication.run(ServerApplication.class, args);

// STUB!
STUB_createSampleGame();

	}

	

	/////////////////////////////////////////////
	//
	// Game calls
	
	
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

	
	/* createGame
	 * 
	 * creates and returns a GameInstance
	 * receives the Player's data, which is automatically added to the GameInstance
	 */
	@PostMapping("/game")
	public GameInstance createGame(@RequestBody PlayerSprite newPlayer) {
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
	public GameInstance joinGame(@PathVariable Integer gameID, @RequestBody PlayerSprite newPlayer) {
		

		if( newPlayer == null) {
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			return null;
		}
		System.out.println("ServerApplication.leaveGame() called with gameID: " +
				gameID + ", playerID: " + newPlayer.getPublicID());
		
		GameInstance game = gamesController.getGame(gameID);
		if( game.isEmpty()) {
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			return null;
		}
		
		if( !game.addPlayer(newPlayer) ) {
			// TODO: Create an Error response 
			//		see examples: https://www.amitph.com/spring-rest-api-custom-error-messages/
			return null;
		}
		
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


	
	/////////////////////////////////////////////
	//
	// Player calls
	
	
	/* 
	 * gets all players
	 * This really only exists for admin/debugging purposes
	 * normally the URI looks like "/player/{id}" in which getPlayer is called
	 */
	@GetMapping("/player")
	public Collection<PlayerSprite> player(@RequestParam(value = "id", defaultValue = "0") String id) {

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
		}
		else {
			System.out.println("getPlayer playerID = " + playerID);

			Collection<PlayerSprite> playerList = new ArrayList<PlayerSprite>();
			PlayerSprite player = playersController.getPlayer(playerID);
			playerList.add(player);
			return playerList;	
			
		}

//PlayerSprite ps = playersController.getPlayer(playerID);
//
//System.out.println("ServerApplication.player() returning PlayerSprite: " + ps);
//		return ps;

		return playersController.getAllPlayers();	
	}

	
	/* getPlayer
	 * 
	 * gets player identified by id passed in 
	 */
	@GetMapping("/player/{id}")
	public PlayerSprite getPlayer(@PathVariable String id) {

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
			
			// STUB!
			playerID = STUB_playerID;
		}
		
		return playersController.getPlayer(playerID);	
	}
	
	

	/* updatePlayer
	 * 
	 * updates a player's data for the game with gameID.
	 * 
	 * if successful return all games (since leaving a game returns to the Multiplayer Menu that lists games)
	 */
	@PutMapping("/player/{gameID}")
	public GameInstance updatePlayer(@PathVariable Integer gameID, @RequestBody PlayerSprite player) {
		System.out.println("ServerApplication.leaveGame() called with gameID: " +
				gameID + ", playerID: " + player.getPublicID());
		
		GameInstance game = gamesController.getGame(gameID);
		game.updatePlayer(player);
		
		playersController.updatePlayer(player);
		
		if( game.isEmpty()) {
			gamesController.removeGame(gameID);
		}
		
		return game;
	}
	

	// STUB Methods for Testing!
	//
	
	private static void STUB_createSampleGame() {


		// STUBS!
		// TODO: Creating just a single game instance for early testing purposes 
		STUB_gameID = GamesController.createGame();
		//GameInstance game = gamesController.getGame(STUB_gameID);
		System.out.println("ServerApplication.STUB_createSampleGame() created demo game. id = " + STUB_gameID);
		
		/*
//		STUB_playerID = playersController.createPlayer();
		PlayerSprite player1 = new PlayerSprite("Player1");
		player1.setPublicID(STUB_playerID);
		playersController.addPlayer(player1);
		//System.out.println("ServerApplication.main() created PlayerSprite: " + player1);
		
//		STUB_playerID2 = playersController.createPlayer();
		PlayerSprite player2 = new PlayerSprite("Player2");
		player2.setPublicID(STUB_playerID2);
		playersController.addPlayer(player2);
		
		gamesController.addPlayer(STUB_gameID, player1);
		gamesController.addPlayer(STUB_gameID, player2);
*/
	}
	
	

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
