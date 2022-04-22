package com.wickedgames.cs195.model;

/*  Class GameVisualization
 * 
 *  
 * 
 *  @author Jason Mahar
 *  
 *  CS 211S-Adv. Java: Standard Edition-931
 */

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;



/**
 *  
 *
 */
abstract public class GameVisualization extends Canvas {

	/**
	 *  required by inherited Interface Serializable
	 */
	private static final long serialVersionUID = -181662728796674260L;
	
	private AssetManager assetManger;
	

	private static GameVisualization theGameVisualization;
	
	protected GameVisualization() {
		super();
		System.out.println("GameVisualization() constructor. ");
	}

	protected GameVisualization(GraphicsConfiguration config) {
		super(config);
		System.out.println("GameVisualization(GraphicsConfiguration) constructor. ");
	}
	
	
	/*
	 * returns an unknown GameVisualization subclass 
	 * 
	 * currently set to GameVisualization2DSpriteBased
	 */
	public static GameVisualization getGameVisualization() {
		if ( theGameVisualization == null ) {
			theGameVisualization = new GameVisualization2DSpriteBased();
		}
		
		return theGameVisualization;
	}
	


	/*
	 *  must be implemented by subclasses to draw the game
	 */
	protected void initialize() {
		System.out.println("GameVisualization.initialize() called ");
		assetManger = AssetManager.getAssetManager();
	}


	/*
	 *  must be implemented by subclasses to draw the game
	 */
	public abstract void start();
	
	/*
	 *  must be implemented by subclasses to draw the game
	 */
	public abstract void draw();
	
	

	/**
	 * @return the assetManger
	 */
	public AssetManager getAssetManger() {
		return assetManger;
	}

	/**
	 * @param assetManger the assetManger to set
	 */
	protected void setAssetManger(AssetManager assetManger) {
		this.assetManger = assetManger;
	}

	

}
