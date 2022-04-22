package com.wickedgames.cs195.model;


import java.util.ArrayList;
//import java.awt.Image;


/*
 *   GameLevel are the static Sprites that remain the same for the whole level 
 *   
 *   Dynamic Sprites like the Player and Enemy are handled separately since they move
 *   
 *   Currently contains required backgroundImage, 
 *   and may or may not contain floor tiles, wall tiles, decoration tiles.
 *   Using a Builder Pattern since new GameLevel attributes are expected to be 
 *   added in the future.
 *   
 */
public class GameLevel {


	private String backgroundImage;			// currently String for image filename. 
//	private Image backgroundImage;			// but may be better to store an Image in future
	
	private ArrayList<Sprite> floors;
	private ArrayList<Sprite> walls;
	private ArrayList<Sprite> decorations;
	
	
	private GameLevel( GameLevelBuilder builder ) {

		this.backgroundImage = builder.backgroundImage;
		this.floors = builder.floors;
		this.walls = builder.walls;
		this.decorations = builder.decorations;
	}

	public void draw() {
		
		// draw backgroundImage
		
		if( floors != null ) {
			for( Sprite s : floors) { s.draw(); }
		}

		if( walls != null ) {
			for( Sprite s : walls) { s.draw(); }
		}

		if( decorations != null ) {
			for( Sprite s : decorations) { s.draw(); }
		}

	}
	
	
	public ArrayList<Sprite> getAllSprites() {

		ArrayList<Sprite> allSprites = new ArrayList<Sprite>();
		if( walls != null ) { allSprites.addAll( walls ); }
		if( floors != null ) { allSprites.addAll( floors ); }
		if( decorations != null ) { allSprites.addAll( decorations ); }
		return allSprites;
	}
	
	// M3 BUILDER
	/*
	 *  
	 */
	public static class GameLevelBuilder {

		private String backgroundImage;			// currently String for image filename. 
//		private Image backgroundImage;			// but may be better to store an Image in future
		
		private ArrayList<Sprite> floors = null;
		private ArrayList<Sprite> walls = null;
		private ArrayList<Sprite> decorations = null;
		

//		public GameLevelBuilder( String backgroundImage ) { //  may be better to store an Image in future
		public GameLevelBuilder( String backgroundImage ) {
			this.backgroundImage = backgroundImage;
		}

		public GameLevelBuilder addFloors( ArrayList<Sprite> floors ) {
			
			this.floors = floors;
			return this;
		}

		public GameLevelBuilder addWalls( ArrayList<Sprite> walls ) {
			
			this.walls = walls;
			return this;
		}

		public GameLevelBuilder addDecorations( ArrayList<Sprite> decorations ) {
			
			this.decorations = decorations;
			return this;
		}
		
		
		public GameLevel build() {
			return new GameLevel(this);
		}
		
		
	}

	@Override
	public String toString() {
		return "GameLevel [backgroundImage=" + backgroundImage + ", floors=" + floors + ", walls=" + walls
				+ ", decorations=" + decorations + "]";
	}
	
	
}
