/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sinys
 */
public class Traitement {

    StopWords stopWords = new StopWords();
    private List<DataEntity> lCommentaires;
    private HashMap<String, HashMap<String, Integer>> mapCategorie = new HashMap();
    private List<String> words = new ArrayList<>();

    public void traitement() {
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

                    //Remplissage de la map
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

                    if (!words.contains(word)) {
                        words.add(word);
                    }

                }
                bStopWord = false;
            }
            entity.setCommentaireTrie(lTrie);
//            System.out.println(entity.getCommentaires());
//            System.out.println(entity.getCommentaireTrie());
        }

        for (Map.Entry entry : mapCategorie.entrySet()) {
            System.out.println(entry);
        }

        for (String word : words) {
            Integer max = 0;
            HashMap catMax;
            for (Map.Entry entry : mapCategorie.entrySet()) {
//                if ()
            }
        }
    }

}
