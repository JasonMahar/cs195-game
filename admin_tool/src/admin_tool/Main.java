package admin_tool;


import com.wickedgames.cs195.transport.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;



/**
 * @author Jason Mahar
 *
 */
public class Main extends Application {


    private static final String SERVER_COMMAND_STRINGS[] = { 
    		"Create Game", 
    		"List Games", 
    		"Get Game Info", 
    		"Leave Game",
    		"List All Players", 
    		"Get Player Info", 
    		"Update Player"};
    
    
	private static final String TEXT_MAIN_INSTRUCTIONS = "Use keys W, A, S, and D to move.\nCurrently controlling player: ";

	
	
	
	private Scene scene;
	private GridPane controlsPane;
	private Pane gamePlayPane;
	private PlayerController controller;
	
	private GameSessionInterface[] allGames;
	private GameSessionInterface currentlySelectedGame;
	private Integer currentlySelectedPlayer;

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


        SplitPane mainScreen = new SplitPane();
        initializeControlsPane();
        initializeGamePlayPanee();
        mainScreen.getItems().addAll(controlsPane, gamePlayPane);
        
//		Scene scene = new Scene(gamePlayPane, 600, 700, Color.LIGHTGREEN);
		scene = new Scene(mainScreen, 900, 700);
		stage.setScene(scene);
		stage.setTitle("Game Admin Tool");
		stage.show();

		controller.initializeInputs(scene);

	}

	private void initializeControlsPane() {

        controlsPane = new GridPane();
        controlsPane.setStyle("-fx-background-color: null");
        controlsPane.setMinSize(300, 700); 
        controlsPane.setPadding(new Insets(100, 10, 100, 20)); 
        controlsPane.setVgap(25); 
        controlsPane.setHgap(10);       

		Text commandsText = new Text("Server Commands:");     
		ChoiceBox commandsChoices = new ChoiceBox( FXCollections.observableArrayList(SERVER_COMMAND_STRINGS) );

		Text commandsParametersText = new Text("Command Parameters:");   
	      //Creating Text Filed for email        
	      TextField commandsParametersField = new TextField();  
        
		Text gamesText = new Text("Game IDs:");     
		ChoiceBox gamesChoices = new ChoiceBox();
		
        // add a listener. when Game is changed, list of Players is set to that game
		gamesChoices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
  
            // if game in the list is changed
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number value, Number newValue) {
				
				// TODO: get list of Players and 
				//		replace playersChoices with that list
				
			}
        });

        
		Text playersText = new Text("Player IDs:");     
		ChoiceBox playersChoices = new ChoiceBox();
	    Button submitButton = new Button("Submit"); 

		Text resultsText = new Text();     

		//Arranging all the nodes in the grid 
		controlsPane.add(commandsText, 0, 0); 
		controlsPane.add(commandsChoices, 1, 0); 
		controlsPane.add(commandsParametersText, 0, 1);       
		controlsPane.add(commandsParametersField, 1, 1); 
		controlsPane.add(gamesText, 0, 3);       
		controlsPane.add(gamesChoices, 1, 3); 
		controlsPane.add(playersText, 0, 4);    
		controlsPane.add(playersChoices, 1, 4); 
		controlsPane.add(submitButton, 0, 5); 
		controlsPane.setColumnSpan(resultsText, 16); 
		
	}
		
	
	
	private void initializeGamePlayPanee() {

        gamePlayPane = new Pane();
        
        gamePlayPane.setMinSize(600, 700); 
        gamePlayPane.setStyle("-fx-background-color: LIGHTGREEN;");
		
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

		
		gamePlayPane.getChildren().addAll(instructions, line, myCharacter, otherCharacter);

		
		// Set keyboard inputs to control the myCharacter Sprite
		controller = new PlayerController(myCharacter);
		
	}
    
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

// TODO: create the GameSession in a better place - just using this for TESTING
//		GameSession session = new GameSession();
//		session.getGameData();
		
        launch(args);
	}

}
