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
public class SemanticVerbTitle {

    private int sEntryId;
    private int sVerbId;
    private List<String> gerunds;
    private String meaning;
    private List<String> examples;
    private List<String> idioms;
    private List<String> benefits;
    private List<String> commonMistakes;
    private List<String> assimilatedAdjs;
    private List<String> exaggerations;

    public SemanticVerbTitle() {

    }

    public SemanticVerbTitle(int semanticEntryId, int semanticVerbId, List<String> gerunds, String meaning) {
        this.sEntryId = semanticEntryId;
        this.sVerbId = semanticVerbId;
        this.gerunds = gerunds;
        this.meaning = meaning;
    }

    public List<String> getGerunds() {
        return gerunds;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setGerunds(List<String> gerunds) {
        this.gerunds = gerunds;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getSemanticEntryId() {
        return getsEntryId();
    }

    public void setSemanticEntryId(int semanticEntryId) {
        this.setsEntryId(semanticEntryId);
    }

    public int getSemanticVerbId() {
        return getsVerbId();
    }

    public void setSemanticVerbId(int semanticVerbId) {
        this.setsVerbId(semanticVerbId);
    }

    public int getsEntryId() {
        return sEntryId;
    }

    public void setsEntryId(int sEntryId) {
        this.sEntryId = sEntryId;
    }

    public int getsVerbId() {
        return sVerbId;
    }

    public void setsVerbId(int sVerbId) {
        this.sVerbId = sVerbId;
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

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getCommonMistakes() {
        return commonMistakes;
    }

    public void setCommonMistakes(List<String> commonMistakes) {
        this.commonMistakes = commonMistakes;
    }

    public List<String> getAssimilatedAdjs() {
        return assimilatedAdjs;
    }

    public void setAssimilatedAdjs(List<String> assimilatedAdjs) {
        this.assimilatedAdjs = assimilatedAdjs;
    }

    public List<String> getExaggerations() {
        return exaggerations;
    }

    public void setExaggerations(List<String> exaggerations) {
        this.exaggerations = exaggerations;
    }

}
