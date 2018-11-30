package com.galaxian;



import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import com.galaxian.Menu;





public class HelpMenu implements Commons {
		private JFrame mainFrame;
		private JLabel headerLabel;
		private JLabel helpLabel;
		private JButton backButton;
		private JLabel background;
		private JPanel controlFrame;
		
		

	   public HelpMenu(){
			
			prepareGUI();
		}
		
		public static void main(String[] args) {
			
			HelpMenu help = new HelpMenu();
			help.showEvent();
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
			background.setLayout(new GridLayout(3, 1));
			//nastavenie fontu
			Font fnt0 = new Font("Courier New",Font.BOLD, 30); 
		      
		    headerLabel = new JLabel("HELP",JLabel.CENTER );
		    headerLabel.setFont(fnt0);
		    headerLabel.setForeground(Color.white);
		    //zoradenie skore a zapisanie
		    
		    
		    
		    helpLabel = new JLabel("ovladanie",JLabel.CENTER);
		    helpLabel.setFont(fnt0);
		    helpLabel.setForeground(Color.WHITE);
		   // System.out.println(getScore());
		    
		    
		    background.add(headerLabel);
		    background.add(helpLabel);
		    
		    controlFrame.setLayout(new FlowLayout(FlowLayout.CENTER));;
		    controlFrame.setBackground(new Color(0,0,0,100));
		    background.add(controlFrame);
		      
		    
		}
		
		private void showEvent() {
			
			backButton = new JButton("Back");
			
			backButton.setActionCommand("back");
			
			backButton.addActionListener(new ButtonClickListener());
			
			controlFrame.add(backButton);
			
		}
		
		//listener na tlacidlo
	
		
		private class ButtonClickListener implements ActionListener {
			//metoda ktora sa vykona pri akcii stlacenie
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				
				if( command.equals("back")) {
					Menu.main(null);
					mainFrame.setVisible(false);
				}
			}
		}
		
		}
		
	

