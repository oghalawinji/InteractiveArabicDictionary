package DictionaryBeans;

import BusinessLogicLayer.BOManager.ParticleBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class ParticleBean
{

    private ParticleBO currentParticle;
    private ParticleBOManager newParticleBOManager;
    private int checkTracker = 0;

    public ParticleBean()
    {
        currentParticle = new ParticleBO();
        newParticleBOManager = new ParticleBOManager();
    }

/// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public ParticleBO getCurrentParticle()
    {
        return currentParticle;
    }

    public void setCurrentParticle( ParticleBO currentParticle )
    {
        this.currentParticle = currentParticle;
    }

    public String getParticle()
    {
        return currentParticle.getVocalizedParticle();
    }

    public void setParticle( String particle ) throws BadWordException
    {
        currentParticle.setVocalizedParticle( BeansUtil.getFormatedText( particle ) );
    }

    public String getRoot()
    {
        return currentParticle.getRoot();
    }

    public void setRoot( String root ) throws BadWordException
    {
        currentParticle.setRoot( BeansUtil.getFormatedText( root ) );
    }

    public String getType()
    {
        return currentParticle.getParticletype();
    }

    public void setType( String type ) throws BadWordException
    {
        currentParticle.setParticletype( BeansUtil.getFormatedText( type ) );
    }

    public int getCheckTracker()
    {
        return checkTracker;
    }

    public void setCheckTracker( int checkTracker )
    {
        this.checkTracker = checkTracker;
    }
/// </editor-fold>

    public ParticleBO getParticleInfo( int particleId )
    {
        currentParticle = newParticleBOManager.getParticleBO( particleId, SearchProperties.simpleSearchOptions );
        return currentParticle;
    }
    public ParticleBO getCheckedParticle( int particleId ) throws RawNotFoundException
    {
        currentParticle = newParticleBOManager.getParticleBO ( particleId, SearchProperties.simpleSearchOptions );
        checkTracker = newParticleBOManager.getCheckedParticleWeight ( particleId );
        return currentParticle;
    }

    public int addParticle() throws RawNotFoundException, EntryExistedException
    {
        return newParticleBOManager.suggestAdding( currentParticle );
    }

    public void updateParticleInfo() throws RawNotFoundException
    {
        newParticleBOManager.suggestUpdating( currentParticle.getDerivedParticleId(), currentParticle );
    }

    public void deleteParticleEntry() throws RawNotFoundException
    {
        newParticleBOManager.suggestDeleting( currentParticle.getDerivedParticleId() );
    }

    public void affirmAdding() throws RawNotFoundException
    {
        ParticleBOManager.affirmAdding( currentParticle.getDerivedParticleId() );
        clearCheck();
    }

    public void affirmAddingAU() throws RawNotFoundException
    {
        ParticleBOManager.affirmAddingAU( currentParticle.getDerivedParticleId(), currentParticle );
        clearCheck();
    }

    public void rejectAdding() throws RawNotFoundException
    {
        ParticleBOManager.rejectAdding( currentParticle.getDerivedParticleId() );
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        ParticleBOManager.affirmUpdating( currentParticle.getDerivedParticleId() );
        clearCheck();
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        ParticleBOManager.rejectUpdating( currentParticle.getDerivedParticleId() );
        clearCheck();
    }

    public void affirmUpdatingAU() throws RawNotFoundException
    {
        ParticleBOManager.affirmUpdatingAU( currentParticle.getDerivedParticleId(), currentParticle );
        clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        ParticleBOManager.affirmDeleting( currentParticle.getDerivedParticleId() );
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        ParticleBOManager.rejectDeleting( currentParticle.getDerivedParticleId() );
        clearCheck();
    }

    void clearCheck() throws RawNotFoundException
    {
        checkTracker--;
        if ( checkTracker == 0 )
        {
            ParticleBOManager.clearCheck( currentParticle.getDerivedParticleId() );
        }
    }
    public List<ParticleBO> listNeedCheck() throws RawNotFoundException
    {
        return ParticleBOManager.getNeedCheck();
    }
}
