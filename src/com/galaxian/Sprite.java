package com.galaxian;

import java.awt.Graphics;
import java.awt.Image;

//iba duch ktory sluzi na zobrazenie obrazka

public class Sprite {
	//image ktora ma byt vykreslena pre tohto ducha
	private Image image;
	
	//vytvorenie noveho na zaklade obrazka
	public Sprite(Image image) {
		this.image = image;
		
	}
	
	//ziskanie sirky ducha
	
	public int getWidth() {
		return image.getWidth(null);
	}
	
	//ziskanie vysky
	
	public int getHeight() {
		return image.getHeight(null);
	}

	//vykreslenie ducha na platno
	
	public void draw(Graphics g,int x,int y) {
		g.drawImage(image, x, y, null);
	}
}
