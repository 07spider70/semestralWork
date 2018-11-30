package com.galaxian;

import java.io.IOException;

public class ShotEntity extends Entity implements Commons {
	//vertikalna rychlost hracovej strely
	private double moveSpeed = SHOT_VERTI_SPEED;
	//kde entita bude existovat
	private Game game;
	//true ak strela niekoho zasiahne
	private boolean used = false;
	
	//vytvorenie strely
	public ShotEntity(Game game, String ref,int x, int y) {
		super(ref,x,y);
		
		this.game = game;
		dy = moveSpeed;
	}
	
	//delta = cas ktory ubehol od posledneho pohybu
	public void move(long delta) {
		//vykonanie pohybu
		
		super.move(delta);
		
		//mimo obrazovky sa zmaze
		if (y < -100) {
			game.removeEntity(this);
		}
	}

	//kolizia s inou entitou
	public void collidedWith(Entity other) throws IOException {
		//prevencia aby sme jednou strelou nezabili viac entit
		if (used) {
			return;
		}
		
		//ak trafime nepriatela, zabijeme ho
		if (other instanceof AlienEntity) {
			//odstranime strelu aj nepriatela
			game.removeEntity(this);
			game.removeEntity(other);
			//upozornenie pre hru ze sme zabili
			
			game.notifyAlienKilled();
			used = true;
		}
	}
}
