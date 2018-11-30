package com.galaxian;

import java.util.Comparator;

/**
 * trieda pre pracu so score
 * 
 * @param int stScore = velkost skore daneho hraca
 * @param String stName = meno hraca
 * @author janci
 *
 */


public class ScorePlayer
{
    // instance variables - replace the example below with your own
    private String stName;
    private int stScore;


    /**
     * Constructor for objects of class Student
     */
    public ScorePlayer(int stScore, String stName)
    {
        // initialise instance variables
        this.stScore = stScore;
        this.stName = stName;
        
    }

    //vrati meno

    public String getStName()
    {
        
        return this.stName;
    }
    
    //nastavi meno

    public void setstName(String stName) {
        this.stName = stName;
    }
    //vrati skore
    public int getstScore() {
        return this.stScore;
    }
    
    //nastavi skore
    public void setstScore(int stScore) {
        this.stScore = stScore;
    }
    
 
    //yoradenie podla mena //nevyuzite
    public static Comparator<ScorePlayer> stNameComparator= new Comparator<ScorePlayer>() {
        public int compare(ScorePlayer a1, ScorePlayer a2) {
            String StudentMeno1 = a1.getStName().toUpperCase();
            String StudentMeno2 = a2.getStName().toUpperCase();
            //vzostupne
            return StudentMeno1.compareTo(StudentMeno2);
        }
    };

    //zoradenie podla skore
    public static Comparator<ScorePlayer> stScoreComparator= new Comparator<ScorePlayer>() {
        public int compare(ScorePlayer a1, ScorePlayer a2) {  //komparator nad objektami student, nazov stScoreKomp..
            int cislo1 = a1.getstScore();
            int cislo2 = a2.getstScore();
            return cislo2-cislo1;
        }
    };


 

    public String toString() {
        return "[ cislo=" + stScore + ", meno = "+stName+"]";
    }


}