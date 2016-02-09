/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class EntryBO {
    private String vocalizedString;
    private String status;
    private int id;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getVocalizedString() {
        return vocalizedString;
    }

    public void setVocalizedString(String vocalizedString) {
        this.vocalizedString = vocalizedString;
    }

}
