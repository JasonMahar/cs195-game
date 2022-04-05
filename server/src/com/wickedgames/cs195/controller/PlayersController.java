package com.wickedgames.cs195.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;


import com.wickedgames.cs195.model.PlayerSprite;

/*  Class PlayersController
 * 
 *  
 * 
 *  @author Jason Mahar
 *  @created 3/21/2022
 *  
 */
public class PlayersController {
	
	private static HashMap<Integer,PlayerSprite> players = new HashMap<Integer,PlayerSprite>();
	private static Random rand = new Random();
	

	public static Integer createPlayer() {

		Integer key = createNewKey();
		PlayerSprite player = new PlayerSprite();
		player.setPublicID(key);
		players.put(key, player);
		return key;
	}
	
	private static Integer createNewKey() {
		
		Integer key = rand.nextInt();
		while( players.containsKey(key) ) {
			key = rand.nextInt();
		}
		return key;
	}

	
	public PlayerSprite getPlayer(Integer ID) {

		System.out.println("PlayersController.getPlayer() called. id = " + ID);
		
		return players.get(ID);
	}


	// Add/Update/Remove Players:
	
	public boolean addPlayer(PlayerSprite player) {

		if( player == null )	return false;

		return players.put(player.getPublicID(), player) == null;
	}
	
	public boolean updatePlayer(PlayerSprite player) {

		if( player == null )	return false;
				
		return players.put(player.getPublicID(), player) != null;
	}
	
	public boolean removePlayer(Integer playerPublicID ) {

		return players.remove(playerPublicID) != null;
	}

	public Collection<PlayerSprite> getAllPlayers() {
		
		return players.values();
	}

	

}
