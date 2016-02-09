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
public class SemanticNounTitle {

    private String meaning;
    private List<String> femininities;
    private List<String> diminutives;
    private List<String> properAdjs;
    private List<String> examples;
    private List<String> idioms;
    private List<String> linguisticBenefits;
    private List<String> commonMistakes;
    private List<String> plurals;

    public SemanticNounTitle() {
    }

    public SemanticNounTitle(String meaning, List<String> femininities, List<String> diminutives, List<String> properAdjs, List<String> examples, List<String> idioms, List<String> linguisticBenefits, List<String> commonMistakes, List<String> plurals) {
        this.meaning = meaning;
        this.femininities = femininities;
        this.diminutives = diminutives;
        this.properAdjs = properAdjs;
        this.examples = examples;
        this.idioms = idioms;
        this.linguisticBenefits = linguisticBenefits;
        this.commonMistakes = commonMistakes;
        this.plurals = plurals;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<String> getFemininities() {
        return femininities;
    }

    public void setFemininities(List<String> femininities) {
        this.femininities = femininities;
    }

    public List<String> getDiminutives() {
        return diminutives;
    }

    public void setDiminutives(List<String> diminutives) {
        this.diminutives = diminutives;
    }

    public List<String> getProperAdjs() {
        return properAdjs;
    }

    public void setProperAdjs(List<String> properAdjs) {
        this.properAdjs = properAdjs;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getIdioms() {
        return idioms;
    }

    public void setIdioms(List<String> idioms) {
        this.idioms = idioms;
    }

    public List<String> getLinguisticBenefits() {
        return linguisticBenefits;
    }

    public void setLinguisticBenefits(List<String> linguisticBenefits) {
        this.linguisticBenefits = linguisticBenefits;
    }

    public List<String> getCommonMistakes() {
        return commonMistakes;
    }

    public void setCommonMistakes(List<String> commonMistakes) {
        this.commonMistakes = commonMistakes;
    }

    public List<String> getPlurals() {
        return plurals;
    }

    public void setPlurals(List<String> plurals) {
        this.plurals = plurals;
    }
    

}
