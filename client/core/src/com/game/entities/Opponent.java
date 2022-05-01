package com.game.entities;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.BaseActor;
import com.game.BaseGame;
import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.model.PlayerData.*;

public class Opponent extends BaseActor implements PlayerData {
	
	// IDs are set by the server so that they'll be unique
	private int publicID;		// public ID is how a player is referred to by other players
//	private int privateID = PlayerData.INVALID_PLAYER_ID;	// invalid for Opponent PlayerData

	private String name;
	private State state;

//	protected float x;		// x & y inherited from Actor class
//	protected float y;
//	private Facing facing;		// 360 degrees, with ordinal values 
//	private float speed;	// speed inherited from BaseActor class
	
	private Projectile[] projectiles;


	private NamePlate namePlate;

	public Opponent(float x, float y, Stage s) {
        super(x, y, s);

        namePlate = new NamePlate(x, y + getHeight()/2, s);
        
        String[] filenames = {
        		"run/ninja-run_00.png", 
        		"run/ninja-run_01.png", 
        		"run/ninja-run_02.png", 
        		"run/ninja-run_03.png", 
        		"run/ninja-run_04.png",
        		"run/ninja-run_05.png", 
        		"run/ninja-run_06.png", 
        		"run/ninja-run_07.png", 
        		"run/ninja-run_08.png", 
        		"run/ninja-run_09.png", 
        		"run/ninja-run_10.png", 
        		"run/ninja-run_11.png"};
        loadAnimationFromFiles(filenames,0.1f, true);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        setBoundaryPolygon(8);
    }

	// behaves as the update
    public void act(float deltaTime) {

        super.act(deltaTime);

        accelerateAtAngle( getRotation() );
//        if (getFacing() == PlayerData.Facing.LEFT)  accelerateAtAngle(180);
//        if (getFacing() == PlayerData.Facing.RIGHT) accelerateAtAngle(0);
//        if (getFacing() == PlayerData.Facing.UP)    accelerateAtAngle(90);
//        if (getFacing() == PlayerData.Facing.DOWN)  accelerateAtAngle(270);

        applyPhysics(deltaTime);

        setAnimationPaused(!isMoving());

        if (getSpeed() > 0)
            setRotation(getMotionAngle());

        boundToWorld();
    }


	public Opponent(float x, float y, Stage s, PlayerData other) {
		this(x,y,s);
		setPlayerData(other);
	}
	
	
	public void setPlayerData(PlayerData other) {
		
		this.publicID = other.getPublicID();
		this.name = other.getName();
		this.state = other.getState();
		this.setX( other.getX() );
		this.setY( other.getY() );
		this.setFacing( other.getFacing() );
		this.setSpeed( other.getSpeed() );
		this.projectiles =  other.getAllProjectiles();
	}


	@Override
	public int getPublicID() {
		return publicID;
	}
	
	@Override
	public void setPublicID(int publicID) {
		this.publicID = publicID;
	}
	
	@Override
	public void setPrivateID(int privateID) {
//		this.privateID = privateID;		// invalid for Opponent PlayerData
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
		
		Facing facing = Facing.RIGHT;
		facing.setDirection(getRotation());
		return facing;
	}

	@Override
	public void setFacing(Facing facing) {
		setRotation( facing.getDirection() );
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

	/**
	 * @param x the x to set
	 */
	@Override
	public void setX(float x) {
		super.setX(x);
		namePlate.setX(x);
//		System.out.println("Opponent.setX updated. x = " + x);
	}

	/**
	 * @param y the y to set
	 */
	@Override
	public void setY(float y) {
		super.setY(y);
		namePlate.setY(y + getHeight()/2);
//		System.out.println("Opponent.setY updated. y = " + y);
	}
	
	/**
	 * @param x the x to set
	 */
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x,y);
		try {
			namePlate.setPosition(x, y + getHeight()/2);		// not sure which of these to use,
			namePlate.centerAtPosition(x, y + getHeight()/2);	// so setting both
//			System.out.println("Opponent.setPosition updated. x = " + x + ", y = " + y);
		}
		catch(NullPointerException ex) {
			System.out.println("Opponent.setPosition ERROR: namePlate not created yet.");
		}
	}


	/**
	 * @return the namePlate
	 */
	public String getNamePlate() {
		return namePlate.getName();
	}

	/**
	 * @param namePlate the namePlate to set
	 */
	public void setNamePlate(String name) {
		this.namePlate.setName(name);
	}

	
	@Override
	public String toString() {
		
		return /* super.toString() + */
				"{ publicID=" + publicID + ", name=" + name + ", state=" + state + ", x="
				+ getX()  + ", y=" + getY()  + ", facing=" + getFacing() + ", speed=" + getSpeed() + ", projectiles="
				+ Arrays.toString(projectiles) + "}";
	}



	
}
