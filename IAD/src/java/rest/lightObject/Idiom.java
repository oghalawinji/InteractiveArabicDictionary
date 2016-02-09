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
public class Idiom {
    private String type;
    private String vocalized;
    private List<String> meanings;
    private List<String> examples;

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

    public List<String> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<String> meanings) {
        this.meanings = meanings;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }
    
}
