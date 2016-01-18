/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/**
 *
 * @author Sinys
 */
public class Weka {
   
        
        //Listes des attributs
        ArrayList<Attribute> attInfo = new ArrayList<>();
        //Création de l'instance
        Instances instances;
        
        public Weka(){
            //Création des attributs
            attInfo.add(new Attribute("commentaire", (FastVector) null));
            attInfo.add(new Attribute("categorie", (FastVector) null));
            attInfo.add(new Attribute("polarité"));      
            instances = new Instances("ClasserSE", attInfo, 0);
        }
        
        public void chargementData(List<DataEntity> ListEntity){
            //Generation des datas
            double[] vals =  new double[instances.numAttributes()];
            
            for(DataEntity entity : ListEntity){
                
                for(String categ : entity.getListeCategorie()){
                    vals[0] = instances.attribute(0).addStringValue(entity.getCommentaireTrie());
                    vals[1] = instances.attribute(1).addStringValue(categ);
                    vals[2] = Double.parseDouble((String) entity.getPolarite());
                    instances.add(new DenseInstance(1, vals));
                    //Purge vals
                    vals =  new double[instances.numAttributes()];
                }
            }
        }
        
        public void generationArff() throws IOException
        {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(instances);
            saver.setFile(new File("src/epsi/i5/data/projet.arff"));
            saver.writeBatch();
        }
}
