/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedSemanticRelationBO extends SemanticRelationBO{

    private String oldRelationName;

    public UpdatedSemanticRelationBO() {
        super();
    }

    public String getOldRelationName() {
        return oldRelationName;
    }

    public void setOldRelationName(String oldRelationName) {
        this.oldRelationName = oldRelationName;
    }



}
