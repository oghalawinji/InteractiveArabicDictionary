/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedProperAdjectiveBO extends ProperAdjectiveBO{

    private int oldProperAdjectiveId;
    private String oldStatus;

    public UpdatedProperAdjectiveBO() {
        super();
    }

    public int getOldProperAdjectiveId() {
        return oldProperAdjectiveId;
    }

    public void setOldProperAdjectiveId(int oldProperAdjectiveId) {
        this.oldProperAdjectiveId = oldProperAdjectiveId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    
}
