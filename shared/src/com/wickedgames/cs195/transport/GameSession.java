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

	private static final String SERVER_BASE_URI = "http://localhost:8080/";
	private static final String SERVER_GAME_URI = SERVER_BASE_URI + "game/";
	private static final String SERVER_PLAYER_URI = SERVER_BASE_URI + "player/";


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
		    System.out.println("received game json: " + returnedJson.toString());
		    

			if( returnedJson == null || returnedJson.isEmpty() ) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println("received game json: " + returnedJson.toString());

			    return createGameInstanceFromJSON( returnedJson );
			}
		    

		} catch (IOException e) {
			System.out.println("GameSession.getGameData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	@Override
	public Collection<GameInstance> getAllGames() {
		System.out.println("GameSession.getAllGames() called.");
		
		JSONArray json;
		try {
			json = readJsonArrayFromUrl(SERVER_GAME_URI);
			
			if( json == null || json.isEmpty() ) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println("received game json: " + json.toString());
			}

		} catch (IOException e) {
			System.out.println("GameSession.getGameData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		
		return null;
	}

	
	@Override
	public GameInstance getGameData(Integer gameID) {
		System.out.println("GameSession.getGameData() called with gameID: " + gameID);
		
		JSONObject json = null;
		try {
			json = readJsonFromUrl(SERVER_GAME_URI + gameID);
			
			if( json == null || json.isEmpty() ) {
			    System.out.println("ERROR: received game json is null ");
			}
			else {
			    System.out.println("received game json: " + json.toString());
			}

		} catch (IOException e) {
			System.out.println("GameSession.getGameData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		
		return createGameInstanceFromJSON( json );
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
			// HACK: instead of making server parse the json, 
			//		I'm just going to pass the name in as a param
			json = new JSONObject(userPlayer);
		    System.out.println("sending userPlayer json: " + json.toString());
		    returnedJson = postJsonToUrl(json, SERVER_GAME_URI + gameID);
		    
		    
			if( returnedJson == null || returnedJson.isEmpty() ) {
				
				System.out.println("ERROR: received game json = null ");
			}
			else {
				System.out.println("received game json: " + returnedJson);
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
	
	@Override
	public boolean quitGame(PlayerData userPlayer) {

		return false;
	}

	

	//TODO: implement!
	
	@Override
	public GameInstance updatePlayerData(Integer gameID, PlayerData userPlayer) {

//		System.out.println("GameSession.updatePlayerData() called with gameID: " + gameID);

		if(userPlayer == null) {
// TODO: throw an error
			return null;			
		}

		JSONObject json;
		JSONObject returnedJson;
		try {
			// HACK: instead of making server parse the json, 
			//		I'm just going to pass the name in as a param
			json = new JSONObject(userPlayer);
		    System.out.println("sending userPlayer json: " + json.toString());
		    System.out.println("\t to URI: " + SERVER_PLAYER_URI + gameID);
		    returnedJson = putJsonToUrl(json, SERVER_PLAYER_URI + gameID);
		    
		    
			if( returnedJson == null || returnedJson.isEmpty() ) {
				
				System.out.println("ERROR: received game json = null ");
			}
			else {
				System.out.println("received game json: " + returnedJson);
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
	@Override
	public Collection<PlayerData> getAllPlayersData() {
		System.out.println("GameSession.getAllPlayersData() called.");

		ArrayList<PlayerData> playersArray = new ArrayList<PlayerData>();
		
		try {
			JSONArray jsonAry = readJsonArrayFromUrl(SERVER_PLAYER_URI);
		    System.out.println(jsonAry.toString());

		    for( int i=0; i < jsonAry.length(); ++i ) {
		    	JSONObject json = jsonAry.getJSONObject(i);
		    	CS195PlayerData newPlayer = createCS195PlayerDataFromJSON( json );
//			    System.out.println(json.get("id"));
		    	playersArray.add(newPlayer);
		    }
		    	 
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getPlayerData() caught Exception = " + e);
			e.printStackTrace();
		}

		return playersArray;
	}
	
	
	@Override
	public CS195PlayerData getPlayerData(int ID) {
		System.out.println("GameSession.getPlayerData() called.");

		try {
			// if ID = 0, leave the value blank in order to return all players' data
			if( ID != 0 ) {

				JSONObject json = readJsonFromUrl(SERVER_PLAYER_URI + "/" + ID);
			    System.out.println(json.toString());
				return createCS195PlayerDataFromJSON( json );
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
//			jsonSend.append("name", playerName);	// append makes the value a JSON Array
			jsonSend.put("name", playerName);		// put makes the value an Object (which the server casts to String)
		    System.out.println("sending json: " + jsonSend.toString());
		    
		    JSONObject jsonRcv = postJsonToUrl(jsonSend, SERVER_PLAYER_URI+ "?name=" + playerName);
		    System.out.println("received PlayerData json: " + jsonRcv.toString());

			PlayerData newPlayer = createCS195PlayerDataFromJSON(jsonRcv);
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
		JSONObject playersJSON = (JSONObject)json.get("players");
		Map<String, Object> playersMap = playersJSON.toMap();
		
		// parse players into the expected HashMap<Integer, PlayerData>
		HashMap<Integer, PlayerData> playersHashMap = new HashMap<Integer, PlayerData>();
		for (Map.Entry<String, Object> entry : playersMap.entrySet()) {
			
			Integer key = Integer.parseInt(entry.getKey());
			HashMap<String, Object> value = (HashMap<String, Object>)entry.getValue();
			CS195PlayerData nextPlayer = createCS195PlayerDataFromJSON( value );
			playersHashMap.put(key, nextPlayer);
		}

		
//		Iterator<?> keys = playersJSON.keys();
//		.entrySet()
//        while( keys.hasNext() ){
//            String key = (String)keys.next();
//            String value = jObject.getString(key); 
//            map.put(key, value);
//
//        }
				
		
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
