package com.wickedgames.cs195.model;

import com.wickedgames.cs195.model.PlayerData.Facing;

public interface Projectile {

	/**
	 * @return the iD
	 */
	int getID();

	/**
	 * @param iD the iD to set
	 */
	void setID(int iD);

	/**
	 * @return the xPosition
	 */
	float getxPosition();

	/**
	 * @param xPosition the xPosition to set
	 */
	void setxPosition(float xPosition);

	/**
	 * @return the yPosition
	 */
	float getyPosition();

	/**
	 * @param yPosition the yPosition to set
	 */
	void setyPosition(float yPosition);

	/**
	 * @return the facing
	 */
	Facing getFacing();

	/**
	 * @param facing the facing to set
	 */
	void setFacing(Facing facing);

	/**
	 * @return the speed
	 */
	Float getSpeed();

	/**
	 * @param speed the speed to set
	 */
	void setSpeed(Float speed);

}