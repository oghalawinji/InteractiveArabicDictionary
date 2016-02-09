/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class GerundBO
{
    private int gerundId;
    private NounBO gerund;
    private SemanticNounBO gerundMeaning;
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public NounBO getGerund()
    {
        return gerund;
    }

    public void setGerund( NounBO gerund )
    {
        this.gerund = gerund;
    }

    public int getGerundId()
    {
        return gerundId;
    }

    public void setGerundId( int gerundId )
    {
        this.gerundId = gerundId;
    }

    public SemanticNounBO getGerundMeaning()
    {
        return gerundMeaning;
    }

    public void setGerundMeaning( SemanticNounBO gerundMeaning )
    {
        this.gerundMeaning = gerundMeaning;
    }
    
}
