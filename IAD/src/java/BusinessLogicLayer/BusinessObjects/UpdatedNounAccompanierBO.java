/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedNounAccompanierBO extends NounAccompanierBO{

    private int oldNounAccompanierId;
    private String oldStatus;

    public UpdatedNounAccompanierBO() {
        super();
    }

    public int getOldNounAccompanierId() {
        return oldNounAccompanierId;
    }

    public void setOldNounAccompanierId(int oldNounAccompanierId) {
        this.oldNounAccompanierId = oldNounAccompanierId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
