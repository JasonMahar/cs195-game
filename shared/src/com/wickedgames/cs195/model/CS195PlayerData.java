package com.wickedgames.cs195.model;


import java.util.Arrays;


/**
 * @author Jason Mahar
 * @
 */
public class CS195PlayerData implements PlayerData {

	// IDs are set by the server so that they'll be unique
	private int publicID;		// public ID is how a player is referred to by other players
	private int privateID;		// private ID is how it identifies itself to the server

	private String name;
	private State state;

	protected float x;
	protected float y;
	private Facing facing;		// 360 degrees, with ordinal values 
	private float speed;
	
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

	
	public CS195PlayerData(int publicID, int privateID, String name, State state, float x,
			float y, Facing facing, float speed, Projectile[] projectiles) {
		super();
		this.publicID = publicID;
		this.privateID = privateID;
		this.name = name;
		this.state = state;
		this.x = x;
		this.y = y;
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
	public int getPublicID() {
		return publicID;
	}
	
	@Override
	public void setPublicID(int publicID) {
		this.publicID = publicID;
	}

	// getPrivateID() is not public 
	int getPrivateID() {
		return privateID;
	}
	
	@Override
	public void setPrivateID(int privateID) {
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
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}


	@Override
	public void setY(float y) {
		this.y = y;
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
	public float getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(float speed) {
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
		
		return /* super.toString() + */
				"{ publicID=" + publicID + ", name=" + name + ", state=" + state + ", x="
				+ x + ", y=" + y + ", facing=" + facing + ", speed=" + speed + ", projectiles="
				+ Arrays.toString(projectiles) + "}";
	}



}
