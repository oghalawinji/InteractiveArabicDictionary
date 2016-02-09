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
public class SemanticRelationBO {
    
    private String relationName;
    private List<SemanticScopeBO> semanticScope;

    public SemanticRelationBO() {
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public List<SemanticScopeBO> getSemanticScope() {
        return semanticScope;
    }

    public void setSemanticScope(List<SemanticScopeBO> semanticScope) {
        this.semanticScope = semanticScope;
    }


    
}
