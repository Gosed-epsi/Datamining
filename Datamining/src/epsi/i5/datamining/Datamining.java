/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import epsi.i5.datamining.repustate.RepustateException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Edgar
 */
public class Datamining {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.MalformedURLException
     * @throws epsi.i5.datamining.repustate.RepustateException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, MalformedURLException, RepustateException, ParseException {

        Treatment traitement = new Treatment();
        traitement.treatment();

    }

}
