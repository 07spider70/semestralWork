package com.galaxian;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.api.server.Container;


public class Game extends Canvas implements Commons{
	
	/**
	 * 
	 */
	//public static JFrame container;
	
	JFrame container;
	
	private static final long serialVersionUID = 1L;
	//pre rychlejsie vykreslenie
	private BufferStrategy strategy;
	//true ked hra bezi(sme v slucke)
	private boolean gameRunning = true;
	//zoznam vsetkych objektov v hre
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	//zoznam objektov ktore maju byt odstranene z hry
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	//meno hraca
	private String name;
	//entita lode
	private Entity ship;
	//rychlost pohybu hraca (pix/sec)
	private double moveSpeed = PLAYER_MOVE_SPEED;
	//cas kedy sa naposledy vystrelilo
	private long shipLastFire = 0;
	private long alienLastFire = 0;
	//interval medzi jednotlivymi strelami
	private long shipFiringInterval = SHIP_FIRING_INTERVAL;
	private long alienFiringInterval = ALIEN_FIRING_INTERVAL;
	//pocet nepriatelov na obrazovke
	private int alienCount;
	//velkost skore
	private int score;
	//hracove skore
	private int health = PLAYER_HEALTH;
	//sprava pri cakani na stlacenie klavesy
	private String message = "";
	//true pokial nestlacime klavesu
	private boolean waitingForKeyPress = true;
	//true ked stlacim lavu sipku
	private boolean leftPressed = false;
	//true ked stlacim pravu sipku
	private boolean rightPressed = false;
	//true ked strielam
	private boolean firePressed = false;
	//true ked treba update v slucke, pouziva sa po vykonani udalosti
	private boolean logicRequiredThisLoop = false;
	//private int windWidth = WIND_WIDTH * SCALE;
	//private int windHeight = WIND_HEIGHT*SCALE;
	
	//ktora vlna
	private int wave = 1;
	
	
	
	private int windWidth = WIND_WIDTH;
	private int windHeight = WIND_HEIGHT;

	//vytvorenie hry a jej spustenie
	
	public Game() {
		addKeyListener(new KeyInputHandler());
		initGame();
		addKeyListener(new KeyInputHandler());
		
	}
	
	public void initGame() {
		
		
		
		//vytvorenie okna pre hru
		
		container = new JFrame("Galaxian");
		
		//nastavenie rozlisenia
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(windWidth,windHeight));
		panel.setLayout(null);
		//nastavenie umiestnenia
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  
	    container.setLocation(screenSize.width/4 ,screenSize.height/6 );
		
	    //ikona
	    ImageIcon ii;
		ii = new ImageIcon("res/imageIcon.png");
		container.setIconImage(ii.getImage());
	    
	    
		//nastavenie "platna"
		setBounds(0,0,windWidth,windHeight);
		panel.add(this);
		
		//aby nam pocitac cam neprekresloval platno
		setIgnoreRepaint(true);
		//viditelnost okna a ukoncenie hry
		
		container.pack();
		container.setResizable(false);
		
		System.out.println("viditelnost");
		//container.setFocusable(true);
		//container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		
		container.requestFocus();
		
		
		container.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				System.out.println("focus container");
				requestFocus();
			}
		});
		*/
		
		container.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				container.requestFocus();
				
			}
			
			
			
			
			
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					FileWork fw = new FileWork("res/score.txt");
					fw.addScore(name+" "+score);
					container.setVisible(false);
					System.exit(0);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				// TODO Auto-generated method stub
				
			}


			});
		
		
		
		
		//pridanie listenera pre reakcie na klavesy
		
		this.addKeyListener(new KeyInputHandler());
		
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		
		System.out.println("listener klaves");
		
		//setFocusable(true);
		//buffer strategia pre pracu s grafikou
		
		createBufferStrategy(3);
		strategy = getBufferStrategy();
		
		//inicializacia hernych objektov
		
		container.setVisible(true);
		initEntities();
	}
		
	
		
	//zapnutie novej hry po vymazani pripadnych zbytkov v pamati
	

	
	private void startGame() {
		//vycistenie entit a vytvorenie novych
		health = PLAYER_HEALTH;
		score = 0;
		wave = 1; 
		entities.clear();
		initEntities();
		//vycistenie vstupu
		
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	//inicializacia entit
	//budu pridane do spolocneho arrayu entit
	private void initEntities() {
		
		System.out.println("vytvorenie entit");
		//vytvorenie hraca
		ship = new ShipEntity(this,SPRITE_PLAYER,PLAYER_INITIAL_X,PLAYER_INITIAL_Y);
		entities.add(ship);
		//vytvorenie bloku nepriatelov (5 riadkov, po 12)
		switch (wave) {
		case 1: {
			alienCount = 0;
			for (int row=0; row<ALIEN_ROW_AMOUNT;row++) {
				for (int x=0; x<ALIEN_COLUMN_AMOUNT; x++) {
					Entity alien = new AlienEntity(this,SPRITE_ALIEN,100+(x*50),(50)+row*30);
					entities.add(alien);
					alienCount++;
				}
			}
			break;
		}
		
		//zmeni sprita
		case 2: {
			alienCount = 0;
			for (int row=0; row<ALIEN_ROW_AMOUNT;row++) {
				for (int x=0; x<ALIEN_COLUMN_AMOUNT; x++) {
					Entity alien = new AlienEntity(this,SPRITE_ALIEN_W2,100+(x*50),(50)+row*30);
					entities.add(alien);
					alienCount++;
				}
			}
			break;
		}
		
		case 3: {
			alienCount = 0;
			for (int row=0; row<ALIEN_ROW_AMOUNT;row++) {
				for (int x=0; x<ALIEN_COLUMN_AMOUNT; x++) {
					Entity alien = new AlienEntity(this,SPRITE_ALIEN,100+(x*50),(50)+row*30);
					entities.add(alien);
					alienCount++;
				}
			}
			break;
		}
		
		default: {
			alienCount = 0;
			for (int row=0; row<ALIEN_ROW_AMOUNT;row++) {
				for (int x=0; x<ALIEN_COLUMN_AMOUNT; x++) {
					Entity alien = new AlienEntity(this,SPRITE_ALIEN,100+(x*50),(50)+row*30);
					entities.add(alien);
					alienCount++;
				}
			}
			break;
		}
			
		}
		
		
	}

	//notifikacia ze treba spravit update v hre, v dosledku nejakej udalosti
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	//odstranenie entity z hry
	
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}
	
	
	//pri zasahu uberie zivot
	public void removeHealth() throws IOException {
		if (health==1) {
			this.notifyDeath();
		}
		
		health--;
	}
	
	//notifikacia ze hrac zomrel
	
	public void notifyDeath() throws IOException {
		message = "Je mi luto prehral si, skusit znova?";
		//zadanie mena
		//ulozenie skore a mena
		FileWork fw = new FileWork("res/score.txt");
		//meno bude treba zadat
		//TODO
		fw.addScore(name+" "+ Integer.toString(score));
		
		waitingForKeyPress = true;
	}
	
	public void notifyWin() throws IOException {
		message = "Vyhral si!";
		//zadanie mena
		//ulozenie skore a mena
		FileWork fw = new FileWork("res/score.txt");
		//meno bude treba zadat
		//TODO
		fw.addScore(this.name+" "+ Integer.toString(score));
		
		waitingForKeyPress = true;
	}
	
	public void notifyAlienKilled() throws IOException {
		//redukuje sa pocet, ked neostane ziadny, hrac vyhra
		
		alienCount--;
		score++;
		
		//ukoncenie hry v pripade vyhry v 4. kole
		if (alienCount == 0 && wave == 4) {
			entities.clear();
			notifyWin();
		}
		
		
		if (alienCount == 0) {
			//vytvorime novych superov
			entities.clear();
			wave++;
			initEntities();
			
		}
		
		
		
		double multiplicitator = 1;
		//rychlost bude zavisiet od vlny
		switch(wave) {
		case 1: { 
			multiplicitator=1.02;
			break;
		}
		case 2: {
			multiplicitator=1.03;
			break;
		}
		case 3: {
			multiplicitator=1.04;
			break;
		}
		default: {
			multiplicitator=1.06;
			break;
		}
		
		}
		
		
		
		//ti co ostanu musia ist pojdu rychlejsie
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			if (entity instanceof AlienEntity) {
				//zrychil o nejake % v zavislosti od vlny
				entity.setHorizontalMovement(entity.getHorizontalMovement()*(multiplicitator));
			}
		}
	}
	
	//pokus o vystrel, pokus preto lebo najprv overujeme ci preslo dost casu medzi strelami
	
	public void tryToFire() {
		//kontrola ci preslo dost casu
		
		if (System.currentTimeMillis()-shipLastFire < shipFiringInterval) {
			return;
		}
		
		//ak preslo dost casu tak
		shipLastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,SPRITE_SHOT,ship.getX()+10,ship.getY()-30);
		entities.add(shot);
	}
	
	//pokus o vystrel pri nepriateloch, funguje ako pri hracovi
	
	public void alienTryToFire(Entity entity) {
		if (System.currentTimeMillis()-alienLastFire < alienFiringInterval) {
			return;
		}
		
		//ak preslo dost casu tak
		alienLastFire = System.currentTimeMillis();
		AlienShotEntity shot = new AlienShotEntity(this,SPRITE_SHOT,entity.getX()+10,entity.getY()-30);
		entities.add(shot);
	}
	
	//vykreslenie informacii v okne hry
	public void gameGUI(Graphics g, int score, int health, int wave) {
		//vykreslenie skore
		g.setColor(Color.white);
		g.drawString("Skóre: "+ Integer.toString(score), 20, 20);
		
		//vykreslenie zdravia
		
		g.drawString("Zdravie: "+Integer.toString(health), 20, 40);
		
		//vykreslenie vlny
		
		g.drawString("Vlna: " + Integer.toString(wave),20, 60);
	}
	
	
	/*hlavna slucka, stara sa o:
	 *-update pohybov, rychlosti slucky
	 *-pohyb entit
	 *-vykreslovania grafiky
	 *-update eventov
	 *-kontrolovania vstupu
	 */
	
	public void gameLoop() throws IOException {
		
		
		long lastLoopTime = System.currentTimeMillis();
		//slucka az kym neskonci hra
		
		while(gameRunning) {
			//zisti kolko ubehlo od posledneho update
			//pouzite preto, ze ako daleko sa mozu dostat entity v jednej slucke
			
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			//vykreslenie pozadia
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIND_WIDTH, WIND_HEIGHT);
			
			gameGUI(g, score, health, wave);
			
			
			
			if (!waitingForKeyPress) {
				//strelba alienov
				
				
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					if (entity instanceof AlienEntity) {
						//vytvori strelu na zaklade pravdepodobnosti
						int prav;
						
						Random rnd = new Random();
						prav = rnd.nextInt(100);
						if (prav==0) {
							//vytvori entitu strely
							alienTryToFire(entity);
						}
					}
				}
			}
			
			//pohyb entit
			if (!waitingForKeyPress) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					entity.move(delta);
					//System.out.println("pohyb");
				}
			}
	
			//vykreslenie entit
			
			for (int i=0;i<entities.size();i++) {
				Entity entity = (Entity) entities.get(i);
				
				entity.draw(g);
			}
			
			//zistovanie kolizie kazdeho prvku s kazdym
			
			for (int p=0; p<entities.size();p++) {
				for (int s = p+1;s<entities.size();s++) {
					Entity me = (Entity) entities.get(p);
					Entity him = (Entity) entities.get(s);
					
					if (me.collidesWith(him)) {
						
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			
			//odstranenie entit ktore boli oznacene na odstranenie
			
			entities.removeAll(removeList);
			removeList.clear();
			
			//ak treba pouzit logiku, prejde vsetky entity a vyziada vykonanie logiky
			
			if (logicRequiredThisLoop) {
				for (int i=0; i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doLogic();
				}
				
				logicRequiredThisLoop = false;
				
			}
			
			//ak sa caka na tlacidlo vykreslime pre to grafiky
			
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message, (800-g.getFontMetrics().stringWidth(message))/2, 250);
				g.drawString("Stlac tlacidlo", (800-g.getFontMetrics().stringWidth("Stlac tlacidlo"))/2, 300);
				
			}
			
			//dokoncena grafika, vycistime ju a prehodime buffer
			g.dispose();
			strategy.show();
			
			//nastavenie pohybu lode, jej pohyb
			
			ship.setHorizontalMovement(0);
			
			if ((leftPressed) && (!rightPressed)) {
				ship.setHorizontalMovement(-moveSpeed);
				
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setHorizontalMovement(moveSpeed);
			}
			
			//strelba
			if (firePressed) {
				tryToFire();
			}
			
			
			
			
			
			
			//chvilu pockame, zla interpretacia sposobuje ze to moze byt rozne
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				
			}
		}
	}
		//vnorena trieda pre kontrolu pohybu hraca
	private	class KeyInputHandler implements KeyListener {
		
		//pocet klaves ktore treba stlacit kym cakame na hocijaky klaves obrazovka
		private int pressCount = 1;
		KeyInputHandler() {
			addKeyListener(this);
		}
		//zabezpcenenie jedneho stlacenia pri exite, podrzanie by znamenalo stale volanie
		boolean stlac = false;
		//vstup klavesy
		public void keyPressed(KeyEvent e) {
			System.out.println("cakanie na klaves");
			//ak cakame na hocico tak chce hocico len pomocou stlacenia
			if (waitingForKeyPress) {
				return;
				
			}
			if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A))  {
				leftPressed = true;
				
				
			}
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D) ) {
				rightPressed = true;
				
			}
		
			if ((e.getKeyCode() == KeyEvent.VK_SPACE || (e.getKeyCode() == KeyEvent.VK_SHIFT) )) {
				firePressed = true;
				
			}
			
			
			
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE && stlac == false) {
				//vypnutie hry a zapnutie menu
				
				stlac = true;
				/*
				String a;
				a = ManagementFactory.getRuntimeMXBean().getName();
				String aPom[];
				aPom = a.split("@");
				String cmd = "taskkill /F /PID " + aPom[0];
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				container.setVisible(false);
				//Game.container.dispose();
				Menu.main(null);
				System.out.println();
				
				
				
			}
		}
		
		//ak sme pustili tlacidlo
		
		public void keyReleased(KeyEvent e) {
			//ak cakame na hocico tak chce hocico len pomocou pustenia
			if (waitingForKeyPress) {
				System.out.println("stlacenie klavesy");
				return;
				
			}
			if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A) ) {
				leftPressed = false;
				System.out.println("vlavo");
				
			}
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D) ) {
				rightPressed = false;
				System.out.println("vpravo");
				
			}
		
			if ((e.getKeyCode() == KeyEvent.VK_SPACE) || (e.getKeyCode() == KeyEvent.VK_SHIFT) ) {
				firePressed = false;
				System.out.println("strelba");
				
			}
			
		
		}
		
		//ak sme napisali (stlacili aj pustili) tlacidlo
		
		public void keyTyped(KeyEvent e) {
			//ak cakame na hocijaky klaves
			//skontrolujeme ci sme dostali
			//aby sme nebrali vstup strelby a spacu tak pouzivame counter
			
			
			
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					//zacneme novu hru
					waitingForKeyPress = false;
					startGame();
					System.out.println("zacatie hry");
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			//pri esc sa vratime do menu
			//TODO vratenie do menu
			//if (e.getKeyChar() == 27) {
				//System.exit(0);
			//}
			
		}

		
	
	}
	
	//vytvorenie hry
	
	public void start(String name) throws IOException {
		
		
		Game g = new Game();
		g.name = name;
		/*
		g.transferFocus();
		System.out.println(g.hasFocus());
		g.name=name;
		g.addNotify();
		g.requestFocusInWindow();
		g.setFocusable(true);
		g.requestFocus();
		*/
		//g.startGame();
		g.gameLoop();
	}
	
	public static void main(String[] args) throws IOException {
		Game g = new Game();
		g.gameLoop();
	}
}
