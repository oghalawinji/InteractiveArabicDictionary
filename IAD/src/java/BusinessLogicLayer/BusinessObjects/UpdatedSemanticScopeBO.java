/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedSemanticScopeBO extends SemanticScopeBO{

    private Integer oldSemanticScopeId;
    private String oldSemanticScopeTitle;

    public UpdatedSemanticScopeBO() {
        super();
    }

    public Integer getOldSemanticScopeId() {
        return oldSemanticScopeId;
    }

    public void setOldSemanticScopeId(Integer oldSemanticScopeId) {
        this.oldSemanticScopeId = oldSemanticScopeId;
    }

    public String getOldSemanticScopeTitle() {
        return oldSemanticScopeTitle;
    }

    public void setOldSemanticScopeTitle(String oldSemanticScopeTitle) {
        this.oldSemanticScopeTitle = oldSemanticScopeTitle;
    }

}
