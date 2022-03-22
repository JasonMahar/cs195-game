package com.wickedgames.cs195.model;


public class Floor extends Sprite {

	public static final int FLOOR_LAYER = 0;

	public Floor() {
		super(AssetType.FLOOR);
		System.out.println("Floor() constructor. ");
	}

	public Floor(AssetType assetType) {
		super(assetType);
	}

	public Floor(AssetType assetType, String name) {
		super(assetType, name, FLOOR_LAYER);
	}


}
