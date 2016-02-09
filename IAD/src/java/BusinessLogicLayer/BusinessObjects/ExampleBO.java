/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class ExampleBO implements java.io.Serializable
{

    private Integer exampleId;
    private String example;
    private String source;
    private String status;
    private List<byte[]> sounds;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public ExampleBO()
    {
    }

    public Integer getExampleId()
    {
        return exampleId;
    }

    public void setExampleId( Integer exampleId )
    {
        this.exampleId = exampleId;
    }

    public String getExample()
    {
        return example;
    }

    public void setExample( String example )
    {
        this.example = example;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public List<byte[]> getSounds()
    {
        return sounds;
    }

    public void setSounds( List<byte[]> sounds )
    {
        this.sounds = sounds;
    }

    public byte[] getSound()
    {
        if ( this.sounds != null )
        {
            return this.sounds.get( 0 );
        }
        return null;
    }

    public void setSound( byte[] sound )
    {
        if ( sound != null )
        {
            if ( this.sounds == null )
            {
                this.sounds= new ArrayList<byte[]>();
                sounds.add( sound );
            }
            else
            {
                sounds.set( 0 , sound );
            }
        }

    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof ExampleBO )
        {
            ExampleBO newExampleBO = ( ExampleBO ) obj;
            if ( this.example.compareTo( newExampleBO.example ) == 0
                 && this.source.compareTo( newExampleBO.source ) == 0 )
            {
                return true;
            }
        }
        return false;
    }
}
