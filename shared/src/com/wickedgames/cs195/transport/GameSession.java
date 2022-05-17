package com.wickedgames.cs195.transport;

/**
 * @author Jason Mahar
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wickedgames.cs195.model.*;
import com.wickedgames.cs195.model.GameInstance.*;
import com.wickedgames.cs195.model.PlayerData.*;


// NOTE: Should this class just be only static methods since it is stateless?

/*  GameSession
 * 
 *  GameSession is stateless besides storing a GameID.
 *  	Every call requires all data necessary to make the request to be
 *  	passed in via args, and returns all info received to be 
 *  	stored elsewhere
 */
public class GameSession implements GameSessionInterface {

// HACK: using package access for the admin tool to see the raw JSON string received
	private static String lastJSONReceived = "";
	
	/**
	 * @return the lastJSONReceived
	 */
	static String getLastJSONReceived() {
		return lastJSONReceived;
	}

	/**
	 * @param lastJSONReceived the lastJSONReceived to set
	 */
	static void setLastJSONReceived(String lastJSONReceived) {
		GameSession.lastJSONReceived = lastJSONReceived;
	}


	private static final String SERVER_BASE_URI = "http://localhost:8080/";
	private static final String SERVER_GAME_URI = SERVER_BASE_URI + "game/";
	private static final String SERVER_PLAYER_URI = SERVER_BASE_URI + "player/";

	
// TODO: this next should probably get moved to GameSessionInterface
//		Would it be better for GameSessionInterface to know 
//		about all implementations than GameSession?
	
	// only way for clients to get a GameSessionInterface is to call this 
	//		class method: GameSession.getGameSession()
	public static GameSessionInterface getGameSession() {
		
		if( GameDesignVars.USE_STUB_IN_PLACE_OF_SERVER ) {
			return new STUB_GameSession();
		}
		return new GameSession();
	}
	
	// constructor is hidden.
	// use GameSession.getGameSession() instead
	private GameSession() {}
	
	@Override
	public GameInstance createNewGame(PlayerData userPlayer) {
		System.out.println("GameSession.createNewGame() called.");


		if(userPlayer == null) {
			return null;		// client needs to create a PlayerData, even if it's empty. 
								// 		Server will fill in most of data besides name.
		}

		JSONObject json;
		JSONObject returnedJson;
		try {
			json = new JSONObject(userPlayer);
		    System.out.println("sending userPlayer json: " + json.toString());
		    returnedJson = postJsonToUrl(json, SERVER_GAME_URI);
		    
			if( returnedJson == null /* || returnedJson.isEmpty() */) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println( "received game json: " + returnedJson.toString() );
			    setLastJSONReceived( returnedJson.toString() );
			    return createGameInstanceFromJSON( returnedJson );
			}
		    

		} catch (IOException e) {
			System.out.println("GameSession.createNewGame() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.createNewGame() caught JSONException = " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	@Override
	public Collection<GameInstance> getAllGames() {
		System.out.println("GameSession.getAllGames() called.");
		
		JSONArray returnedJson;
		try {
			returnedJson = readJsonArrayFromUrl(SERVER_GAME_URI);
			
			if( returnedJson == null /* || returnedJson.isEmpty() */ ) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println("received game json: " + returnedJson.toString());
			    setLastJSONReceived( returnedJson.toString() );
			    
			    Collection<GameInstance> games = new ArrayList<GameInstance>();
			    for( int i = 0; i < returnedJson.length(); ++i ) {
			    	JSONObject json = returnedJson.getJSONObject(i);
			    	games.add(createGameInstanceFromJSON( json ));
			    }
			    
			    return games;
			}

		} catch (IOException e) {
			System.out.println("GameSession.getAllGames() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getAllGames() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		
		return null;
	}

	
	@Override
	public GameInstance getGameData(Integer gameID) {
		System.out.println("GameSession.getGameData() called with gameID: " + gameID);
		
		JSONObject returnedJson = null;
		try {
			returnedJson = readJsonFromUrl(SERVER_GAME_URI + gameID);
			
			if( returnedJson == null  /* || returnedJson.isEmpty() */ ) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println("received game json: " + returnedJson.toString());
			    setLastJSONReceived( returnedJson.toString() );
			}

		} catch (IOException e) {
			System.out.println("GameSession.getGameData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		
		return createGameInstanceFromJSON( returnedJson );
	}

	@Override
	public GameInstance startGame(Integer gameID) {
		return updateGameState(gameID, GameState.IN_GAME);
	}

	
	public GameInstance updateGameState(Integer gameID, GameState state) {
	
		System.out.println("GameSession.updateGameState() called with gameID: " + gameID);

		JSONObject json;
		JSONObject returnedJson;
		try {

//// HACK: since server is not parsing the json I'm sending, 
////		I'm just going to pass the state in as a param
//		    String uri = SERVER_GAME_URI + gameID + "?state="+state.name();
//		    String uri = SERVER_GAME_URI + gameID;
			String uri = SERVER_BASE_URI + "startgame/";
		    
			JSONObject jsonSend = new JSONObject();
			jsonSend.put("state", state.ordinal());		// put makes the value an Object (which the server casts to String)
//		    System.out.println("sending updateGameState json: " + jsonSend.toString());
		    System.out.println("sending updateGameState json: " + jsonSend);
		    System.out.println("\t to URI: " + uri);

		    returnedJson = putJsonToUrl(jsonSend, uri);
		    
			if( returnedJson == null /* || returnedJson.isEmpty() */ ) {
				
				System.out.println("ERROR: received game json = null ");
			}
			else {
				System.out.println("received game json: " + returnedJson);
			    setLastJSONReceived( returnedJson.toString() );
			    return createGameInstanceFromJSON( returnedJson );
			}
		    

		} catch (IOException e) {
			System.out.println("GameSession.updateGameState() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.updateGameState() caught JSONException = " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	


	// client needs to createPlayer() to get a PlayerData object first
	//		Server will add/change data as needed.
	// 
	@Override
	public GameInstance joinGame(Integer gameID, PlayerData userPlayer) {
		System.out.println("GameSession.joinGame() called.");

		if(userPlayer == null) {
// TODO: throw an error
			return null;			
		}

		JSONObject json;
		JSONObject returnedJson;
		try {
			
			json = new JSONObject(userPlayer);
		    String uri = SERVER_GAME_URI + gameID;
		    System.out.println("sending userPlayer json: " + json.toString());
		    		    System.out.println("\t to URI: " + uri);
		    
		    returnedJson = postJsonToUrl(json, uri);
		    
		    
			if( returnedJson == null /* || returnedJson.isEmpty() */ ) {
				
				System.out.println("ERROR: received game json = null ");
			}
			else {
				System.out.println("received game json: " + returnedJson);
			    setLastJSONReceived( returnedJson.toString() );
			    return createGameInstanceFromJSON( returnedJson );
			}
		    

		} catch (IOException e) {
			System.out.println("GameSession.joinGame() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.joinGame() caught JSONException = " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	

	//TODO: implement!
	// probably return the updated PlayerData
	@Override
	public boolean quitGame(PlayerData userPlayer) {

		return false;
	}

	
	@Override
	public GameInstance updatePlayerData(Integer gameID, PlayerData userPlayer) {

//		System.out.println("\nGameSession.updatePlayerData() called with gameID: " + gameID);

		if(userPlayer == null) {
// TODO: throw an error
			return null;			
		}

		JSONObject json;
		JSONObject returnedJson;
		try {
			
			json = new JSONObject(userPlayer);
//		    System.out.println("sending userPlayer json: " + json.toString());
//		    System.out.println("\t to URI: " + SERVER_PLAYER_URI + gameID);
		    returnedJson = putJsonToUrl(json, SERVER_PLAYER_URI + gameID);
		    
		    
			if( returnedJson == null /* || returnedJson.isEmpty() */ ) {
				
				System.out.println("ERROR: received game json = null ");
			}
			else {
//				System.out.println("received game json: " + returnedJson);
			    setLastJSONReceived( returnedJson.toString() );
			    return createGameInstanceFromJSON( returnedJson );
			}
		    

		} catch (IOException e) {
			System.out.println("GameSession.updatePlayerData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.updatePlayerData() caught JSONException = " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	


	@Override
	public HashMap<Integer, PlayerData> getAllPlayersData() {
		System.out.println("GameSession.getAllPlayersData() called.");

		HashMap<Integer, PlayerData> playersMap = new HashMap<>();
		
		try {
			JSONArray jsonAry = readJsonArrayFromUrl(SERVER_PLAYER_URI);
		    System.out.println(jsonAry.toString());
		    setLastJSONReceived( jsonAry.toString() );

		    for( int i=0; i < jsonAry.length(); ++i ) {
		    	JSONObject json = jsonAry.getJSONObject(i);
		    	CS195PlayerData newPlayer = createCS195PlayerDataFromJSON( json );
//			    System.out.println(json.get("id"));
		    	playersMap.put(newPlayer.getPublicID(), newPlayer);
		    }
		    	 
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getPlayerData() caught Exception = " + e);
			e.printStackTrace();
		}

		return playersMap;
	}
	
	
	@Override
	public CS195PlayerData getPlayerData(int ID) {
		System.out.println("GameSession.getPlayerData() called.");

		try {
			// if ID = 0, leave the value blank in order to return all players' data
			if( ID != 0 ) {

				JSONObject returnedJson = readJsonFromUrl(SERVER_PLAYER_URI  + ID);
			    System.out.println(returnedJson.toString());
			    setLastJSONReceived( returnedJson.toString() );
				return createCS195PlayerDataFromJSON( returnedJson );
			}
			else {
//				JSONArray jsonAry;
//				jsonAry = readJsonArrayFromUrl(SERVER_PLAYER_URI);
//			    System.out.println(jsonAry.toString());
				
// TODO: this should throw an error
			}
		    
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getPlayerData() caught JSONException = " + e);
			e.printStackTrace();
		}

		return null;
	}
	

	// Creating new PlayerData
	//
	// This is a convenience method for creating a basic PlayerData object 
	// 		with only the playerName, so that it can be passed into 
	//		createNewGame() or joinGame()
	public PlayerData createNewPlayer(String playerName) {

		if( playerName == null || playerName.isBlank() ) {
			// thrown an error?
			return null;
		}
		// TODO: should give more details to the player
		

		try {

			JSONObject jsonSend = new JSONObject();
			jsonSend.put("name", playerName);		// put makes the value an Object (which the server casts to String)

			// HACK: instead of making server parse the json, 
			//		I'm just going to pass the name in as a param
		    String URI = SERVER_PLAYER_URI+ "?name=" + playerName;
		    System.out.println("sending createNewPlayer json: " + jsonSend);
		    System.out.println("\t to URI: " + URI);
		    JSONObject returnedJson = postJsonToUrl(jsonSend, URI );
		    System.out.println("received PlayerData json: " + returnedJson.toString());

			PlayerData newPlayer = createCS195PlayerDataFromJSON(returnedJson);
		    setLastJSONReceived( returnedJson.toString() );
//			newPlayer.setName(playerName);
			
			return newPlayer;
		    
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getPlayerData() caught JSONException = " + e);
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	//////////////////////////////////////////////////
	// Conversion from JSON to Objects
	

	// TODO: GSON or jackson may give easier ways to do this, but since there's
	// 		only 3 classes and not that many attributes, it's quicker to just
	//		do this manually.

	private static GameInstance createGameInstanceFromJSON( JSONObject json ) {
		

		GameState state = GameState.valueOf((String)json.get("gameState"));
		Integer id = json.getInt("id");
//		JSONObject playersJSON = (JSONObject)json.get("players");
//		Map<String, Object> playersMap = playersJSON.toMap();
		JSONArray playersJSON = (JSONArray)json.get("allPlayers");
//		List<Object> playersMap = playersJSON.toList();
		
		// parse players into the expected HashMap<Integer, PlayerData>
		HashMap<Integer, PlayerData> playersHashMap = new HashMap<Integer, PlayerData>();
		for (int i=0; i < playersJSON.length(); ++i) {
			JSONObject value = playersJSON.getJSONObject(i);
			CS195PlayerData nextPlayer = createCS195PlayerDataFromJSON( value );
			playersHashMap.put(nextPlayer.getPublicID(), nextPlayer);
		}	
		
		return new GameInstance( state, id, playersHashMap );
	}
	

	private static CS195PlayerData createCS195PlayerDataFromJSON( HashMap<String, Object> map ) {

		float x = ((BigDecimal)map.get("x")).floatValue();
		float y = ((BigDecimal)map.get("y")).floatValue();
		float speed = ((BigDecimal)map.get("speed")).floatValue();

//		public CS195PlayerData(int publicID, /* int privateID, */ String name, State state, float x,
//				float y, Facing facing, float speed /*, int livesRemaining, Projectile[] projectiles */ )
		CS195PlayerData player = new CS195PlayerData(
				(int)map.get("publicID"),
				/* (int)map.get("privateID"), */
				(String)map.get("name"), 
				State.valueOf( map.get("state").toString() ),
				x,
				y,
				Facing.valueOf( map.get("facing").toString() ), 
				speed /*,
				(Projectile[])json.get("projectiles") */
			);
		return player;
	}
	
	private static CS195PlayerData createCS195PlayerDataFromJSON( JSONObject json ) {
		
		CS195PlayerData player = new CS195PlayerData(
				json.getInt("publicID"), 
				/* (int)map.get("privateID"), */
				json.getString("name"), 
				State.valueOf( json.getString("state") ),
				json.getFloat("x"),
				json.getFloat("y"),
				Facing.valueOf( json.getString("facing") ), 
				json.getFloat("speed") /*,
				(Projectile[])json.get("projectiles") */
				);
			return player;
		}
	

	private static Projectile createCS195ProjectileFromJSON( JSONObject json ) {

		Projectile projectile = new CS195Projectile(
				json.getInt("ID"), 
				json.getFloat("xPosition"),
				json.getFloat("yPosition"),
				(Facing)json.get("facing"), 
				json.getFloat("speed")
			);
		return projectile;
	}
	
	
	//////////////////////////////////////////////////
	// REST Calls: GET, POST, PUT, and DELETE

	
	
	// these next 2 methods were originally copied from a Stack Overflow answer:
	// https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	// author: Roland Illig
	//
	// modified by: Jason Mahar
	//
    private static String readAll(InputStream is) throws IOException {

    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	    	sb.append((char) cp);
	    }
	    return sb.toString();
	}

	  
	// REST GET a JSONObject from the url String
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	    	String jsonText = readAll(is);
	    	JSONObject json = new JSONObject(jsonText);
	    	return json;
	    }
//	    catch( Exception ex) {
//
//			System.out.println("GameSession.readJsonFromUrl() caught JSONException = " + ex);
//	    	ex.printStackTrace();
//	    }
	    finally {
	    	is.close();
	    }
	    
//	    return null;
	}
	


	// REST GET a JSONArray of objects from the url String
	public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	    	String jsonText = readAll(is);
	    	JSONArray json = new JSONArray(jsonText);
	    	return json;
	    }
//	    catch( Exception ex) {
//
//			System.out.println("GameSession.readJsonFromUrl() caught JSONException = " + ex);
//	    	ex.printStackTrace();
//	    }
	    finally {
	    	is.close();
	    }
	    
	}

	
	// REST POST a JSONObject to the urlString and return results in a JSONObject
	public static JSONObject postJsonToUrl(JSONObject json, String urlString) 
			throws IOException, JSONException, MalformedURLException {
		

        URL url = new URL(urlString);
        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
        httpcon.setRequestMethod("POST");
        httpcon.setRequestProperty("Content-Type", "application/json; utf-8");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setDoOutput(true);
        
// NOTE: server is saying "Required request body is missing"
//		so changing from OutputStreamWriter to OutputStream
        /*       
               OutputStreamWriter output=new OutputStreamWriter(httpcon.getOutputStream());
               System.out.println("json:            " + json);
               System.out.println("json.toString(): " + json.toString());
               output.write(json.toString());
               httpcon.connect();
       	*/	
        try( OutputStream os = httpcon.getOutputStream() ) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        
        
	    InputStream is = httpcon.getInputStream();
	    try {
//		      String jsonText = readAll(is);
//		      JSONArray jsonAry = new JSONArray(jsonText);
//		      return jsonAry;
		      String jsonText = readAll(is);
		      if( jsonText.isEmpty() )	{	return null;	}
		      JSONObject returnedJson = new JSONObject(jsonText);
		      return returnedJson;
	    }
//	    catch( Exception ex) {
//
//			System.out.println("GameSession.readJsonFromUrl() caught JSONException = " + ex);
//	    	ex.printStackTrace();
//	    }
	    finally {
	      is.close();
	    }

	  }

	
	// REST PUT a JSONObject to the urlString and return results in a JSONObject
	public static JSONObject putJsonToUrl(JSONObject json, String urlString) 
			throws IOException, JSONException, MalformedURLException {
		
		if( json == null || urlString == null || urlString.isEmpty() ) {

		    System.out.println("ERROR: GameSession.putJsonToUrl(): json or url is null ");
		}
		
        URL url = new URL(urlString);
        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
        httpcon.setRequestMethod("PUT");
        httpcon.setRequestProperty("Content-Type", "application/json; utf-8");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setDoOutput(true);
        
// NOTE: server is saying "Required request body is missing"
//		so changing from OutputStreamWriter to OutputStream
        /*       
               OutputStreamWriter output=new OutputStreamWriter(httpcon.getOutputStream());
               System.out.println("json:            " + json);
               System.out.println("json.toString(): " + json.toString());
               output.write(json.toString());
               httpcon.connect();
       	*/	
        try( OutputStream os = httpcon.getOutputStream() ) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        
        
	    InputStream is = httpcon.getInputStream();
	    try {
//		      String jsonText = readAll(is);
//		      JSONArray jsonAry = new JSONArray(jsonText);
//		      return jsonAry;
		      String jsonText = readAll(is);
		      if( jsonText.isEmpty() )	{	return null;	}
		      JSONObject returnedJson = new JSONObject(jsonText);
		      return returnedJson;
	    }
//	    catch( Exception ex) {
//
//			System.out.println("GameSession.readJsonFromUrl() caught JSONException = " + ex);
//	    	ex.printStackTrace();
//	    }
	    finally {
	      is.close();
	    }

	  }




}
