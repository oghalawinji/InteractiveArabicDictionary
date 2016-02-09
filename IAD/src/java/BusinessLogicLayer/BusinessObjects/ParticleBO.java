package BusinessLogicLayer.BusinessObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class ParticleBO extends WordBO implements java.io.Serializable
{

    private Integer derivedParticleId;
    private String root;
    private String particletype;
    private String phonetic;
    private String status;
    private List<SemanticParticleBO> semanticCases = new ArrayList<SemanticParticleBO>();

    public ParticleBO()
    {
        super();
    }

    public Integer getDerivedParticleId()
    {
        return derivedParticleId;
    }

    public void setDerivedParticleId( Integer derivedParticleId )
    {
        this.derivedParticleId = derivedParticleId;
    }

    public String getParticletype()
    {
        return particletype;
    }

    public void setParticletype( String particletype )
    {
        this.particletype = particletype;
    }

    public String getPhonetic()
    {
        return phonetic;
    }

    public void setPhonetic( String phonetic )
    {
        this.phonetic = phonetic;
    }

    public String getRoot()
    {
        return root;
    }

    public void setRoot( String root )
    {
        this.root = root;
    }

    public String getVocalizedParticle()
    {
        return this.getVocalizedString();
    }

    public void setVocalizedParticle( String vocalizedParticle )
    {
        this.setVocalizedString( vocalizedParticle );
    }

    public List<SemanticParticleBO> getSemanticCases()
    {
        return semanticCases;
    }

    public void setSemanticCases( List<SemanticParticleBO> semanticCases )
    {
        this.semanticCases = semanticCases;
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

    @Override
    public int getId()
    {
        return derivedParticleId;
    }

    @Override
    public void setId( int id )
    {
        this.derivedParticleId = id;
    }
}
