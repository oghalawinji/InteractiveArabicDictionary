/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

import java.util.List;

/**
 *
 * @author riad
 */
public class SemanticScopeBO {
    
    private Integer semanticScopeId;
    private String SemanticScopeTitle;
    private List<String> elements;
    private List<SemanticRelationBO> semanticRelation;

    public SemanticScopeBO() {
    }

    public List<SemanticRelationBO> getSemanticRelation() {
        return semanticRelation;
    }

    public void setSemanticRelation(List<SemanticRelationBO> semanticRelation) {
        this.semanticRelation = semanticRelation;
    }


    public String getSemanticScopeTitle() {
        return SemanticScopeTitle;
    }

    public void setSemanticScopeTitle(String SemanticScopeTitle) {
        this.SemanticScopeTitle = SemanticScopeTitle;
    }

    public Integer getSemanticScopeId() {
        return semanticScopeId;
    }

    public void setSemanticScopeId(Integer semanticScopeId) {
        this.semanticScopeId = semanticScopeId;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    
}
