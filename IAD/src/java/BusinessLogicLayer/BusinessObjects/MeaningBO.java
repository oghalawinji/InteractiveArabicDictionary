/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class MeaningBO implements java.io.Serializable
{

    private String description;
    private String source;
    private String status = "Q";
    private int meaningId;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public MeaningBO()
    {
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public int getMeaningId()
    {
        return meaningId;
    }

    public void setMeaningId( int meaningId )
    {
        this.meaningId = meaningId;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof MeaningBO )
        {
            MeaningBO newMeaningBO = ( MeaningBO ) obj;
            if ( newMeaningBO.description.compareTo( this.description ) == 0 && newMeaningBO.source.compareTo( this.source ) == 0 )
            {
                return true;
            }
            return false;
        }
        return false;
    }
}
