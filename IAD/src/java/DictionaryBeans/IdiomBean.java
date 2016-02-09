package DictionaryBeans;

import BusinessLogicLayer.BOManager.IdiomBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class IdiomBean
{

    private IdiomBO currentIdiom;
    private IdiomBOManager newIdiomBOManager;
    private int checkTracker = 0;

    public IdiomBean() throws RawNotFoundException
    {
        this.currentIdiom = new IdiomBO();
        newIdiomBOManager = new IdiomBOManager();
    }

/// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public IdiomBO getCurrentIdiom()
    {
        return this.currentIdiom;
    }

    public void setCurrentIdiom( IdiomBO currentIdiom )
    {
        this.currentIdiom = currentIdiom;
    }

    public String getIdiom()
    {
        return this.currentIdiom.getVocalizedIdiom();
    }

    public void setIdiom( String idiom ) throws BadWordException
    {
        this.currentIdiom.setVocalizedIdiom( BeansUtil.getFormatedText( idiom ) );
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

    public IdiomBO getIdiomInfo( int idiomId ) throws RawNotFoundException
    {
        this.currentIdiom = newIdiomBOManager.getIdiomBO( idiomId, SearchProperties.simpleSearchOptions );
        this.currentIdiom.setIdiomId( idiomId );
        return this.currentIdiom;
    }

    public int addIdiom() throws RawNotFoundException, EntryExistedException
    {
        return newIdiomBOManager.suggestAdding( this.currentIdiom );
    }

    public void updateIdiomInfo() throws RawNotFoundException
    {
        newIdiomBOManager.suggestUpdating( this.currentIdiom.getIdiomId(), this.currentIdiom );
    }

    public void deleteIdiomEntry() throws RawNotFoundException
    {
        newIdiomBOManager.suggestDeleting( this.currentIdiom.getIdiomId() );
    }

    public void affirmAdding() throws RawNotFoundException
    {
        IdiomBOManager.affirmAdding( this.currentIdiom.getIdiomId() );
        clearCheck();
    }

    public void affirmAddingAU() throws RawNotFoundException
    {
        IdiomBOManager.affirmAddingAU( this.currentIdiom.getIdiomId(), this.currentIdiom );
        clearCheck();
    }

    public void rejectAdding() throws RawNotFoundException
    {
        IdiomBOManager.rejectAdding( this.currentIdiom.getIdiomId() );
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        IdiomBOManager.affirmUpdating( this.currentIdiom.getIdiomId() );
        clearCheck();
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        IdiomBOManager.rejectUpdating( this.currentIdiom.getIdiomId() );
        clearCheck();
    }

    public void affirmUpdatingAU() throws RawNotFoundException
    {
        IdiomBOManager.affirmUpdatingAU( this.currentIdiom.getIdiomId(), this.currentIdiom );
        clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        IdiomBOManager.affirmDeleting( this.currentIdiom.getIdiomId() );
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        IdiomBOManager.rejectDeleting( this.currentIdiom.getIdiomId() );
        clearCheck();
    }

    void clearCheck() throws RawNotFoundException
    {
        checkTracker--;
        if ( checkTracker == 0 )
        {
            IdiomBOManager.clearCheck( this.currentIdiom.getIdiomId() );
        }
    }
    public List<IdiomBO> listNeedCheck() throws RawNotFoundException
    {
        return IdiomBOManager.getNeedCheck();
    }

    public IdiomBO getCheckedIdiom( int idiomId ) throws RawNotFoundException
    {
        this.currentIdiom = newIdiomBOManager.getIdiomBO ( idiomId, SearchProperties.simpleSearchOptions );
        checkTracker = newIdiomBOManager.getCheckedIdiomWeight ( idiomId );
        return this.currentIdiom;
    }
}
