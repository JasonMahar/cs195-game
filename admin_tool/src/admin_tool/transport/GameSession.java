package admin_tool.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

import com.wickedgames.cs195.model.GameInstance;
import com.wickedgames.cs195.model.PlayerSprite;



public class GameSession {

	private static PlayerSprite userPlayer;
	private static GameInstance currentGameInstance;
	private static Collection<GameInstance> availableGameInstances;

	
	public boolean createNewGame() {

		
		return false;
	}
	
	public boolean joinGame() {
		
		return false;
	}
	
	public boolean quitGame() {

		
		return false;
	}

	public boolean updatePlayerData(PlayerSprite userPlayer) {

		this.userPlayer = userPlayer;
		
		return true;
	}

	public boolean getGameData() {
		System.out.println("GameSession.getGameData() called.");
		
		JSONArray json;
		try {
			json = readJsonArrayFromUrl("http://localhost:8080/game");
		    System.out.println(json.toString());
//		    System.out.println(json.get("id"));
		    
		} catch (JSONException | IOException e) {
			System.out.println("GameSession.getGameData() caught JSONException = " + e);
			e.printStackTrace();
		}
		    
		    
//		try {
//			URL url = new URL("https://localhost:8080/game");
//			BufferedReader in = new BufferedReader( 
//				new InputStreamReader(url.openStream()) 
//			);
//	        text = in.readLine();
//			in.close();
//				
//		} catch (IOException e) {
//			e.printStackTrace();
//	    	System.out.println( "IOException while reading text from " +
//	    						"forismatic.com. \ntext = " + text );
//			text = "Sorry. Error retrieving quote.";
//		}

		return false;
	}
	
	
	// these next 2 methods were copied from a Stack Overflow answer
	// https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	// author: Roland Illig
	//
	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
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
	

	  public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
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
	
	
}
