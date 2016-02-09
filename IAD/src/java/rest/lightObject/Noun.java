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
public class Noun {

    private int id;
    private String type;
    private String vocalized;
    private String pattern;
    private String root;
    private String gender;
    private String number;
    private String origin;
    private List<SemanticNounTitle> semanticTitles;

    public Noun() {
    }

    public Noun(String type, String vocalized, String pattern, String root, String gender, String number, String origin, List<SemanticNounTitle> semanticTitles) {
        this.type = type;
        this.vocalized = vocalized;
        this.pattern = pattern;
        this.root = root;
        this.gender = gender;
        this.number = number;
        this.origin = origin;
        this.semanticTitles = semanticTitles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<SemanticNounTitle> getSemanticTitles() {
        return semanticTitles;
    }

    public void setSemanticTitles(List<SemanticNounTitle> semanticTitles) {
        this.semanticTitles = semanticTitles;
    }

}
