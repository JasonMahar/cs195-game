package com.wickedgames.cs195.model;

/**
 * 
 */


	
/**
 * @author Jason Mahar
 *
 * @implNote currently only handles 2D image assets,
 *   		 but should be made more generic to handle other types of assets
 */
public enum AssetType  {
	

	
	//	defaultName, fileName, defaultLayer, minQuantity, maxQuantity
	//      

	FLOOR( "Floor", 
			"assets/Sprites/Level/Floor.png",
			0, AssetQuantity.ZERO, AssetQuantity.MANY ),
	TOP_WALL( "TopWall", 
			"assets/Sprites/Level/Walls/7x7Room/top_wall.png",
			100, AssetQuantity.ZERO, AssetQuantity.MANY ),
	LEFT_WALL( "LeftWall", 
			"aassets/Sprites/Level/Walls/7x7Room/left_wall.png",
			100, AssetQuantity.ZERO, AssetQuantity.MANY ),
	RIGHT_WALL( "RightWall", 
			"assets/Sprites/Level/Walls/7x7Room/right_wall.png",
			100, AssetQuantity.ZERO, AssetQuantity.MANY ),
	BOTTOM_WALL( "BottomWall", 
			"assets/Sprites/Level/Walls/7x7Room/bot_wall.png",
			100, AssetQuantity.ZERO, AssetQuantity.MANY ),
	GREEN_MONSTER( "GreenMonster", 
			"assets/Sprites/Monsters/Green Monster/M1_Front/Idle/Monster1Front-Idle_0.png",
			200, AssetQuantity.ZERO, AssetQuantity.MANY ),
	FLYING_MONSTER( "FlyingMonster", 
			"ssets/Sprites/Monsters/Flying Monster/Front/Idle/Monster2Right-Idle_0.png",
			250, AssetQuantity.ZERO, AssetQuantity.MANY ),
	BOSS_MONSTER( "GreenMonster", 
			"assets/Sprites/Monsters/Boss/BossFront/Idle/Monster3Front-Idle_0.png",
			200, AssetQuantity.ZERO, AssetQuantity.MANY ),
	PLAYER( "Player", 
			"assets/Sprites/Player/Character_Front/Idle/CharacterFront-Idle_0.png",
			200, AssetQuantity.ONE, AssetQuantity.ONE );
	

	private  String defaultName;
	private  String fileName;
	private  int defaultLayer;
	private  AssetQuantity minQuantity; 
	private  AssetQuantity maxQuantity;
	
	/**
	 * 
	 */
	private AssetType( String defaultName, String fileName, int defaultLayer, 
			AssetQuantity minQuantity, AssetQuantity maxQuantity ) {
		
		this.setDefaultName(defaultName);
		this.setFileName(fileName);
		this.setDefaultLayer(defaultLayer);
		this.setMinQuantity(minQuantity);
		this.setMaxQuantity(maxQuantity);
	}

	

	/**
	 * @return the defaultName
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * @param defaultName the defaultName to set
	 */
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the defaultLayer
	 */
	public int getDefaultLayer() {
		return defaultLayer;
	}

	/**
	 * @param defaultLayer the defaultLayer to set
	 */
	public void setDefaultLayer(int defaultLayer) {
		this.defaultLayer = defaultLayer;
	}

	/**
	 * @return the minQuantity
	 */
	public AssetQuantity getMinQuantity() {
		return minQuantity;
	}

	/**
	 * @param minQuantity the minQuantity to set
	 */
	public void setMinQuantity(AssetQuantity minQuantity) {
		this.minQuantity = minQuantity;
	}

	/**
	 * @return the maxQuantity
	 */
	public AssetQuantity getMaxQuantity() {
		return maxQuantity;
	}

	/**
	 * @param maxQuantity the maxQuantity to set
	 */
	public void setMaxQuantity(AssetQuantity maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	
	
}
