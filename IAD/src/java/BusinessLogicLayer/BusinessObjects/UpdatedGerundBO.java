/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedGerundBO extends GerundBO{

    private int oldGerundId;
    private String oldStatus;

    public UpdatedGerundBO() {
        super();
    }

    public int getOldGerundId() {
        return oldGerundId;
    }

    public void setOldGerundId(int oldGerundId) {
        this.oldGerundId = oldGerundId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
