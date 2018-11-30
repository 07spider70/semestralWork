package com.galaxian;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

//trieda pre pracu s obrazkami
//nacita subory, cachne ich aby ich nebolo treba stale nacitavat
public class SpriteStore {

	//vytvori jedinu instanciu tejto triedy
	private static SpriteStore single = new SpriteStore();
	
	//get vreti jedinu instanciu tejto triedy
	
	public static SpriteStore get() {
		return single;
	}
	
	//vytvori cached obrazkovu mapu z referencie na obrazkovu instanciu
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	//vrati obrazok
	//@param ref referencia k obrazku
	//@return instanciu obsahujuca obrazok podla referncie
	
	public Sprite getSprite(String ref) {
		//ak ju uz mame ulozenu v cache len ju vratime
		if (sprites.get(ref) != null) {
			return (Sprite) sprites.get(ref);
		}
		//inak nacita
		BufferedImage sourceImage = null;
		
		try {
			//ClassLoader.getResource() zabezpeci spravne najdenie priecinka a nacitanie
			
			URL url = this.getClass().getClassLoader().getResource(ref);
			System.out.println(this.getClass().getClassLoader().getResource(ref));
			
			if (url==null) {
				fail("Nenajdena ref: "+ref);
			}
			
			//nacitanie obrazku
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			fail("Nepodarilo sa nacitat: "+ref);
		}
		//vytvorenie obrazku v spravnej velkosti k ulozeniu
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(),Transparency.BITMASK);
		//vykreslenie obrazku do akcelerovaneho
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		//vytvorenie sprajta pridania do cachu a returnutie
		Sprite sprite = new Sprite(image);
		sprites.put(ref, sprite);
		
		return sprite;
	}
	
	//metoda pri chybe nacitavania
	
	private void fail(String message) {
		//ukonci hru
		System.err.println(message);
		System.exit(0);
	}
}
