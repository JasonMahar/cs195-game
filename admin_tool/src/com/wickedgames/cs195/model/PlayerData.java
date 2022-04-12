package com.wickedgames.cs195.model;

import java.text.DecimalFormat;

import com.wickedgames.cs195.model.PlayerSprite.Facing;
import com.wickedgames.cs195.model.PlayerSprite.State;

public interface PlayerData {


	// Facing is the direction the Sprite is angled at
	// Not sure if facing should actually be 360 degrees and should actually provide Float constants for the cardinal directions instead?
	//	 { UP = 0, RIGHT = 90.0, DOWN = 180.0, LEFT = 270.0 };
	public enum Facing { 
		UP(0.0f), 
		RIGHT(90.0f), 
		DOWN(180.0f), 
		LEFT(270.0f);
		
		
		private static final DecimalFormat DF = new DecimalFormat("0.00");
		
		private Float direction;
		
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

	public enum State { RUNNING, CROUCHING, DEAD }; 
	
	
	
	public static final int MAX_PROJECTILES = 1;		// this should work for any value
	
	
	Integer getPublicID();
	void setPublicID(Integer publicID);

	State getState();
	void setState(State state);

	Facing getFacing();
	void setFacing(Facing facing);

	Float getSpeed();
	void setSpeed(Float speed);

	String getName();
	void setName(String name);

	Projectile[] getProjectiles();
	void setProjectiles(Projectile[] projectiles);

}