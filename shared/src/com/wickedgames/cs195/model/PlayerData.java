package com.wickedgames.cs195.model;

import java.text.DecimalFormat;


public interface PlayerData {

	public static final int INVALID_PLAYER_ID = 0;

	// Facing is the direction the Sprite is angled at
	// Not sure if facing should actually be 360 degrees and 
	// 		should actually provide float constants for the cardinal directions instead?
	//
	public enum Facing { 
		
		// this is for a 360 degree direction starting with 0  for UP and increasing clockwise
//		UP(0.0f), 
//		RIGHT(90.0f), 
//		DOWN(180.0f), 
//		LEFT(270.0f);
		
		// matching directional system client is currently using:
		// RIGHT = 0.0, and then values increase counter-clockwise
		RIGHT(0.0f), 
		UP(90.0f), 
		LEFT(180.0f), 
		DOWN(270.0f);
		
		
		private static final DecimalFormat DF = new DecimalFormat("0.00");

		private float direction;

		// defaults to direction = 0.0f
		Facing() {
			this.direction = 0.0f;
		}
		
		Facing(float direction) {
			this.direction = direction;
		}

		public float getDirection() {
			return direction;
		}

		public void setDirection(float direction) {
			this.direction = direction;
		}
		
		// not certain about overriding this
		@Override
		public String toString() {

// Float version	//			return DF.format(direction.doubleValue());
			return DF.format(direction);
		}
	};

	public enum State { MAIN_MENU, IN_LOBBY, RUNNING, CROUCHING, DEAD }; 
	

	// value range for speed:
	public static final float MAX_SPEED = GameDesignVars.MAX_PLAYER_SPEED;
	public static final float STOPPED = 0.0f;
	
	public static final int MAX_PROJECTILES = GameDesignVars.MAX_AMMO;		
	
	
	int getPublicID();
	void setPublicID(int publicID);

	void setPrivateID(int privateID);

	String getName();
	void setName(String name);

	State getState();
	void setState(State state);

	float getX();
	void setX(float x);
	
	float getY();
	void setY(float y);
	
	Facing getFacing();
	void setFacing(Facing facing);

	float getSpeed();
	void setSpeed(float speed);

    
//TODO: Add Projectiles back
	
//	Projectile[] getAllProjectiles();
//	Projectile getProjectile(int index);
//	void setProjectile(int index, Projectile projectile);

	// TODO: String toString();

}