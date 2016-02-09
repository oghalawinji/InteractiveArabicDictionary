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
public class Verb {

    private int id;
    private String vocalized;
    private String present;
    private String pattern;
    private String root;
    private List<SemanticVerbTitle> semanticTitles;

    public Verb() {
    }

    public Verb(int id, String vocalized, String present, String pattern, String root, List<SemanticVerbTitle> semanticTitles) {
        this.id = id;
        this.vocalized = vocalized;
        this.present = present;
        this.pattern = pattern;
        this.root = root;
        this.semanticTitles = semanticTitles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVocalized() {
        return vocalized;
    }

    public void setVocalized(String vocalized) {
        this.vocalized = vocalized;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<SemanticVerbTitle> getSemanticTitles() {
        return semanticTitles;
    }

    public void setSemanticTitles(List<SemanticVerbTitle> semanticTitles) {
        this.semanticTitles = semanticTitles;
    }

}
