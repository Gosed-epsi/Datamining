/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.i5.datamining;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edgar
 */
public class JsonEntity {

    private Object id;
    private String commentaires;
    private Object polarite;
    private List<String> listeCategorie = new ArrayList();
    private String simpleCategorie;

    public String getSimpleCategorie() {
        return simpleCategorie;
    }

    public void setSimpleCategorie(String simpleCategorie) {
        this.simpleCategorie = simpleCategorie;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Object getPolarite() {
        return polarite;
    }

    public void setPolarite(Object polarite) {
        this.polarite = polarite;
    }

    public List<String> getListeCategorie() {
        return listeCategorie;
    }

    public void setListeCategorie(List<String> listeCategorie) {
        this.listeCategorie = listeCategorie;
    }

}
