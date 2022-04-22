package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public abstract class Enemy extends Sprite implements DynamicSprite, Attacker {

	private float xVelocity = 0;
	private float yVelocity = 0;
	
	private Attacker attackType;

	/**
	 * 
	 */
	public Enemy(AssetType assetType, Attacker atk) {
		super(assetType);
		System.out.println("Enemy() constructor. ");
		attackType = atk;
	}

	public Enemy(AssetType assetType, String name, Attacker atk) {
		super(assetType, name);
		System.out.println("Enemy(name) constructor. assetType = " + assetType + ", name = " + name);

		attackType = atk;
	}




	public void update(long timePassed) {
		
		this.setPosition( this.getXPosition() + timePassed * xVelocity, 
						  this.getYPosition() + timePassed * yVelocity );
		
		// M3 USING STRATEGY
		this.attack(timePassed);
	}

	@Override
	public void attack(long timePassed) {
		
		attackType.attack(timePassed);
		
	}

	/**
	 * @return whether is moving on the x or y access
	 */
	public boolean isMoving() {
		return xVelocity != 0 || yVelocity != 0;
	}
	

	
	//////////////////////////////////////////
	//
	// getters & setters:
	//
	
	/**
	 * @param xVelocity the xVelocity to set
	 */
	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}


	/**
	 * @param yVelocity the yVelocity to set
	 */
	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}

	//////////////////////////////////////////
	//
	// toString & equals:
	//

	@Override
	public String toString() {
		return super.toString() + "[Enemy [xVelocity=" + xVelocity + ", yVelocity=" + yVelocity + ", attackType=" + attackType + "]]";
	}

	

}
