package com.wickedgames.cs195.model;


import java.util.Arrays;

//import com.wickedgames.cs195.model.PlayerData.Facing;
//import com.wickedgames.cs195.model.PlayerData.State;


/**
 * @author Jason Mahar
 * @
 */
public class CS195PlayerData implements PlayerData {

	// IDs are set by the server so that they'll be unique
	private Integer publicID;		// public ID is how a player is referred to by other players
	private Integer privateID;		// private ID is how it identifies itself to the server

	private String name;
	private State state;

	protected float xPosition;
	protected float yPosition;
	private Facing facing;		// 360 degrees, with ordinal values 
	private Float speed;
	
	private Projectile[] projectiles;
	
	/**
	 * 
	 */
	public CS195PlayerData() {
		System.out.println("CS195PlayerData() constructor. ");
		
		publicID = 0; 		// IDs are set by the server
		privateID = 0;
		name = ""; 			// Server will def
		state = State.IN_LOBBY;
		facing = Facing.RIGHT;
		speed = STOPPED;
		
		projectiles = new Projectile[GameDesignVars.MAX_AMMO];
		
		System.out.println("Created CS195PlayerData: " + this.toString());
		
	}

	
	public CS195PlayerData(Integer publicID, Integer privateID, String name, State state, float xPosition,
			float yPosition, Facing facing, Float speed, Projectile[] projectiles) {
		super();
		this.publicID = publicID;
		this.privateID = privateID;
		this.name = name;
		this.state = state;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.facing = facing;
		this.speed = speed;
		this.projectiles = projectiles;
	}


	/**
	 * 
	 */
	public CS195PlayerData(String name) {
		this();
		System.out.println("CS195PlayerData() constructor. name = " + name);
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

	// getPrivateID() is not public 
	Integer getPrivateID() {
		return privateID;
	}
	
	@Override
	public void setPrivateID(Integer privateID) {
		this.privateID = privateID;
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
	public Projectile[] getAllProjectiles() {
		return projectiles;
	}
	
	@Override
	public Projectile getProjectile(int index) {
		return projectiles[index];
	}
	
	@Override
	public void setProjectile(int index, Projectile projectile) {
		projectiles[index] = projectile;
	}

	
	@Override
	public String toString() {
		return "{ publicID=" + publicID + ", name=" + name + ", state=" + state + ", xPosition="
				+ xPosition + ", yPosition=" + yPosition + ", facing=" + facing + ", speed=" + speed + ", projectiles="
				+ Arrays.toString(projectiles) + "}";
	}



}
