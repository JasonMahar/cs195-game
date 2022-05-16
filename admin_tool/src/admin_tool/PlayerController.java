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

	Node myCharacter;
//	double speed = 5.0;
	double speed = GameDesignVars.MAX_PLAYER_SPEED;
	
	
	public PlayerController(Node character) {
		myCharacter = character;
	}

	
	public void setNodeToControl(Node character) {
		myCharacter = character;
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
	}
	
	public void moveDown() {
		
//		System.out.println("MOVIN' DOWN!");
		myCharacter.setTranslateY(myCharacter.getTranslateY() + speed);
	}
	
	public void moveLeft() {
		
//		System.out.println("MOVIN' LEFT!");
		myCharacter.setTranslateX(myCharacter.getTranslateX() - speed);
	}
	
	public void moveRight() {
		
//		System.out.println("MOVIN' RIGHT!");
		myCharacter.setTranslateX(myCharacter.getTranslateX() + speed);
	}


}