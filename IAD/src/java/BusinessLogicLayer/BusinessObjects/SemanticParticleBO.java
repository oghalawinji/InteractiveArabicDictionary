/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class SemanticParticleBO extends SemanticEntryBO implements java.io.Serializable
{

    private Integer semanticParticleId;
    private String status;
    private boolean checked;

    public SemanticParticleBO()
    {
        super();
    }

    public Integer getSemanticParticleId()
    {
        return semanticParticleId;
    }

    public void setSemanticParticleId( Integer semanticParticleId )
    {
        this.semanticParticleId = semanticParticleId;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked( boolean checked )
    {
        this.checked = checked;
    }

    @Override
    public String getStatus()
    {
        return status;
    }

    @Override
    public void setStatus( String status )
    {
        this.status = status;
    }
}
