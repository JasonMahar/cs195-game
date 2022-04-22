package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public class GreenMonster extends Enemy {

	/**
	 * 
	 */
	public GreenMonster(Attacker atk) {
		super(AssetType.GREEN_MONSTER, atk);
		System.out.println("GreenMonster() constructor. ");
	}

	/**
	 * 
	 */
	public GreenMonster(String name, Attacker atk) {
		super(AssetType.GREEN_MONSTER, name, atk);
		System.out.println("GreenMonster(name) constructor. name = " + name);
	}
	
	

}
