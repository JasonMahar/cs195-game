package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public class FlyingMonster extends Enemy {

	/**
	 * 
	 */
	public FlyingMonster(Attacker atk) {
		super(AssetType.FLYING_MONSTER, atk);
		System.out.println("FlyingMonster() constructor. ");
	}

	/**
	 * 
	 */
	public FlyingMonster(String name, Attacker atk) {
		super(AssetType.FLYING_MONSTER, name, atk);
		System.out.println("FlyingMonster(name) constructor. name = " + name);
	}

	

}
