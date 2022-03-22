package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public class Wall extends Obstacle {

	/**
	 * 
	 */
	public Wall() {
		super(AssetType.TOP_WALL);
		System.out.println("Wall() constructor. ");
	}

	/**
	 * 
	 */
	public Wall(String name) {
		super(AssetType.TOP_WALL, name);
		System.out.println("Wall(name) constructor. name = " + name);
	}
	

	/**
	 * 
	 */
	public Wall(AssetType type, String name) {
		super(type, name);
		System.out.println("Wall(name) constructor. name = " + name);
	}
	
	

}
