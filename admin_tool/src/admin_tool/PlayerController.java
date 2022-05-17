package admin_tool;


/**
 * @author Jason Mahar
 * 
 */

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import com.wickedgames.cs195.model.*;


public class PlayerController{

	PlayerData myPlayerData;
	Node myCharacter;
//	double speed = 5.0;
	float speed = GameDesignVars.MAX_PLAYER_SPEED;
	
	
	public PlayerController(Node character, PlayerData playerData ) {
		myCharacter = character;
		myPlayerData = playerData;
	}

	
	public void setNodeToControl(Node character, PlayerData playerData ) {
		myCharacter = character;
		myPlayerData = playerData;
	}
	
	
	public void initializeInputs(Scene scene) {

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
//				System.out.println(event.getCode());

				switch(event.getCode()) {
				
				case W:
					moveUp();
					break;
				case S:
					moveDown();
					break;
				case A:
					moveLeft();
					break;
				case D:
					moveRight();
					break;
				default:
					break;
				}		
			}	
		});
		
	}
	
	
	public void moveUp() {
		
//		System.out.println("MOVIN' UP!");
		myCharacter.setTranslateY(myCharacter.getTranslateY() - speed);
		myPlayerData.setY(myPlayerData.getY() - speed);
	}
	
	public void moveDown() {
		
//		System.out.println("MOVIN' DOWN!");
		myCharacter.setTranslateY(myCharacter.getTranslateY() + speed);
		myPlayerData.setY(myPlayerData.getY() + speed);
	}
	
	public void moveLeft() {
		
//		System.out.println("MOVIN' LEFT!");
		myCharacter.setTranslateX(myCharacter.getTranslateX() - speed);
		myPlayerData.setX(myPlayerData.getX() - speed);
	}
	
	public void moveRight() {
		
//		System.out.println("MOVIN' RIGHT!");
		myCharacter.setTranslateX(myCharacter.getTranslateX() + speed);
		myPlayerData.setX(myPlayerData.getX() + speed);
	}


}