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
	private static Integer STUB_gameID = 3;
	private static Integer STUB_playerID = 1;
	private static Integer STUB_playerID2 = 2;
	
	
	private static GamesController gamesController = new GamesController();
	private static PlayersController playersController = new PlayersController();
	
	public static void main(String[] args) {
		
		SpringApplication.run(ServerApplication.class, args);

// STUB!
STUB_createSampleGame();

	}

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

	
	// I
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
	

	@GetMapping("/player")
	public PlayerSprite player(@RequestParam(value = "id", defaultValue = "0") String id) {

		System.out.println("ServerApplication.player() called. id = " + id);
		
		Integer playerID = 0;
		try {
			playerID = Integer.valueOf(id);
		}
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		if( playerID == null || playerID == 0 ) {
			System.out.println("bad playerID = " + playerID);
		
			// TODO:
			// 	should normally return an error code for invalid param or data not found
			
			// STUB!
			playerID = STUB_playerID;
		}

//PlayerSprite ps = playersController.getPlayer(playerID);
//
//System.out.println("ServerApplication.player() returning PlayerSprite: " + ps);
//		return ps;

		return playersController.getPlayer(playerID);	
	}


	// STUB Methods for Testing!
	//
	
	private static void STUB_createSampleGame() {


		// STUBS!
		// TODO: Creating just a single game instance for early testing purposes 
		STUB_gameID = gamesController.createGame();
		//GameInstance game = gamesController.getGame(STUB_gameID);
		System.out.println("ServerApplication.STUB_createSampleGame() created demo game. id = " + STUB_gameID);
		
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

	}
	
	

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
