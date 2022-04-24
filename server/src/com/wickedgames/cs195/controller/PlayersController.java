package com.wickedgames.cs195.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;


import com.wickedgames.cs195.model.*;

/*  Class PlayersController
 * 
 *  
 * 
 *  @author Jason Mahar
 *  @created 3/21/2022
 *  
 */
public class PlayersController {
	
	private static HashMap<Integer,PlayerData> players = new HashMap<Integer,PlayerData>();
	private static Random rand = new Random();
	

	public static Integer createPlayer() {

		Integer key = createNewKey();
		PlayerData player = new CS195PlayerData();
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

	
	public PlayerData getPlayer(Integer ID) {

		System.out.println("PlayersController.getPlayer() called. id = " + ID);
		
		return players.get(ID);
	}


	// Add/Update/Remove Players:
	
	public boolean addPlayer(PlayerData player) {

		if( player == null )	return false;

		return players.put(player.getPublicID(), player) == null;
	}
	
	public boolean updatePlayer(PlayerData player) {

		if( player == null )	return false;
				
		return players.put(player.getPublicID(), player) != null;
	}
	
	public boolean removePlayer(Integer playerPublicID ) {

		return players.remove(playerPublicID) != null;
	}

	public Collection<PlayerData> getAllPlayers() {
		
		return players.values();
	}

	

}
