package DictionaryBeans;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BOManager.VerbBOManager;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author Fadel
 */
public class VerbBean
{

    private VerbBO currentVerb;
    private VerbBOManager newVerbManager;
    private int checkTracker = 0;

    public VerbBean() throws RawNotFoundException
    {
        currentVerb = new VerbBO();
        newVerbManager = new VerbBOManager();
    }

///<editor-fold defaultstate="collapsed" desc="getter and setter">
    public String getPattern()
    {
        return currentVerb.getPattern();
    }

    public void setPattern( String pattern ) throws BadWordException
    {
        currentVerb.setPattern( BeansUtil.getFormatedText( pattern ) );
    }

    public String getPresentForm()
    {
        return currentVerb.getPresentForm();
    }

    public void setPresentForm( String presentForm ) throws BadWordException
    {
        currentVerb.setPresentForm( BeansUtil.getFormatedText( presentForm ) );
    }

    public String getRoot()
    {
        return currentVerb.getRoot();
    }

    public void setRoot( String root ) throws BadWordException
    {
        currentVerb.setRoot( BeansUtil.getFormatedText( root ) );
    }

    public String getVerb()
    {
        return currentVerb.getVocalizedVerb();
    }

    public void setVerb( String verb ) throws BadWordException
    {
        currentVerb.setVocalizedVerb( BeansUtil.getFormatedText( verb ) );
    }

    public VerbBO getCurrentVerb()
    {
        return this.currentVerb;
    }

    public VerbBO getCurrentEntry()
    {
        return currentVerb;
    }

    public void setCurrentEntry( VerbBO currentVerb )
    {
        this.currentVerb = currentVerb;
    }

    public int getCheckTracker()
    {
        return checkTracker;
    }

    public void setCheckTracker( int checkTracker )
    {
        this.checkTracker = checkTracker;
    }
    //</editor-fold>

    public int addVerb() throws EntryExistedException, RawNotFoundException
    {
        /*return  InsertDerivedVerb.execute ( currentVerb.getVocalizedVerb () , currentVerb.getPresentForm () ,
        currentVerb.getRoot () , currentVerb.getPattern () , BeansUtil.insertStatus );*/
        return newVerbManager.suggestAdding( currentVerb );
    }

    public int getVerbId( String verb ) throws RawNotFoundException
    {
        List<VerbBO> founVerbs = newVerbManager.listVerbs( verb );
        if ( founVerbs.size() > 0 )
        {
            for ( VerbBO verbBO : founVerbs )
            {
                if ( verbBO.getVocalizedVerb().compareTo( verb ) == 0 )
                {
                    return founVerbs.get( 0 ).getDerivedVerbId();
                }
            }
        }
        return -1;
    }

    public VerbBO getVerbInfo( int id ) throws RawNotFoundException
    {
        currentVerb = newVerbManager.get( id, SearchProperties.simpleSearchOptions, "" );
        return currentVerb;
    }

    public VerbBO getCheckedVerb( int verbId ) throws RawNotFoundException
    {
        currentVerb = newVerbManager.get( verbId, SearchProperties.simpleSearchOptions, "" );
        checkTracker = newVerbManager.getCheckedVerbWeight( verbId );
        return currentVerb;
    }

    public void updateVerbInfo() throws RawNotFoundException
    {
        newVerbManager.suggestUpdating( currentVerb.getDerivedVerbId(), currentVerb );
    }

    public void deleteVerbEntry() throws RawNotFoundException
    {
        newVerbManager.suggestDeleting( currentVerb.getDerivedVerbId() );
    }

    public List<VerbBO> listNeedCheck() throws RawNotFoundException
    {
        return VerbBOManager.getNeedCheck();
    }

    public void affirmAdding() throws RawNotFoundException
    {
        VerbBOManager.affirmAdding( currentVerb.getDerivedVerbId() );
        clearCheck();
    }

    public boolean affirmAddingAU() throws RawNotFoundException
    {
        boolean isUpdated = VerbBOManager.affirmAddingAU( currentVerb.getDerivedVerbId(), currentVerb );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        else
        {
            return false;
        }
    }

    public void rejectAdding() throws RawNotFoundException
    {
        VerbBOManager.rejectAdding( currentVerb.getDerivedVerbId() );
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        VerbBOManager.affirmUpdating( currentVerb.getDerivedVerbId() );
        clearCheck();
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        VerbBOManager.rejectUpdating( currentVerb.getDerivedVerbId() );
        clearCheck();
    }

    public boolean affirmUpdatingAU() throws RawNotFoundException
    {
        boolean isUpdated = VerbBOManager.affirmUpdatingAU( currentVerb.getDerivedVerbId(), currentVerb );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        return false;
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        VerbBOManager.affirmDeleting( currentVerb.getDerivedVerbId() );
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        VerbBOManager.rejectDeleting( currentVerb.getDerivedVerbId() );
        clearCheck();
    }

    public void clearCheck() throws RawNotFoundException
    {
        checkTracker--;
        if ( checkTracker == 0 )
        {
            VerbBOManager.clearCheck( currentVerb.getDerivedVerbId() );
        }
    }
}
