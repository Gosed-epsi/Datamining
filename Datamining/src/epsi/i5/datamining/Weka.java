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
import java.util.Random;
import weka.attributeSelection.AttributeSelection;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
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
        String fileOne = "NewARFFOne";
        
        public Weka(){
            
        }
        
        public void setFileName(String file){
            fileOne = file;
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
            attInfo.add(new Attribute("polarité"));      
            instances = new Instances("ClasserSE", attInfo, 0);
            
            //Generation des datas
            double[] vals =  new double[instances.numAttributes()];
            
            for(DataEntity entity : ListEntity){
                
                for(String categ : entity.getListeCategorie()){
                    //System.out.println(categ);
                    vals[0] = instances.attribute(0).addStringValue(entity.getCommentaireTrie());
                    vals[1] = listCat.indexOf(categ);//instances.classAttribute().indexOfValue(categ);
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
            saver.setFile(new File("src/epsi/i5/data/"+ fileOne +".arff"));
            saver.writeBatch();
        }
        
        public void generationArffFilter() throws IOException, Exception{
        
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/epsi/i5/data/"+ fileOne +".arff"));
            Instances data = new Instances(reader);
            reader.close();
                          
            StringToWordVector filter = new StringToWordVector(); 
            filter.setInputFormat(data);
            instances = Filter.useFilter(data, filter);
            fileOne = fileOne + "Two";
            generationArff();
        }
        
        public void excutionAlgo() throws FileNotFoundException, IOException, Exception{
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/epsi/i5/data/"+ fileOne +".arff"));
            Instances data = new Instances(reader);
            reader.close();
            //System.out.println(data.attribute(0));
            data.setClass(data.attribute(0));
            NaiveBayes NB = new NaiveBayes();
            NB.buildClassifier(data);
            Evaluation naiveBayes = new Evaluation(data);
            naiveBayes.crossValidateModel(NB, data, 10, new Random(1));
            naiveBayes.evaluateModel(NB, data);
            //System.out.println(test.confusionMatrix() + "1");
            //System.out.println(test.correct() + "2");
            System.out.println("*****************************");
            System.out.println("******** Naive Bayes ********");
            System.out.println(naiveBayes.toMatrixString());
            System.out.println("*****************************");
            System.out.println("**** Pourcentage Correct ****");
            System.out.println(naiveBayes.pctCorrect());
            System.out.println("");
            J48 j = new J48();
            j.buildClassifier(data);
            Evaluation jeval = new Evaluation(data);
            jeval.crossValidateModel(j, data, 10, new Random(1));
            jeval.evaluateModel(j, data);
            System.out.println("*****************************");
            System.out.println("************ J48 ************");
            System.out.println(jeval.toMatrixString());
            System.out.println("*****************************");
            System.out.println("**** Pourcentage Correct ****");
            System.out.println(jeval.pctCorrect());
            System.out.println("");
            DecisionTable DT = new DecisionTable();
            DT.buildClassifier(data);
            Evaluation decisionTable = new Evaluation(data);
            decisionTable.crossValidateModel(DT, data, 10, new Random(1));
            decisionTable.evaluateModel(DT, data);
            System.out.println("*****************************");
            System.out.println("******* DecisionTable *******");
            System.out.println(decisionTable.toMatrixString());
            System.out.println("*****************************");
            System.out.println("**** Pourcentage Correct ****");
            System.out.println(decisionTable.pctCorrect());
            System.out.println("");
            OneR OR = new OneR();
            OR.buildClassifier(data);
            Evaluation oneR = new Evaluation(data);
            oneR.crossValidateModel(OR, data, 10, new Random(1));
            oneR.evaluateModel(OR, data);
            System.out.println("*****************************");
            System.out.println("************ OneR ***********");
            System.out.println(oneR.toMatrixString());
            System.out.println("*****************************");
            System.out.println("**** Pourcentage Correct ****");
            System.out.println(oneR.pctCorrect());
            
            
            
            //Polarité
            data.setClass(data.attribute(1));
            System.out.println("");
            M5Rules MR = new M5Rules();
            MR.buildClassifier(data);
            Evaluation m5rules = new Evaluation(data);
            m5rules.crossValidateModel(MR, data, 10, new Random(1));
            m5rules.evaluateModel(MR, data);
            System.out.println("*****************************");
            System.out.println("********** M5Rules **********");
            System.out.println(m5rules.correlationCoefficient());

            System.out.println("");
            LinearRegression LR = new LinearRegression();
            LR.buildClassifier(data);
            Evaluation linearR = new Evaluation(data);
            linearR.crossValidateModel(LR, data, 10, new Random(1));
            linearR.evaluateModel(LR, data);
            System.out.println("*****************************");
            System.out.println("********** linearR **********");
            System.out.println(linearR.correlationCoefficient());
        }
        
        
}
