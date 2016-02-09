/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class AnnexedNounBO
{

    private int annexedNounId;
    private NounBO annexedNoun;
    private SemanticNounBO annexedNounMeaning;
    private String status;

    public int getAnnexedNounId()
    {
        return annexedNounId;
    }

    public void setAnnexedNounId( int annexedNounId )
    {
        this.annexedNounId = annexedNounId;
    }

    public NounBO getAnnexedNoun()
    {
        return annexedNoun;
    }

    public void setAnnexedNoun( NounBO annexedNoun )
    {
        this.annexedNoun = annexedNoun;
    }

    public SemanticNounBO getAnnexedNounMeaning()
    {
        return annexedNounMeaning;
    }

    public void setAnnexedNounMeaning( SemanticNounBO annexedNounMeaning )
    {
        this.annexedNounMeaning = annexedNounMeaning;
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
