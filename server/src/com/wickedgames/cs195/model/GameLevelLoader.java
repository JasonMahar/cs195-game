package com.wickedgames.cs195.model;

import java.util.ArrayList;

public class GameLevelLoader {

	public GameLevelLoader() {
		// TODO Auto-generated constructor stub
	}

	private void instantiateWalls(ArrayList<Sprite> walls, AssetType type, int quantity) {

		Wall newWall;
		for(int i=1; i <= quantity; ++i) {	// 4 RIGHT_WALL
			newWall = new Wall(type, type.getDefaultName()+i);
			walls.add(newWall);
		}
	}
	
	public GameLevel createSampleLevel( ) {
		ArrayList<Sprite> floors = new ArrayList<Sprite>();
		ArrayList<Sprite> walls = new ArrayList<Sprite>();

		floors.add( new Floor( ) );

		instantiateWalls( walls, AssetType.RIGHT_WALL, 4); 		// 4 RIGHT_WALL
		instantiateWalls( walls, AssetType.TOP_WALL, 3); 		// 3 TOP_WALL
		instantiateWalls( walls, AssetType.BOTTOM_WALL, 2); 	// 2 BOTTOM_WALL
		instantiateWalls( walls, AssetType.LEFT_WALL, 2); 		// 2 LEFT_WALL
		
		// M3 USING BUILDER
		return new GameLevel.GameLevelBuilder("assets/Sprites/Level/Textures/Concrete/Ground_Albedo.tif")
				.addFloors(floors)
				.addWalls(walls)
				.build();
		
		
	}
	
//	
//	public GameLevel createLevel( String filename) {
//		
//	}
	
}
