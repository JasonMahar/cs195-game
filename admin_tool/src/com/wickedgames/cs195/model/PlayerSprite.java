package com.wickedgames.cs195.model;


import java.text.DecimalFormat;

/**
 * @author Jason Mahar
 * @
 */
public class PlayerSprite extends Sprite implements PlayerData {


	// values for speed:
	public static Float MAX_SPEED = 1.0f;
	public static Float STOPPED = 0.0f;

	private String name;
	private Integer publicID, privateID;
	private State state;
	private Facing facing;		// 360 degrees, with ordinal values 
	private Float speed;
	private Projectile[] projectiles;
	
	/**
	 * 
	 */
	public PlayerSprite() {
		super(AssetType.PLAYER);
		System.out.println("PlayerSprite() constructor. ");
		
		facing = Facing.UP;
		speed = STOPPED;
		

		System.out.println(this.toString());
		
	}

	
	/**
	 * 
	 */
	public PlayerSprite(String name) {
		this();
		System.out.println("PlayerSprite() constructor. name = " + name);
		this.setName(name);
	}

	
	@Override
	public Integer getPublicID() {
		return publicID;
	}

	@Override
	public void setPublicID(Integer publicID) {
		this.publicID = publicID;
	}

	protected Integer getPrivateID() {
		return privateID;
	}

	protected void setPrivateID(Integer privateID) {
		this.privateID = privateID;
	}


	@Override
	public State getState() {
		return state;
	}


	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public Facing getFacing() {
		return facing;
	}

	@Override
	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	@Override
	public Float getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(Float speed) {
		this.speed = speed;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}



	@Override
	public Projectile[] getProjectiles() {
		return projectiles;
	}


	@Override
	public void setProjectiles(Projectile[] projectiles) {
		this.projectiles = projectiles;
	}

//	
//	@Override
//	public String toString() {
//		return "{ "
//				+ "publicID=" + publicID + ", facing=" + facing + ", speed=" + speed + ", xPosition="
//				+ xPosition + ", yPosition=" + yPosition + "}";
//	}
//	


}
