package com.wickedgames.cs195.model;

/*  Class GameVisualization2DSpriteBased
 * 
 *  
 * 
 *  @author Jason Mahar
 *  9/21/21
 *  
 *  CS 211S-Adv. Java: Standard Edition-931
 */


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;		// For Testing



/**
 *  @author Jason
 */
public class GameVisualization2DSpriteBased extends GameVisualization {


	//////////////////////////////////////////
	//
	// Class (static) functions:
	//
	

	//////////////////////////////////////////
	//
	// Class constants:
	//
	
	/**
	 *  required by inherited Interface Serializable
	 */
	private static final long serialVersionUID = -2160931247218381395L;
	

	//////////////////////////////////////////
	//
	// Private Instance Attributes:
	//
	
	// these are deprecated, since we'll be moving to GameLevel static Sprites
	// and then DyanmicSprites (later to be moved into just PlayerHandler and 
	// EnemiesHandler
	// 
	private ArrayList<Sprite> backgroundSprites = new ArrayList<Sprite>();
	private ArrayList<Sprite> mainLayerSprites = new ArrayList<Sprite>();
	private ArrayList<Sprite> foregroundSprites = new ArrayList<Sprite>();

	// New way of handling Sprites
	PlayerSprite player;
	private ArrayList<Sprite> dyanmicSprites;		// This should be replaced by an Enemies Handler Class
	
	GameLevelLoader levelLoader;
	GameLevel currentLevel;


	//////////////////////////////////////////
	//
	// Public Methods:
	//
	
	/**
	 * 
	 */
	protected GameVisualization2DSpriteBased() {
		super();
		System.out.println("GameVisualization2DSpriteBased() constructor. ");
		this.setAssetManger( AssetManager.getAssetManager() );
		dyanmicSprites = new ArrayList<Sprite>();
		
// DEBUG:
//if( mainLayerSprites == null ) {
//	System.out.println("\n !!! ERROR: GameVisualization2DSpriteBased() constructor: mainLayerSprites == null  ");			
//}
//else {
//	System.out.println("\n ! SUCCESS: GameVisualization2DSpriteBased() constructor: mainLayerSprites == null  ");			
//
//}
	
	}

	/**
	 * @param config
	 */
	protected GameVisualization2DSpriteBased(GraphicsConfiguration config) {
		super(config);
		System.out.println("GameVisualization2DSpriteBased(GraphicsConfiguration) constructed. ");

		this.setAssetManger( AssetManager.getAssetManager() );
		dyanmicSprites = new ArrayList<Sprite>();
		
	}

	@Override
	public void draw() {
		System.out.println("\nGameVisualization2DSpriteBased.draw() called ");

		
		// TODO: these will be deprecated in favor of (GameLevel) currentLevel.draw()
		//		and a soon to be created PlayerHandler.draw()  and EnemiesHandler.draw()
		for( Sprite s : getBackgroundSprites()) { s.draw(); }
		for( Sprite s : getMainLayerSprites()) { s.draw(); }
		for( Sprite s : getForegroundSprites()) { s.draw(); }

		currentLevel.draw();
		for( Sprite s : dyanmicSprites) { s.draw(); }
		
	}
	
	@Override
	public void start() {
		System.out.println("GameVisualization2DSpriteBased.start() called ");
		initialize();
		draw();
	}


	//////////////////////////////////////////
	//
	// Protected Methods:
	//

	protected void initCanvasSettings() {
		System.out.println("GameVisualization2DSpriteBased.initCanvasSettings() called ");

		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.BLUE);
		
		Font font = new Font(Font.SANS_SERIF, Font.BOLD+Font.ITALIC, 20);
		this.setFont(font);

		this.setBounds(200,100, 600, 300);
		
		// images of Java cursors at https://www.e-reading-lib.com/htmbook.php/orelly/java-ent/jfc/figs/jfcn.0303.gif
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		

		// not sure if these are needed or already default to true;
//		this.setEnabled(true);		// defaults to TRUE
//		this.setVisible(true);		// defaults to TRUE
//		System.out.println( "GameVisualization2DSpriteBased isEnabled = " + this.isEnabled());
//		System.out.println( "GameVisualization2DSpriteBased isVisible = " + this.isVisible());
//		System.out.println( "GameVisualization2DSpriteBased isShowing = " + this.isShowing());		// currently FALSE
		
//		System.out.println( "GameVisualization2DSpriteBased isFocusOwner = " + this.isFocusOwner());	// currently FALSE
//		this.setFocusable(true);
//		this.requestFocus();
	}
	

	
//	@Override
	protected void initialize() {
		super.initialize();
		System.out.println("GameVisualization2DSpriteBased.initialize() called ");
		
		initCanvasSettings();
		
		// This method is being replaced 
		//
		// @TESTING: we actually want to call some map class
		//TESTING_createSampleSprites();

		AssetManager assetManager = this.getAssetManger(); 
		assetManager.loadAllAssetTypes();
		
		levelLoader = new GameLevelLoader();
		currentLevel = levelLoader.createSampleLevel();


		PlayerSprite newPlayer = new PlayerSprite();
		this.setPlayer(newPlayer);
//		this.addMainLayerSprite(newPlayer);					// only 1 PlayerSprite
		assetManager.getAssetImageIcon( newPlayer.getType() );

		dyanmicSprites.add(newPlayer);


		// Monsters should actually get instantiated by a Enemies Spawn Handler class not yet made
		//
		
		FlyingMonster newFMonster;
		for(int i=1; i < 4; ++i) {	// 3 FLYING_MONSTER
	// M3 USING STRATEGY
			newFMonster = new FlyingMonster(AssetType.FLYING_MONSTER.getDefaultName()+i, new FlyingAttacker());
//			this.addMainLayerSprite(newFMonster);
			dyanmicSprites.add(newFMonster);
			assetManager.getAssetImageIcon( newFMonster.getType() );
		}
		
		GreenMonster newGMonster;
		for(int i=1; i < 3; ++i) {	// 2 GREEN_MONSTER
	// M3 USING STRATEGY
			newGMonster = new GreenMonster(AssetType.GREEN_MONSTER.getDefaultName()+i, new GroundAttacker());
//			this.addMainLayerSprite(newGMonster);
			dyanmicSprites.add(newGMonster);
			assetManager.getAssetImageIcon( newGMonster.getType() );
		}
		

		
		// This stuff should be moved to an Input Handler class
		//
//		this.addKeyListener(KeyListener);
//		this.addMouseMotionListener(MouseMotionListener);
//		this.addMouseListener(MouseListener);
	}
	

	//////////////////////////////////////////
	//
	// Private Methods:
	//


	/**
	 * @param Sprite to add to backgroundSprites 
	 */
	private void addBackgroundSprite(Sprite sprite) {
		System.out.println( "GameVisualization2DSpriteBased.addBackgroundSprite()" +
				"called. Sprite = " + sprite.getName());
		backgroundSprites.add(sprite);
	}

	/**
	 * @param Sprite to add to mainLayerSprites 
	 */
	private void addMainLayerSprite(Sprite sprite) {
		System.out.println( "GameVisualization2DSpriteBased.addMainLayerSprite()" +
				"called. Sprite = " + sprite.getName());
		mainLayerSprites.add(sprite);
	}
	

	/**
	 * @param Sprite to add to foregroundSprites 
	 */
	private void addForegroundSprite(Sprite sprite) {
		System.out.println( "GameVisualization2DSpriteBased.addForegroundSprite()" +
							"called. Sprite = " + sprite.getName());
		foregroundSprites.add(sprite);
	}
	

	//////////////////////////////////////////
	//
	// Getters & Setters:
	//


	/**
	 * @return the backgroundSprites
	 */
	private ArrayList<Sprite> getBackgroundSprites() {
		return backgroundSprites;
	}

	/**
	 * @return the mainLayerSprites
	 */
	private ArrayList<Sprite> getMainLayerSprites() {
		return mainLayerSprites;
	}

	/**
	 * @return the foregroundSprites
	 */
	private ArrayList<Sprite> getForegroundSprites() {
		return foregroundSprites;
	}


	/**
	 * @return the player
	 */
	private PlayerSprite getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	private void setPlayer(PlayerSprite player) {
		this.player = player;
	}




	//////////////////////////////////////////
	//
	// toString & equals:
	//

	@Override
	public String toString() {
		return "GameVisualization2DSpriteBased [backgroundSprites=" + backgroundSprites + ", \nmainLayerSprites="
				+ mainLayerSprites + ", \nforegroundSprites=" + foregroundSprites + ", \nplayer=" + player + "]";
	}
	
	
	// no need for equals(). it would just do same thing as Object.equals()
	
	

	
	//////////////////////////////////////////////////////////////////////////
	// static main()
	//
	
	/**
	 *  Unit Test for GameVisualization2DSpriteBased and the classes it uses
	 */
	public static void main(String[] args) {
		System.out.println("Running GameVisualization2DSpriteBased.main()\n");
		
		GameVisualization2DSpriteBased visualization = new GameVisualization2DSpriteBased();
		visualization.start();

		// @deprecated 
		//
//		ArrayList<Sprite> allSprites = new ArrayList<Sprite>();
//		allSprites.addAll( visualization.getMainLayerSprites() );
//		allSprites.addAll( visualization.getForegroundSprites() );
//		allSprites.addAll( visualization.getBackgroundSprites() );
//		
//		System.out.println("\n\nPresorted allSprites: \n" + allSprites);
//
//		allSprites.sort(null);
//		System.out.println("\n\nSorted allSprites: \n" + allSprites);
		

		ArrayList<Sprite> allSprites2 = visualization.currentLevel.getAllSprites();  ;
		System.out.println("\n\nPresorted allSprites: \n" + allSprites2);
		
		// M3 USING COMPARATOR
		Collections.sort(allSprites2, Sprite.LAYER_NAME_COMPARATOR);
		System.out.println("\n\nM3 USING COMPARATOR\n-----------------");
		System.out.println("\nLAYER then NAME Sorted allSprites: \n" + allSprites2);

		Collections.sort(allSprites2, Sprite.NAME_COMPARATOR);
		System.out.println("\n\nName Sorted allSprites: \n" + allSprites2);
		

		 // M3 USING FACTORY
		ImageIcon playerIcon = AssetManager.getAssetManager().getAssetImageIcon( AssetType.PLAYER );
		System.out.println("\n\nM3 USING FACTORY\n-----------------");
		System.out.println("\nPlayer Sprite: \n" + playerIcon);
		
		
		GameLevelLoader levelLoader = new GameLevelLoader();

		// M3 USING BUILDER
		GameLevel level = levelLoader.createSampleLevel( );
		System.out.println("\n\nM3 USING BUILDER\n-----------------");
		System.out.println("\nBuilt level: \n" + level);
		
		
		
		
//		System.out.println("\nEnd GameVisualization2DSpriteBased.main()");
	}
	
	
}
