/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Edgar
 */
public class StopWords {

    private String regEx = "";

    public StopWords() {
        try {
            File f1 = new File("/Users/Sinys/MEGAsync/EPSI/Projet/Datamining/Datamining/src/epsi/i5/data/stop-words_french_1_fr.txt");
            File f2 = new File("/Users/Sinys/MEGAsync/EPSI/Projet/Datamining/Datamining/src/epsi/i5/data/stop-words_french_2_fr.txt");
            File f3 = new File("/Users/Sinys/MEGAsync/EPSI/Projet/Datamining/Datamining/src/epsi/i5/data/stop-words_french.txt");
            FileReader fr1 = new FileReader(f1);
            FileReader fr2 = new FileReader(f2);
            FileReader fr3 = new FileReader(f3);
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);
            BufferedReader br3 = new BufferedReader(fr3);
            
            try {
                String line1 = br1.readLine();
                while (line1 != null) {
                    if (!line1.equals("")) {
//                        System.out.println(line1);
                        regEx = regEx.concat(line1 + " | ");
                    }
                    line1 = br1.readLine();
                }
                br1.close();
                fr1.close();

                String line2 = br2.readLine();
                while (line2 != null) {
                    if (!line2.equals("")) {
//                        System.out.println(line2);
                        regEx = regEx.concat(line2 + " | ");
                    }
                    line2 = br2.readLine();
                }
                br2.close();
                fr2.close();
                
                String line3 = br3.readLine();
                while (line3 != null) {
                    if (!line3.equals("")) {
//                        System.out.println(line2);
                        regEx = regEx.concat(line3 + " | ");
                    }
                    line3 = br3.readLine();
                }
                br3.close();
                fr3.close();

                //new Regex("^(this|is|about|after|all|also)$");
                regEx = regEx.substring(0, regEx.length() - 1);
                //System.out.println(regEx);
            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Le fichier n'a pas été trouvé");
            System.out.println("Cause : " + exception.getMessage());
        }
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

}
