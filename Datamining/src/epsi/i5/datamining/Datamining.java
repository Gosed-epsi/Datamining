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
    public static void main(String[] args) throws IOException, MalformedURLException, RepustateException, ParseException, Exception {

        Treatment traitement = new Treatment();
        traitement.treatment();


        Weka arff = new Weka();
        arff.excutionAlgo();
        /* Score example */
//        Map map = new HashMap();
//        map.put("text1", "Hôtel très bien tenu, idéalement situé. Le check in et check out sont très rapidement effectués. Le petit déjeuner, très copieux et en formule 'à volonté' est délicieux. Les chambres sont calmes, propres. A recommander !");
//        map.put("text2", "C'est un hôtel de chaîne aux standards Ibis donc ni bonne ni mauvaise surprise. La chambre n'est pas grande mais très fonctionnelle. Salle de bains avec baignoire. Les deux points forts sont l'emplacement, à une dizaine de minutes à pieds de la gare de Saint Pancras, au pied du métro Euston qui amène très rapidement au centre de Londres.");
//        map.put("text3", "accueil très agréable à la réception , hotel idéalement situé,à proximité des gares de St Pancras et Euston et à deux pas du métro parfaitement isolé avec une déco tout à la fois reposante et moderne");
//        map.put("text4", "Hôtel très sympa, accueillant et chaleureux, emplacement idéal, chambre tout à fait correcte, petit-déjeuner extra et personnel du restaurant très professionnel! Un clin d'oeil spécial à Aurélia et Tomas (ainsi qu'à la fille qui a fait notre C/I)! Merci pour votre accueil, disponibilité, gentillesse et sourire!!! C'était super! Kristel L.");
//        map.put("text5", "Bon accueil, moderne, chambre prête dès l'arrivée et propre. Salle de bain nickel.Ménage et changement serviettes tous les jours. Petit déjeuner anglais excellent en buffet (même si pas beaucoup de place si affluence) et le resto est pas mal (hamburger recommandé). Hôtel pas loin de St Pancras à pied et métro Euston à 5mn pour les sorties quotidiennes. Je conseille.");
//        map.put("text6", "Hotel tres bien placé pour ceux qui arrivent et repartent de saint pancras. Il a été entièrement refait, est assez moder est bien décoré. Le restaurant est sympa, lumineux et le petit-déjeuner tres copieux et bons. Les chambres sont petites et moins design, mais fonctionnelles et confortables.");
//        map.put("text7", "Impossibilité de téléphoner sur Londres de ma chambre: problème non résolu en 3 jours! Très difficile de commander des boissons au bar, attente ++ et obligation d'aller chercher ses boisons... Des détails certes, mais à revoir d'urgence!");
//        System.out.println(RepustateClient.getSentimentBulk(map));
//        JSONParser jp = new JSONParser();
//        JSONObject json = (JSONObject) jp.parse(RepustateClient.getSentimentBulk(map));
//        System.out.println(json.get("results"));
//        JSONArray jsonArray = (JSONArray) json.get("results");
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//            String id = (String) jsonObject.get("id");
//            Double score = new Double(jsonObject.get("score").toString());
//            System.out.println(id + " : " + score * 10);
//        }
    }

}
