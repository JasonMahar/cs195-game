package com.wickedgames.cs195.model;

/**
 * 
 */


import java.util.HashMap;

import javax.swing.ImageIcon;


/**
 *  AssetManager Singleton
 * 
 *   @implNote currently only handles 2D image assets,
 *   			but should be made more generic to handle other types of assets
 * 
 *
 */
public class AssetManager {

	
	/**
	 * 
	 */
	public static AssetManager getAssetManager() {

		if( theAssetManager == null ) {
			theAssetManager = new AssetManager(); 
		}
		return theAssetManager;
	}

	/**
	 * 
	 */
	private static AssetManager theAssetManager;
	
	private HashMap<AssetType, ImageIcon> assetMap;


	/**
	 *  hide the default Constructor
	 */
	private AssetManager() { 

		assetMap = new HashMap<AssetType, ImageIcon>(); 
	}
	
	
	
	/**
	 * 
	 */
	private HashMap<AssetType, ImageIcon> getAssetMap() {

		return assetMap;
	}
	

	 // M3 FACTORY METHOD
	/**
	 * @return 
	 * 
	 */
	public ImageIcon getAssetImageIcon( AssetType type ) {
		
		HashMap<AssetType, ImageIcon> theAssetMap = getAssetMap();
		
		// for a given fileName, we only need to create an ImageIcon the first time it's seen
		if( ! theAssetMap.containsKey(type) ) {
			ImageIcon icon = new ImageIcon( type.getFileName() );
			theAssetMap.put(type, icon);
		}
		
		return theAssetMap.get(type);
	}
	
	/**
	 *  Clears theAssetMap and then creates an ImageIcon for every AssetType
	 *  
	 *  @NOTE: This could be better streamlined for performance if needed later
	 */
	public void loadAllAssetTypes() {
		
		HashMap<AssetType, ImageIcon> theAssetMap = getAssetMap();
		theAssetMap.clear();
		
		for (AssetType type: AssetType.values()) { 
			getAssetImageIcon( type );
		}
		
	}
	
}
