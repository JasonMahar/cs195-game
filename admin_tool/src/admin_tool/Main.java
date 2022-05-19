package admin_tool;

/**
 * @author Jason Mahar
 * 
 */


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

import java.util.Collection;
import java.util.HashMap;

import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.model.GameInstance.GameState;
import com.wickedgames.cs195.transport.*;



public class Main extends Application {

	// Sizes of panes
	private static int MAP_WIDTH = 800; 
	private static int MAP_HEIGHT = 600; 
	private static int INSTRUCTIONS_HEIGHT = 100; 
	private static int CONTROLS_WIDTH = 300; 
	private static int SCREEN_WIDTH = MAP_WIDTH + CONTROLS_WIDTH;
	private static int SCREEN_HEIGHT = MAP_HEIGHT + INSTRUCTIONS_HEIGHT;

	
	// avatar will just be a triangle for now
	private static float AVATAR_HEIGHT = 50f;
	private static float AVATAR_WIDTH = 40f;
	private static float AVATAR_MID_HEIGHT = AVATAR_HEIGHT / 2.0f;
	private static float AVATAR_MID_WIDTH = AVATAR_WIDTH / 2.0f;
	private static final Color[] AVATAR_COLORS =  
		{ Color.DARKBLUE, Color.DARKRED, Color.DARKVIOLET, Color.DARKORANGE};
	
	
	// Strings
	private static final String APPLICATION_NAME = "Game Admin Tool";
	private static final String TEXT_MAIN_INSTRUCTIONS = 
			"Use keys W, A, S, and D to move.\nCurrently controlling player: ";

	// creating this dynamically from ServerCommands enum
	private static String[] SERVER_COMMAND_STRINGS;
	/*
    private static final String SERVER_COMMAND_STRINGS[] = { 
    		"Create Game", 
    		"List Games", 
    		"Get Game Info", 
    		"Create Player",
    		"Join Game",
    		"Leave Game",
    		"List All Players", 
    		"Get Player Info", 
    		"Update Player"};
    */
	
	
	// JavaFX Nodes that are accessed throughout the class
	private Scene scene;
	private GridPane controlsPane;
	private Pane gamePlayPane;
	private ChoiceBox<String> commandsChoices;
	private ChoiceBox<Integer> gamesChoices;
	private ChoiceBox<String> playersChoices;
	private TextField commandsParametersField;
	private Text instructions;
	private Text resultsText;     
	private Text jsonText; 
	
	
	// Data used throughout the class
//	private GameInstance[] allGames = null;
	private GameInstance currentlySelectedGame = null;
	private PlayerData currentlySelectedPlayer = null;
	private HashMap<String, PlayerData> knownPlayers = null;
	private HashMap<String, Polygon> playerAvatars;		// Map of player names to avatars. For now avatars are just triangles
	private PlayerController controller;				// knows how to move an avatar

	
	
	/* 
	 *  Initializes data, and creates a mainScreen consisting of 
	 *  a game view and a controls pane
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception {

		playerAvatars = new HashMap<String, Polygon>();
		knownPlayers = new HashMap<String, PlayerData>();
		initializeServerCommands();	// sets values of String SERVER_COMMAND_STRINGS[]
		
        SplitPane mainScreen = new SplitPane();
        initializeGamePlayPane();
        initializeControlsPane();
        mainScreen.getItems().addAll(gamePlayPane, controlsPane);

		scene = new Scene(mainScreen, SCREEN_WIDTH, SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.setTitle(APPLICATION_NAME);
		stage.show();

		
// STUB:
//		STUB_initializeMap();
		
	}

	
	/*
// STUB:
	private void STUB_initializeMap() {
		
		// STUB:
				Polygon myCharacter = createPlayerAvatar(AVATAR_WIDTH, MAP_HEIGHT - AVATAR_HEIGHT, 90, AVATAR_COLORS[0]);
				Polygon otherCharacter = createPlayerAvatar(MAP_WIDTH - AVATAR_WIDTH, AVATAR_HEIGHT, 270, AVATAR_COLORS[1]);
				Polygon thirdCharacter = createPlayerAvatar(AVATAR_WIDTH, AVATAR_HEIGHT, 270, AVATAR_COLORS[2]);
				Polygon fourthCharacter = createPlayerAvatar(MAP_WIDTH - AVATAR_WIDTH, MAP_HEIGHT - AVATAR_HEIGHT, 90, AVATAR_COLORS[3]);
				playerAvatars.put("Player 1", myCharacter);
				playerAvatars.put("Player 2", otherCharacter);
				playerAvatars.put("Player 3", thirdCharacter);
				playerAvatars.put("Player 4", fourthCharacter);
				
		// STUB:
				// Set keyboard inputs to control the myCharacter Sprite
				setAvatarToControl(myCharacter);

				controller.initializeInputs(scene);
	}
*/
	
	
	/*
	 * puts player avatars on the map in their initial positions
	 * 
	 */
	private void initializeMap(Collection<PlayerData> players) {

		int index = 0;
		String playerName = "";
		
		for(PlayerData player : players) {
			
			Polygon character = createPlayerAvatar(
					player.getX(),
					player.getY(), 
					player.getFacing().getDirection(), 
					AVATAR_COLORS[index++]);
			
			playerName = player.getName();
			playerAvatars.put(playerName, character);
		}
		
		// Set keyboard inputs to control the first Player (should be same as 
		// 		currentlySelectedGame and the currently selected playersChoices)
		//

//		String playerName = (String)playersChoices.getValue();
//		String playerName = playersChoices.getSelectionModel().getSelectedItem();
		
		
		if( currentlySelectedPlayer != null && !currentlySelectedPlayer.getName().isEmpty()) {
			playerName = currentlySelectedPlayer.getName();
		}
		else {  // just select last playerName added to be currentlySelectedPlayer
			this.updateCurrentlySelectedPlayer(playerName);
		}
		System.out.println("initializeMap setting active player to " + playerName);
		
		Polygon avatar = playerAvatars.get(playerName);
		setAvatarToControl( avatar );

		controller.initializeInputs(scene);
	}
	
	
	private void initializeServerCommands() {
		
		int size= ServerCommands.values().length;
//		System.out.println("num of ServerCommands: " + size);
		SERVER_COMMAND_STRINGS = new String [size];
		int index = 0;
		for( ServerCommands command : ServerCommands.values() ) {
//			System.out.println("adding command: " + command.toString());
			SERVER_COMMAND_STRINGS[index++] = command.toString();
		}
	}

	private void initializeControlsPane() {

        controlsPane = new GridPane();
        controlsPane.setStyle("-fx-background-color: null");
        controlsPane.setMinSize(CONTROLS_WIDTH, SCREEN_HEIGHT);  
        controlsPane.setPadding(new Insets(50, 10, 10, 20)); 
        controlsPane.setVgap(20); 
        controlsPane.setHgap(10);       
		

		Text commandsText = new Text("Server Commands:");     
		commandsChoices = new ChoiceBox<String>( FXCollections.observableArrayList(SERVER_COMMAND_STRINGS) );
		commandsChoices.setValue("Create Player");
		Text commandsParametersText = new Text("Command Parameters:");   
	      
		// Text Field for passing in additional data like player name when creating a player        
	    commandsParametersField = new TextField();  
        
		Text gamesText = new Text("Game IDs:");   ;
		gamesChoices = new ChoiceBox<Integer>();

// STUB:	We only have 1 game instance for this first iteration, so hardcoding it's ID
		gamesChoices.getItems().add(GameDesignVars.GAME_LOBBY_ID);
		gamesChoices.setValue( Integer.valueOf(GameDesignVars.GAME_LOBBY_ID) );
		
        // add a listener. when Game is changed, list of Players is set to that game
		gamesChoices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
  
            // if game in the list is changed
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number value, Number newValue) {
				
				// TODO: get list of Players and 
				//		replace playersChoices with that list

				System.out.println("gamesChoices changed() handler not implimented");
			}
        });

        
		Text playersText = new Text("Players:");     
		playersChoices = new ChoiceBox<String>();
		
		// add a listener. when Player is changed, list of Players is set to that game
//		playersChoices.setOnAction((event) -> {
//
//				System.out.println("playersChoices OnAction handler called");
//			  	onPlayerChoiceChanged();
//	        });

		// add a listener. when Player is changed, currentlySelectedPlayer is set 
		playersChoices.getSelectionModel()
				.selectedItemProperty()
				.addListener(
		         (ObservableValue<? extends String> ov, String old_val, String new_val) -> {

						System.out.println("playersChoices OnAction handler called with new_val = " + new_val);
					  	onPlayerChoiceChanged(new_val);
	         	});

	    Button submitButton = new Button("Submit"); 
	    submitButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	onSubmitButtonPressed(event);
	        }
	    });

	    Label resultsLabel = new Label("Results:");
		resultsText = new Text();     
		resultsText.setWrappingWidth(CONTROLS_WIDTH - 40);
		Label jsonLabel = new Label("Data:");
		jsonText = new Text();     
		jsonText.setWrappingWidth(CONTROLS_WIDTH - 40);

		//Arranging all the nodes in the grid 
		controlsPane.add(commandsText, 0, 0); 
		controlsPane.add(commandsChoices, 1, 0); 
		controlsPane.add(commandsParametersText, 0, 1);       
		controlsPane.add(commandsParametersField, 1, 1); 
		controlsPane.add(gamesText, 0, 3);       
		controlsPane.add(gamesChoices, 1, 3); 
		controlsPane.add(playersText, 0, 4);    
		controlsPane.add(playersChoices, 1, 4); 
		controlsPane.add(submitButton, 1, 5, 2, 1); 
//		controlsPane.setColumnSpan(submitButton, 5); 
		controlsPane.add(resultsLabel, 0, 7);    
		controlsPane.add(resultsText, 0, 8);    
		GridPane.setColumnSpan(resultsText, GridPane.REMAINING); 
		controlsPane.add(jsonLabel, 0, 10);    
		controlsPane.add(jsonText, 0, 11);    
		GridPane.setColumnSpan(jsonText, GridPane.REMAINING); 
	

//// STUB
//		String playerName = "Dummy Player";
//		playersChoices.getItems().add(playerName);
//		playersChoices.setValue(playerName);
	}
		

	

	private void initializeGamePlayPane() {

        gamePlayPane = new Pane();
        
        gamePlayPane.setMinSize(MAP_WIDTH, SCREEN_HEIGHT); 
        gamePlayPane.setStyle("-fx-background-color: LIGHTGREEN;");

		Line line = new Line();
		line.setStartX(0);
		line.setStartY(MAP_HEIGHT);
		line.setEndX(MAP_WIDTH);
		line.setEndY(MAP_HEIGHT);
		line.setStrokeWidth(5);
		
		instructions = new Text();
		instructions.setText(TEXT_MAIN_INSTRUCTIONS);
//    	instructions.setTextAlignment(TextAlignment.CENTER);
		instructions.setX(100);
		instructions.setY(MAP_HEIGHT + 40);
		instructions.setFont(Font.font("Ariel", 20));
		instructions.setFill(Color.rgb(0x00, 0x60, 0xA0 ));

		gamePlayPane.getChildren().addAll(instructions, line);
		
	}

	
	// Set keyboard inputs to control the myCharacter Sprite
	private void setAvatarToControl(Polygon character) {

		if( character == null ) {
			System.out.println("ERROR: Main.setAvatarToControl() - avatar is null. " );
			return;
		}
		
		if( controller == null ) {
			controller = new PlayerController(character, currentlySelectedPlayer);
		}
		else {
			controller.setNodeToControl(character, currentlySelectedPlayer);
		}
		
		if( currentlySelectedPlayer == null ) {
			instructions.setText(TEXT_MAIN_INSTRUCTIONS);
		}
		else {
			instructions.setText(TEXT_MAIN_INSTRUCTIONS + currentlySelectedPlayer.getName());
		}
		
	}

	
	private Polygon createPlayerAvatar(double x, double y, double rotation, Color color) {

// NOTE: was trying to use an image instead of triangle but couldn't get the image to load for some reason
		
//		Image image = new Image("../../assets/player/CharacterFront-Run_0.png");
		
//		Image image = new Image("C:\\Users\\Jason\\Documents\\GitHub\\cs195-game\\admin_tool\\assets\\PlayerCharacterFront-Run_0.png");
//		Image image = new Image("CharacterFront-Run_0.png");
//		ImageView myCharacter = new ImageView(image);

		Polygon character = new Polygon();
		updatePlayerAvatar( character, x, y, rotation);
		character.setFill(color);

		gamePlayPane.getChildren().addAll(character);	// add it to the map
		
		return character;
	}

	
	private void updatePlayerAvatar(Polygon character, double x, double y, double rotation) {

		
		// Triangle:         x
		//                   |
		//                   V
		//      1st point --->   
		//                   /\
		//           y ---> /  \
		// 2nd point  ---> /____\ <--- 3rd point
		
		character.getPoints().setAll(
				x                   , y + AVATAR_MID_HEIGHT,
				x - AVATAR_MID_WIDTH, y - AVATAR_MID_HEIGHT,
				x + AVATAR_MID_WIDTH, y - AVATAR_MID_HEIGHT
				);
		
		rotatePlayerAvatar(character, rotation);
	}

	/*
	 *  Note: JavaFX displays our avatar's rotation of 0 as pointing up and rotates clockwise
	 * 		But the Client (either due to libgdx or the game Nat built off of) displays
	 *  	our avatar's rotation of 0 as pointing right and rotates counter-clockwise
	 * 
	 */
	private void rotatePlayerAvatar(Polygon character, double rotation) {

		
		character.setRotate(270 - rotation);
	}

	
	private void onSubmitButtonPressed(ActionEvent event) {

//		String commandString = (String)commandsChoices.getSelectionModel().getSelectedItem();
		String commandString = (String)commandsChoices.getValue();

		System.out.println("\nsending command: " + commandString);
		
		// the ServerCommands toString() are not the enum's values
//		ServerCommands command = ServerCommands.valueOf(commandString);	
		ServerCommands command = ServerCommands.getServerCommandsFromString(commandString);
		if( command == null ) {

			resultsText.setText(ServerCommands.getLastResultMessage());
			return;
		}

		System.out.println("\tcommand enum: " + command.name());
		
		String params = commandsParametersField.getText();
		commandsParametersField.clear();					// auto clearing commandsParametersField but maybe shouldn't?
		Integer gameID = (Integer)gamesChoices.getValue();
		

		String playerName = (String)playersChoices.getSelectionModel().getSelectedItem();
//		String playerName = (String)playersChoices.getValue();

		System.out.println("\tparams: " + params);
		System.out.println("\tgameID: " + gameID.toString());
		System.out.println("\tplayerName: " + playerName);
		
		if( currentlySelectedPlayer == null ) {
			// update currentlySelectedPlayer to pass into ServerCommands  
			updateCurrentlySelectedPlayer(playerName);
		}
		if( currentlySelectedPlayer == null ) {
			System.out.println("command enum: " + command.name());
			System.out.println("WARNING: no playerData yet for " + playerName);
		}
		
		ServerCommands.clearLastResults();
		
		// note that since since send() can only return GameInstance,
		//		sometimes the results is only the part of the GameInstance we asked for
		//		e.g. if command == ServerCommands.CREATE_PLAYER, the GameInstance data 
		//		will be empty except for one PlayerData in the results.getAllPlayers()
		GameInstance results = command.send(params, currentlySelectedPlayer, gameID);
		
		processResults( command, results, params );
		resultsText.setText(ServerCommands.getLastResultMessage());
		System.out.println("\tgetLastResultMessage: " + ServerCommands.getLastResultMessage());

		jsonText.setText(ServerCommands.getLastJSONReceived());
		System.out.println("\tgetLastJSONReceived: " + ServerCommands.getLastJSONReceived());

		if( ServerCommands.SUCCESS_MESSAGE.equals( ServerCommands.getLastResultMessage() ) ) {
			resultsText.setFill(Color.GREEN);
		}
		else {
			resultsText.setFill(Color.RED);
		}
	}

	
// HACK: currentlySelectedPlayer is supposed to get set whenever playersChoices gets 
//		set or changed. But sometimes it's not getting updated, so trying to make sure 
//		we get most updated data.
	/*
	 *  sets currentlySelectedPlayer to the most up-to-date PlayerData that has
	 *  playerName, based on:
	 *  	(1) looking in currentlySelectedGame,
	 *  	(2) leaves currentlySelectedPlayer as-is if already has that name, or
	 *  	(3) in knownPlayers
	 */
	private void updateCurrentlySelectedPlayer(String playerName) {
		
		// first check in currentlySelectedGame 
		if( currentlySelectedGame != null ) {
			
			PlayerData playerFromGame = currentlySelectedGame.getPlayer(playerName);
			if(playerFromGame != null) {
				currentlySelectedPlayer = playerFromGame;
			}
		}
		
		// if PlayerData was not found in currentlySelectedGame or already set in
		// currentlySelectedPlayer, check knownPlayers
		if( currentlySelectedPlayer == null ) {
			currentlySelectedPlayer = knownPlayers.get(playerName);
		}
	}


	private void addPlayerNameToPlayersChoices(String playerName) {

		if( !playersChoices.getItems().contains(playerName) ) {
			playersChoices.getItems().add(playerName);
		}
		playersChoices.setValue(playerName);
	}

	/*
	 *  NOTE: this also adds the PlayerData into knownPlayers
	 */
	private void addAllPlayerNamesToPlayersChoices(Collection<PlayerData> players) {
		
		for(PlayerData player : players) {
			// NOTE: not calling addPlayerNameToPlayersChoices because 
			//		don't want to change playersChoices.setValue
//			addPlayerNameToPlayersChoices(player.getName());

			knownPlayers.put(player.getName(), player);
			
			if( !playersChoices.getItems().contains( player.getName() ) ) {	
				playersChoices.getItems().add( player.getName() );
			}
		}
	}
	

	private void clearPlayersChoices() {
		
		playersChoices.getItems().clear();
	}
	
	private String onPlayerChoiceChanged(String nameSelected) {

//		String nameSelected = playersChoices.getSelectionModel().getSelectedItem();
		updateCurrentlySelectedPlayer(nameSelected);
		jsonText.setText("PlayerData: " + currentlySelectedPlayer);
		resultsText.setText("");
		
		if( currentlySelectedGame != null  ) {
			
//			int indexSelected = playersChoices.getSelectionModel().getSelectedIndex();
//			if( indexSelected < playerAvatars.size() ) {
				
			Polygon avatar = playerAvatars.get(nameSelected);
			setAvatarToControl( avatar );
			
		}
	
	
//		else  {
//			resultsText.setText(resultsText.getText() + "\nPlayerData not available.");
//			jsonText.setText("");
//
//			resultsText.setFill(Color.RED);
//		}
		
		return nameSelected;
	}
	

	// !!! TODO:	finish IMPLEMENTING THIS!!!	
	
	/* 
	 *  Most of the time we can set currentlySelectedGame = results
	 *  But there's some  ServerCommands like ServerCommands.CREATE_PLAYER
	 */
	private void processResults(ServerCommands command, GameInstance results, String playerName) {
		
		if( command == ServerCommands.CREATE_PLAYER || 
				command == ServerCommands.GET_PLAYER_DATA ||
				command == ServerCommands.LEAVE_GAME) {
			// GameInstance just holds the updated player data
			
			if( results == null ) {
				return;
			}
			currentlySelectedPlayer = results.getPlayer(playerName);
			addPlayerNameToPlayersChoices(playerName);
			knownPlayers.put(playerName, currentlySelectedPlayer);

			// append currentlySelectedPlayer data after the existing resultsText
			resultsText.setText( resultsText.getText() + "\nPlayerData: " + currentlySelectedPlayer);
			
			// if we don't already have a currentlySelectedGame, go ahead and 
			//		use the dummy game in results to store the PlayerData
			if( currentlySelectedGame == null ) {
				currentlySelectedGame = results;
			}
			currentlySelectedGame.addPlayer(currentlySelectedPlayer);
		}
		else if( command == ServerCommands.GET_ALL_PLAYERS_DATA) {
			System.out.println("Main.processResults: ServerCommands.GET_ALL_PLAYERS_DATA");
			
			if( results == null ) {
				resultsText.setText( "No players returned." );
			}
			else {

				addAllPlayerNamesToPlayersChoices( results.getAllPlayers() );
				resultsText.setText( resultsText.getText() + "\nPlayers Data: " + results.getAllPlayers() );
			}
			
		}
		else if( command == ServerCommands.START_GAME) {
			System.out.println("Main.processResults: ServerCommands.START_GAME.  Go Players!");
			
			currentlySelectedGame = results;
			
			// replace playersChoices with returned list of Players
			// Since game has now started playersChoices shouldn't be changed after this,
			// 		except for removing players that leave the game
			clearPlayersChoices();
			addAllPlayerNamesToPlayersChoices( currentlySelectedGame.getAllPlayers() );
			
			initializeMap(currentlySelectedGame.getAllPlayers());
		}
		else { // ServerCommands.CREATE_GAME, ServerCommands.GET_GAME_INFO, ServerCommands.JOIN_GAME, ?ServerCommands.LEAVE_GAME?
			
			System.out.println("WARNING: Main.processResults() some game level ServerCommands are not implimented");
			
			if( results == null ) {
				System.out.println("ERROR: Main.processResults() no game data returned from command: " + command);
					
			}
			else {
				
				currentlySelectedGame = results;
				
				// NOTE: once game has started we shouldn't change names in playersChoices
				if( currentlySelectedGame.getGameState() != GameState.IN_GAME ) {
	
					// ?replace playersChoices with returned list of Players?
	//				clearPlayersChoices();
					
					// or maybe just add new players
					addAllPlayerNamesToPlayersChoices( currentlySelectedGame.getAllPlayers() );
				}
			}
			
		} 	// END processResults()
		
		
	}
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        launch(args);
	}

}
