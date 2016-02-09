package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class LinguisticBenefitBO implements java.io.Serializable
{

    private String description;
    private String source;
    private String status;
    private int linguisticBenefitId;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public LinguisticBenefitBO()
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

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof LinguisticBenefitBO )
        {
            LinguisticBenefitBO newLinguisticBenefitBO = ( LinguisticBenefitBO ) obj;
            if ( newLinguisticBenefitBO.getDescription().compareTo( description ) == 0
                 && newLinguisticBenefitBO.getSource().compareTo( source ) == 0 )
            {
                return true;
            }
        }
        return false;
    }

    public int getLinguisticBenefitId()
    {
        return linguisticBenefitId;
    }

    public void setLinguisticBenefitId( int linguisticBenefitId )
    {
        this.linguisticBenefitId = linguisticBenefitId;
    }
}
