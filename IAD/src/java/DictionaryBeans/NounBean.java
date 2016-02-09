package DictionaryBeans;

import BusinessLogicLayer.BOManager.BOManagerFactory;
import BusinessLogicLayer.BOManager.NounBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class NounBean
{

    private NounBO currentNoun;
    private NounBOManager newNounBOManager;
    private int checkTracker = 0;

    public NounBean()
    {
        currentNoun = new NounBO();
        newNounBOManager = new NounBOManager();
    }

    ///<editor-fold defaultstate="collapsed" desc="getter and setter...">
    public NounBO getCurrentNoun()
    {
        return currentNoun;
    }

    public void setCurrentNoun( NounBO currentNoun )
    {
        this.currentNoun = currentNoun;
    }

    public NounBO getCurrentEntry()
    {
        return currentNoun;
    }

    public void setCurrentEntry( NounBO currentNoun )
    {
        this.currentNoun = currentNoun;
    }

    public String getNoun()
    {
        return currentNoun.getVocalizedNoun();
    }

    public void setNoun( String noun ) throws BadWordException
    {
        currentNoun.setVocalizedNoun( BeansUtil.getFormatedText( noun ) );
    }

    public String getGender()
    {
        return currentNoun.getGender();
    }

    public void setGender( String gender ) throws BadWordException
    {
        this.currentNoun.setGender( BeansUtil.getFormatedText( gender ) );
    }

    public String getNumber()
    {
        return currentNoun.getNumber();
    }

    public void setNumber( String number ) throws BadWordException
    {
        currentNoun.setNumber( BeansUtil.getFormatedText( number ) );
    }

    public String getOrigin()
    {
        return currentNoun.getOrigin();
    }

    public void setOrigin( String origin ) throws BadWordException
    {
        currentNoun.setOrigin( BeansUtil.getFormatedText( origin ) );
    }

    public String getPattern()
    {
        return currentNoun.getPattern();
    }

    public void setPattern( String pattern ) throws BadWordException
    {
        currentNoun.setPattern( BeansUtil.getFormatedText( pattern ) );
    }

    public String getRoot()
    {
        return currentNoun.getRoot();
    }

    public void setRoot( String root ) throws BadWordException
    {
        currentNoun.setRoot( BeansUtil.getFormatedText( root ) );
    }

    public String getType()
    {
        return currentNoun.getType();
    }

    public void setType( String type ) throws BadWordException
    {
        currentNoun.setType( BeansUtil.getFormatedText( type ) );
    }

    public int getCheckTracker()
    {
        return checkTracker;
    }

    public void setCheckTracker( int checkTracker )
    {
        this.checkTracker = checkTracker;
    }

    ///</editor-fold>
    public int addNoun() throws RawNotFoundException, EntryExistedException
    {
        return newNounBOManager.suggestAdding( currentNoun );
    }

    public NounBO getNounInfo( int id )
    {
        currentNoun = NounBOManager.getNounBO( id, SearchProperties.simpleSearchOptions, "" );
        return currentNoun;
    }

    public int getNounId( String noun ) throws RawNotFoundException
    {
        List<NounBO> founNouns = newNounBOManager.listNouns( noun );
        if ( founNouns.size() > 0 )
        {
            for ( NounBO nounBO : founNouns )
            {
                if ( nounBO.getVocalizedNoun().compareTo( noun ) == 0 )
                {
                    return founNouns.get( 0 ).getDerivedNounId();
                }
            }
        }
        return -1;
    }

    public NounBO getCheckedNoun( int nounId ) throws RawNotFoundException
    {
        currentNoun = newNounBOManager.getNounBO( nounId, SearchProperties.simpleSearchOptions, "" );
        checkTracker = newNounBOManager.getCheckedNounWeight( nounId );
        return currentNoun;
    }

    public void updateNounInfo() throws RawNotFoundException
    {
        newNounBOManager.suggestUpdating( currentNoun.getDerivedNounId(), currentNoun );
    }

    public void deleteNounEntry() throws RawNotFoundException
    {
        newNounBOManager.suggestDeleting( currentNoun.getDerivedNounId() );
    }

    public void affirmAdding() throws RawNotFoundException
    {
        NounBOManager.affirmAdding( currentNoun.getDerivedNounId() );
        clearCheck();
    }

    public void affirmAddingAU() throws RawNotFoundException
    {
        NounBOManager.affirmAddingAU( currentNoun.getDerivedNounId(), currentNoun );
        clearCheck();
    }

    public void rejectAdding() throws RawNotFoundException
    {
        NounBOManager.rejectAdding( currentNoun.getDerivedNounId() );
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        NounBOManager.affirmUpdating( currentNoun.getDerivedNounId() );
        clearCheck();
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        NounBOManager.rejectUpdating( currentNoun.getDerivedNounId() );
        clearCheck();
    }

    public void affirmUpdatingAU() throws RawNotFoundException
    {
        NounBOManager.affirmUpdatingAU( currentNoun.getDerivedNounId(), currentNoun );
        clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        NounBOManager.affirmDeleting( currentNoun.getDerivedNounId() );
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        NounBOManager.rejectDeleting( currentNoun.getDerivedNounId() );
        clearCheck();
    }

    void clearCheck() throws RawNotFoundException
    {
        checkTracker--;
        if ( checkTracker == 0 )
        {
            NounBOManager.clearCheck( currentNoun.getDerivedNounId() );
        }
    }
    public List<NounBO> listNeedCheck() throws RawNotFoundException
    {
        return NounBOManager.getNeedCheck();
    }
}
