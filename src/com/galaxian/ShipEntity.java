package com.galaxian;

import java.io.IOException;

//player ship

public class ShipEntity extends Entity implements Commons {
	
	private Game game;
	
	//ref for sprite
	//x,y pre zaciatone pozicie
	
	public ShipEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		
		this.game = game;
	}
	
	//pohyb zalozeny na mnozstve uplynuleho casu
	//delta, cas od posledneho pohybu
	
	public void move(long delta) {
		// osetrenie opustenia mapy na lavej strane
		
		if ((dx < 0) && (x < 10)) {
			return;
		}
		//osetrenie opustenia mapy na pravej strane
		if ((dx > 0) && (x > 750)) {
			return;
		}
		
		super.move(delta);
	}
	
	//pri kolizi 
	public void collidedWith(Entity other) throws IOException {
		//ak je to mimozemstan tak hrac umre
		if (other instanceof AlienEntity) {
			game.notifyDeath();
		}
	}
}