package com.galaxian;

import java.io.IOException;

public class AlienEntity extends Entity implements Commons {
	//horizontalna rychlost
	private double moveSpeed = ALIEN_HORIZ_SPEED;
	
	private Game game;
	
	//vytvorenie nepriatela
	//v ktorej hre, ref k spritu, x,y na zaciatku
	
	public AlienEntity(Game game, String ref, int x, int y) {
		super(ref, x, y);
		
		this.game = game;
		dx = -moveSpeed;
		
	}
	
	//pohyb, delta cas uplynuty od posledneho pohybu
	
	public void move(long delta) {
		//ked sme na lavej strane na konci poziadane o update game logiky
		if ((dx < 0) && (x < 10)) {
			game.updateLogic();
		}
		
		//a naopak
		if ((dx > 0) && (x > 750)) {
			game.updateLogic();
		}
		
		super.move(delta);
	}
	
	public void doLogic() throws IOException {
	//update logiky vztahujucej sa na mimozemstanov
	//zmena pohybu a posunutie smerom dole
	
		dx = -dx;
		y += 10;
	//ak sa dostaneme na spodok hrac umrie
		if (y > 570) {
			game.notifyDeath();
		}
	}
	
	//kolizia s inym telesom
	public void collidedWith(Entity other) {
		//riesi sa inde
	}

}
