/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.lightObject;

import java.util.List;

/**
 *
 * @author Omar
 */
public class Particle {
    private String type;
    private String vocalized;
    private String root;
    List<SemanticParticleTitle> semanticTitles;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVocalized() {
        return vocalized;
    }

    public void setVocalized(String vocalized) {
        this.vocalized = vocalized;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<SemanticParticleTitle> getSemanticTitles() {
        return semanticTitles;
    }

    public void setSemanticTitles(List<SemanticParticleTitle> semanticTitles) {
        this.semanticTitles = semanticTitles;
    }
    
}
