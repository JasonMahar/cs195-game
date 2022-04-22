package com.game.entities;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.*;
import com.wickedgames.cs195.transport.*;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;

public class Ninja extends BaseActor {


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

	public Ninja(float x, float y, Stage s) {

        super(x, y, s);

        String[] filenames = {"run/ninja-run_00.png", "run/ninja-run_01.png", "run/ninja-run_02.png", "run/ninja-run_03.png", "run/ninja-run_04.png", "run/ninja-run_05.png"};

        loadAnimationFromFiles(filenames, 0.1f, true);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        setBoundaryPolygon(8);
    }

    public void act(float deltaTime) {

        super.act(deltaTime);

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
    }
}