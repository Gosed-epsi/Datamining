/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Edgar
 */
public class Datamining {

    StopWords stopWords = new StopWords();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        JsonBuilder builder = new JsonBuilder();

        for (JsonEntity entity : builder.listeCommentaires) {
            System.out.println("id : " + entity.getId());
        }

    }
}
