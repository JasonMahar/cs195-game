package com.wickedgames.cs195.model;

/*  Class Sprite
 * 
 *  
 * 
 *  @author Jason Mahar
 *  9/21/21
 *  
 *  CS 211S-Adv. Java: Standard Edition-931
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Comparator;


/**
 *
 */
public abstract class Sprite extends Image implements Drawable, Comparable<Sprite> {

	// M3 COMPARATOR PATTERN
	public final static Comparator<Sprite> LAYER_NAME_COMPARATOR = new LayerNameComparator();
	public final static Comparator<Sprite> NAME_COMPARATOR = new NameComparator();

	/*
	 * This is mainly to establish which Sprites should be drawn first
	 * higher layer # is drawn on top of lower layer #
	 * Given same Layer #: Secondarily names are used for which are drawn first 
	 */
	public static class LayerNameComparator implements Comparator<Sprite> {
		
		
		public int compare( Sprite s1, Sprite s2 ) {
			if( s1.layer > s2.layer ) { return 1; }
			if( s1.layer < s2.layer ) { return -1; }
			return s1.name.compareToIgnoreCase(s2.name);
		}
	}
	
	/*
	 * This is just for seeing an alphabetic list of all Sprites
	 */
	public static class NameComparator implements Comparator<Sprite> {
		
		public int compare( Sprite s1, Sprite s2 ) {
			return s1.name.compareToIgnoreCase(s2.name);
		}
	}
	
	
	
	private final static String DEFAULT_NAME = "<not set>";
	
	// for this iteration we're going to use a fixed tile size
	private final static float SPRITE_WIDTH = 10.0f;
	private final static float SPRITE_HEIGHT = 10.0f;

	private int layer;				// draw layer.  higher numbers will be drawn over lower numbers.

	private AssetType type;			// an abstraction of the image file name 
									// for this Sprite, among other info
	
	private String name;
	
	protected float xPosition;
	protected float yPosition;
	
	

	/**
	 * 
	 */
	public Sprite(AssetType assetType) {
		this(assetType, assetType.getDefaultName(), assetType.getDefaultLayer());
//		System.out.println("Sprite(name) constructor. assetType = " + assetType);
		
	}
	
	/**
	 * 
	 */
	public Sprite(AssetType assetType, String name) {
		this(assetType, name, assetType.getDefaultLayer());
//		System.out.println("Sprite(name) constructor. assetType = " + assetType + ", name = " + name);
		
	}

	/**
	 * 
	 */
	public Sprite(AssetType assetType, String name, int layer) {
		super();
//		System.out.println("Sprite(name) constructor. assetType = " + assetType + ", name = " + name);

		this.setType(assetType);
		this.setName( name );
		this.setLayer( layer );
	}
	
	
	public void draw() {
		System.out.println("Sprite.draw() called on name = " + getName());
		
		 // M3 USING FACTORY
		AssetManager.getAssetManager().getAssetImageIcon( this.getType() );
		
	}


	
	

	/**
	 * compares this Sprite to other Sprite
	 * first by layer
	 * second by type
	 * third by name
	 */
	@Override
	public int compareTo(Sprite other) {

		if( this.getLayer() < other.getLayer() ) { return -1; }
		if( this.getLayer() > other.getLayer() ) { return 1; }
		
		int typeComparison = this.getType().compareTo( other.getType() );
		if( typeComparison != 0 ) { return typeComparison; }

		return this.getName().compareTo(other.getName());
	}
	

	
	/*
	 *  Inherited Abstract from Image methods that need to be implemented
	 * 
	 */
	
	@Override
	public int getWidth(ImageObserver observer) {
		return this.getWidth(null);
	}

	@Override
	public int getHeight(ImageObserver observer) {
		return this.getHeight(null);
	}

	@Override
	public ImageProducer getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String name, ImageObserver observer) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//////////////////////////////////////////
	//
	// getters & setters:
	//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if( name == null || name.equals("") ) {
			this.name = DEFAULT_NAME;
		}
		else {
			this.name = name;
		}
	}

	/**
	 * @param xPosition the xPosition to set
	 * @param yPosition the yPosition to set
	 */
	public void setPosition(float xPosition, float yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	/**
	 * @return the xPosition
	 */
	public float getXPosition() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public float getYPosition() {
		return yPosition;
	}


	/**
	 * @return the type
	 */
	public AssetType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AssetType type) {
		this.type = type;
	}

	/**
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	
	
	//////////////////////////////////////////
	//
	// toString & equals:
	//

	@Override
	public String toString() {
		return "\nSprite [layer=" + layer + ", type=" + type + ", name=" + name + ", xPosition=" + xPosition
				+ ", yPosition=" + "]";
	}


	@Override
	public boolean equals(Object other) {
		
		// I think this only comparing names gives me a way to have multiple 
		// Sprites that are still considered to be the same. I can use 
		// different names for each sprite if I want them not to be comparable
		if (other instanceof Sprite) {
	         return this.getName().equals( ((Sprite)other).getName() ) &&
	        		 this.getType().equals( ((Sprite)other).getType() ) &&
	        		 this.getLayer() == ((Sprite)other).getLayer() ;
		} 
	     
		return false;
	}


	
	
}
