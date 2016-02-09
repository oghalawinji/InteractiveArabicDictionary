package DictionaryBeans;

import BusinessLogicLayer.BOManager.ExampleBOManager;
import BusinessLogicLayer.BOManager.MeaningBOManager;
import BusinessLogicLayer.BOManager.SemanticVerbBOManager;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BOManager.VerbBOManager;
import BusinessLogicLayer.BusinessObjects.AssimilateAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExaggerationBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.GerundBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.NounAccompanierBO;
import BusinessLogicLayer.BusinessObjects.SemanticVerbBO;
import BusinessLogicLayer.SearchProperties;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fadel
 */
public class SemanticVerbBean extends SemanticEntryBean
{

    private int derivedVerbId = -1;
    private SemanticVerbBO currentSemanticVerbBO;
    private SemanticVerbBOManager newSemanticVerbBOManager;
    private MeaningBOManager newMeaningBOManager;
    private ExampleBOManager newExampleBOManager;
    private int semanticVerbId;
    private byte[] exampleSound;
    private int checkTracker = 0;
    private VerbBean currentVerbBean;

    //Construtor:
    public SemanticVerbBean ()
    {
        currentSemanticVerbBO = new SemanticVerbBO ();
        newSemanticVerbBOManager = new SemanticVerbBOManager ();
        newMeaningBOManager = new MeaningBOManager ();
        newExampleBOManager = new ExampleBOManager ();
    }

    public int addSemanticVerbBO () throws EntryExistedException , RawNotFoundException
    {
        this.semanticVerbId = this.newSemanticVerbBOManager.suggestAdding ( currentSemanticVerbBO , derivedVerbId );
        return this.semanticVerbId;
    }

    public void addContextActor ( String contextActor ) throws BadWordException
    {
    }

    public void updateSemanticVerbInfo () throws RawNotFoundException
    {
        newSemanticVerbBOManager.suggestUpdateInfo ( currentSemanticVerbBO , currentSemanticVerbBO.getSemanticVerbId () );
        currentSemanticVerbBO.setStatus ( "U" );
    }

    public void deleteSemanticVerb ( int semanticVerbId ) throws RawNotFoundException
    {
        newSemanticVerbBOManager.suggestDeleting ( semanticVerbId );
    }

    public void affirmAdding () throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmAdding ( currentSemanticVerbBO.getSemanticVerbId () );
        clearCheck ();
    }

    public boolean affirmAddingAU () throws RawNotFoundException , EntryExistedException
    {
        boolean isUpdated = SemanticVerbBOManager.affirmAddingAU ( semanticVerbId , currentSemanticVerbBO , currentVerbBean.getCurrentVerb ().getDerivedVerbId () );
        if ( isUpdated )
        {
            clearCheck ();
            return true;
        }
        return false;
    }

    public void affirmUpdating () throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmUpdating ( semanticVerbId );
        clearCheck ();
    }

    public boolean affirmUpdatingAU () throws RawNotFoundException
    {
        boolean isUpdated = SemanticVerbBOManager.affirmUpdatingAU ( semanticVerbId , currentSemanticVerbBO );
        if ( isUpdated )
        {
            clearCheck ();
            return true;
        }
        return false;
    }

    public void rejectUpdating () throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectUpdating ( semanticVerbId );
        clearCheck ();
    }

    public void rejectAdding () throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectAdding ( semanticVerbId );
        clearCheck ();
    }

    public void affirmDeleting () throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmDeleting ( semanticVerbId );
        clearCheck ();
    }

    public void rejectDeleting () throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectDeleting ( semanticVerbId );
        clearCheck ();
    }

    public void clearCheck () throws RawNotFoundException
    {
        checkTracker --;
        if ( checkTracker == 0 )
        {
            SemanticVerbBOManager.clearCheck ( currentSemanticVerbBO.getSemanticVerbId () );
            currentVerbBean.clearCheck ();
        }
    }

    /// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public SemanticVerbBO getSemanticVerb ( int semanticVerbId )
    {
        //Waleed
        //if(semanticVerbId == 0)
      //  {
        this.semanticVerbId = semanticVerbId;
        currentSemanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO ( semanticVerbId , SearchProperties.detailedSearchOptions );
      //  }
        return currentSemanticVerbBO;
    }

    public SemanticVerbBO getCheckedSemanticVerb ( int semanticVerbId ) throws RawNotFoundException
    {
        this.semanticVerbId = semanticVerbId;
        currentSemanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO ( semanticVerbId , SearchProperties.detailedSearchOptions );
        checkTracker = SemanticVerbBOManager.getCheckedSemVerbWeight ( semanticVerbId );
        return currentSemanticVerbBO;
    }

    public SemanticVerbBO getCurrentSemanticVerbBO ()
    {
        return currentSemanticVerbBO;
    }

    public int getDerivedVerbId ()
    {
        return derivedVerbId;
    }

    public void setDerivedVerbId ( int deriverVerbId )
    {
        this.derivedVerbId = deriverVerbId;
    }

    public String getDifficulty ()
    {
        return this.currentSemanticVerbBO.getDifficultydegree ();
    }

    public void setDifficulty ( String difficulty ) throws BadWordException
    {
        this.currentSemanticVerbBO.setDifficultydegree ( BeansUtil.getFormatedText ( difficulty ) );
    }

    public String getEpoch ()
    {
        return this.currentSemanticVerbBO.getEpoch ();
    }

    public void setEpoch ( String epoch ) throws BadWordException
    {
        this.currentSemanticVerbBO.setEpoch ( BeansUtil.getFormatedText ( epoch ) );
    }

    public String getMeaning ()
    {
        return this.currentSemanticVerbBO.getMeanings ().get ( 0 ).getDescription ();
    }

    public void setMeaning ( String meaning ) throws BadWordException
    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( BeansUtil.getFormatedText ( meaning ) );
        currentSemanticVerbBO.getMeanings ().clear ();
        currentSemanticVerbBO.getMeanings ().add ( newMeaningBO );
    }

    public String getRegion ()
    {
        return this.currentSemanticVerbBO.getRegion ();
    }

    public void setRegion ( String region ) throws BadWordException
    {
        this.currentSemanticVerbBO.setRegion ( BeansUtil.getFormatedText ( region ) );
    }

    public String getSemanticScop ()
    {
        return this.getSemanticScop ();
    }

    public void setSemanticScop ( String semanticScop ) throws BadWordException
    {
        this.currentSemanticVerbBO.setSemanticscop ( BeansUtil.getFormatedText ( semanticScop ) );
    }

    public String getSource ()
    {
        return this.currentSemanticVerbBO.getMeanings ().get ( 0 ).getSource ();
    }

    public void setSource ( String source ) throws BadWordException
    {
        this.currentSemanticVerbBO.getMeanings ().get ( 0 ).setSource ( BeansUtil.getFormatedText ( source ) );
    }

    public String getSpecialization ()
    {
        return this.currentSemanticVerbBO.getSpecialization ();
    }

    public void setSpecialization ( String specialization ) throws BadWordException
    {
        this.currentSemanticVerbBO.setSpecialization ( BeansUtil.getFormatedText ( specialization ) );
    }

    public String getSpreading ()
    {
        return this.currentSemanticVerbBO.getSpreadingdegree ();
    }

    public void setSpreading ( String spreading ) throws BadWordException
    {
        this.currentSemanticVerbBO.setSpreadingdegree ( BeansUtil.getFormatedText ( spreading ) );
    }

    public String getTransitivityCase ()
    {
        return this.currentSemanticVerbBO.getTransitivityCase ();
    }

    public void setTransitivityCase ( String transitivityCase ) throws BadWordException
    {
        this.currentSemanticVerbBO.setTransitivityCase ( BeansUtil.getFormatedText ( transitivityCase ) );
    }

    public List<AssimilateAdjectiveBO> getAssimilateAdjectives ()
    {
        return this.currentSemanticVerbBO.getAssimilateAdjectives ();
    }

    public void setAssimilateAdjectives ( List<AssimilateAdjectiveBO> assimilateAdjectives )
    {
        this.currentSemanticVerbBO.setAssimilateAdjectives ( assimilateAdjectives );
    }

    public List<String> getContextActors ()
    {
        return null;
    }

    public void setContextActors ( List<String> contextActors )
    {
    }

    public List<ExaggerationBO> getExaggerations ()
    {
        return currentSemanticVerbBO.getExaggerations ();
    }

    public void setExaggerations ( List<ExaggerationBO> exaggerations )
    {
        this.currentSemanticVerbBO.setExaggerations ( exaggerations );
    }

    public List<GerundBO> getGerunds ()
    {
        return this.currentSemanticVerbBO.getGerunds ();
    }

    public void setGerunds ( List<GerundBO> gerunds )
    {
        this.currentSemanticVerbBO.setGerunds ( gerunds );
    }

    public List<NounAccompanierBO> getNounAccompaniers ()
    {
        return this.currentSemanticVerbBO.getNounAccompaniers ();
    }

    public void setNounAccompaniers ( List<NounAccompanierBO> nounAccompaniers )
    {
        this.currentSemanticVerbBO.setNounAccompaniers ( nounAccompaniers );
    }

    public SemanticVerbBOManager getNewSemanticVerbBOManager ()
    {
        return newSemanticVerbBOManager;
    }

    public void setNewSemanticVerbBOManager ( SemanticVerbBOManager newSemanticVerbBOManager )
    {
        this.newSemanticVerbBOManager = newSemanticVerbBOManager;
    }

    public int getSemanticVerbId ()
    {
        return semanticVerbId;
    }

    public void setSemanticVerbId ( int semanticVerbId )
    {
        this.semanticVerbId = semanticVerbId;
    }

    public void setCurrentSemanticVerbBO ( SemanticVerbBO newSemanticVerbBO )
    {
        this.currentSemanticVerbBO = newSemanticVerbBO;
    }

    public void setExampleSound ( byte[] sound )
    {
        this.exampleSound = sound;
    }

    public byte[] getExampleSound ()
    {
        return this.exampleSound;
    }

    public VerbBean getCurrentVerbBean ()
    {
        return currentVerbBean;
    }

    public void setCurrentVerbBean ( VerbBean currentVerbBean )
    {
        this.currentVerbBean = currentVerbBean;
    }
    // </editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Noun Accompanier">

    public void addNounAccompanier ( NounAccompanierBO nounAccompanier ) throws BadWordException
    {
        if (  ! this.currentSemanticVerbBO.getNounAccompaniers ().contains ( nounAccompanier ) )
        {
            this.currentSemanticVerbBO.getNounAccompaniers ().add ( nounAccompanier );
        }
    }

    public List<NounAccompanierBO> joinNounAccompanier ( NounAccompanierBO newNounAccompanierBO ) throws RawNotFoundException
    {
        int nounAccompanierId = newSemanticVerbBOManager.suggestAddNounAccompanier ( currentSemanticVerbBO.getSemanticVerbId () , newNounAccompanierBO );
        newNounAccompanierBO.setNounAccompanierId ( nounAccompanierId );

        newNounAccompanierBO.setStatus ( "I" );
        if (  ! currentSemanticVerbBO.getNounAccompaniers ().contains ( newNounAccompanierBO ) )
        {
            currentSemanticVerbBO.getNounAccompaniers ().add ( newNounAccompanierBO );
        }

        return currentSemanticVerbBO.getNounAccompaniers ();
    }

    public List<NounAccompanierBO> deleteNounAccompanier ( int id ) throws RawNotFoundException
    {
        int nounAccompanierId = currentSemanticVerbBO.getNounAccompaniers ().get ( id ).getNounAccompanierId ();
        currentSemanticVerbBO.getNounAccompaniers ().get ( id ).setStatus ( "D" );
        SemanticVerbBOManager.suggestDeleteNounAccompanier ( nounAccompanierId , currentSemanticVerbBO.getSemanticVerbId () );
        /*try{
        VerbBOManager.setNeedCheck ( derivedVerbId );
        }
        catch(Exception ex)
        {}*/
        return currentSemanticVerbBO.getNounAccompaniers ();
    }

    public void affirmNounAccompanierAdding ( int nounId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmNounAccompanierAdding ( nounId );
        clearCheck ();
    }

    public void rejectNounAccompanierAdding ( int nounId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectNounAccompanierAdding ( nounId );
        clearCheck ();
    }

    public void affirmNounAccompanierDeleting ( int nounId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmNounAccompanierDeleting ( nounId );
        clearCheck ();
    }

    public void rejectNounAccompanierDeleting ( int nounId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectNounAccompanierDeleting ( nounId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Gerund">

    public void addGerund ( GerundBO gerund ) throws BadWordException
    {
        if ( this.currentSemanticVerbBO.getGerunds () == null )
        {
            this.currentSemanticVerbBO.setGerunds ( new ArrayList<GerundBO> () );
        }
        if (  ! this.currentSemanticVerbBO.getGerunds ().contains ( gerund ) )
        {
            this.currentSemanticVerbBO.getGerunds ().add ( gerund );
        }
    }

    public List<GerundBO> joinGerund ( GerundBO newGerundBO ) throws RawNotFoundException
    {
        int nounAccompanierId = newSemanticVerbBOManager.suggestAddGerund ( currentSemanticVerbBO.getSemanticVerbId () , newGerundBO );
        newGerundBO.setGerundId ( nounAccompanierId );

        newGerundBO.setStatus ( "I" );
        if (  ! currentSemanticVerbBO.getGerunds ().contains ( newGerundBO ) )
        {
            currentSemanticVerbBO.getGerunds ().add ( newGerundBO );
        }

        return currentSemanticVerbBO.getGerunds ();
    }

    public List<GerundBO> deleteGerund ( int id ) throws RawNotFoundException
    {
        int gerundId = currentSemanticVerbBO.getGerunds ().get ( id ).getGerundId ();
        currentSemanticVerbBO.getGerunds ().get ( id ).setStatus ( "D" );
        SemanticVerbBOManager.suggestDeleteGerund ( gerundId , currentSemanticVerbBO.getSemanticVerbId () );
        //VerbBOManager.setNeedCheck ( derivedVerbId );
        return currentSemanticVerbBO.getGerunds ();
    }

    public void affirmGerundAdding ( int gerundId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmGerundAdding ( gerundId );
        clearCheck ();
    }

    public void rejectGerundAdding ( int gerundId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectGerundAdding ( gerundId );
        clearCheck ();
    }

    public void affirmGerundDeleting ( int gerundId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmGerundDeleting ( gerundId );
        clearCheck ();
    }

    public void rejectGerundDeleting ( int gerundId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectGerundDeleting ( gerundId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Assimilate Adjective">
    public void addAssimilateAdjective ( AssimilateAdjectiveBO assimilateAdjective ) throws BadWordException
    {
        if ( this.currentSemanticVerbBO.getAssimilateAdjectives () == null )
        {
            this.currentSemanticVerbBO.setAssimilateAdjectives ( new ArrayList<AssimilateAdjectiveBO> () );
        }
        if (  ! this.currentSemanticVerbBO.getAssimilateAdjectives ().contains ( assimilateAdjective ) )
        {
            this.currentSemanticVerbBO.getAssimilateAdjectives ().add ( assimilateAdjective );
        }
    }

    public List<AssimilateAdjectiveBO> joinAssimilateAdjective ( AssimilateAdjectiveBO newAssimilateAdjectiveBO ) throws RawNotFoundException
    {
        int assimilateAdjectiveId = newSemanticVerbBOManager.suggestAddAssimilateAdjective ( currentSemanticVerbBO.getSemanticVerbId () , newAssimilateAdjectiveBO );
        newAssimilateAdjectiveBO.setAssimilateAdjectiveId ( assimilateAdjectiveId );

        newAssimilateAdjectiveBO.setStatus ( "I" );
        if (  ! currentSemanticVerbBO.getAssimilateAdjectives ().contains ( newAssimilateAdjectiveBO ) )
        {
            currentSemanticVerbBO.getAssimilateAdjectives ().add ( newAssimilateAdjectiveBO );
        }

        return currentSemanticVerbBO.getAssimilateAdjectives ();
    }

    public List<AssimilateAdjectiveBO> deleteAssimilateAdjective ( int id ) throws RawNotFoundException
    {
        int assimilateAdjectiveId = currentSemanticVerbBO.getAssimilateAdjectives ().get ( id ).getAssimilateAdjectiveId ();
        currentSemanticVerbBO.getAssimilateAdjectives ().get ( id ).setStatus ( "D" );
        SemanticVerbBOManager.suggestDeleteAssimilateAdjective ( assimilateAdjectiveId , currentSemanticVerbBO.getSemanticVerbId () );
        //VerbBOManager.setNeedCheck ( derivedVerbId );
        return currentSemanticVerbBO.getAssimilateAdjectives ();
    }

    public void affirmAssimilateAdjectiveAdding ( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmAssimilateAdjectiveAdding ( assimilateAdjectiveId );
        clearCheck ();
    }

    public void rejectAssimilateAdjectiveAdding ( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectAssimilateAdjectiveAdding ( assimilateAdjectiveId );
        clearCheck ();
    }

    public void affirmAssimilateAdjectiveDeleting ( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmAssimilateAdjectiveDeleting ( assimilateAdjectiveId );
        clearCheck ();
    }

    public void rejectAssimilateAdjectiveDeleting ( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectAssimilateAdjectiveDeleting ( assimilateAdjectiveId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Exaggeration">
    public void addExaggeration ( ExaggerationBO exaggeration ) throws BadWordException
    {
        if ( this.currentSemanticVerbBO.getExaggerations () == null )
        {
            this.currentSemanticVerbBO.setExaggerations ( new ArrayList<ExaggerationBO> () );
        }
        if (  ! this.currentSemanticVerbBO.getExaggerations ().contains ( exaggeration ) )
        {
            this.currentSemanticVerbBO.getExaggerations ().add ( exaggeration );
        }
    }

    public List<ExaggerationBO> joinExaggeration ( ExaggerationBO newExaggerationBO ) throws RawNotFoundException
    {
        int exaggerationId = newSemanticVerbBOManager.suggestAddExaggeration ( currentSemanticVerbBO.getSemanticVerbId () , newExaggerationBO );
        newExaggerationBO.setExaggerationId ( exaggerationId );

        newExaggerationBO.setStatus ( "I" );
        if (  ! currentSemanticVerbBO.getExaggerations ().contains ( newExaggerationBO ) )
        {
            currentSemanticVerbBO.getExaggerations ().add ( newExaggerationBO );
        }

        return currentSemanticVerbBO.getExaggerations ();
    }

    public List<ExaggerationBO> deleteExaggeration ( int id ) throws RawNotFoundException
    {
        int exaggerationId = currentSemanticVerbBO.getExaggerations ().get ( id ).getExaggerationId ();
        currentSemanticVerbBO.getExaggerations ().get ( id ).setStatus ( "D" );
        SemanticVerbBOManager.suggestDeleteExaggeration ( exaggerationId , currentSemanticVerbBO.getSemanticVerbId () );
        //VerbBOManager.setNeedCheck ( derivedVerbId );
        return currentSemanticVerbBO.getExaggerations ();
    }

    public void affirmExaggerationAdding ( int exaggerationId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmExaggerationAdding ( exaggerationId );
        clearCheck ();
    }

    public void rejectExaggerationAdding ( int exaggerationId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectExaggerationAdding ( exaggerationId );
        clearCheck ();
    }

    public void affirmExaggerationDeleting ( int exaggerationId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmExaggerationDeleting ( exaggerationId );
        clearCheck ();
    }

    public void rejectExaggerationDeleting ( int exaggerationId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectExaggerationDeleting ( exaggerationId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Example...">
    public List<ExampleBO> addNewExample ( String example , String source , byte[] sound ) throws RawNotFoundException , EntryExistedException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( example );
        newExampleBO.setSource ( source );
        newExampleBO.setStatus ( "I" );
        newExampleBO.setSound ( sound );
        if (  ! this.currentSemanticVerbBO.getExamples ().contains ( newExampleBO ) )
        {
            this.currentSemanticVerbBO.getExamples ().add ( newExampleBO );
        }
        newSemanticVerbBOManager.suggestAddExample ( newExampleBO , this.currentSemanticVerbBO );
        return this.currentSemanticVerbBO.getExamples ();
    }
    public List<ExampleBO> addNewExample ( String example , String source ) throws RawNotFoundException , EntryExistedException    {
        return addNewExample ( example , source , exampleSound );
    }
    public List<ExampleBO> updateExample ( String example , String source , byte[] sound , int id ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( example );
        newExampleBO.setSource ( source );
        newExampleBO.setSound ( sound );
        newExampleBO.setStatus ( "U" );
        ExampleBO oldExampleBO = currentSemanticVerbBO.getExamples ().get ( id );
        this.newSemanticVerbBOManager.suggestUpdateExample ( newExampleBO , oldExampleBO , currentSemanticVerbBO );
        this.currentSemanticVerbBO.getExamples ().set ( id , newExampleBO );
        return this.currentSemanticVerbBO.getExamples ();
    }
    public List<ExampleBO> updateExample ( String example , String source , int id ) throws RawNotFoundException
    {
        return this.updateExample ( example , source , exampleSound , id );
    }
    public List<ExampleBO> deleteExample ( int id ) throws RawNotFoundException    {
        ExampleBO newExampleBO = this.currentSemanticVerbBO.getExamples ().get ( id );
        newExampleBO.setStatus ( "D" );
        newSemanticVerbBOManager.suggestDeleteExample ( newExampleBO , currentSemanticVerbBO.getSemanticVerbId () );
        //Waleed
        this.currentSemanticVerbBO.getExamples ().get ( id ).setStatus ( "D" );
        return this.currentSemanticVerbBO.getExamples ();
    }
    public void affirmExampleAdding ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmExampleAdding ( semanticVerbId , exampleId );
        clearCheck ();
    }
    public boolean affirmExampleAddingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmExampleAddingAU ( semanticVerbId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectExampleAdding ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectExampleAdding ( semanticVerbId , exampleId );
        clearCheck ();
    }
    public void affirmExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmExampleUpdating ( semanticVerbId , exampleId );
        clearCheck ();
    }
    public boolean affirmExampleUpdatingAU ( int exampleId , String newExample , String newSource ) throws RawNotFoundException    {
        ExampleBO newExampleBO = new ExampleBO ();
        newExampleBO.setExample ( newExample );
        newExampleBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmExampleUpdatingAU ( semanticVerbId , exampleId , newExampleBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectExampleUpdating ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectExampleUpdating ( semanticVerbId , exampleId );
        clearCheck ();
    }
    public void affirmExampleDeleting ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmExampleDeleting ( semanticVerbId , exampleId );
        clearCheck ();
    }
    public void rejectExampleDeleting ( int exampleId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectExampleDeleting ( semanticVerbId , exampleId );
        clearCheck ();
    }
    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    public List<MeaningBO> addNewMeaning ( String discription , String source ) throws RawNotFoundException , EntryExistedException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( discription );
        newMeaningBO.setSource ( source );
        newMeaningBO.setStatus ( "I" );
        if (  ! this.currentSemanticVerbBO.getMeanings ().contains ( newMeaningBO ) )
        {
            this.currentSemanticVerbBO.getMeanings ().add ( newMeaningBO );
        }
        newSemanticVerbBOManager.suggestAddMeaning ( newMeaningBO , this.currentSemanticVerbBO );
        return this.currentSemanticVerbBO.getMeanings ();
    }
    public List<MeaningBO> updateMeaning ( String description , String source , int id ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( description );
        newMeaningBO.setSource ( source );
        newMeaningBO.setStatus ( "U" );
        MeaningBO oldMeaningBO = currentSemanticVerbBO.getMeanings ().get ( id );
        this.newSemanticVerbBOManager.suggestUpdateMeaning ( newMeaningBO , oldMeaningBO , currentSemanticVerbBO );
        this.currentSemanticVerbBO.getMeanings ().set ( id , newMeaningBO );
        return this.currentSemanticVerbBO.getMeanings ();
    }
    public List<MeaningBO> deleteMeaning ( int id ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = this.currentSemanticVerbBO.getMeanings ().get ( id );
        newMeaningBO.setStatus ( "D" );
        newSemanticVerbBOManager.suggestDeleteMeaning ( newMeaningBO , this.currentSemanticVerbBO );
        this.currentSemanticVerbBO.getMeanings ().get(id).setStatus ( "D");
        //Waleed
        // this.currentSemanticVerbBO.getMeanings ().remove ( id );
        return this.currentSemanticVerbBO.getMeanings ();
    }
    public void affirmMeaningAdding ( int meaningId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmMeaningAdding ( meaningId );
        clearCheck ();
    }
    public boolean affirmMeaningAddingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmMeaningAddingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectMeaningAdding ( int meaningId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectMeaningAdding ( meaningId );
        clearCheck ();
    }
    public void affirmMeaningUpdating ( int meaningId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmMeaningUpdating ( meaningId );
        clearCheck ();
    }
    public boolean affirmMeaningUpdatingAU ( int meaningId , String newMeaning , String newSource ) throws RawNotFoundException    {
        MeaningBO newMeaningBO = new MeaningBO ();
        newMeaningBO.setDescription ( newMeaning );
        newMeaningBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmMeaningUpdatingAU ( meaningId , newMeaningBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectMeaningUpdating ( int meaningId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectMeaningUpdating ( meaningId );
        clearCheck ();
    }
    public void affirmMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmMeaningDeleting ( semanticVerbId , meaningId );
        clearCheck ();
    }
    public void rejectMeaningDeleting ( int meaningId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectMeaningDeleting ( semanticVerbId , meaningId );
        clearCheck ();
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Common Mistake...">
    public List<CommonMistakeBO> addNewCommonMistake ( String commonMistake , String source ) throws RawNotFoundException , EntryExistedException     {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( commonMistake );
        newCommonMistakeBO.setSource ( source );
        newCommonMistakeBO.setStatus ( "I" );
        if (  ! this.currentSemanticVerbBO.getCommonMistakes ().contains ( newCommonMistakeBO ) )
        {
            this.currentSemanticVerbBO.getCommonMistakes ().add ( newCommonMistakeBO );
        }
        newSemanticVerbBOManager.suggestAddCommonMistake ( newCommonMistakeBO , currentSemanticVerbBO );
        return this.currentSemanticVerbBO.getCommonMistakes ();
    }
    public List<CommonMistakeBO> updateCommonMistake ( String commonMistake , String source , int id ) throws RawNotFoundException     {
        CommonMistakeBO oldCommonMistakeBO = currentSemanticVerbBO.getCommonMistakes ().get ( id );
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( commonMistake );
        newCommonMistakeBO.setSource ( source );
        newCommonMistakeBO.setStatus ( "U" );
        newSemanticVerbBOManager.suggestUpdateCommonMistake ( newCommonMistakeBO , oldCommonMistakeBO , currentSemanticVerbBO );
        currentSemanticVerbBO.getCommonMistakes ().set ( id , newCommonMistakeBO );
        return currentSemanticVerbBO.getCommonMistakes ();
    }
    public List<CommonMistakeBO> deleteCommonMistake ( int id ) throws RawNotFoundException     {
        CommonMistakeBO newCommonMistakeBO = currentSemanticVerbBO.getCommonMistakes ().get ( id );
        newCommonMistakeBO.setStatus ( "D" );
        newSemanticVerbBOManager.suggestDeleteCommonMistake ( newCommonMistakeBO , currentSemanticVerbBO );
        currentSemanticVerbBO.getCommonMistakes ().get ( id ).setStatus ( "D" );
        return this.currentSemanticVerbBO.getCommonMistakes ();
    }
    public void affirmCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmCommonMistakeAdding ( semanticVerbId ,commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeAddingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmCommonMistakeAddingAU ( semanticVerbId, commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeAdding ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectCommonMistakeAdding ( semanticVerbId, commonMistakeId );
        clearCheck ();
    }
    public void affirmCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmCommonMistakeUpdating (semanticVerbId, commonMistakeId );
        clearCheck ();
    }
    public boolean affirmCommonMistakeUpdatingAU ( int commonMistakeId , String newCommonMistake , String newSource ) throws RawNotFoundException    {
        CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO ();
        newCommonMistakeBO.setDescription ( newCommonMistake );
        newCommonMistakeBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmCommonMistakeUpdatingAU ( semanticVerbId, commonMistakeId , newCommonMistakeBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectCommonMistakeUpdating ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectCommonmistakeUpdating ( semanticVerbId, commonMistakeId );
        clearCheck ();
    }

    public void affirmCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmCommonmistakeDeleting ( semanticVerbId , commonMistakeId );
        clearCheck ();
    }

    public void rejectCommonMistakeDeleting ( int commonMistakeId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectCommonmistakeDeleting ( semanticVerbId , commonMistakeId );
        clearCheck ();
    }


//</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Linguistic benefit...">
    public List<LinguisticBenefitBO> addNewLinguisticBenefit ( String linguisticBenefit , String source ) throws RawNotFoundException , EntryExistedException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( linguisticBenefit );
        newLinguisticBenefitBO.setSource ( source );
        newLinguisticBenefitBO.setStatus ( "I" );
        if (  ! this.currentSemanticVerbBO.getLinguisticBenefits ().contains ( newLinguisticBenefitBO ) )
        {
            this.currentSemanticVerbBO.getLinguisticBenefits ().add ( newLinguisticBenefitBO );
        }
        newSemanticVerbBOManager.suggestAddLinguisticBenefit ( newLinguisticBenefitBO , currentSemanticVerbBO );
        return this.currentSemanticVerbBO.getLinguisticBenefits ();
    }
    public List<LinguisticBenefitBO> updateLinguisticBenefit ( String linguisticBenefit , String source , int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO oldLinguisticBenefitBO = currentSemanticVerbBO.getLinguisticBenefits ().get ( id );
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( linguisticBenefit );
        newLinguisticBenefitBO.setSource ( source );
        newLinguisticBenefitBO.setStatus ( "U" );
        newSemanticVerbBOManager.suggestUpdateLinguisticBenefit ( newLinguisticBenefitBO , oldLinguisticBenefitBO , currentSemanticVerbBO );
        currentSemanticVerbBO.getLinguisticBenefits ().set ( id , newLinguisticBenefitBO );
        return currentSemanticVerbBO.getLinguisticBenefits ();
    }
    public List<LinguisticBenefitBO> deleteLinguisticBenefit ( int id ) throws RawNotFoundException
    {
        LinguisticBenefitBO newLinguisticBenefitBO = currentSemanticVerbBO.getLinguisticBenefits ().get ( id );
        newLinguisticBenefitBO.setStatus ( "D" );
        newSemanticVerbBOManager.suggestDeleteLinguisticBenefit ( newLinguisticBenefitBO , currentSemanticVerbBO );
        currentSemanticVerbBO.getLinguisticBenefits ().get ( id ).setStatus ( "D" );
        return this.currentSemanticVerbBO.getLinguisticBenefits ();
    }
    public void affirmLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmLinguisticBenefitAdding ( semanticVerbId ,linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitAddingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmLinguisticBenefitAddingAU ( semanticVerbId, linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitAdding ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticVerbBOManager.rejectLinguisticBenefitAdding ( semanticVerbId, linguisticBenefitId );
        clearCheck ();
    }
    public void affirmLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException    {
        SemanticVerbBOManager.affirmLinguisticBenefitUpdating (semanticVerbId, linguisticBenefitId );
        clearCheck ();
    }
    public boolean affirmLinguisticBenefitUpdatingAU ( int linguisticBenefitId , String newLinguisticBenefit , String newSource ) throws RawNotFoundException    {
        LinguisticBenefitBO newLinguisticBenefitBO = new LinguisticBenefitBO ();
        newLinguisticBenefitBO.setDescription ( newLinguisticBenefit );
        newLinguisticBenefitBO.setSource ( newSource );
        boolean updated = SemanticVerbBOManager.affirmLinguisticBenefitUpdatingAU ( semanticVerbId, linguisticBenefitId , newLinguisticBenefitBO );
        if ( updated )
        {
            clearCheck ();
        }
        return updated;
    }
    public void rejectLinguisticBenefitUpdating ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectLinguisticBenefitUpdating ( semanticVerbId, linguisticBenefitId );
        clearCheck ();
    }

    public void affirmLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.affirmLinguisticBenefitDeleting ( semanticVerbId , linguisticBenefitId );
        clearCheck ();
    }

    public void rejectLinguisticBenefitDeleting ( int linguisticBenefitId ) throws RawNotFoundException
    {
        SemanticVerbBOManager.rejectLinguisticBenefitDeleting ( semanticVerbId , linguisticBenefitId );
        clearCheck ();
    }

//</editor-fold>
}