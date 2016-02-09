/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedFemininityBO extends FemininityBO{

    private int oldFemininityId;
    private String oldStatus;

    public UpdatedFemininityBO() {
        super();
    }

    public int getOldFemininityId() {
        return oldFemininityId;
    }

    public void setOldFemininityId(int oldFemininityId) {
        this.oldFemininityId = oldFemininityId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    
}
