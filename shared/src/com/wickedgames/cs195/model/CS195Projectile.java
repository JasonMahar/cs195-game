
package com.wickedgames.cs195.model;

import com.wickedgames.cs195.model.PlayerData.Facing;

/**
 * @author Jason Mahar
 *
 */
public class CS195Projectile implements Projectile {

	private int ID;				// I'm not sure these have to be unique between players
								// 	 most likely will just be numbered 1 to GameDesignVars.MAX_AMMO
	protected float xPosition;
	protected float yPosition;
	
	private Facing facing;		// direction projectile is moving 360 degrees, with ordinal values 
	private Float speed;
	
	
	/**
	 * 
	 */
	public CS195Projectile() {
		super();
		ID = 0;
		this.xPosition = 0;
		this.yPosition = 0;
		this.facing = Facing.RIGHT;
		this.speed = 0.0f;
	}
	
	public CS195Projectile(int iD, float xPosition, float yPosition, Facing facing, Float speed) {
		super();
		ID = iD;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.facing = facing;
		this.speed = speed;
	}
	/**
	 * @return the iD
	 */
	@Override
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	@Override
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the xPosition
	 */
	@Override
	public float getxPosition() {
		return xPosition;
	}

	/**
	 * @param xPosition the xPosition to set
	 */
	@Override
	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * @return the yPosition
	 */
	@Override
	public float getyPosition() {
		return yPosition;
	}

	/**
	 * @param yPosition the yPosition to set
	 */
	@Override
	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * @return the facing
	 */
	@Override
	public Facing getFacing() {
		return facing;
	}

	/**
	 * @param facing the facing to set
	 */
	@Override
	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	/**
	 * @return the speed
	 */
	@Override
	public Float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	@Override
	public void setSpeed(Float speed) {
		this.speed = speed;
	}


}
