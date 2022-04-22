package com.wickedgames.cs195;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wickedgames.cs195.controller.GamesController;
import com.wickedgames.cs195.controller.PlayersController;
import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;

import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@RestController
public class ServerApplication {


// STUBS!
	private static Integer STUB_gameID = 0;
	private static Integer STUB_playerID = 0;
	private static Integer STUB_playerID2 = -1;
	
	
	private static GamesController gamesController = new GamesController();
	private static PlayersController playersController = new PlayersController();
	
	public static void main(String[] args) {
		
		SpringApplication.run(ServerApplication.class, args);
		

// STUBS!
// TODO: Creating just a single game instance for early testing purposes 
STUB_gameID = gamesController.createGame();
//GameInstance game = gamesController.getGame(STUB_gameID);
STUB_playerID = playersController.createPlayer();
PlayerSprite player1 = playersController.getPlayer(STUB_playerID);
STUB_playerID2 = playersController.createPlayer();
PlayerSprite player2 = playersController.getPlayer(STUB_playerID2);
//System.out.println("ServerApplication.main() created PlayerSprite: " + player1);
gamesController.addPlayer(STUB_gameID, player1);
gamesController.addPlayer(STUB_gameID, player2);


	}


	@GetMapping("/game")
	public GameInstance game(@RequestParam(value = "id", defaultValue = "0") String id) {
		System.out.println("ServerApplication.game() called with id: " + id);

		Integer gameID = Integer.getInteger(id);
		
// STUB!
if( gameID == null || gameID == 0 ) 
	gameID = STUB_gameID;


//		return gamesController.getGame(gameID);

GameInstance ps =  gamesController.getGame(gameID);

System.out.println("ServerApplication.game() returning GameInstance: " + ps);
return ps;
	}

	


	@GetMapping("/player")
	public PlayerSprite player(@RequestParam(value = "id", defaultValue = "0") String id) {

		Integer playerID = Integer.getInteger(id);
		
// STUB!
if( playerID == null || playerID == 0 ) 
	playerID = STUB_playerID;

//	return playersController.getPlayer(playerID);
PlayerSprite ps = playersController.getPlayer(playerID);

System.out.println("ServerApplication.player() returning PlayerSprite: " + ps);
		return ps;
	}

	
	
	

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
