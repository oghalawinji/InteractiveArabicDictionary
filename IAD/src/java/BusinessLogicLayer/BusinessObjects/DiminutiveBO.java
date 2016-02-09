/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class DiminutiveBO
{
        private int diminutiveId;
    private NounBO diminutive;
    private SemanticNounBO diminutiveMeaning;
    private String status;

    public int getDiminutiveId()
    {
        return diminutiveId;
    }

    public void setDiminutiveId( int diminutiveId )
    {
        this.diminutiveId = diminutiveId;
    }

    public NounBO getDiminutive()
    {
        return diminutive;
    }

    public void setDiminutive( NounBO diminutive )
    {
        this.diminutive = diminutive;
    }

    public SemanticNounBO getDiminutiveMeaning()
    {
        return diminutiveMeaning;
    }

    public void setDiminutiveMeaning( SemanticNounBO diminutiveMeaning )
    {
        this.diminutiveMeaning = diminutiveMeaning;
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
