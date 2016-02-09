package DictionaryBeans;

import BusinessLogicLayer.BOManager.MeaningBOManager;
import BusinessLogicLayer.BOManager.SemanticParticleBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.SemanticParticleBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class SemanticParticleBean extends SemanticEntryBean
{

    private int semanticParticleId;
    private SemanticParticleBO currentSemanticParticleBO;
    private SemanticParticleBOManager newSemanticParticleBOManager;
    private MeaningBOManager newMeaningBOManager;
    private int derivedParticleId;
    private byte[] exampleSound;
    private int checkTracker = 0;
    private ParticleBean currentParticleBean;

    public SemanticParticleBean()
    {
        currentSemanticParticleBO = new SemanticParticleBO();
        newSemanticParticleBOManager = new SemanticParticleBOManager();
        newMeaningBOManager = new MeaningBOManager();
    }

/// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public SemanticParticleBO getCurrentSemanticParticleBO()
    {
        return currentSemanticParticleBO;
    }

    public void setCurrentSemanticParticleBO( SemanticParticleBO currentSemanticParticleBO )
    {
        this.currentSemanticParticleBO = currentSemanticParticleBO;
    }

    public int getDerivedParticleId()
    {
        return derivedParticleId;
    }

    public void setDerivedParticleId( int derivedParticleId )
    {
        this.derivedParticleId = derivedParticleId;
    }

    public String getDifficulty()
    {
        return this.currentSemanticParticleBO.getDifficultydegree();
    }

    public void setDifficulty( String difficulty ) throws BadWordException
    {
        this.currentSemanticParticleBO.setDifficultydegree( BeansUtil.getFormatedText( difficulty ) );
    }

    public String getEpoch()
    {
        return this.currentSemanticParticleBO.getEpoch();
    }

    public void setEpoch( String epoch ) throws BadWordException
    {
        this.currentSemanticParticleBO.setEpoch( BeansUtil.getFormatedText( epoch ) );
    }

    public String getMeaning()
    {
        return this.currentSemanticParticleBO.getMeanings().get( 0 ).getDescription();
    }

    public void setMeaning( String meaning ) throws BadWordException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( BeansUtil.getFormatedText( meaning ) );
        currentSemanticParticleBO.getMeanings().clear();
        currentSemanticParticleBO.getMeanings().add( newMeaningBO );
    }

    public String getRegion()
    {
        return this.currentSemanticParticleBO.getRegion();
    }

    public void setRegion( String region ) throws BadWordException
    {
        this.currentSemanticParticleBO.setRegion( BeansUtil.getFormatedText( region ) );
    }

    public String getSemanticScop()
    {
        return this.getSemanticScop();
    }

    public void setSemanticScop( String semanticScop ) throws BadWordException
    {
        this.currentSemanticParticleBO.setSemanticscop( BeansUtil.getFormatedText( semanticScop ) );
    }

    public String getSource()
    {
        return this.currentSemanticParticleBO.getMeanings().get( 0 ).getSource();
    }

    public void setSource( String source ) throws BadWordException
    {
        this.currentSemanticParticleBO.getMeanings().get( 0 ).setSource( BeansUtil.getFormatedText( source ) );
    }

    public String getSpecialization()
    {
        return this.currentSemanticParticleBO.getSpecialization();
    }

    public void setSpecialization( String specialization ) throws BadWordException
    {
        this.currentSemanticParticleBO.setSpecialization( BeansUtil.getFormatedText( specialization ) );
    }

    public String getSpreading()
    {
        return this.currentSemanticParticleBO.getSpreadingdegree();
    }

    public void setSpreading( String spreading ) throws BadWordException
    {
        this.currentSemanticParticleBO.setSpreadingdegree( BeansUtil.getFormatedText( spreading ) );
    }

    public void setExampleSound( byte[] sound )
    {
        this.exampleSound = sound;
    }

    public byte[] getExampleSound()
    {
        return this.exampleSound;
    }

    public ParticleBean getCurrentParticleBean()
    {
        return currentParticleBean;
    }

    public void setCurrentParticleBean( ParticleBean currentParticleBean )
    {
        this.currentParticleBean = currentParticleBean;
    }
/// </editor-fold>

    public void updateSemanticParticleInfo() throws RawNotFoundException
    {
        newSemanticParticleBOManager.suggestUpdateInfo( currentSemanticParticleBO, currentSemanticParticleBO.getSemanticParticleId() );
        currentSemanticParticleBO.setStatus( "U" );
    }

    public void deleteSemanticParticle( int semantciParticleId ) throws RawNotFoundException
    {
        newSemanticParticleBOManager.suggestDelete( semantciParticleId );
    }

    public SemanticParticleBO getSemanticParticle( int semanticParticleId )
    {
        this.semanticParticleId = semanticParticleId;
        currentSemanticParticleBO = SemanticParticleBOManager.getSemanticParticleBO( semanticParticleId , SearchProperties.detailedSearchOptions );
        return currentSemanticParticleBO;
    }

    public SemanticParticleBO getCheckedSemanticParticle ( int semanticParticleId ) throws RawNotFoundException
    {
         this.semanticParticleId = semanticParticleId;
         currentSemanticParticleBO = SemanticParticleBOManager.getSemanticParticleBO ( semanticParticleId , SearchProperties.detailedSearchOptions );
        checkTracker = SemanticParticleBOManager.getCheckedSemParticleWeight ( semanticParticleId );
        return currentSemanticParticleBO;
    }

    public int getSemanticParticleId ()
    {
        return semanticParticleId;
    }
    public void setSemanticParticleId ( int semanticParticleId )
    {
        this.semanticParticleId = semanticParticleId;
    }
    public int addSemanticParticleBO() throws EntryExistedException , RawNotFoundException
    {
        this.semanticParticleId = this.newSemanticParticleBOManager.suggestAdding( currentSemanticParticleBO, derivedParticleId );
        return this.semanticParticleId;
    }

    public void affirmAdding() throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmAdding( currentSemanticParticleBO.getSemanticParticleId() );
        clearCheck();
    }

    public boolean affirmAddingAU() throws RawNotFoundException , EntryExistedException
    {
        boolean isUpdated = SemanticParticleBOManager.affirmAddingAU( currentSemanticParticleBO.getSemanticParticleId() , currentSemanticParticleBO , currentParticleBean.getCurrentParticle().getDerivedParticleId() );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        return false;
    }
    public void clearCheck() throws RawNotFoundException
    {
        if ( checkTracker == 0 )
        {
            SemanticParticleBOManager.clearCheck( currentSemanticParticleBO.getSemanticParticleId() );
            currentParticleBean.clearCheck();
        }
        else
        {
            checkTracker --;
        }
    }

    public void affirmUpdating() throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmUpdating( currentSemanticParticleBO.getSemanticParticleId() );
        clearCheck();
    }

    public boolean affirmUpdatingAU() throws RawNotFoundException
    {
        boolean isUpdated = SemanticParticleBOManager.affirmUpdatingAU( currentSemanticParticleBO.getSemanticParticleId() , currentSemanticParticleBO );
        if ( isUpdated )
        {
            clearCheck();
            return true;
        }
        return false;
    }

    public void rejectUpdating() throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectUpdating( derivedParticleId );
        clearCheck();
    }

    public void rejectAdding() throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectAdding( derivedParticleId );
        clearCheck();
    }

    public void affirmDeleting() throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmDeleting( derivedParticleId );
        clearCheck();
    }

    public void rejectDeleting() throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectDeleting( derivedParticleId );
        clearCheck();
    }

    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    @Override
    public List<MeaningBO> addNewMeaning( String discription , String source ) throws RawNotFoundException , EntryExistedException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( discription );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "I" );
        if (  ! this.currentSemanticParticleBO.getMeanings().contains( newMeaningBO ) )
        {
            this.currentSemanticParticleBO.getMeanings().add( newMeaningBO );
        }
        newSemanticParticleBOManager.suggestAddMeaning( newMeaningBO , currentSemanticParticleBO );
        return this.currentSemanticParticleBO.getMeanings();
    }
    @Override
    public List<MeaningBO> updateMeaning( String description , String source , int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = new MeaningBO();
        newMeaningBO.setDescription( description );
        newMeaningBO.setSource( source );
        newMeaningBO.setStatus( "U" );
        MeaningBO oldMeaningBO = currentSemanticParticleBO.getMeanings().get( id );
        this.newSemanticParticleBOManager.suggestUpdateMeaning( newMeaningBO , oldMeaningBO , currentSemanticParticleBO );
        this.currentSemanticParticleBO.getMeanings().set( id , newMeaningBO );
        return this.currentSemanticParticleBO.getMeanings();
    }
    @Override
    public List<MeaningBO> deleteMeaning( int id ) throws RawNotFoundException
    {
        MeaningBO newMeaningBO = this.currentSemanticParticleBO.getMeanings().get( id );
        newMeaningBO.setStatus ( "D" );
        newSemanticParticleBOManager.suggestDeleteMeaning( newMeaningBO , currentSemanticParticleBO );
        newSemanticParticleBOManager.suggestDeleteMeaning ( newMeaningBO , currentSemanticParticleBO );
        this.currentSemanticParticleBO.getMeanings().remove( id );
        return this.currentSemanticParticleBO.getMeanings();
    }
    public void affirmMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmMeaningAdding ( meaningId );
        clearCheck ();
    }

    public boolean affirmMeaningAddingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmMeaningAddingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectMeaningAdding ( int meaningId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectMeaningAdding ( meaningId );
        clearCheck ();
    }

    public void rejectMeaningUpdating ( int meaningId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectMeaningUpdating ( meaningId );
        clearCheck ();
    }

    public void affirmMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmMeaningDeleting (semanticParticleId ,meaningId );
        clearCheck ();
    }

    public void rejectMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectMeaningDeleting ( semanticParticleId ,meaningId );
        clearCheck ();
    }
    public void affirmMeaningUpdating ( int meaningId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmMeaningUpdating ( meaningId );
        clearCheck ();
    }
    public boolean affirmMeaningUpdatingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmMeaningUpdatingAU ( meaningId , newMeaningBO );
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
        if (  ! this.currentSemanticParticleBO.getExamples().contains( newExampleBO ) )
        {
            this.currentSemanticParticleBO.getExamples().add( newExampleBO );
        }
        newSemanticParticleBOManager.suggestAddExample( newExampleBO , this.currentSemanticParticleBO );
        return this.currentSemanticParticleBO.getExamples();
    }
    @Override
    public List<ExampleBO> addNewExample( String example , String source ) throws RawNotFoundException , EntryExistedException
    {
        return addNewExample( example , source , exampleSound );
    }
    public void affirmExampleAdding( int exampleId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmExampleAdding( semanticParticleId, exampleId );
        clearCheck();
    }
     public boolean affirmExampleAddingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmExampleAddingAU ( semanticParticleId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    @Override
    public List<ExampleBO> updateExample( String example , String source , byte[] sound , int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample( example );
        newExampleBO.setSource( source );
        newExampleBO.setSound( sound );
        newExampleBO.setStatus( "U" );
        ExampleBO oldExampleBO = currentSemanticParticleBO.getExamples().get( id );
        this.newSemanticParticleBOManager.suggestUpdateExample( newExampleBO , oldExampleBO , currentSemanticParticleBO );
        this.currentSemanticParticleBO.getExamples().set( id , newExampleBO );
        return this.currentSemanticParticleBO.getExamples();
    }
    @Override
    public List<ExampleBO> updateExample( String example , String source , int id ) throws RawNotFoundException
    {
        return this.updateExample( example , source , exampleSound , id );
    }

    @Override
    public List<ExampleBO> deleteExample( int id ) throws RawNotFoundException
    {
        ExampleBO newExampleBO = this.currentSemanticParticleBO.getExamples().get( id );
        newExampleBO.setStatus ( "D" );
        newSemanticParticleBOManager.suggestDeleteExample( newExampleBO , currentSemanticParticleBO );
        this.currentSemanticParticleBO.getExamples().get( id ).setStatus( "D" );
        return this.currentSemanticParticleBO.getExamples();
    }
    public void affirmExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmExampleUpdating ( semanticParticleId , exampleId );
        clearCheck ();
    }
    public boolean affirmExampleUpdatingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmExampleUpdatingAU ( semanticParticleId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticParticleBOManager.rejectExampleUpdating ( semanticParticleId , exampleId );
        clearCheck ();
    }

    public void rejectExampleAdding ( int exampleId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectExampleAdding ( semanticParticleId, exampleId );
        clearCheck ();
    }

    public void affirmExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmExampleDeleting (semanticParticleId ,exampleId );
        clearCheck ();
    }

    public void rejectExampleDeleting ( int exampleId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectExampleDeleting ( semanticParticleId ,exampleId );
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
        if (  ! this.currentSemanticParticleBO.getCommonMistakes().contains( newCommonMistakeBO ) )
        {
            this.currentSemanticParticleBO.getCommonMistakes().add( newCommonMistakeBO );
        }
        newSemanticParticleBOManager.suggestAddCommonMistake( newCommonMistakeBO , currentSemanticParticleBO );
        return this.currentSemanticParticleBO.getCommonMistakes();
    }

    @Override
    public List<CommonMistakeBO> updateCommonMistake( String commonMistake , String source , int id ) throws RawNotFoundException
    {
        CommonMistakeBO oldCommonMistakeBO = currentSemanticParticleBO.getCommonMistakes().get( id );
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
        newCommonMistakeBO.setDescription( commonMistake );
        newCommonMistakeBO.setSource( source );
        newCommonMistakeBO.setStatus( "U" );
        newSemanticParticleBOManager.suggestUpdateCommonMistake( newCommonMistakeBO , oldCommonMistakeBO , currentSemanticParticleBO );
        currentSemanticParticleBO.getCommonMistakes().set( id , newCommonMistakeBO );
        return currentSemanticParticleBO.getCommonMistakes();
    }
    @Override
    public List<CommonMistakeBO> deleteCommonMistake( int id ) throws RawNotFoundException
    {
        CommonMistakeBO newCommonMistakeBO = currentSemanticParticleBO.getCommonMistakes().get( id );
        newCommonMistakeBO.setStatus ( "D" );
        newSemanticParticleBOManager.suggestDeleteCommonMistake( newCommonMistakeBO , currentSemanticParticleBO );
        currentSemanticParticleBO.getCommonMistakes().get( id ).setStatus( "D" );
        return this.currentSemanticParticleBO.getCommonMistakes();
    }

    public void affirmCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmCommonMistakeAdding ( semanticParticleId ,commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeAddingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmCommonMistakeAddingAU ( semanticParticleId, commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticParticleBOManager.rejectCommonMistakeAdding ( semanticParticleId, commonMistakeId );
        clearCheck ();
    }
    public void affirmCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmCommonMistakeUpdating (semanticParticleId, commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeUpdatingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmCommonMistakeUpdatingAU ( semanticParticleId, commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectCommonmistakeUpdating ( semanticParticleId, commonMistakeId );
        clearCheck ();
    }

    public void affirmCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmCommonmistakeDeleting ( semanticParticleId , commonMistakeId );
        clearCheck ();
    }

    public void rejectCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectCommonmistakeDeleting ( semanticParticleId , commonMistakeId );
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
        if (  ! this.currentSemanticParticleBO.getLinguisticBenefits().contains( newLinguisticBenefitBO ) )
        {
            this.currentSemanticParticleBO.getLinguisticBenefits().add( newLinguisticBenefitBO );
        }
        newSemanticParticleBOManager.suggestAddLinguisticBenefit( newLinguisticBenefitBO , currentSemanticParticleBO );
        return this.currentSemanticParticleBO.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> updateLinguisticBenefit( String linguisticBenefit , String source , int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO oldLinguisticBenefitBO = currentSemanticParticleBO.getLinguisticBenefits().get( id );
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO();
        newLinguisticBenefitBO.setDescription( linguisticBenefit );
        newLinguisticBenefitBO.setSource( source );
        newLinguisticBenefitBO.setStatus( "U" );
        newSemanticParticleBOManager.suggestUpdateLinguisticBenefit( newLinguisticBenefitBO , oldLinguisticBenefitBO , currentSemanticParticleBO );
        currentSemanticParticleBO.getLinguisticBenefits().set( id , newLinguisticBenefitBO );
        return currentSemanticParticleBO.getLinguisticBenefits();
    }

    @Override
    public List<LinguisticBenefitBO> deleteLinguisticBenefit( int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = currentSemanticParticleBO.getLinguisticBenefits().get( id );
        newLinguisticBenefitBO.setStatus ( "D" );
        newSemanticParticleBOManager.suggestDeleteLinguisticBenefit( newLinguisticBenefitBO , currentSemanticParticleBO );
        currentSemanticParticleBO.getLinguisticBenefits().get( id ).setStatus( "D" );
        return this.currentSemanticParticleBO.getLinguisticBenefits();
    }
    
    public void affirmLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmLinguisticBenefitAdding ( semanticParticleId ,linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitAddingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmLinguisticBenefitAddingAU ( semanticParticleId, linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticParticleBOManager.rejectLinguisticBenefitAdding ( semanticParticleId, linguisticBenefitId );
        clearCheck ();
    }
    public void affirmLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticParticleBOManager.affirmLinguisticBenefitUpdating (semanticParticleId, linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitUpdatingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticParticleBOManager.affirmLinguisticBenefitUpdatingAU ( semanticParticleId, linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectLinguisticBenefitUpdating ( semanticParticleId, linguisticBenefitId );
        clearCheck ();
    }

    public void affirmLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.affirmLinguisticBenefitDeleting ( semanticParticleId , linguisticBenefitId );
        clearCheck ();
    }

    public void rejectLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticParticleBOManager.rejectLinguisticBenefitDeleting ( semanticParticleId , linguisticBenefitId );
        clearCheck ();
    }
/// </editor-fold>
}
