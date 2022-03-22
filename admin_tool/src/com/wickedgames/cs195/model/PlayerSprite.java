package com.wickedgames.cs195.model;

/**
 * 
 */

/**
 *
 */
public class PlayerSprite extends Sprite {


	// Not sure if facing should actually be 360 degrees and should actually provide Float constants for the cardinal directions instead?
	//	 { UP = 0, RIGHT = 90.0, DOWN = 180.0, LEFT = 270.0 };
	public enum Facing { UP, RIGHT, DOWN, LEFT };

	// values for speed:
	public static Float MAX_SPEED = 1.0f;
	public static Float STOPPED = 0.0f;


	private Integer publicID, privateID;
	private Facing facing;
	private Float speed;
	
	/**
	 * 
	 */
	public PlayerSprite() {
		super(AssetType.PLAYER);
		System.out.println("PlayerSprite() constructor. ");
		facing = Facing.UP;
		speed = STOPPED;
	}

	
	/**
	 * 
	 */
	public PlayerSprite(String name) {
		this();
		System.out.println("PlayerSprite() constructor. name = " + name);
		this.setName(name);
	}

//	public PlayerSprite(AssetType assetType) {
//		super(assetType);
//		// TODO Auto-generated constructor stub
//	}
//
//	public PlayerSprite(AssetType assetType, String name, int layer) {
//		super(assetType, name, layer);
//		// TODO Auto-generated constructor stub
//	}
	
	public Integer getPublicID() {
		return publicID;
	}

	public void setPublicID(Integer publicID) {
		this.publicID = publicID;
	}

	protected Integer getPrivateID() {
		return privateID;
	}

	protected void setPrivateID(Integer privateID) {
		this.privateID = privateID;
	}

	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "{ "
				+ "publicID=" + publicID + ", facing=" + facing + ", speed=" + speed + ", xPosition="
				+ xPosition + ", yPosition=" + yPosition + "}";
	}
	


}
