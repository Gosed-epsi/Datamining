/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static List<DataEntity> lCommentaires;
    private static HashMap<String, HashMap<String, Integer>> mapCategorie = new HashMap();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        JsonBuilder builder = new JsonBuilder();
        StopWords stopword = new StopWords();
        boolean bStopWord = false;
        lCommentaires = builder.listeCommentaires;
        for (DataEntity entity : builder.listeCommentaires) {
            String lTrie = "";

            for (String cat : entity.getCategorie()) {
                if (!mapCategorie.containsKey(cat)) {
                    mapCategorie.put(cat, new HashMap<String, Integer>());
                }
            }

            for (String word : entity.getCommentaires().split(" ")) {
                //System.out.println(stopword.getRegEx());
                for (String stopApo : stopword.getRegExApos().replace("|", " ").split(" ")) {
                    word = word.replaceAll(stopApo, "");
//                    System.out.println(stopApo);
                }

                word = word.replace(".", " ").replace(",", " ").replace("!", " ").replace("(", "").replace(")", "").replace("'", "").replace(":", "").trim();

                for (String stop : stopword.getRegEx().replace("|", " ").split(" ")) {
                    //System.out.println(stop);
                    if ((word.equalsIgnoreCase(stop)) && !word.equalsIgnoreCase("")) {
                        bStopWord = true;
                    }
                }
                if (bStopWord == false && !"".equals(word)) {
                    lTrie = lTrie + " " + word;
                    for (String cat : entity.getCategorie()) {
                        if (mapCategorie.get(cat).containsKey(word)) {
                            Integer occurs = mapCategorie.get(cat).get(word);
                            occurs++;
                            mapCategorie.get(cat).remove(word);
                            mapCategorie.get(cat).put(word, occurs);
                        } else {
                            mapCategorie.get(cat).put(word, 1);
                        }
                    }
                }
                bStopWord = false;
            }
            entity.setCommentaireTrie(lTrie);
//            System.out.println(entity.getCommentaires());
//            System.out.println(entity.getCommentaireTrie());
        }

        for (Entry entry : mapCategorie.entrySet()) {
            System.out.println(entry);
        }
    }
}
