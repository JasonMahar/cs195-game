/**
 * 
 */
package admin_tool;

import javafx.application.*;
import javafx.stage.Stage;

import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.*;

//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;



/**
 * @author Jason Mahar
 *
 */
public class Main extends Application {


	private static final String TEXT_MAIN_INSTRUCTIONS = "Use keys W, A, S,D to move.";



	@Override
	public void start(Stage stage) throws Exception {

//		FXMLLoader loader = new FXMLLoader();
////				FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
//		Parent root = loader.load();
//		
//		Scene scene = new Scene(root);
//		PlayerController controller = loader.getController();

//    	Pane pane = new FlowPane();
//    	pane.setStyle("-fx-background-color: null");
		Group pane = new Group();
		
		Scene scene = new Scene(pane, 600, 700, Color.LIGHTGREEN);
	    
		Text instructions = new Text();
		instructions.setText(TEXT_MAIN_INSTRUCTIONS);
//    	instructions.setTextAlignment(TextAlignment.CENTER);
		instructions.setX(150);
		instructions.setY(50);
		instructions.setFont(Font.font("Ariel", 20));
		instructions.setFill(Color.rgb(0x00, 0x60, 0xA0 ));

		Line line = new Line();
		line.setStartX(0);
		line.setStartY(95);
		line.setEndX(600);
		line.setEndY(95);
		line.setStrokeWidth(5);
		
		Polygon myCharacter = new Polygon();
		myCharacter.getPoints().setAll(
				120.0, 500.0,
				100.0, 550.0,
				140.0, 550.0
				);
		myCharacter.setFill(Color.DARKBLUE);

		Polygon otherCharacter = new Polygon();
		otherCharacter.getPoints().setAll(
				480.0, 200.0, 
				520.0, 200.0, 
				500.0, 250.0 
				);
		otherCharacter.setFill(Color.DARKRED);

//		Image image = new Image("../../assets/player/CharacterFront-Run_0.png");
		
//		Image image = new Image("C:\\Users\\Jason\\Documents\\GitHub\\cs195-game\\admin_tool\\assets\\PlayerCharacterFront-Run_0.png");
//		Image image = new Image("CharacterFront-Run_0.png");
//		ImageView myCharacter = new ImageView(image);
//		myCharacter.setRotate(90.0);
//		myCharacter.setX(100.0);
//		myCharacter.setY(500.0);

//		pane.getChildren().addAll(instructions, myCharacter);
		
		pane.getChildren().addAll(instructions, line, myCharacter, otherCharacter);
		stage.setScene(scene);
		stage.show();
		

		PlayerController controller = new PlayerController(myCharacter);
		controller.initializeInputs(scene);
		
	}

	
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
	}

}
