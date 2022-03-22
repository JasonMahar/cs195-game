package com.wickedgames.cs195.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;

/*  Class GamesController
 * 
 *  
 * 
 *  @author Jason Mahar
 *  @created 3/21/2022
 *  
 */
public class GamesController {

	private static HashMap<Integer,GameInstance> games = new HashMap<Integer,GameInstance>();
	private static Random rand = new Random();
	
	public static Integer createGame() {

		Integer key = createNewKey();
		GameInstance game = new GameInstance(key);
		games.put(key, game);
		return key;
	}
	
	private static Integer createNewKey() {
		
		Integer key = rand.nextInt();
		while( games.containsKey(key) ) {
			key = rand.nextInt();
		}
		return key;
	}

	public Collection<GameInstance> getAllGames() {

		return games.values();
	}

	public GameInstance getGame(Integer ID) {

		return games.get(ID);
	}


	
	// Add/Update/Remove Players:
	
	public boolean addPlayer(Integer gameID, PlayerSprite player) {
		
		GameInstance game = getGame(gameID);
		return game.addPlayer(player);
	}
	
	public boolean updatePlayer(Integer gameID,PlayerSprite player) {

		GameInstance game = getGame(gameID);
		return game.updatePlayer(player);
	}
	
	public boolean removePlayer(Integer gameID, Integer playerPublicID) {

		GameInstance game = getGame(gameID);
		return game.removePlayer(playerPublicID);
	}

}
