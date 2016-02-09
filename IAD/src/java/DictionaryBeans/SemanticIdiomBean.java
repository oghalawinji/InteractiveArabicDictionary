package DictionaryBeans;

import BusinessLogicLayer.BOManager.IdiomBOManager;
import BusinessLogicLayer.BOManager.MeaningBOManager;
import BusinessLogicLayer.BOManager.SemanticIdiomBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class SemanticIdiomBean extends SemanticEntryBean
{

    private SemanticEntryBO currentSemanticEntry;
    private IdiomBO currentIdiom;
    private SemanticIdiomBOManager newSemanticIdiomBOManager;
    private IdiomBOManager newIdiomBOManager;
    private MeaningBOManager newMeaningBOManager;
    private int idiomId;
    private int semanticIdiomId;
    private byte[] exampleSound;
    private int checkTracker = 0;
    private IdiomBean currentIdiomBean;

    public SemanticIdiomBean()
    {
        currentSemanticEntry = new SemanticEntryBO();
        newSemanticIdiomBOManager = new SemanticIdiomBOManager();
        newMeaningBOManager = new MeaningBOManager();
        newIdiomBOManager = new IdiomBOManager();
    }

/// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public SemanticEntryBO getCurrentSemanticIdiom()
    {
        return currentSemanticEntry;
    }

    public void setCurrentSemanticIdiom( SemanticEntryBO currentSemanticIdiom )
    {
        this.currentSemanticEntry = currentSemanticIdiom;
    }

    public int getIdiomId()
    {
        return idiomId;
    }

    public IdiomBO getIdiomInfo( int idiomId ) throws RawNotFoundException
    {
        currentIdiom = newIdiomBOManager.getIdiomBO( idiomId , SearchProperties.simpleSearchOptions );
        currentSemanticEntry = currentIdiom.getSemanticCase();
        currentIdiom.setIdiomId( idiomId );
        return currentIdiom;
    }

    public void setIdiomId( int idiomId )
    {
        if ( currentIdiom == null )
        {
            currentIdiom = new IdiomBO();
        }
        this.idiomId = idiomId;
        currentIdiom.setIdiomId( idiomId );
    }

    public void setCurrentIdiomBO( int idiomId ) throws RawNotFoundException
    {
        currentIdiom = IdiomBOManager.getIdiom( idiomId );
       // this.idiomId = idiomId;
    }

    public String getDifficulty()
    {
        return this.currentSemanticEntry.getDifficultydegree();
    }

    public void setDifficulty( String difficulty ) throws BadWordException
    {
        this.currentSemanticEntry.setDifficultydegree( BeansUtil.getFormatedText( difficulty ) );
    }

    public String getEpoch()
    {
        return this.currentSemanticEntry.getEpoch();
    }

    public void setEpoch( String epoch ) throws BadWordException
    {
        this.currentSemanticEntry.setEpoch( BeansUtil.getFormatedText( epoch ) );
    }

    public String getMeaning()
    {
        return this.currentSemanticEntry.getMeanings().get( 0 ).getDescription();
    }

    public void setMeaning( String meaning ) throws BadWordException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( BeansUtil.getFormatedText( meaning ) );
        currentSemanticEntry.getMeanings().clear();
        currentSemanticEntry.getMeanings().add( newMeaningBO );
    }

    public String getRegion()
    {
        return this.currentSemanticEntry.getRegion();
    }

    public void setRegion( String region ) throws BadWordException
    {
        this.currentSemanticEntry.setRegion( BeansUtil.getFormatedText( region ) );
    }

    public String getSemanticScop()
    {
        return this.getSemanticScop();
    }

    public void setSemanticScop( String semanticScop ) throws BadWordException
    {
        this.currentSemanticEntry.setSemanticscop( BeansUtil.getFormatedText( semanticScop ) );
    }

    public String getSource()
    {
        return this.currentSemanticEntry.getMeanings().get( 0 ).getSource();
    }

    public void setSource( String source ) throws BadWordException
    {
        this.currentSemanticEntry.getMeanings().get( 0 ).setSource( BeansUtil.getFormatedText( source ) );
    }

    public String getSpecialization()
    {
        return this.currentSemanticEntry.getSpecialization();
    }

    public void setSpecialization( String specialization ) throws BadWordException
    {
        this.currentSemanticEntry.setSpecialization( BeansUtil.getFormatedText( specialization ) );
    }

    public String getSpreading()
    {
        return this.currentSemanticEntry.getSpreadingdegree();
    }

    public void setSpreading( String spreading ) throws BadWordException
    {
        this.currentSemanticEntry.setSpreadingdegree( BeansUtil.getFormatedText( spreading ) );
    }

    public void setExampleSound( byte[] sound )
    {
        this.exampleSound = sound;
    }

    public byte[] getExampleSound()
    {
        return this.exampleSound;
    }

    public IdiomBean getCurrentIdiomBean()
    {
        return currentIdiomBean;
    }

    public void setCurrentIdiomBean( IdiomBean currentIdiomBean )
    {
        this.currentIdiomBean = currentIdiomBean;
    }

/// </editor-fold>
/// <editor-fold defaultstate="collapsed" desc="adding...">
    public int addSemanticIdiomBO() throws EntryExistedException , RawNotFoundException , Exception
    {
        return newSemanticIdiomBOManager.suggestAdding( currentSemanticEntry , idiomId );
    }
/// </editor-fold>Adding

    public void deleteSemanticIdiom( int idiomId ) throws RawNotFoundException
    {
        newSemanticIdiomBOManager.suggestDeleting( idiomId );
    }

    public SemanticEntryBO getSemanticIdiom( int semanticIdiomId ) throws RawNotFoundException
    {
        currentSemanticEntry = SemanticIdiomBOManager.getSemanticIdiomBO( semanticIdiomId , SearchProperties.detailedSearchOptions );
        return currentSemanticEntry;
    }

    public void updateSemanticIdiomInfo() throws RawNotFoundException
    {
        newSemanticIdiomBOManager.suggestUpdateInfo ( currentIdiom.getId () , currentSemanticEntry );
        currentSemanticEntry.setStatus( "U" );
    }

    public void affirmAdding() throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmAdding( currentIdiomBean.getCurrentIdiom ().getIdiomId () );
        currentIdiomBean.clearCheck();
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmUpdating( currentIdiomBean.getCurrentIdiom ().getIdiomId() );
        currentIdiomBean.clearCheck();
    }

    public boolean affirmUpdatingAU() throws RawNotFoundException
    {
        boolean isUpdated = SemanticIdiomBOManager.affirmUpdatingAU( currentIdiomBean.getCurrentIdiom ().getIdiomId() , currentSemanticEntry );
        if ( isUpdated )
        {
            currentIdiomBean.clearCheck();
            return true;
        }
        return false;
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectUpdating( currentIdiomBean.getCurrentIdiom ().getIdiomId () );
        currentIdiomBean.clearCheck();
    }

    public void rejectAdding() throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectAdding( currentIdiomBean.getCurrentIdiom ().getIdiomId () );
        currentIdiomBean.clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmDeleting( currentIdiom.getId () );
        currentIdiomBean.clearCheck();
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectDeleting( currentIdiom.getId () );
        currentIdiomBean.clearCheck();
    }
    public SemanticEntryBO getCheckedSemanticIdiom ( int semanticIdiomId ) throws RawNotFoundException
    {
        this.semanticIdiomId = semanticIdiomId;
        currentSemanticEntry = SemanticIdiomBOManager.getSemanticIdiomBO ( semanticIdiomId , SearchProperties.detailedSearchOptions );
        checkTracker = SemanticIdiomBOManager.getCheckedSemEntryWeight ( semanticIdiomId );
        return currentSemanticEntry;
    }
    public void clearCheck() throws RawNotFoundException
    {
        checkTracker--;
        if ( checkTracker == 0 )
        {
            IdiomBOManager.clearCheck( currentIdiom.getIdiomId () );
        }
    }
/// <editor-fold defaultstate="collapsed" desc="Meaning...">

    public List<MeaningBO> addNewMeaning( String discription , String source ) throws RawNotFoundException , EntryExistedException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( discription );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "I" );
        if (  ! this.currentSemanticEntry.getMeanings().contains( newMeaningBO ) )
        {
            this.currentSemanticEntry.getMeanings().add( newMeaningBO );
        }
        newSemanticIdiomBOManager.suggestAddMeaning( newMeaningBO , currentIdiom );
        return this.currentSemanticEntry.getMeanings();
    }

    @Override
    public List<MeaningBO> updateMeaning( String description , String source , int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( description );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "U" );
        MeaningBO oldMeaningBO = currentSemanticEntry.getMeanings().get( id );
        this.newSemanticIdiomBOManager.suggestUpdateMeaning( newMeaningBO , oldMeaningBO , currentIdiom );
        this.currentSemanticEntry.getMeanings().set( id , newMeaningBO );
        return this.currentSemanticEntry.getMeanings();
    }

    @Override
    public List<MeaningBO> deleteMeaning( int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = this.currentSemanticEntry.getMeanings().get( id );
        newSemanticIdiomBOManager.suggestDeleteMeaning( newMeaningBO , currentIdiom );
        this.currentSemanticEntry.getMeanings().remove( id );
        return this.currentSemanticEntry.getMeanings();
    }

    public void affirmMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmMeaningAdding ( meaningId );
        clearCheck ();
    }

    public boolean affirmMeaningAddingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmMeaningAddingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectMeaningAdding ( meaningId );
        clearCheck ();
    }

    public void rejectMeaningUpdating ( int meaningId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectMeaningUpdating ( meaningId );
        clearCheck ();
    }

    public void affirmMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmMeaningDeleting (currentIdiomBean.getCurrentIdiom ().getId () ,meaningId );
        clearCheck ();
    }

    public void rejectMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectMeaningDeleting ( currentIdiomBean.getCurrentIdiom ().getId () ,meaningId );
        clearCheck ();
    }
    public void affirmMeaningUpdating ( int meaningId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmMeaningUpdating ( meaningId );
        clearCheck ();
    }
    public boolean affirmMeaningUpdatingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmMeaningUpdatingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }

/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Example...">
    @Override
    public List<ExampleBO> addNewExample( String example , String source , byte[] sound ) throws RawNotFoundException , EntryExistedException
    {
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample( example );
        newExampleBO.setSource( source );
        newExampleBO.setStatus( "I" );
        newExampleBO.setSound( sound );
        if (  ! this.currentSemanticEntry.getExamples().contains( newExampleBO ) )
        {
            this.currentSemanticEntry.getExamples().add( newExampleBO );
        }
        newSemanticIdiomBOManager.suggestAddExample( newExampleBO , this.currentIdiom );
        return this.currentSemanticEntry.getExamples();
    }

    @Override
    public List<ExampleBO> addNewExample( String example , String source ) throws RawNotFoundException , EntryExistedException
    {
        return addNewExample( example , source , exampleSound );
    }

    @Override
    public List<ExampleBO> updateExample( String example , String source , byte[] sound , int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample( example );
        newExampleBO.setSource( source );
        newExampleBO.setSound( sound );
        newExampleBO.setStatus( "U" );
        ExampleBO oldExampleBO = currentSemanticEntry.getExamples().get( id );
        this.newSemanticIdiomBOManager.suggestUpdateExample( newExampleBO , oldExampleBO , currentIdiom );
        this.currentSemanticEntry.getExamples().set( id , newExampleBO );
        return this.currentSemanticEntry.getExamples();
    }

    @Override
    public List<ExampleBO> updateExample( String example , String source , int id ) throws RawNotFoundException
    {
        return this.updateExample( example , source , exampleSound , id );
    }

    @Override
    public List<ExampleBO> deleteExample( int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = this.currentSemanticEntry.getExamples().get( id );
        newSemanticIdiomBOManager.suggestDeleteExample( newExampleBO , currentIdiom );
        this.currentSemanticEntry.getExamples().get( id ).setStatus( "D" );
        return this.currentSemanticEntry.getExamples();
    }
    public void affirmExampleAdding( int exampleId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmExampleAdding( currentIdiomBean.getCurrentIdiom ().getId (), exampleId );
        clearCheck();
    }
     public boolean affirmExampleAddingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmExampleAddingAU (currentIdiomBean.getCurrentIdiom ().getId () , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    
    public void affirmExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmExampleUpdating ( currentIdiomBean.getCurrentIdiom ().getId () , exampleId );
        clearCheck ();
    }
    public boolean affirmExampleUpdatingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmExampleUpdatingAU ( currentIdiomBean.getCurrentIdiom ().getId () , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.rejectExampleUpdating ( currentIdiomBean.getCurrentIdiom ().getId () , exampleId );
        clearCheck ();
    }

    public void rejectExampleAdding ( int exampleId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectExampleAdding ( currentIdiomBean.getCurrentIdiom ().getId (), exampleId );
        clearCheck ();
    }

    public void affirmExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmExampleDeleting (currentIdiomBean.getCurrentIdiom ().getId () ,exampleId );
        clearCheck ();
    }

    public void rejectExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectExampleDeleting ( currentIdiomBean.getCurrentIdiom ().getId () ,exampleId );
        clearCheck ();
    }
/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Common mistake...">
    @Override
    public List<CommonMistakeBO> addNewCommonMistake( String commonMistake , String source ) throws RawNotFoundException , EntryExistedException
    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
        newCommonMistakeBO.setDescription( commonMistake );
        newCommonMistakeBO.setSource( source );
        newCommonMistakeBO.setStatus( "I" );
        if (  ! this.currentSemanticEntry.getCommonMistakes().contains( newCommonMistakeBO ) )
        {
            this.currentSemanticEntry.getCommonMistakes().add( newCommonMistakeBO );
        }
        newSemanticIdiomBOManager.suggestAddCommonMistake( newCommonMistakeBO , currentIdiom );
        return this.currentSemanticEntry.getCommonMistakes();
    }

    @Override
    public List<CommonMistakeBO> updateCommonMistake( String commonMistake , String source , int id ) throws RawNotFoundException
    {
        CommonMistakeBO oldCommonMistakeBO = currentSemanticEntry.getCommonMistakes().get( id );
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
        newCommonMistakeBO.setDescription( commonMistake );
        newCommonMistakeBO.setSource( source );
        newCommonMistakeBO.setStatus( "U" );
        newSemanticIdiomBOManager.suggestUpdateCommonMistake( newCommonMistakeBO , oldCommonMistakeBO , currentIdiom );
        currentSemanticEntry.getCommonMistakes().set( id , newCommonMistakeBO );
        return currentSemanticEntry.getCommonMistakes();
    }

    @Override
    public List<CommonMistakeBO> deleteCommonMistake( int id ) throws RawNotFoundException
    {
        CommonMistakeBO newCommonMistakeBO = currentSemanticEntry.getCommonMistakes().get( id );
        newSemanticIdiomBOManager.suggestDeleteCommonMistake( newCommonMistakeBO , currentIdiom );
        currentSemanticEntry.getCommonMistakes().get( id ).setStatus( "D" );
        return this.currentSemanticEntry.getCommonMistakes();
    }

    public void affirmCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmCommonMistakeAdding ( currentIdiomBean.getCurrentIdiom ().getId () ,commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeAddingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmCommonMistakeAddingAU ( currentIdiomBean.getCurrentIdiom ().getId (), commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.rejectCommonMistakeAdding ( currentIdiomBean.getCurrentIdiom ().getId (), commonMistakeId );
        clearCheck ();
    }
    public void affirmCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmCommonMistakeUpdating (currentIdiomBean.getCurrentIdiom ().getId (), commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeUpdatingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmCommonMistakeUpdatingAU ( currentIdiomBean.getCurrentIdiom ().getId (), commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectCommonmistakeUpdating ( currentIdiomBean.getCurrentIdiom ().getId (), commonMistakeId );
        clearCheck ();
    }

    public void affirmCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmCommonmistakeDeleting ( currentIdiomBean.getCurrentIdiom ().getId () , commonMistakeId );
        clearCheck ();
    }

    public void rejectCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectCommonmistakeDeleting ( currentIdiomBean.getCurrentIdiom ().getId () , commonMistakeId );
        clearCheck ();
    }

/// </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Linguistic benefit...">
    @Override
    public List<LinguisticBenefitBO> addNewLinguisticBenefit( String linguisticBenefit , String source ) throws RawNotFoundException , EntryExistedException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO();
        newLinguisticBenefitBO.setDescription( linguisticBenefit );
        newLinguisticBenefitBO.setSource( source );
        newLinguisticBenefitBO.setStatus( "I" );
        if (  ! this.currentSemanticEntry.getLinguisticBenefits().contains( newLinguisticBenefitBO ) )
        {
            this.currentSemanticEntry.getLinguisticBenefits().add( newLinguisticBenefitBO );
        }
        newSemanticIdiomBOManager.suggestAddLinguisticBenefit( newLinguisticBenefitBO , currentIdiom );
        return this.currentSemanticEntry.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> updateLinguisticBenefit( String linguisticBenefit , String source , int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO oldLinguisticBenefitBO = currentSemanticEntry.getLinguisticBenefits().get( id );
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO();
        newLinguisticBenefitBO.setDescription( linguisticBenefit );
        newLinguisticBenefitBO.setSource( source );
        newLinguisticBenefitBO.setStatus( "U" );
        newSemanticIdiomBOManager.suggestUpdateLinguisticBenefit( newLinguisticBenefitBO , oldLinguisticBenefitBO , currentIdiom );
        currentSemanticEntry.getLinguisticBenefits().set( id , newLinguisticBenefitBO );
        return currentSemanticEntry.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> deleteLinguisticBenefit( int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = currentSemanticEntry.getLinguisticBenefits().get( id );
        newSemanticIdiomBOManager.suggestDeleteLinguisticBenefit( newLinguisticBenefitBO , currentIdiom );
        currentSemanticEntry.getLinguisticBenefits().get( id ).setStatus( "D" );
        return this.currentSemanticEntry.getLinguisticBenefits();
    }

    public void affirmLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmLinguisticBenefitAdding ( currentIdiomBean.getCurrentIdiom ().getId () ,linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitAddingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmLinguisticBenefitAddingAU  ( currentIdiomBean.getCurrentIdiom ().getId (), linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.rejectLinguisticBenefitAdding ( currentIdiomBean.getCurrentIdiom ().getId (), linguisticBenefitId );
        clearCheck ();
    }
    public void affirmLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticIdiomBOManager.affirmLinguisticBenefitUpdating (currentIdiomBean.getCurrentIdiom ().getId (), linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitUpdatingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticIdiomBOManager.affirmLinguisticBenefitUpdatingAU ( currentIdiomBean.getCurrentIdiom ().getId (), linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectLinguisticBenefitUpdating (  currentIdiomBean.getCurrentIdiom ().getId (), linguisticBenefitId );
        clearCheck ();
    }

    public void affirmLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.affirmLinguisticBenefitDeleting ( currentIdiomBean.getCurrentIdiom ().getId () , linguisticBenefitId );
        clearCheck ();
    }

    public void rejectLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticIdiomBOManager.rejectLinguisticBenefitDeleting ( currentIdiomBean.getCurrentIdiom ().getId () , linguisticBenefitId );
        clearCheck ();
    }
/// </editor-fold>
}