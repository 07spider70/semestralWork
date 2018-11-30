package com.galaxian;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

//reprezentacia vsetkeho v hre
//riesi pohyb aj kolizie
public abstract class Entity {
	//x suradnica
	protected double x;
	//y suradnica
	protected double y;
	//duch ktory reprezentuje entitu
	protected Sprite sprite;
	// horiyontalna rychlost 
	protected double dx;
	//vertikalna rychlost
	protected double dy;
	//rec. pre koliziu mna
	private Rectangle me = new Rectangle();
	//rec pre koliziu, ina entita                                           
	private Rectangle him = new Rectangle();
	
	// vytvori entitu zalozenu na duchovi a suracniciach
	
	public Entity(String ref,int x,int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	
	//pohybovanie entity zalozenej na plynuti casu
	public void move(long delta) {
		// update suradnic

		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	//nastavenie hprizonalnej rychlosti
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}

	//nastavenie vertikalnej rychlosti
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	
	//velkost horizontalnej rychlosti
	public double getHorizontalMovement() {
		return dx;
	}

	//vratenie vertikalnej rychlosti
	public double getVerticalMovement() {
		return dy;
	}
	
	//vykreslenie entity
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
	}
	
	//logika spojena s druho entity
	public void doLogic() throws IOException {
	}
	
	//xsova suradnica
	public int getX() {
		return (int) x;
	}

	//y suradnica
	public int getY() {
		return (int) y;
	}
	
	//kontrola kolizie s inou entitou
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	//upozornenie ze mala koliziu s inou entitou
	public abstract void collidedWith(Entity other) throws IOException;
}
