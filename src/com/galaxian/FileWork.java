package com.galaxian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

//praca so suborom score.txt
//umoznuje zoradit subor podla hodnot skore a nasledne ho zapisat
//umoznuje pridat novy riadok po dokonceni hry
//vracia vypis vhodny pre label v menu

public class FileWork {
	
	private String url;
	private ArrayList<ScorePlayer> arrOfScores;
	
	FileWork(String url) throws IOException {
		this.url = url;
		this.arrOfScores = this.sortScore();
		
	}
	
	public ArrayList<ScorePlayer> sortScore() throws IOException {
		ArrayList<ScorePlayer> arrOfScores = new ArrayList<ScorePlayer>();
		
		//nacitanie suboru
		BufferedReader br = new BufferedReader(new FileReader(this.url));
		
		//treba davat pozor na spravne zapisanie do dokumentu
		try {
			String temp[];
			String line = br.readLine();
			while(line!=null) {
				temp = line.split(" ");
				//osetrenie zleho suboru
				try {
					//pridanie riadku s menom a skore
					arrOfScores.add(new ScorePlayer(Integer.parseInt(temp[1]),temp[0]));
				} 
				
				catch (Exception e) {
					System.out.println("chyba");
				}
				
				line = br.readLine();
			}
		} finally {
			br.close();
			
		}
		
		Collections.sort(arrOfScores, ScorePlayer.stScoreComparator);
		
		return arrOfScores;		
			
	}
		
	
	
	//score pre label v menu
	//vrati prve 3 miesta
	public String getScore() throws IOException {
		
		String everything;
		BufferedReader br = new BufferedReader(new FileReader(url));
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
	
	//zapis zoradeneho skore do suboru
	public void writeScore() throws IOException {
		
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(url));
		PrintWriter out = new PrintWriter(bw);
		//kazdy jeden zo zoradeneho array sa zapise 
		try {
			for (ScorePlayer item : arrOfScores) {
				String line;
				line = item.getStName() +" "+ item.getstScore();
				out.println(line);
				
			}
			
			out.close();
		} finally {
			System.out.println("hotovo");
		}
		
		
	}
	
	//zapis jedneho riadku v tvare Meno + " " + score
	//prida nakoniec suboru so skore
	public void addScore(String line) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(url,true));
		PrintWriter out = new PrintWriter(bw);
		 
		try {
			out.println(line);
			out.close();
		} finally {
			System.out.println("hotovo");
		}
	}
}
