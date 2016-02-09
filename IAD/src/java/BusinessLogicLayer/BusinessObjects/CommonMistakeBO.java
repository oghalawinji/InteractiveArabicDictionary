/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class CommonMistakeBO implements java.io.Serializable
{

    private String description;
    private String source;
    private String status = "Q";
    private int commonMistakeId;

    public CommonMistakeBO()
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public int getCommonMistakeId()
    {
        return commonMistakeId;
    }

    public void setCommonMistakeId( int commonMistakeId )
    {
        this.commonMistakeId = commonMistakeId;
    }
}
