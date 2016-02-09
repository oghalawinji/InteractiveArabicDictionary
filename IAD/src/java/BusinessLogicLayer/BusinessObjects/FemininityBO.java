/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class FemininityBO
{
    private int femininityId;
    private NounBO femininity;
    private SemanticNounBO femininityMeaning;
    private String status;

    public int getFemininityId()
    {
        return femininityId;
    }

    public void setFemininityId( int femininityId )
    {
        this.femininityId = femininityId;
    }

    public NounBO getFemininity()
    {
        return femininity;
    }

    public void setFemininity( NounBO femininity )
    {
        this.femininity = femininity;
    }

    public SemanticNounBO getFemininityMeaning()
    {
        return femininityMeaning;
    }

    public void setFemininityMeaning( SemanticNounBO femininityMeaning )
    {
        this.femininityMeaning = femininityMeaning;
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
