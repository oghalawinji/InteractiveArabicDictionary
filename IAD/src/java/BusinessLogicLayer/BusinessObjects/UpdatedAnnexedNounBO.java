/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedAnnexedNounBO extends AnnexedNounBO{

    private int oldAnnexedNounId;
    private String oldStatus;

    public UpdatedAnnexedNounBO() {
        super();
    }

    public int getOldAnnexedNounId() {
        return oldAnnexedNounId;
    }

    public void setOldAnnexedNounId(int oldAnnexedNounId) {
        this.oldAnnexedNounId = oldAnnexedNounId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
