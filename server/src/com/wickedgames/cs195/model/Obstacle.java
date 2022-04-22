package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public abstract class Obstacle extends Sprite {
	
	/**
	 * 
	 */
	public Obstacle(AssetType assetType) {
		super(assetType);
		System.out.println("Obstacle() constructor. ");
	}

	public Obstacle(AssetType assetType, String name) {
		super(assetType, name);
		System.out.println("Obstacle(name) constructor. assetType = " + assetType + ", name = " + name);
	}
	

}
