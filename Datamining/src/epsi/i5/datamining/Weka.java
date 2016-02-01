/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weka.attributeSelection.AttributeSelection;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

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
            
        }
        
        public void chargementData(List<DataEntity> ListEntity){
            
            //En tête class Weka
            List<String> listCat = new ArrayList();
            for(DataEntity entity : ListEntity){
                for (String laCat : entity.getListeCategorie()){
                    if(!listCat.contains(laCat)){
                        listCat.add(laCat);
                    }
                }
            }
            
            //Création des attributs
            attInfo.add(new Attribute("commentaire", (FastVector) null));
            attInfo.add(new Attribute("categorie", listCat));
           // attInfo.add(new Attribute("polarité"));      
            instances = new Instances("ClasserSE", attInfo, 0);
            
            //Generation des datas
            double[] vals =  new double[instances.numAttributes()];
            
            for(DataEntity entity : ListEntity){
                
                for(String categ : entity.getListeCategorie()){
                    //System.out.println(categ);
                    vals[0] = instances.attribute(0).addStringValue(entity.getCommentaireTrie());
                    vals[1] = listCat.indexOf(categ);//instances.classAttribute().indexOfValue(categ);
                    //vals[2] = Double.parseDouble((String) entity.getPolarite());
                    instances.add(new DenseInstance(1, vals));
                    //Purge vals
                    vals =  new double[instances.numAttributes()];
               
                }
            }
        }
        
        public void generationArff(String nameFile) throws IOException
        {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(instances);
            saver.setFile(new File("src/epsi/i5/data/"+ nameFile +".arff"));
            saver.writeBatch();
        }
        
        public void generationArffFilter() throws IOException, Exception{
        
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/epsi/i5/data/Step_One.arff"));
            Instances data = new Instances(reader);
            reader.close();
                          
            StringToWordVector filter = new StringToWordVector(); 
            filter.setInputFormat(data);
            instances = Filter.useFilter(data, filter);
            generationArff("Step_Two");
        }
        
        public void excutionAlgo() throws FileNotFoundException, IOException, Exception{
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/epsi/i5/data/Step_Two.arff"));
            Instances data = new Instances(reader);
            reader.close();
            //System.out.println(data.attribute(0));
            data.setClass(data.attribute(0));
            NaiveBayes NB = new NaiveBayes();
            NB.buildClassifier(data);
            Evaluation test = new Evaluation(data);
            test.evaluateModel(NB, data);
            System.out.println(test.confusionMatrix());
            System.out.println(test.correct());
            System.out.println(test. toMatrixString());
        }
        
        
}
