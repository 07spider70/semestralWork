package com.galaxian;



import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;


import com.galaxian.Menu;





public class NameMenu implements Commons {
		private JFrame mainFrame;
		private JLabel headerLabel;
		private JTextField nameField;
		private JButton backButton;
		private JButton startButton;
		private JLabel background;
		private JPanel controlFrame;
		public String name;
		
		

	   public NameMenu(){
			
			prepareGUI();
		}
		
		public static void main(String[] args) {
			
			NameMenu name = new NameMenu();
			name.showEvent();
		}
		
		private void prepareGUI() {
			
			//nacitanie pozadia
			background = new JLabel(new ImageIcon("res/background.png"));
			
			mainFrame = new JFrame();
			controlFrame = new JPanel();
			//velkost okna
			mainFrame.setSize(WIND_WIDTH,WIND_HEIGHT);
			//titulok
			mainFrame.setTitle(TITLE);
			//reakcia na krizik
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//pri spusteni na stred
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			  
		    mainFrame.setLocation(screenSize.width/4 ,screenSize.height/6 );
			//zakazane zmensovanie
			mainFrame.setResizable(false);
			//zvidilenenie
			mainFrame.setVisible(true);
			//ikona
			ImageIcon ii;
			ii = new ImageIcon("res/imageIcon.png");
			mainFrame.setIconImage(ii.getImage());
			//pozadir
			mainFrame.add(background);
			
			//komponenty na zobrazenie
			
			//layout na ich rozmiestnenie
			background.setLayout(null);
			
			background.setBounds(100, 100, 500, 500);
			//nastavenie fontu
			Font fnt0 = new Font("Courier New",Font.BOLD, 30); 
		      
		    headerLabel = new JLabel("Zadaj meno",JLabel.CENTER );
		    headerLabel.setFont(fnt0);
		    headerLabel.setForeground(Color.white);
		    
		    //TODO nastavit poziciu
		    headerLabel.setBounds(100,20,500,20);
		    
		    
		    nameField = new JTextField();
		    nameField.setPreferredSize(new Dimension(100, 50));
		    nameField.setBounds(300,120,nameField.getPreferredSize().width,nameField.getPreferredSize().height);
		    
		   
		    
		    
		    background.add(headerLabel);
		    background.add(nameField);
		    
		    controlFrame.setLayout(new GridLayout(2, 1) );;
		    controlFrame.setBackground(new Color(0,0,0,100));
		    //TODO pozicia tlacidiel
		    controlFrame.setBounds(300,200,200,200);
		    background.add(controlFrame);
		    
		    mainFrame.addWindowListener( new WindowAdapter() {
		    	   public void windowOpened( WindowEvent e ){
		    	        nameField.requestFocus();
		    	     }
		    	   } ); 
		      
		    
		}
		
		private void showEvent() {
			
			//pridanie tlacidiel
			startButton = new JButton("Start");
			startButton.setActionCommand("START");
			startButton.addActionListener(new ButtonClickListener());
			controlFrame.add(startButton);
			
			backButton = new JButton("Back");
			
			backButton.setActionCommand("back");
			
			backButton.addActionListener(new ButtonClickListener());
			
			controlFrame.add(backButton);
			
		}
		
		public String getName() {
			return this.name;
		}
		
		private class ButtonClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				
				if( command.equals("back")) {
					Menu.main(null);
					mainFrame.setVisible(false);
				} 
				
				else if(command.equals("START")) {
					
					//stiahnutie mena a odoslanieho hre pre spracovanie
					name = nameField.getText();
					System.out.println(name);
					mainFrame.setVisible(false);
		        	mainFrame = null;     
					
						
					/*
					Game g = new Game();
					//g.addKeyListener(g);
					g.setName(name);
					g.gameLoop();
					
					*/
					try {
						//new Game();
						Game.main(name);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					
				}
			}
		}
		
		}
		
	

