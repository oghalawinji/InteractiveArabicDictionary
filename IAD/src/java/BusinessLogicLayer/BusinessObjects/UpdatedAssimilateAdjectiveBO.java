/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedAssimilateAdjectiveBO extends AssimilateAdjectiveBO{

    private int oldAssimilateAdjectiveId;
    private String oldStatus;

    public UpdatedAssimilateAdjectiveBO() {
        super();
    }

    public int getOldAssimilateAdjectiveId() {
        return oldAssimilateAdjectiveId;
    }

    public void setOldAssimilateAdjectiveId(int oldAssimilateAdjectiveId) {
        this.oldAssimilateAdjectiveId = oldAssimilateAdjectiveId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
