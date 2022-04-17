package com.wickedgames.cs195.model;

import java.text.DecimalFormat;

import com.wickedgames.cs195.model.PlayerData.Facing;
import com.wickedgames.cs195.model.PlayerData.State;


public interface PlayerData {


	// Facing is the direction the Sprite is angled at
	// Not sure if facing should actually be 360 degrees and 
	// 		should actually provide Float constants for the cardinal directions instead?
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
		
		private Float direction;

		// defaults to direction = 0.0f
		Facing() {
			this.direction = 0.0f;
		}
		
		Facing(Float direction) {
			this.direction = direction;
		}

		public Float getDirection() {
			return direction;
		}

		public void setDirection(Float direction) {
			this.direction = direction;
		}
		
		// not certain about overriding this
		@Override
		public String toString() {
			
			return DF.format(direction.doubleValue());
		}
	};

	public enum State { IN_LOBBY, RUNNING, CROUCHING, DEAD }; 
	

	// value range for speed:
	public static final Float MAX_SPEED = GameDesignVars.MAX_PLAYER_SPEED;
	public static final Float STOPPED = 0.0f;
	
	public static final int MAX_PROJECTILES = GameDesignVars.MAX_AMMO;		
	
	
	Integer getPublicID();
	void setPublicID(Integer publicID);

	void setPrivateID(Integer privateID);

	String getName();
	void setName(String name);

	State getState();
	void setState(State state);

	Facing getFacing();
	void setFacing(Facing facing);

	Float getSpeed();
	void setSpeed(Float speed);

	Projectile[] getAllProjectiles();
	Projectile getProjectile(int index);
	void setProjectile(int index, Projectile projectile);

	String toString();

}