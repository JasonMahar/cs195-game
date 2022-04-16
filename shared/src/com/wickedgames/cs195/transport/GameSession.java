package com.wickedgames.cs195.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.Charset;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wickedgames.cs195.model.PlayerData;
import com.wickedgames.cs195.model.CS195PlayerData;
import com.wickedgames.cs195.model.Projectile;
import com.wickedgames.cs195.model.PlayerData.Facing;
import com.wickedgames.cs195.model.PlayerData.State;
import com.wickedgames.cs195.model.CS195Projectile;
import com.wickedgames.cs195.model.GameInstance;


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
		    

			if( returnedJson != null && !returnedJson.isEmpty() ) {

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
		    System.out.println(json.toString());
//		    System.out.println(json.get("id"));

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
			json = readJsonFromUrl(SERVER_GAME_URI + "/" + gameID);
		    System.out.println(json.toString());
//		    System.out.println(json.get("id"));

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



	//TODO: implement!
	
	@Override
	public GameInstance joinGame(PlayerData userPlayer) {

		
		if(userPlayer == null) {
			return null;		// client needs to create a PlayerData, even if it's empty. 
								// 		Server will fill in most of data besides name.
		}
		

		//PutMapping("/game/{gameID}/{playerID}")
		
		return null;
	}
	

	//TODO: implement!
	
	@Override
	public boolean quitGame(PlayerData userPlayer) {

		return false;
	}

	

	//TODO: implement!
	
	@Override
	public GameInstance updatePlayerData(PlayerData userPlayer) {

		
		return null;
	}



	//TODO: implement!
	@Override
	public Collection<PlayerData> getAllPlayersData() {

		return null;
	}
	
	
	@Override
	public PlayerData getPlayerData(int ID) {
		System.out.println("GameSession.getPlayerData() called.");

		JSONObject json = null;
		JSONArray jsonAry;
		try {
			// if ID = 0, leave the value blank in order to return all players' data
			if( ID != 0 ) {
				json = readJsonFromUrl(SERVER_PLAYER_URI + "/" + ID);
			    System.out.println(json.toString());
			}
			else {
				jsonAry = readJsonArrayFromUrl(SERVER_PLAYER_URI);
			    System.out.println(jsonAry.toString());
			}
//		    System.out.println(json.get("id"));
		    
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getPlayerData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		
		return createCS195PlayerDataFromJSON( json );
	}
	

	// Creating new PlayerData
	//
	// This is a convenience method for creating a basic PlayerData object 
	// 		with only the playerName, so that it can be passed into 
	//		createNewGame() or joinGame()
	public PlayerData createNewPlayer(String playerName) {

		if( playerName == null || playerName.isBlank() ) {
			return null;
		}
		// TODO: should give more details to the player
		return new CS195PlayerData(playerName);
	}
	
	
	
	
	//////////////////////////////////////////////////
	// Conversion from JSON to Objects
	

	// TODO: GSON or jackson may give easier ways to do this, but since there's
	// 		only 3 classes and not that many attributes, it's quicker to just
	//		do this manually.

	private static GameInstance createGameInstanceFromJSON( JSONObject json ) {
		return null;
	}
	

	private static PlayerData createCS195PlayerDataFromJSON( JSONObject json ) {
		PlayerData player = new CS195PlayerData(
				json.getInt("publicID"), 
				json.getInt("privateID"), 
				json.getString("name"), 
				(State)json.get("state"), 
				json.getFloat("xPosition"),
				json.getFloat("yPosition"),
				(Facing)json.get("facing"), 
				json.getFloat("speed"),
				(Projectile[])json.get("projectiles")
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

	
	// REST POST a JSONObject to the urlString and return results in a JSONArray
	public static JSONObject postJsonToUrl(JSONObject json, String urlString) 
			throws IOException, JSONException, MalformedURLException {
		

        URL url = new URL(urlString);
        HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
        httpcon.setDoOutput(true);
        httpcon.setRequestMethod("POST");
//        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestProperty("Content-Type", "application/json");
        
        
        OutputStreamWriter output=new OutputStreamWriter(httpcon.getOutputStream());
        System.out.println(json);
        output.write(json.toString());
        httpcon.connect();
		
	    InputStream is = httpcon.getInputStream();
	    try {
//		      String jsonText = readAll(is);
//		      JSONArray jsonAry = new JSONArray(jsonText);
//		      return jsonAry;
		      String jsonText = readAll(is);
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
