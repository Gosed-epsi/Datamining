/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Edgar
 */
public class Datamining {

    StopWords stopWords = new StopWords();

    private static Pattern pattern;
    private static Matcher matcher;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        JsonBuilder builder = new JsonBuilder();
        StopWords stopword = new StopWords();
        String[] tabStopWord = stopword.getRegEx().split("|");
        HashMap< Integer, String > map = new HashMap<>();
        int cpt = 0;
        boolean bStopWord = false;
        for(JsonEntity entity : builder.listeCommentaires){
            for () {
                
            }
            
            
            
            for (String word : entity.getCommentaires().split(" ")){
                //System.out.println(stopword.getRegEx());
                for(String stop : stopword.getRegEx().replace("|", " ").split(" ")){
                    //System.out.println(stop);
                    
                    word = word.replace(".", "").replace(",", "").replace("!", "").replace("(", "").replace(")", "").trim();
                    if((word.equalsIgnoreCase(stop)) && !word.equalsIgnoreCase("")){
                        bStopWord = true;   
                    }
                }
                if(bStopWord == false){
                    map.put(cpt, word);
                }
                bStopWord = false;
                cpt++;
            }
            
            
            //System.out.println(stopword.regEx);
        }
        
        for (Entry mapentry : map.entrySet()) {
           System.out.println(mapentry.getValue() + " ");
        }
        

    }
}
