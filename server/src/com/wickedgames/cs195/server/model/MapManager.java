package com.wickedgames.cs195.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.model.PlayerData.Facing;

/*
 *  This is a Singleton
 */
public class MapManager {

	// indices into the Float[]
	public static int X_VALUE = 0;
	public static int Y_VALUE = 1;
	public static int FACING_VALUE = 2;
	
	
	// use 4 corners as starting locations - this should change as 
	// list should be GameDesignVars.MAX_PLAYERS_PER_GAME rows
	//		by 3 columns/datum  (x, y, initial facing)
	private static ArrayList<Float[]> startingLocations = null;
	
	/*
	 * 
	startingLocations					
	{ 
		//	 	X		Y	FACING		CORNER OF SCREEN
		//	   ---     ---  ------ 		----------------
			{ 64.0f, 896.0f, UP}, 		// LEFT, BOTTOM 
			{536.0f,  64.0f, DOWN}, 	// RIGHT, TOP
			{ 64.0f,  64.0f, DOWN}, 	// LEFT, TOP
			{536.0f, 896.0f, UP} 		// RIGHT, BOTTOM
		});
	
	 */
	public static ArrayList<Float[]> getstartingLocations() {
		
		// only need to create startingLocations once 
		if( startingLocations == null) {
			
			startingLocations = new ArrayList<Float[]>();
			startingLocations.set(0, new Float[] {64.0f, 896.0f, Facing.UP.getDirection()} );
			startingLocations.set(1, new Float[] {536.0f, 64.0f, Facing.DOWN.getDirection()} );
			startingLocations.set(2, new Float[] {64.0f, 64.0f, Facing.DOWN.getDirection()} );
			startingLocations.set(3, new Float[] {536.0f, 896.0f, Facing.UP.getDirection()} );
		}
		
		return startingLocations;
	}
	

	public static boolean setPlayersStartingLocations( Collection<PlayerData> players ) {
		
		getstartingLocations();		// initializes startingLocations
		
		
		// only need to create startingLocations once 
		if(startingLocations == null) {
			startingLocations = new  ArrayList<Float[]>();
			
			Float [] nextLocation;
			int locationIndex = 0;
			for( PlayerData player : players ) {

				// if more players than startingLocations
				if( locationIndex >= startingLocations.size() ) {	

					System.out.println("WARNING: ServerApplication.setPlayersStartingLocations() locationIndex = " +
							locationIndex + " is greater than # of locations =  " + startingLocations.size() +
							"\n\tRepeating same locations previously used.");
					locationIndex = 0;	
				}
				
				
				nextLocation = startingLocations.get(locationIndex);
				player.setX( nextLocation[X_VALUE] );
				player.setY( nextLocation[Y_VALUE] );
				player.getFacing().setDirection( nextLocation[FACING_VALUE] );
				
				++locationIndex;
			}

		}
		return true;
	}
	
	

}
