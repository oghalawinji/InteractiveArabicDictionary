/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedEntryBO extends EntryBO{

    private String oldVocalizedString;
    private String oldStatus;
    private int oldId;

    public UpdatedEntryBO() {
        super();
    }

    public int getOldId() {
        return oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getOldVocalizedString() {
        return oldVocalizedString;
    }

    public void setOldVocalizedString(String oldVocalizedString) {
        this.oldVocalizedString = oldVocalizedString;
    }

}
