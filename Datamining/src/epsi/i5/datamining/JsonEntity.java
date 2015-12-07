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

    private String id;
    private String commentaires;
    private String polarite;
    private List<String> categorie = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public String getPolarite() {
        return polarite;
    }

    public void setPolarite(String polarite) {
        this.polarite = polarite;
    }

    public List<String> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<String> categorie) {
        this.categorie = categorie;
    }

}
