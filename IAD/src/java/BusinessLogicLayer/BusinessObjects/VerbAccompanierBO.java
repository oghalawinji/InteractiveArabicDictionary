/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class VerbAccompanierBO
{

    private int verbAccompanierId;
    private VerbBO verbAccompanier;
    private SemanticVerbBO verbAccompanierMeaning;
    private String status;

    public int getVerbAccompanierId()
    {
        return verbAccompanierId;
    }

    public void setVerbAccompanierId( int verbAccompanierId )
    {
        this.verbAccompanierId = verbAccompanierId;
    }

    public VerbBO getVerbAccompanier()
    {
        return verbAccompanier;
    }

    public void setVerbAccompanier( VerbBO verbAccompanier )
    {
        this.verbAccompanier = verbAccompanier;
    }

    public SemanticVerbBO getVerbAccompanierMeaning()
    {
        return verbAccompanierMeaning;
    }

    public void setVerbAccompanierMeaning( SemanticVerbBO verbAccompanierMeaning )
    {
        this.verbAccompanierMeaning = verbAccompanierMeaning;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }
}
