/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedVerbAccompanierBO extends VerbAccompanierBO{

    private int oldVerbAccompanierId;
    private String oldVtatus;

    public UpdatedVerbAccompanierBO() {
        super();
    }

    public int getOldVerbAccompanierId() {
        return oldVerbAccompanierId;
    }

    public void setOldVerbAccompanierId(int oldVerbAccompanierId) {
        this.oldVerbAccompanierId = oldVerbAccompanierId;
    }

    public String getOldVtatus() {
        return oldVtatus;
    }

    public void setOldVtatus(String oldVtatus) {
        this.oldVtatus = oldVtatus;
    }
    
}
