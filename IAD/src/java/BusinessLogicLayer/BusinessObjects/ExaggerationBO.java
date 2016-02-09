/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class ExaggerationBO
{
    private int exaggerationId;
    private NounBO exaggeration;
    private SemanticNounBO exaggerationMeaning;
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public NounBO getExaggeration()
    {
        return exaggeration;
    }

    public void setExaggeration( NounBO exaggeration )
    {
        this.exaggeration = exaggeration;
    }

    public int getExaggerationId()
    {
        return exaggerationId;
    }

    public void setExaggerationId( int exaggerationId )
    {
        this.exaggerationId = exaggerationId;
    }

    public SemanticNounBO getExaggerationMeaning()
    {
        return exaggerationMeaning;
    }

    public void setExaggerationMeaning( SemanticNounBO exaggerationMeaning )
    {
        this.exaggerationMeaning = exaggerationMeaning;
    }
}
