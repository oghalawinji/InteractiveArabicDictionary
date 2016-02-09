/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class NounAccompanierBO
{

    private int nounAccompanierId;
    private NounBO accompanierNoun;
    private SemanticNounBO accompanierNounMeaning;
    private String status;

    public int getNounAccompanierId()
    {
        return nounAccompanierId;
    }

    public void setNounAccompanierId( int nounAccompanierId )
    {
        this.nounAccompanierId = nounAccompanierId;
    }

    public NounBO getAccompanierNoun()
    {
        return accompanierNoun;
    }

    public void setAccompanierNoun( NounBO accompanierNoun )
    {
        this.accompanierNoun = accompanierNoun;
    }

    public SemanticNounBO getAccompanierNounMeaning()
    {
        return accompanierNounMeaning;
    }

    public void setAccompanierNounMeaning( SemanticNounBO accompanierNounMeaning )
    {
        this.accompanierNounMeaning = accompanierNounMeaning;
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
