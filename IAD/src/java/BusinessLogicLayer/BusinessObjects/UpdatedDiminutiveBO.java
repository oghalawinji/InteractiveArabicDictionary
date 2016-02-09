/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedDiminutiveBO extends DiminutiveBO{

    private int oldDiminutiveId;
    private String oldStatus;

    public UpdatedDiminutiveBO() {
        super();
    }

    public int getOldDiminutiveId() {
        return oldDiminutiveId;
    }

    public void setOldDiminutiveId(int oldDiminutiveId) {
        this.oldDiminutiveId = oldDiminutiveId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
