package admin_tool.transport;

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

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;



public class GameSession {

	private static final String SERVER_BASE_URI = "http://localhost:8080/";
	private static final String SERVER_GAME_URI = SERVER_BASE_URI + "game/";
	private static final String SERVER_PLAYER_URI = SERVER_BASE_URI + "player/";
	
//	private static PlayerSprite userPlayer;
	private static GameInstance currentGameInstance = null;
	private static Collection<GameInstance> availableGameInstances;

	
	public GameInstance createNewGame(PlayerSprite userPlayer) {
		System.out.println("GameSession.createNewGame() called.");


		if(currentGameInstance != null) {
			quitGame();
		}

		if(userPlayer == null) {
			userPlayer = createNewPlayer();
		}
		
		JSONObject json;
		JSONArray jsonAry;
		try {
			json = new JSONObject(userPlayer);
		    System.out.println("sending userPlayer json: " + json.toString());
			jsonAry = postJsonToUrl(json, SERVER_GAME_URI);
		    System.out.println("received game json: " + jsonAry.toString());

		} catch (IOException e) {
			System.out.println("GameSession.getGameData() caught IOException = " + e);
			System.out.println("Check if server is running and that SERVER_BASE_URI is set correct." );
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		return currentGameInstance;
	}
	

	public GameInstance getGameData() {
		System.out.println("GameSession.getGameData() called.");
		
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
		    
		
		return currentGameInstance;
	}
	public boolean joinGame(PlayerSprite userPlayer) {

		if(userPlayer == null) {
			userPlayer = createNewPlayer();
		}
		
		return false;
	}
	
	public boolean quitGame() {

		currentGameInstance = null;
		return false;
	}

	
	// This only instantiates a PlayerSprite
	// To register the user(PlayerSprite) with the server, we need to call 
	// 		createGame() or joinGame() (and pass the PlayerSprite to the Game) 
	// 		
	private PlayerSprite createNewPlayer() {

		// TODO: should give more details to the player
		PlayerSprite userPlayer = new PlayerSprite();
		userPlayer.setName("Player" + userPlayer.getPublicID());
		return userPlayer;
	}

	private PlayerSprite createNewPlayer(String playerName) {

		// TODO: should give more details to the player
		return new PlayerSprite(playerName);
	}
	
	
	
	//TODO: implement!
	public boolean updatePlayerData(PlayerSprite userPlayer) {

		
		return true;
	}


	public boolean getPlayerData(int ID) {
		System.out.println("GameSession.getPlayerData() called.");

		JSONObject json;
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
		    
		
		return false;
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
	public static JSONArray postJsonToUrl(JSONObject json, String urlString) 
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
	      String jsonText = readAll(is);
	      JSONArray jsonAry = new JSONArray(jsonText);
	      return jsonAry;
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
