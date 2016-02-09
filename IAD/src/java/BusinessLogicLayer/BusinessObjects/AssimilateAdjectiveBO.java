/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class AssimilateAdjectiveBO
{
    private int assimilateAdjectiveId;
    private NounBO assimilateAdjective;
    private SemanticNounBO assimilateAdjectiveMeaning;
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public NounBO getAssimilateAdjective()
    {
        return assimilateAdjective;
    }

    public void setAssimilateAdjective( NounBO assimilateAdjective )
    {
        this.assimilateAdjective = assimilateAdjective;
    }

    public int getAssimilateAdjectiveId()
    {
        return assimilateAdjectiveId;
    }

    public void setAssimilateAdjectiveId( int assimilateAdjectiveId )
    {
        this.assimilateAdjectiveId = assimilateAdjectiveId;
    }

    public SemanticNounBO getAssimilateAdjectiveMeaning()
    {
        return assimilateAdjectiveMeaning;
    }

    public void setAssimilateAdjectiveMeaning( SemanticNounBO assimilateAdjectiveMeaning )
    {
        this.assimilateAdjectiveMeaning = assimilateAdjectiveMeaning;
    }
}