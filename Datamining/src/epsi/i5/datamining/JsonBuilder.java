/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Edgar
 */
public class JsonBuilder {

    private JSONParser parser = new JSONParser();
    public List<JsonEntity> listeCommentaires = new ArrayList<>();

    public JsonBuilder() {
        try {

            Object objFile = parser.parse(new FileReader("D:\\GitRepository\\Datamining\\Datamining\\src\\epsi\\i5\\data\\commentaires_tripadvisor.json"));

            JSONArray jsonArray = (JSONArray) objFile;
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JsonEntity commentaire = new JsonEntity();
                commentaire.setId((String) jsonObject.get("id"));
                commentaire.setCommentaires((String) jsonObject.get("commentaires"));
                commentaire.setPolarite((String) jsonObject.get("polarité"));
                commentaire.setCategorie((List) jsonObject.get("catégorie"));

                listeCommentaires.add(commentaire);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue lors de lecture du ficheir JSON");
            System.out.println("Cause : " + e.getCause());
            System.out.println("Message : " + e.getMessage());
        } catch (IOException | ParseException ex) {
            Logger.getLogger(JsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
