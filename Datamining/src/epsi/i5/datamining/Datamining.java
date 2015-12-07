/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        StopWords stopWords = new StopWords();
        for (JsonEntity entity : builder.listeCommentaires) {
            String newCom;
            newCom = entity.getCommentaires().toLowerCase().replaceAll(stopWords.getRegEx(), " ");
//            newCom = newCom.replaceAll("    ", " ");
//
//            newCom = newCom.replaceAll("  ", "");
            System.out.println("Nouveau commentaire : " + newCom);
            System.out.println("Commentaires : " + entity.getCommentaires());
        }

    }
}
