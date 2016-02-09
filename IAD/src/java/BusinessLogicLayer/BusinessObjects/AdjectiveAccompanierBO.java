/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class AdjectiveAccompanierBO
{
    private int adjectiveAccompanierId;
    private NounBO adjectiveAccompanier;
    private SemanticNounBO adjectiveAccompanierMeaning;
    private String status;

    public int getAdjectiveAccompanierId()
    {
        return adjectiveAccompanierId;
    }

    public void setAdjectiveAccompanierId( int adjectiveAccompanierId )
    {
        this.adjectiveAccompanierId = adjectiveAccompanierId;
    }

    public NounBO getAdjectiveAccompanier()
    {
        return adjectiveAccompanier;
    }

    public void setAdjectiveAccompanier( NounBO adjectiveAccompanier )
    {
        this.adjectiveAccompanier = adjectiveAccompanier;
    }

    public SemanticNounBO getAdjectiveAccompanierMeaning()
    {
        return adjectiveAccompanierMeaning;
    }

    public void setAdjectiveAccompanierMeaning( SemanticNounBO adjectiveAccompanierMeaning )
    {
        this.adjectiveAccompanierMeaning = adjectiveAccompanierMeaning;
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
