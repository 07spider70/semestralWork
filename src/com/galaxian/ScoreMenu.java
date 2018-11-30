package com.galaxian;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreMenu implements Commons {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel scoreLabel;
	private JButton backButton;
	private JLabel background;
	private JPanel controlFrame;
	private FileWork fw;
	
	
	public ScoreMenu() throws IOException {
		fw = new FileWork("res/score.txt");
		prepareGUI();
	}
	
	public static void main(String[] args) throws IOException {
		
		ScoreMenu score = new ScoreMenu();
		score.showEvent();
	}
	
	private void prepareGUI() throws IOException {
		
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
	      
	    headerLabel = new JLabel("SCORE",JLabel.CENTER );
	    headerLabel.setFont(fnt0);
	    headerLabel.setForeground(Color.white);
	    //zoradenie skore a zapisanie
	    fw.writeScore();
	    
	    
	    scoreLabel = new JLabel(fw.getScore(),JLabel.CENTER);
	    scoreLabel.setFont(fnt0);
	    scoreLabel.setForeground(Color.WHITE);
	   // System.out.println(getScore());
	    
	    
	    background.add(headerLabel);
	    background.add(scoreLabel);
	    
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
	
	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if( command.equals("back")) {
				Menu.main(null);
				mainFrame.setVisible(false);
			}
		}
	}
	
	//nacitanie score

	private String getScore() throws IOException {
		String everything;
		BufferedReader br = new BufferedReader(new FileReader("res/score.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			//html kompozicia pre viac riadkov
			sb.append("<html>");
			String line = br.readLine();
			int i = 1;
			while (line != null) {
				
				sb.append(i+". ");
				sb.append(line);
				sb.append("<br>");
				line = br.readLine();
				i++;
				if (i==4) {
					line = null;
				}
				
			}
			sb.append("</html>");
			everything = sb.toString();
		} finally {
			br.close();
		}
		
		return everything;
	}
	
}
