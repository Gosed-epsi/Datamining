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

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        Traitement traitement = new Traitement();
        traitement.traitement();

    }
}
