package DictionaryBeans;

import BusinessLogicLayer.BOManager.DiminutiveManager;
import BusinessLogicLayer.BOManager.FemininityManager;
import BusinessLogicLayer.BOManager.MeaningBOManager;
import BusinessLogicLayer.BOManager.NounAdjectiveAccompanierManager;
import BusinessLogicLayer.BOManager.ProperAdjectiveManager;
import BusinessLogicLayer.BOManager.SemanticNounBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.AdjectiveAccompanierBO;
import BusinessLogicLayer.BusinessObjects.AnnexedNounBO;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.DiminutiveBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.FemininityBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.ProperAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.BusinessObjects.VerbAccompanierBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class SemanticNounBean extends SemanticEntryBean
{
    private int semanticNounId;
    private SemanticNounBO currentSemanticNounBO;
    private SemanticNounBOManager newSemanticNounBOManager;
    private MeaningBOManager newMeaningBOManager;
    private int derivedNounId;
    private byte[] exampleSound;
    private int checkTracker = 0;
    private NounBean currentNounBean;

    public SemanticNounBean()
    {
        currentSemanticNounBO = new SemanticNounBO();
        newSemanticNounBOManager = new SemanticNounBOManager();
        newMeaningBOManager = new MeaningBOManager();
    }

     public int addSemanticNounBO () throws EntryExistedException , RawNotFoundException
    {
        this.semanticNounId = this.newSemanticNounBOManager.suggestAdding ( currentSemanticNounBO , derivedNounId );
        return this.semanticNounId;
    }

    public void updateSemanticNounInfo() throws RawNotFoundException
    {
        newSemanticNounBOManager.suggestUpdateInfo( currentSemanticNounBO, currentSemanticNounBO.getSemanticNounId() );
        currentSemanticNounBO.setStatus( "U" );
    }

    public void deleteSemanticNoun( int semantciNounId ) throws RawNotFoundException
    {
        newSemanticNounBOManager.suggestDelete( semantciNounId );
    }


    public void clearCheck() throws RawNotFoundException
    {
        if ( checkTracker == 0 )
        {
            SemanticNounBOManager.clearCheck( currentSemanticNounBO.getSemanticNounId() );
            currentNounBean.clearCheck();
        }
        else
        {
            checkTracker--;
        }
    }

    public void affirmAdding() throws RawNotFoundException
    {
        SemanticNounBOManager.affirmAdding( currentSemanticNounBO.getSemanticNounId() );
        clearCheck();
    }

    /*public boolean affirmAddingAU() throws RawNotFoundException, EntryExistedException
    {
        boolean isUpdated = SemanticNounBOManager.affirmAddingAU( currentSemanticNounBO.getSemanticNounId(), currentSemanticNounBO, currentNounBean.getCurrentNoun().getDerivedNounId() );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        return false;
    }*/
    public boolean affirmAddingAU () throws RawNotFoundException , EntryExistedException
    {
        boolean isUpdated = SemanticNounBOManager.affirmAddingAU ( currentSemanticNounBO.getSemanticNounId() , currentSemanticNounBO , currentNounBean.getCurrentNoun ().getDerivedNounId () );
        if ( isUpdated )
        {
            clearCheck ();
            return true;
        }
        return false;
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        SemanticNounBOManager.affirmUpdating( currentSemanticNounBO.getSemanticNounId() );
        clearCheck();
    }

    public boolean affirmUpdatingAU() throws RawNotFoundException
    {
        boolean isUpdated = SemanticNounBOManager.affirmUpdatingAU( currentSemanticNounBO.getSemanticNounId(), currentSemanticNounBO );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        return false;
    }

    public void rejectAdding() throws RawNotFoundException
    {
        SemanticNounBOManager.rejectAdding( derivedNounId );
        clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        SemanticNounBOManager.affirmDeleting( derivedNounId );
        clearCheck();
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        SemanticNounBOManager.rejectUpdating( derivedNounId );
        clearCheck();
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        SemanticNounBOManager.rejectDeleting( derivedNounId );
        clearCheck();
    }

    /// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public SemanticNounBO getSemanticNoun( int semanticNounId )
    {
        this.semanticNounId = semanticNounId;
        currentSemanticNounBO = newSemanticNounBOManager.getSemanticNounBO( semanticNounId, SearchProperties.detailedSearchOptions );
        return currentSemanticNounBO;
    }

    public SemanticNounBO getCheckedSemanticNoun ( int semanticNounId ) throws RawNotFoundException
    {
         this.semanticNounId = semanticNounId;
         currentSemanticNounBO = SemanticNounBOManager.getSemanticNounBO ( semanticNounId , SearchProperties.detailedSearchOptions );
        checkTracker = SemanticNounBOManager.getCheckedSemNounWeight ( semanticNounId );
        return currentSemanticNounBO;
    }
    
    public SemanticNounBO getCurrentSemanticNounBO()
    {
        return currentSemanticNounBO;
    }

    public void setCurrentSemanticNounBO( SemanticNounBO currentSemanticNounBO )
    {
        this.currentSemanticNounBO = currentSemanticNounBO;
    }

    public int getDerivedNounId()
    {
        return derivedNounId;
    }

    public void setDerivedNounId( int derivedNounId )
    {
        this.derivedNounId = derivedNounId;
    }

    public String getDifficulty()
    {
        return this.currentSemanticNounBO.getDifficultydegree();
    }

    public void setDifficulty( String difficulty ) throws BadWordException
    {
        this.currentSemanticNounBO.setDifficultydegree( BeansUtil.getFormatedText( difficulty ) );
    }

    public String getEpoch()
    {
        return this.currentSemanticNounBO.getEpoch();
    }

    public void setEpoch( String epoch ) throws BadWordException
    {
        this.currentSemanticNounBO.setEpoch( BeansUtil.getFormatedText( epoch ) );
    }

    public String getMeaning()
    {
        return this.currentSemanticNounBO.getMeanings().get( 0 ).getDescription();
    }

    public void setMeaning( String meaning ) throws BadWordException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( BeansUtil.getFormatedText( meaning ) );
        currentSemanticNounBO.getMeanings().clear();
        currentSemanticNounBO.getMeanings().add( newMeaningBO );
    }

    public String getRegion()
    {
        return this.currentSemanticNounBO.getRegion();
    }

    public void setRegion( String region ) throws BadWordException
    {
        this.currentSemanticNounBO.setRegion( BeansUtil.getFormatedText( region ) );
    }

    public String getSemanticScop()
    {
        return this.getSemanticScop();
    }

    public void setSemanticScop( String semanticScop ) throws BadWordException
    {
        this.currentSemanticNounBO.setSemanticscop( BeansUtil.getFormatedText( semanticScop ) );
    }

    public String getSource()
    {
        return this.currentSemanticNounBO.getMeanings().get( 0 ).getSource();
    }

    public void setSource( String source ) throws BadWordException
    {
        this.currentSemanticNounBO.getMeanings().get( 0 ).setSource( BeansUtil.getFormatedText( source ) );
    }

    public String getSpecialization()
    {
        return this.currentSemanticNounBO.getSpecialization();
    }

    public void setSpecialization( String specialization ) throws BadWordException
    {
        this.currentSemanticNounBO.setSpecialization( BeansUtil.getFormatedText( specialization ) );
    }

    public String getSpreading()
    {
        return this.currentSemanticNounBO.getSpreadingdegree();
    }

    public void setSpreading( String spreading ) throws BadWordException
    {
        this.currentSemanticNounBO.setSpreadingdegree( BeansUtil.getFormatedText( spreading ) );
    }

    public void setExampleSound( byte[] sound )
    {
        this.exampleSound = sound;
    }

    public byte[] getExampleSound()
    {
        return this.exampleSound;
    }

    public NounBean getCurrentNounBean()
    {
        return currentNounBean;
    }

    public void setCurrentNounBean( NounBean currentNounBean )
    {
        this.currentNounBean = currentNounBean;
    }

    public int getSemanticNounId ()
    {
        return semanticNounId;
    }

    public int getCheckTracker ()
    {
        return checkTracker;
    }

    public void setCheckTracker ( int checkTracker )
    {
        this.checkTracker = checkTracker;
    }

    public void setSemanticNounId ( int semanticNounId )
    {
        this.semanticNounId = semanticNounId;
    }
/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Femininity">

    public List<FemininityBO> joinFemininity( FemininityBO newFemininityBO ) throws RawNotFoundException
    {
        int femininityId = newSemanticNounBOManager.suggestAddFemininity( currentSemanticNounBO.getSemanticNounId(), newFemininityBO );
        newFemininityBO.setFemininityId( femininityId );

        newFemininityBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getFemininities().contains( newFemininityBO ) )
        {
            currentSemanticNounBO.getFemininities().add( newFemininityBO );
        }

        return currentSemanticNounBO.getFemininities();
    }

    public void addFemininitie( FemininityBO noun )
    {
        if ( !currentSemanticNounBO.getFemininities().contains( noun ) )
        {
            currentSemanticNounBO.getFemininities().add( noun );
        }
    }

    public List<FemininityBO> deleteFemininity( int id ) throws RawNotFoundException
    {
        int femininityId = currentSemanticNounBO.getFemininities().get( id ).getFemininityId();
        currentSemanticNounBO.getFemininities().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteFemininity ( femininityId , currentSemanticNounBO.getSemanticNounId () );
        FemininityManager.suggestDeleting( femininityId );
        return currentSemanticNounBO.getFemininities();
    }
    
    public void affirmFemininityAdding ( int femininityId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmFemininityAdding ( femininityId );
        clearCheck ();
    }

    public void rejectFemininityAdding ( int femininityId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectFemininityAdding ( femininityId );
        clearCheck ();
    }

    public void affirmFemininityDeleting ( int femininityId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmFemininityDeleting ( femininityId );
        clearCheck ();
    }

    public void rejectFemininityDeleting ( int femininityId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectFemininityDeleting ( femininityId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Diminutive">
    public List<DiminutiveBO> joinDiminutive( DiminutiveBO newDiminutiveBO ) throws RawNotFoundException
    {
        int diminutiveId = newSemanticNounBOManager.suggestAddDiminutive( currentSemanticNounBO.getSemanticNounId(), newDiminutiveBO );
        newDiminutiveBO.setDiminutiveId( diminutiveId );

        newDiminutiveBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getDiminutives().contains( newDiminutiveBO ) )
        {
            currentSemanticNounBO.getDiminutives().add( newDiminutiveBO );
        }

        return currentSemanticNounBO.getDiminutives();
    }

    public void addDiminutive( DiminutiveBO noun )
    {
        if ( !currentSemanticNounBO.getDiminutives().contains( noun ) )
        {
            currentSemanticNounBO.getDiminutives().add( noun );
        }
    }

    public List<DiminutiveBO> deleteDiminutive( int id ) throws RawNotFoundException
    {
        int diminutiveId = currentSemanticNounBO.getDiminutives().get( id ).getDiminutiveId();
        currentSemanticNounBO.getDiminutives().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteDiminutive ( diminutiveId , currentSemanticNounBO.getSemanticNounId () );
        DiminutiveManager.suggestDeleting( diminutiveId );
        return currentSemanticNounBO.getDiminutives();
    }

    public void affirmDiminutiveAdding ( int diminutiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmDiminutiveAdding ( diminutiveId );
        clearCheck ();
    }

    public void rejectDiminutiveAdding ( int diminutiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectDiminutiveAdding ( diminutiveId );
        clearCheck ();
    }

    public void affirmDiminutiveDeleting ( int diminutiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmDiminutiveDeleting ( diminutiveId );
        clearCheck ();
    }

    public void rejectDiminutiveDeleting ( int diminutiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectDiminutiveDeleting ( diminutiveId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Proper Adjective">
    public List<ProperAdjectiveBO> joinProperAdjective( ProperAdjectiveBO newProperAdjectiveBO ) throws RawNotFoundException
    {
        int properAdjectiveId = newSemanticNounBOManager.suggestAddProperAdjective( currentSemanticNounBO.getSemanticNounId(), newProperAdjectiveBO );
        newProperAdjectiveBO.setProperAdjectiveId( properAdjectiveId );

        newProperAdjectiveBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getProperAdjectives().contains( newProperAdjectiveBO ) )
        {
            currentSemanticNounBO.getProperAdjectives().add( newProperAdjectiveBO );
        }

        return currentSemanticNounBO.getProperAdjectives();
    }

    public void addProperAdjective( ProperAdjectiveBO noun )
    {
        if ( !currentSemanticNounBO.getProperAdjectives().contains( noun ) )
        {
            currentSemanticNounBO.getProperAdjectives().add( noun );
        }
    }

    public List<ProperAdjectiveBO> deleteProperAdjective( int id ) throws RawNotFoundException
    {
        int properAdjectiveId = currentSemanticNounBO.getProperAdjectives().get( id ).getProperAdjectiveId();
        currentSemanticNounBO.getProperAdjectives().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteProperAdjective ( properAdjectiveId , currentSemanticNounBO.getSemanticNounId () );
        ProperAdjectiveManager.suggestDeleting( properAdjectiveId );
        return currentSemanticNounBO.getProperAdjectives();
    }

    public void affirmProperAdjectiveAdding ( int properAdjectiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmProperAdjectiveAdding ( properAdjectiveId );
        clearCheck ();
    }

    public void rejectProperAdjectiveAdding ( int properAdjectiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectProperAdjectiveAdding ( properAdjectiveId );
        clearCheck ();
    }

    public void affirmProperAdjectiveDeleting ( int properAdjectiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmProperAdjectiveDeleting ( properAdjectiveId );
        clearCheck ();
    }

    public void rejectProperAdjectiveDeleting ( int properAdjectiveId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectProperAdjectiveDeleting ( properAdjectiveId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Adjective Accompanier">

    public List<AdjectiveAccompanierBO> joinAdjectiveAccompanier( AdjectiveAccompanierBO newAdjectiveAccompanierBO ) throws RawNotFoundException
    {
        int adjectiveAccompanierId = newSemanticNounBOManager.suggestAddAdjectiveAccompanier( currentSemanticNounBO.getSemanticNounId(), newAdjectiveAccompanierBO );
        newAdjectiveAccompanierBO.setAdjectiveAccompanierId( adjectiveAccompanierId );

        newAdjectiveAccompanierBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getAdjectiveAccompaniers().contains( newAdjectiveAccompanierBO ) )
        {
            currentSemanticNounBO.getAdjectiveAccompaniers().add( newAdjectiveAccompanierBO );
        }

        return currentSemanticNounBO.getAdjectiveAccompaniers();
    }
    public void addAdjectiveAccompanier( AdjectiveAccompanierBO noun ) throws BadWordException
    {
        if ( !currentSemanticNounBO.getAdjectiveAccompaniers().contains( noun ) )
        {
            currentSemanticNounBO.getAdjectiveAccompaniers().add( noun );
        }
    }
    
    public List<AdjectiveAccompanierBO> deleteAdjectiveAccompanier( int id ) throws RawNotFoundException
    {
        int adjectiveAccompanierId = currentSemanticNounBO.getAdjectiveAccompaniers().get( id ).getAdjectiveAccompanierId();
        currentSemanticNounBO.getAdjectiveAccompaniers().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteNounAdjectiveAccompanier ( adjectiveAccompanierId , currentSemanticNounBO.getSemanticNounId () );
        //NounAdjectiveAccompanierManager.suggestDeleting( adjectiveAccompanierId );
        return currentSemanticNounBO.getAdjectiveAccompaniers();
    }

    public void affirmAdjectiveAccompanierAdding ( int adjectiveAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmNounAdjectiveAccompanierAdding ( adjectiveAccompanierId );
        clearCheck ();
    }

    public void rejectAdjectiveAccompanierAdding ( int adjectiveAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectNounAdjectiveAccompanierAdding ( adjectiveAccompanierId );
        clearCheck ();
    }

    public void affirmAdjectiveAccompanierDeleting ( int adjectiveAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmNounAdjectiveAccompanierDeleting ( adjectiveAccompanierId );
        clearCheck ();
    }

    public void rejectAdjectiveAccompanierDeleting ( int adjectiveAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectNounAdjectiveAccompanierDeleting ( adjectiveAccompanierId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Annexed Noun">

    public void addAnnexednoun( AnnexedNounBO noun )throws BadWordException
    {
        if ( ! this.currentSemanticNounBO.getAnnexedNouns().contains( noun ) )
        {
            currentSemanticNounBO.getAnnexedNouns().add( noun );
        }
    }

    public List<AnnexedNounBO> joinAnnexedNoun( AnnexedNounBO newAnnexedNounBO ) throws RawNotFoundException
    {
        int annexedNounId = newSemanticNounBOManager.suggestAddAnnexedNoun( currentSemanticNounBO.getSemanticNounId(), newAnnexedNounBO );
        newAnnexedNounBO.setAnnexedNounId( annexedNounId );

        newAnnexedNounBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getAnnexedNouns().contains( newAnnexedNounBO ) )
        {
            currentSemanticNounBO.getAnnexedNouns().add( newAnnexedNounBO );
        }

        return currentSemanticNounBO.getAnnexedNouns();
    }

    public List<AnnexedNounBO> deleteAnnexedNoun( int id ) throws RawNotFoundException
    {
        int annexedNounId = currentSemanticNounBO.getAnnexedNouns().get( id ).getAnnexedNounId();
        currentSemanticNounBO.getAnnexedNouns().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteAnnexedNoun ( annexedNounId , currentSemanticNounBO.getSemanticNounId () );
        return currentSemanticNounBO.getAnnexedNouns();
    }
    
    public void affirmAnnexedNounAdding ( int nounId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmAnnexedNounAdding ( nounId );
        clearCheck ();
    }

    public void rejectAnnexedNounAdding ( int nounId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectAnnexedNounAdding ( nounId );
        clearCheck ();
    }

    public void affirmAnnexedNounDeleting ( int nounId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmAnnexedNounDeleting ( nounId );
        clearCheck ();
    }

    public void rejectAnnexedNounDeleting ( int nounId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectAnnexedNounDeleting ( nounId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Verb Accompanier">
    public List<VerbAccompanierBO> joinVerbAccompanier( VerbAccompanierBO newVerbAccompanierBO ) throws RawNotFoundException
    {
        int verbAccompanierId = newSemanticNounBOManager.suggestAddVerbAccompanier( currentSemanticNounBO.getSemanticNounId(), newVerbAccompanierBO );
        newVerbAccompanierBO.setVerbAccompanierId( verbAccompanierId );

        newVerbAccompanierBO.setStatus( "I" );
        if ( !currentSemanticNounBO.getVerbAccompaniers().contains( newVerbAccompanierBO ) )
        {
            currentSemanticNounBO.getVerbAccompaniers().add( newVerbAccompanierBO );
        }

        return currentSemanticNounBO.getVerbAccompaniers();
    }

    public void addVerbAccompanier( VerbAccompanierBO verbAccompanier )
    {
        if ( !currentSemanticNounBO.getVerbAccompaniers().contains( verbAccompanier ) )
        {
            currentSemanticNounBO.getVerbAccompaniers().add( verbAccompanier );
        }
    }

    public List<VerbAccompanierBO> deleteVerbAccompanier( int id ) throws RawNotFoundException
    {
        int verbAccompanierId = currentSemanticNounBO.getVerbAccompaniers().get( id ).getVerbAccompanierId();
        currentSemanticNounBO.getVerbAccompaniers().get( id ).setStatus( "D" );
        SemanticNounBOManager.suggestDeleteNounAccompanier ( verbAccompanierId , currentSemanticNounBO.getSemanticNounId () );
        //SemanticNounBOManager.suggestDeleteAnnexedNoun( verbAccompanierId, currentSemanticNounBO.getSemanticNounId() );
        return currentSemanticNounBO.getVerbAccompaniers();
    }

    public void affirmVerbAccompanierAdding ( int verbAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmNounAccompanierAdding ( verbAccompanierId );
        clearCheck ();
    }

    public void rejectVerbAccompanierAdding ( int verbAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectNounAccompanierAdding ( verbAccompanierId );
        clearCheck ();
    }

    public void affirmVerbAccompanierDeleting ( int verbAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmNounAccompanierDeleting ( verbAccompanierId );
        clearCheck ();
    }

    public void rejectVerbAccompanierDeleting ( int verbAccompanierId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectNounAccompanierDeleting ( verbAccompanierId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    @Override
    public List<MeaningBO> addNewMeaning( String discription, String source ) throws RawNotFoundException, EntryExistedException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( discription );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "I" );
        if ( !this.currentSemanticNounBO.getMeanings().contains( newMeaningBO ) )
        {
            this.currentSemanticNounBO.getMeanings().add( newMeaningBO );
        }
        newSemanticNounBOManager.suggestAddMeaning( newMeaningBO, currentSemanticNounBO );
        
        return this.currentSemanticNounBO.getMeanings();

    }

    @Override
    public List<MeaningBO> updateMeaning( String description, String source, int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( description );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "U" );
        MeaningBO oldMeaningBO = currentSemanticNounBO.getMeanings().get( id );
        this.newSemanticNounBOManager.suggestUpdateMeaning( newMeaningBO, oldMeaningBO, currentSemanticNounBO );
        this.currentSemanticNounBO.getMeanings().set( id, newMeaningBO );
        return this.currentSemanticNounBO.getMeanings();
    }

    @Override
    public List<MeaningBO> deleteMeaning( int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = this.currentSemanticNounBO.getMeanings().get( id );
        newMeaningBO.setStatus ( "D" );
        newSemanticNounBOManager.suggestDeleteMeaning( newMeaningBO, currentSemanticNounBO );
        SemanticNounBOManager.suggestDeleteMeaning ( newMeaningBO , currentSemanticNounBO );
        this.currentSemanticNounBO.getMeanings().remove( id );
        return this.currentSemanticNounBO.getMeanings();
    }

    public void affirmMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmMeaningAdding ( meaningId );
        clearCheck ();
    }

    public boolean affirmMeaningAddingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmMeaningAddingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectMeaningAdding ( meaningId );
        clearCheck ();
    }

    public void rejectMeaningUpdating ( int meaningId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectMeaningUpdating ( meaningId );
        clearCheck ();
    }
    
    public void affirmMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmMeaningDeleting (semanticNounId ,meaningId );
        clearCheck ();
    }

    public void rejectMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectMeaningDeleting ( semanticNounId ,meaningId );
        clearCheck ();
    }
    public void affirmMeaningUpdating ( int meaningId ) throws RawNotFoundException    {
        SemanticNounBOManager.affirmMeaningUpdating ( meaningId );
        clearCheck ();
    }
    public boolean affirmMeaningUpdatingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmMeaningUpdatingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }


/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Example...">
    @Override
    public List<ExampleBO> addNewExample( String example, String source, byte[] sound ) throws RawNotFoundException, EntryExistedException
    {
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample( example );
        newExampleBO.setSource( source );
        newExampleBO.setStatus( "I" );
        newExampleBO.setSound( sound );
        if ( !this.currentSemanticNounBO.getExamples().contains( newExampleBO ) )
        {
            this.currentSemanticNounBO.getExamples().add( newExampleBO );
        }
        newSemanticNounBOManager.suggestAddExample( newExampleBO, this.currentSemanticNounBO );
        return this.currentSemanticNounBO.getExamples();
    }

    @Override
    public List<ExampleBO> addNewExample( String example, String source ) throws RawNotFoundException, EntryExistedException
    {
        return addNewExample( example, source, exampleSound );
    }

    public void affirmExampleAdding( int exampleId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmExampleAdding( semanticNounId, exampleId );
        clearCheck();
    }
    public boolean affirmExampleAddingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmExampleAddingAU ( semanticNounId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    @Override
    public List<ExampleBO> updateExample( String example, String source, byte[] sound, int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample( example );
        newExampleBO.setSource( source );
        newExampleBO.setSound( sound );
        newExampleBO.setStatus( "U" );
        ExampleBO oldExampleBO = currentSemanticNounBO.getExamples().get( id );
        this.newSemanticNounBOManager.suggestUpdateExample( newExampleBO, oldExampleBO, currentSemanticNounBO );
        this.currentSemanticNounBO.getExamples().set( id, newExampleBO );
        return this.currentSemanticNounBO.getExamples();
    }

    @Override
    public List<ExampleBO> updateExample( String example, String source, int id ) throws RawNotFoundException
    {
        return this.updateExample( example, source, exampleSound, id );
    }

    public List<ExampleBO> deleteExample( int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = this.currentSemanticNounBO.getExamples().get( id );
        newExampleBO.setStatus ( "D" );
        newSemanticNounBOManager.suggestDeleteExample( newExampleBO, currentSemanticNounBO );
        this.currentSemanticNounBO.getExamples().get( id ).setStatus( "D" );
        return this.currentSemanticNounBO.getExamples();
    }
    public void affirmExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticNounBOManager.affirmExampleUpdating ( semanticNounId , exampleId );
        clearCheck ();
    }
    public boolean affirmExampleUpdatingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmExampleUpdatingAU ( semanticNounId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticNounBOManager.rejectExampleUpdating ( semanticNounId , exampleId );
        clearCheck ();
    }

    public void rejectExampleAdding ( int exampleId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectExampleAdding ( semanticNounId, exampleId );
        clearCheck ();
    }

    public void affirmExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmExampleDeleting (semanticNounId ,exampleId );
        clearCheck ();
    }

    public void rejectExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectExampleDeleting ( semanticNounId ,exampleId );
        clearCheck ();
    }
/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Common mistake...">

    @Override
    public List<CommonMistakeBO> addNewCommonMistake( String commonMistake, String source ) throws RawNotFoundException, EntryExistedException
    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
        newCommonMistakeBO.setDescription( commonMistake );
        newCommonMistakeBO.setSource( source );
        newCommonMistakeBO.setStatus( "I" );
        if ( !this.currentSemanticNounBO.getCommonMistakes().contains( newCommonMistakeBO ) )
        {
            this.currentSemanticNounBO.getCommonMistakes().add( newCommonMistakeBO );
        }
        newSemanticNounBOManager.suggestAddCommonMistake( newCommonMistakeBO, currentSemanticNounBO );
        return this.currentSemanticNounBO.getCommonMistakes();
    }

    @Override
    public List<CommonMistakeBO> updateCommonMistake( String commonMistake, String source, int id ) throws RawNotFoundException
    {
        CommonMistakeBO oldCommonMistakeBO = currentSemanticNounBO.getCommonMistakes().get( id );
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
        newCommonMistakeBO.setDescription( commonMistake );
        newCommonMistakeBO.setSource( source );
        newCommonMistakeBO.setStatus( "U" );
        newSemanticNounBOManager.suggestUpdateCommonMistake( newCommonMistakeBO, oldCommonMistakeBO, currentSemanticNounBO );
        currentSemanticNounBO.getCommonMistakes().set( id, newCommonMistakeBO );
        return currentSemanticNounBO.getCommonMistakes();
    }

    @Override
    public List<CommonMistakeBO> deleteCommonMistake( int id ) throws RawNotFoundException
    {
        CommonMistakeBO newCommonMistakeBO = currentSemanticNounBO.getCommonMistakes().get( id );
        newCommonMistakeBO.setStatus ( "D" );
        newSemanticNounBOManager.suggestDeleteCommonMistake( newCommonMistakeBO, currentSemanticNounBO );
        currentSemanticNounBO.getCommonMistakes().get( id ).setStatus( "D" );
        return this.currentSemanticNounBO.getCommonMistakes();
    }
    public void affirmCommonMistakeAdding( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmCommonMistakeAdding( semanticNounId, commonMistakeId );
        clearCheck();
    }
    public boolean affirmCommonMistakeAddingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException
    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmCommonMistakeAddingAU ( semanticNounId , commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void affirmCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticNounBOManager.affirmCommonMistakeUpdating ( semanticNounId , commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeUpdatingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmCommonMistakeUpdatingAU ( semanticNounId , commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticNounBOManager.rejectCommonmistakeUpdating ( semanticNounId , commonMistakeId );
        clearCheck ();
    }

    public void rejectCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectCommonMistakeAdding ( semanticNounId, commonMistakeId );
        clearCheck ();
    }

    public void affirmCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmCommonmistakeDeleting (semanticNounId ,commonMistakeId );
        clearCheck ();
    }

    public void rejectCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectCommonmistakeDeleting ( semanticNounId ,commonMistakeId );
        clearCheck ();
    }
/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Linguistic Benefit...">

    @Override
    public List<LinguisticBenefitBO> addNewLinguisticBenefit( String linguisticBenefit, String source ) throws RawNotFoundException, EntryExistedException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO();
        newLinguisticBenefitBO.setDescription( linguisticBenefit );
        newLinguisticBenefitBO.setSource( source );
        newLinguisticBenefitBO.setStatus( "I" );
        if ( !this.currentSemanticNounBO.getLinguisticBenefits().contains( newLinguisticBenefitBO ) )
        {
            this.currentSemanticNounBO.getLinguisticBenefits().add( newLinguisticBenefitBO );
        }
        newSemanticNounBOManager.suggestAddLinguisticBenefit( newLinguisticBenefitBO, currentSemanticNounBO );
        return this.currentSemanticNounBO.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> updateLinguisticBenefit( String linguisticBenefit, String source, int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO oldLinguisticBenefitBO = currentSemanticNounBO.getLinguisticBenefits().get( id );
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO();
        newLinguisticBenefitBO.setDescription( linguisticBenefit );
        newLinguisticBenefitBO.setSource( source );
        newLinguisticBenefitBO.setStatus( "U" );
        newSemanticNounBOManager.suggestUpdateLinguisticBenefit( newLinguisticBenefitBO, oldLinguisticBenefitBO, currentSemanticNounBO );
        currentSemanticNounBO.getLinguisticBenefits().set( id, newLinguisticBenefitBO );
        return currentSemanticNounBO.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> deleteLinguisticBenefit( int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = currentSemanticNounBO.getLinguisticBenefits().get( id );
        newLinguisticBenefitBO.setStatus ( "D" );
        newSemanticNounBOManager.suggestDeleteLinguisticBenefit( newLinguisticBenefitBO, currentSemanticNounBO );
        currentSemanticNounBO.getLinguisticBenefits().get( id ).setStatus( "D" );
        return this.currentSemanticNounBO.getLinguisticBenefits();
    }
    public void affirmLinguisticBenefitAdding( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmLinguisticBenefitAdding( semanticNounId, linguisticBenefitId );
        clearCheck();
    }
    public boolean affirmLinguisticBenefitAddingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticNounBOManager.affirmLinguisticBenefitAddingAU ( semanticNounId , linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void affirmLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticNounBOManager.affirmLinguisticBenefitUpdating ( semanticNounId , linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitUpdatingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        newLinguisticBenefitBO.setLinguisticBenefitId (linguisticBenefitId);
        boolean updated = SemanticNounBOManager.affirmLinguisticBenefitUpdatingAU ( semanticNounId , linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticNounBOManager.rejectCommonmistakeUpdating ( semanticNounId , linguisticBenefitId );
        clearCheck ();
    }

    public void rejectLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectLinguisticBenefitAdding ( semanticNounId, linguisticBenefitId );
        clearCheck ();
    }

    public void affirmLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticNounBOManager.affirmCommonmistakeDeleting (semanticNounId ,linguisticBenefitId );
        clearCheck ();
    }

    public void rejectLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticNounBOManager.rejectCommonmistakeDeleting ( semanticNounId ,linguisticBenefitId );
        clearCheck ();
    }
/// </editor-fold>
}
