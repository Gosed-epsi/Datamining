/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import epsi.i5.datamining.repustate.RepustateClient;
import epsi.i5.datamining.repustate.RepustateException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Sinys Comment 108
 */
public class Treatment {

    JsonBuilder builder = new JsonBuilder();
    StopWords stopWords = new StopWords();
    public List<DataEntity> dataEnter = new ArrayList();
    List<JsonEntity> dataExit = new ArrayList();
    private final HashMap<String, HashMap<String, Integer>> mapCategorie = new HashMap();
    private final List<String> words = new ArrayList<>();
    private final List<String> polarites = new ArrayList();
    private float fiabilite = 0;

    /**
     * Main treatment
     *
     * @param file
     * @throws IOException
     * @throws MalformedURLException
     * @throws RepustateException
     * @throws ParseException
     */
    public void treatment(File file) throws IOException, MalformedURLException, RepustateException, ParseException {
        boolean bStopWord;
        for (DataEntity entity : builder.getFullCommentaires(file)) {

            fillCatMap(entity);

            entity.setCommentaireTrie(sortComment(entity));

            dataEnter.add(entity);
        }

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

            //Suppression des mots si ce n'est pas la valeur max
            for (Entry entry : mapCategorie.entrySet()) {
                HashMap mapDonnee = (HashMap) entry.getValue();
                if (mapDonnee.get(word) != max) {
                    mapDonnee.remove(word);
                }
                entry.setValue(mapDonnee);
            }
        }

        List<DataEntity> commentairesFinaux = builder.getSimpleCommentaires(file);
        for (DataEntity commentaire : commentairesFinaux) {
            List<String> categorie;
            for (String word : commentaire.getCommentaires().split(" ")) {
                //System.out.println(stopword.getRegEx());

                word = useStopWords(word);

                bStopWord = checkStopWords(word);

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

            }
            dataExit.add(commentaire);
        }

        calculAllpolarite();

        for (int i = 0; i < dataExit.size(); i++) {

            System.out.println("Expected     : " + dataEnter.get(i).getListeCategorie());
            System.out.println("Found        : " + dataExit.get(i).getListeCategorie());

            if (dataEnter.get(i).getListeCategorie().containsAll(dataExit.get(i).getListeCategorie())) {
                System.out.println("Consistency  : " + true);
                fiabilite++;
            } else {
                System.out.println("Consistency  : " + false);
            }

            System.out.println("Raiting      : " + polarites.get(i));

            System.out.println("****************************");
        }
        System.out.println("****************************");
        fiabilite = (fiabilite * 100) / dataExit.size();
        if (fiabilite != 100) {
            System.err.println("Success rate : " + fiabilite + "%");
        } else {
            System.out.println("Success rate : " + fiabilite + "%");
        }
    }

    /**
     * Method which fill the map with the categories and init the value
     *
     * @param entity
     */
    private void fillCatMap(DataEntity entity) {
        for (String cat : entity.getListeCategorie()) {
            if (!mapCategorie.containsKey(cat)) {
                mapCategorie.put(cat, new HashMap<String, Integer>());
            }
        }
    }

    /**
     * Method will call the API to calculatethe rating of each comment
     *
     * @param commentaire
     * @return rating
     * @throws IOException
     * @throws MalformedURLException
     * @throws RepustateException
     * @throws ParseException
     */
    public String calculPolarite(String commentaire) throws IOException, MalformedURLException, RepustateException, ParseException {
        String polarite;
        Double score = new Double("0");
        Map map = new HashMap();
        map.put("text1", commentaire);
//        System.out.println(RepustateClient.getSentimentBulk(map));
        JSONParser jp = new JSONParser();
        JSONObject json = (JSONObject) jp.parse(RepustateClient.getSentimentBulk(map));
//        System.out.println(json.get("results"));
        JSONArray jsonArray = (JSONArray) json.get("results");
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            score = new Double(jsonObject.get("score").toString());
////            score = score * 10;
////            System.out.println("Polarité : " + score);
        }
        polarite = score.toString();
        return polarite;
    }

    /**
     * Method which add rating of all comment and the list
     *
     * @throws IOException
     * @throws MalformedURLException
     * @throws RepustateException
     * @throws ParseException
     */
    public void calculAllpolarite() throws IOException, MalformedURLException, RepustateException, ParseException {

        for (DataEntity de : dataEnter) {
            polarites.add(calculPolarite(de.getCommentaireTrie()));
        }

    }

    /**
     * Extraction of the comment sorted from full comment with stop words
     *
     * @param entity
     * @return lTrie
     */
    private String sortComment(DataEntity entity) {
        String lTrie = "";
        boolean bStopWord = false;
        for (String word : entity.getCommentaires().split(" ")) {
            //System.out.println(stopword.getRegEx());
            word = useStopWords(word);

            bStopWord = checkStopWords(word);

            if (bStopWord == false && !"".equals(word)) {
                //Construction of comment sorted
                lTrie = lTrie + " " + word;

                //Remplissage de la map
                fillOccursCatMap(entity, word);

                if (!words.contains(word)) {
                    words.add(word);
                }

            }

        }
        return lTrie;
    }

    /**
     * This method will fill occurs the map of 'catégories'
     *
     * @param entity
     * @param word
     */
    private void fillOccursCatMap(DataEntity entity, String word) {
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
    }

    /**
     * Method which use the stop words
     *
     * @param aWord
     * @return
     */
    private String useStopWords(String aWord) {
        for (String stopApo : stopWords.getRegExApos().replace("|", " ").split(" ")) {
            aWord = aWord.replaceAll(stopApo, "");
//                    System.out.println(stopApo);
        }

        aWord = aWord.replace(".", " ").replace(",", " ").replace("!", " ").replace("(", "").replace(")", "").replace("'", "").replace(":", "").trim();
        return aWord;
    }

    /**
     * Method which check if stop words is found in comment
     *
     * @param aWord
     * @return
     */
    private boolean checkStopWords(String aWord) {
        boolean lFind = false;
        for (String stop : stopWords.getRegEx().replace("|", " ").split(" ")) {
            if ((aWord.equalsIgnoreCase(stop)) && !aWord.equalsIgnoreCase("")) {
                lFind = true;
            }
        }
        return lFind;
    }

}
