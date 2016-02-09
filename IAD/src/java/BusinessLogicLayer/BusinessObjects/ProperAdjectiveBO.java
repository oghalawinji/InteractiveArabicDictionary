/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class ProperAdjectiveBO
{

    private int properAdjectiveId;
    private NounBO properAdjective;
    private SemanticNounBO properAdjectiveMeaning;
    private String status;

    public int getProperAdjectiveId()
    {
        return properAdjectiveId;
    }

    public void setProperAdjectiveId( int properAdjectiveId )
    {
        this.properAdjectiveId = properAdjectiveId;
    }

    public NounBO getProperAdjective()
    {
        return properAdjective;
    }

    public void setProperAdjective( NounBO properAdjective )
    {
        this.properAdjective = properAdjective;
    }

    public SemanticNounBO getProperAdjectiveMeaning()
    {
        return properAdjectiveMeaning;
    }

    public void setProperAdjectiveMeaning( SemanticNounBO properAdjectiveMeaning )
    {
        this.properAdjectiveMeaning = properAdjectiveMeaning;
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
