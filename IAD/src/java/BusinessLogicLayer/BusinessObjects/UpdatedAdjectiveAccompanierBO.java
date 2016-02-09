/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedAdjectiveAccompanierBO extends AdjectiveAccompanierBO{

    private int oldAdjectiveAccompanierId;
    private String oldStatus;

    public UpdatedAdjectiveAccompanierBO() {
        super();
    }

    public int getOldAdjectiveAccompanierId() {
        return oldAdjectiveAccompanierId;
    }

    public void setOldAdjectiveAccompanierId(int oldAdjectiveAccompanierId) {
        this.oldAdjectiveAccompanierId = oldAdjectiveAccompanierId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
