package com.galaxian; 

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Menu implements Commons {
   private JFrame mainFrame; //hlavna kostra
 
   private JLabel headerLabel;
   
   private JPanel controlPanel;
   private JPanel buttPanel;

   public Menu(){
      prepareGUI();
   }
   public static void main(String[] args) {
      Menu Menu = new Menu();  
      Menu.showEvent();       
   }
   private void prepareGUI(){
	  JLabel background = new JLabel(new ImageIcon("res/background.png"));
	   
      mainFrame = new JFrame();
      buttPanel = new JPanel();
      
      //velkost okna
      mainFrame.setSize(WIND_WIDTH,WIND_HEIGHT);
      //titulok
      mainFrame.setTitle(TITLE);
      //reakcia na krizik
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //poloha pri spusteni na stred
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  
	  mainFrame.setLocation(screenSize.width/4 ,screenSize.height/6 );

      
     //zakazanie zmensovania okna
      mainFrame.setResizable(false);
      //viditelnost
      mainFrame.setVisible(true);  
      //nacitanie ikony
      ImageIcon ii;
      ii = new ImageIcon("res/imageIcon.png");
      mainFrame.setIconImage(ii.getImage());
      //nacitanie pozadia
      mainFrame.add(background);
   
      //nastavenie hlavneho layoutu
      background.setLayout(new GridLayout(3, 1));
      //nastavenie fontu
      Font fnt0 = new Font("Courier New",Font.BOLD, 30); 
      
      headerLabel = new JLabel("GALAXIAN",JLabel.CENTER );
      headerLabel.setFont(fnt0);
      headerLabel.setForeground(Color.white);
      //panel pre panel s tlacidlami
      
      buttPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      //priesvitnost pozadia
      buttPanel.setBackground(new Color(0, 0, 0,100));
      
      //panel pre tlacidla
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(4, 1));
      controlPanel.setBackground(new Color(0,0,0,100));
      //pridanie komponentov
      background.add(headerLabel);
      background.add(buttPanel);
      buttPanel.add(controlPanel);
      
      
      
   }
   private void showEvent(){
      

      JButton newButton = new JButton("NEW");
      JButton scoreButton = new JButton("SCORE");
      JButton helpButton = new JButton("HELP");
      JButton exitButton = new JButton("EXIT");
      
      //command pri vykonani akcie
      
      
      newButton.setActionCommand("NEW");
      scoreButton.setActionCommand("SCORE");
      helpButton.setActionCommand("HELP");
      exitButton.setActionCommand("EXIT");

      //pridanie listeneru pre tlacidla
      
      newButton.addActionListener(new ButtonClickListener()); 
      scoreButton.addActionListener(new ButtonClickListener()); 
      helpButton.addActionListener(new ButtonClickListener());
      exitButton.addActionListener(new ButtonClickListener()); 
      

      controlPanel.add(newButton);
      controlPanel.add(scoreButton);
      controlPanel.add(helpButton);
      controlPanel.add(exitButton);       

      mainFrame.setVisible(true);  
   }
   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
    	 //ziskanie commandu
         String command = e.getActionCommand();  
         
         //co sa stane pri akom prikaze
         if( command.equals( "NEW" ))  {
            
        	mainFrame.setVisible(false);
        	mainFrame = null;        	
        	
        	NameMenu.main(null);
        	 
            
         } else if( command.equals( "SCORE" ) )  {
            //okno so skore
        	 try {
				ScoreMenu.main(null);
			} catch (IOException e1) {
				System.out.println("chyba");
				e1.printStackTrace();
			}
        	 mainFrame.setVisible(false);
         } else if(command.equals("HELP")) {
        	 
        	 HelpMenu.main(null);
        	 mainFrame.setVisible(false);
         }
         
         else {
            System.exit(0);
         }  	
      }		
   }
}