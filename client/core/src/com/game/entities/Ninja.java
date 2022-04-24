package com.game.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.game.*;
import com.wickedgames.cs195.transport.*;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;



public class Ninja extends BaseActor {

	///////////////////////////////////////
	// static attributes/accessors
	
	public static String playerName;
//	public static Integer playerID;
// STUB:
	public static Integer playerID = STUB_GameSession.STUB_DEFAULT_PLAYER_ID;
	
	
    /**
	 * @return the playerName
	 */
	public static String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public static void setPlayerName(String playerName) {
		Ninja.playerName = playerName;
	}

	/**
	 * @return the playerID
	 */
	public static Integer getPlayerID() {
		return playerID;
	}

	/**
	 * @param playerID the playerID to set
	 */
	public static void setPlayerID(Integer playerID) {
		Ninja.playerID = playerID;
	}

	// end static attributes/accessors
	///////////////////////////////////////
	

    private Pie pie;

    public Ninja(float x, float y, Stage stage) {
        super(x, y, stage);
        
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

    
    public void shoot() {
        if (getStage() == null)
            return;

// jason's comment: I'm assuming this was meant to be the Ninja's Pie attribute 
//        instead of creating a new one. But I could be wrong.
//
//        Pie pie = new Pie(0, 0, this.getStage());
        pie = new Pie(0, 0, this.getStage());
        pie.centerAtActor(this);
        pie.setRotation(this.getRotation());
        pie.setMotionAngle(this.getRotation());
    }


   public void act(float deltaTime) {
        super.act(deltaTime);
        
/*
        float degreesPerSecond = 120; // degrees per second
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rotateBy(degreesPerSecond * deltaTime);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rotateBy(-degreesPerSecond * deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerateAtAngle(getRotation());

        }
*/


// jason's comment: reverting movement back to absolute directions instead of
//		relative as above.
//        
        if (input.isKeyPressed(Keys.LEFT))  accelerateAtAngle(180);
        if (input.isKeyPressed(Keys.RIGHT)) accelerateAtAngle(0);
        if (input.isKeyPressed(Keys.UP))    accelerateAtAngle(90);
        if (input.isKeyPressed(Keys.DOWN))  accelerateAtAngle(270);

        applyPhysics(deltaTime);
                            
        setAnimationPaused(!isMoving());
        if (getSpeed() > 0)
            setRotation(getMotionAngle());

        boundToWorld();
        
        alignCamera();

// jason's comment: I'm assuming we don't want players or pies to wrap around to other side of screen/world
//        
//        wrapAroundWorld();
        
    }

}