/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Sinys
 */
public class Traitement {

    StopWords stopWords = new StopWords();
    private HashMap<String, HashMap<String, Integer>> mapCategorie = new HashMap();
    private List<String> words = new ArrayList<>();
    private List<List> jsonListCat = new ArrayList();
    private List<String> jsonSimpleCat = new ArrayList();
    private List<List> findCom = new ArrayList();
    private float fiabilite = 0;

    public void traitement() {
        JsonBuilder builder = new JsonBuilder();
        StopWords stopword = new StopWords();
        boolean bStopWord = false;
        for (DataEntity entity : builder.getFullCommentaires()) {
            if (entity.getListeCategorie() == null) {
                jsonSimpleCat.add(entity.getSimpleCategorie());
                jsonListCat = null;
            } else {
                jsonListCat.add(entity.getListeCategorie());
                jsonSimpleCat = null;
            }

            String lTrie = "";

            if (entity.getSimpleCategorie() == null) {
                for (String cat : entity.getListeCategorie()) {
                    if (!mapCategorie.containsKey(cat)) {
                        mapCategorie.put(cat, new HashMap<String, Integer>());
                    }
                }
            } else {
                if (!mapCategorie.containsKey(entity.getSimpleCategorie())) {
                    mapCategorie.put(entity.getSimpleCategorie(), new HashMap<String, Integer>());
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

                    if (entity.getSimpleCategorie() == null) {
                        //Remplissage de la map
                        for (String cat : entity.getListeCategorie()) {
                            if (mapCategorie.get(cat).containsKey(word)) {
                                Integer occurs = mapCategorie.get(cat).get(word);
                                occurs++;
                                mapCategorie.get(cat).remove(word);
                                mapCategorie.get(cat).put(word, occurs);
                            } else {
                                mapCategorie.get(cat).put(word, 1);
                            }
                        }
                    } else {
                        if (mapCategorie.get(entity.getSimpleCategorie()).containsKey(word)) {
                            Integer occurs = mapCategorie.get(entity.getSimpleCategorie()).get(word);
                            occurs++;
                            mapCategorie.get(entity.getSimpleCategorie()).remove(word);
                            mapCategorie.get(entity.getSimpleCategorie()).put(word, occurs);
                        } else {
                            mapCategorie.get(entity.getSimpleCategorie()).put(word, 1);
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

//        for (Map.Entry entry : mapCategorie.entrySet()) {
//            System.out.println(entry);
//        }
        //Recherche de la valeur max de chaque mots
        for (String word : words) {
            Integer max = 0;
            for (Entry entry : mapCategorie.entrySet()) {
                HashMap mapDonnee = (HashMap) entry.getValue();
                if (mapDonnee.containsKey(word)) {
                    if (max < (Integer) mapDonnee.get(word)) {
                        max = (Integer) mapDonnee.get(word);
                    }
                }
            }

//            System.out.println(max);
            //Suppression des mots si ce n'est pas al valuer max
            for (Entry entry : mapCategorie.entrySet()) {
                HashMap mapDonnee = (HashMap) entry.getValue();
                if (mapDonnee.get(word) != max) {
                    mapDonnee.remove(word);
                }
                entry.setValue(mapDonnee);
            }
        }

//        for (Entry entry : mapCategorie.entrySet()) {
//            System.out.println(entry);
//        }
        List<DataEntity> commentairesFinaux = builder.getSimpleCommentaires();
        for (DataEntity commentaire : commentairesFinaux) {
            List<String> categorie;
            for (String word : commentaire.getCommentaires().split(" ")) {
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
                if (bStopWord == false) {
                    for (Entry entry : mapCategorie.entrySet()) {
                        HashMap mapDonnee = (HashMap) entry.getValue();
                        if (mapDonnee.containsKey(word) && !"".equals(word)) {
                            categorie = commentaire.getListeCategorie();
                            if (!categorie.contains((String) entry.getKey())) {
                                categorie.add((String) entry.getKey());
                            }
                            commentaire.setListeCategorie(categorie);
                        }
                    }
                }
                bStopWord = false;
            }
            findCom.add(commentaire.getListeCategorie());
        }
        for (int i = 0; i < findCom.size(); i++) {

            if (jsonListCat == null) {
                System.out.println("Expected : " + jsonSimpleCat.get(i));
            } else {
                System.out.println("Expected : " + jsonListCat.get(i));
            }

            System.out.println("Found    : " + findCom.get(i));

            if (jsonListCat == null) {
                if (findCom.get(i).contains(jsonSimpleCat.get(i))) {
                    System.out.println(true);
                    fiabilite++;
                } else {
                    System.out.println(false);
                }
            } else {
                if (jsonListCat.get(i).containsAll(findCom.get(i))) {
                    System.out.println(true);
                    fiabilite++;
                } else {
                    System.out.println(false);
                }
            }

            System.out.println("****************************");
        }
        fiabilite = (fiabilite * 100) / findCom.size();
        System.out.println(fiabilite + "%");
    }

}
