/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedExaggerationBO extends ExaggerationBO{

    private int oldExaggerationId;
    private String oldStatus;

    public UpdatedExaggerationBO() {
        super();
    }

    public int getOldExaggerationId() {
        return oldExaggerationId;
    }

    public void setOldExaggerationId(int oldExaggerationId) {
        this.oldExaggerationId = oldExaggerationId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
