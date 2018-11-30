package com.galaxian;



public interface Commons {
	
	public static final int WIND_WIDTH = 800;
	public static final int WIND_HEIGHT = 600;

	public static final int BUT_WIDTH = 320;
	public static final int BUT_HEIGHT = 110;
	public static final String TITLE = "Galaxian";
	public static final int PLAYER_INITIAL_X = 370;
	public static final int PLAYER_INITIAL_Y = 550;
	
	public static final int ALIEN_ROW_AMOUNT = 5;
	public static final int ALIEN_COLUMN_AMOUNT = 12;
	
	//rychlosti
	
	public static final int ALIEN_HORIZ_SPEED = 75;
	public static final int SHOT_VERTI_SPEED = -300;
	public static final int PLAYER_MOVE_SPEED = 350;
	public static final double GAINED_SPEED = 1.019;
	
	
	//(ms)
	public static final int SHIP_FIRING_INTERVAL = 300;  
	public static final int ALIEN_FIRING_INTERVAL = 1500;
	
	
	public static final int PLAYER_HEALTH = 5;
	
	//sprity
	public static final String SPRITE_SHOT = "sprites/shot.gif";
	public static final String SPRITE_PLAYER = "sprites/ship.gif";
	public static final String SPRITE_ALIEN = "sprites/alien.png";
	public static final String SPRITE_ALIEN_W2 = "sprites/alien.gif";
	
}
