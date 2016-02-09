package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.IdiomJPADAO;
import DataAccessLayer.JPADAO.SemanticentryJPADAO;
import PersistenceLayer.Idiom;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.User;
import Util.RawNotFoundException;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class SemanticIdiomBOManager extends SemanticEntryBOManager
{

    SemanticEntryBOManager newSemanticEntryBOManager;
    MeaningBOManager newMeaningBOManager;
    private static SemanticentryJPADAO SEMANTIC_IDIOM_DAO = BLUtil.daoFactory.getSemanticentryDAO();
    private static IdiomJPADAO IDIOM_DAO = BLUtil.daoFactory.getIdiomDAO();

    public SemanticIdiomBOManager()
    {
        newSemanticEntryBOManager = new SemanticEntryBOManager();
        newMeaningBOManager = new MeaningBOManager();
    }

    public static SemanticEntryBO getSemanticIdiomBO( int semanticIdiomId , SearchProperties detailedSearchOptions ) throws RawNotFoundException
    {
        SemanticEntryBO newSemanticEntryBO = SemanticEntryBOManager.getSemanticEntryBO( semanticIdiomId , detailedSearchOptions );
        return newSemanticEntryBO;
    }

    public int suggestAdding( SemanticEntryBO newSemanticEntryBO , int idiomId ) throws EntryExistedException , RawNotFoundException , Exception
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        if ( newIdiom.getSemanticentry() != null )
        {
            throw new Exception( "arDic_Ex" + "لا يمكن ربط التركيب مع أكثر من معنى!" + "arDic_Ex" );
        }
        int semantciEntryId = newSemanticEntryBOManager.suggestAdding( newSemanticEntryBO );
        Semanticentry newSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById( semantciEntryId );

        newIdiom.setSemanticentry( newSemanticentry );
        newIdiom.setChechStatus( BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus() );
        BLUtil.daoFactory.getIdiomDAO().update( newIdiom );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return semantciEntryId;
    }

    public static void affirmAdding( int idiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.affirmAdding( newIdiom.getSemanticentry().getIdentity() );
    }

    public static boolean affirmAddingAU( int semanticEntryId , SemanticEntryBO updateSemanticEntryBO , int idiomId ) throws RawNotFoundException , EntryExistedException
    {
        SemanticEntryBOManager.update( semanticEntryId , updateSemanticEntryBO );
        return true;
    }

    public void suggestUpdateInfo( Integer idiomId , SemanticEntryBO newSemanticEntryBO ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );

        newSemanticEntryBOManager.suggestUpdating( newIdiom.getSemanticentry().getIdentity() , newSemanticEntryBO );

        IdiomBOManager.setNeedCheck( idiomId );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmUpdating( int idiomId ) throws RawNotFoundException
    {
        Idiom oldIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.affirmUpdating( oldIdiom.getSemanticentry().getIdentity() );
    }

    public static boolean affirmUpdatingAU( int idiomId , SemanticEntryBO newSemanticEntryBO ) throws RawNotFoundException
    {
        Idiom oldIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.affirmUpdatingAU( oldIdiom.getSemanticentry().getIdentity() , newSemanticEntryBO );
        return true;

    }

    public static void rejectUpdating( int IdiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( IdiomId );
        SemanticEntryBOManager.rejectUpdating( newIdiom.getSemanticentry().getIdentity() );
    }

    public void suggestDeleting( int idiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        newSemanticEntryBOManager.suggestDeleting( newIdiom.getSemanticentry().getIdentity() );
        IdiomBOManager.setNeedCheck( idiomId );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void rejectAdding( int idiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.rejectAdding( newIdiom.getSemanticentry().getIdentity() );
    }

    public static void affirmDeleting( int idiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.affirmDeleting( newIdiom.getSemanticentry().getIdentity() );
    }

    public static void rejectDeleting( int idiomId ) throws RawNotFoundException
    {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById( idiomId );
        SemanticEntryBOManager.rejectDeleting( newIdiom.getSemanticentry().getIdentity() );
    }

    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    public static int suggestAddMeaning( MeaningBO newMeaningBO , IdiomBO newIdiomBO ) throws RawNotFoundException , EntryExistedException
    {
        int newMeaningId = MeaningBOManager.suggestAdding( newMeaningBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        int idiomId = newIdiomBO.getIdiomId();
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newMeaningId;
    }

    public static void suggestUpdateMeaning( MeaningBO newMeaningBO , MeaningBO oldMeaningBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        MeaningBOManager.suggestUpdating( newMeaningBO , oldMeaningBO  );
        int idiomId = newIdiomBO.getIdiomId();
        IdiomBOManager.setNeedCheck( idiomId );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void suggestDeleteMeaning( MeaningBO newMeaningBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        MeaningBOManager.suggestDeleting( newMeaningBO );
        int idiomId = newIdiomBO.getIdiomId();
        IdiomBOManager.setNeedCheck( idiomId );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }
///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Example...">
    public int suggestAddExample( ExampleBO newExampleBO , IdiomBO newIdiomBO ) throws RawNotFoundException , EntryExistedException
    {
        int newExampleId = super.suggestAddExample( newExampleBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        int idiomId = newIdiomBO.getIdiomId();
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newExampleId;
    }

    public void suggestUpdateExample( ExampleBO newExampleBO , ExampleBO oldExampleBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestUpdateExample( newExampleBO , oldExampleBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public void suggestDeleteExample( ExampleBO newExampleBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestDeleteExample( newExampleBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmExampleAdding( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.affirmExampleAdding( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static boolean affirmExampleAddingAU( int IdiomId , int exampleId , ExampleBO newExampleBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        return SemanticEntryBOManager.affirmExampleAddingAU( newIdiom.getSemanticentry().getIdentity() , exampleId , newExampleBO );
    }

    public static void rejectExampleAdding( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.rejectExampleAdding( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static void affirmExampleUpdating( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.affirmExampleUpdating( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static boolean affirmExampleUpdatingAU( int IdiomId , int exampleId , ExampleBO newExampleBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        return SemanticEntryBOManager.affirmExampleUpdatingAU( newIdiom.getSemanticentry().getIdentity() , exampleId , newExampleBO );
    }

    public static void rejectExampleUpdating( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.rejectExampleUpdating( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static void affirmExampleDeleting( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.affirmExampleDeleting( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static void rejectExampleDeleting( int IdiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( IdiomId );
        SemanticEntryBOManager.rejectExampleDeleting( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="CommonMistake...">
    public int suggestAddCommonMistake( CommonMistakeBO newCommonMistakeBO , IdiomBO newIdiomBO ) throws RawNotFoundException , EntryExistedException
    {
        int newCommonMistakeId = super.suggestAddCommonMistake( newCommonMistakeBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newCommonMistakeId;
    }

    public void suggestUpdateCommonMistake( CommonMistakeBO newCommonMistakeBO , CommonMistakeBO oldCommonMistakeBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestUpdateCommonMistake( newCommonMistakeBO , oldCommonMistakeBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public void suggestDeleteCommonMistake( CommonMistakeBO newCommonMistakeBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestDeleteCommonMistake( newCommonMistakeBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static boolean affirmCommonMistakeUpdatingAU( int idiomId , int commonMistakeId , CommonMistakeBO newCommonMistakeBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        return SemanticEntryBOManager.affirmCommonMistakeUpdatingAU( newIdiom.getSemanticentry().getIdentity() , commonMistakeId , newCommonMistakeBO );
    }

    public static void rejectCommonmistakeUpdating( int idiomId , int commonmistakeId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectCommonmistakeUpdating( newIdiom.getIdentity() , commonmistakeId );
    }

    public static void affirmCommonmistakeDeleting( int idiomId , int commonmistakeId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.affirmCommonmistakeDeleting( newIdiom.getSemanticentry().getIdentity() , commonmistakeId );
    }

    public static void rejectCommonmistakeDeleting( int idiomId , int commonmistakeId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectCommonmistakeDeleting( newIdiom.getSemanticentry().getIdentity() , commonmistakeId );
    }

    public static void affirmCommonMistakeAdding( int idiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.affirmCommonMistakeAdding( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static boolean affirmCommonMistakeAddingAU( int idiomId , int commonMistakeId , CommonMistakeBO newCommonMistakeBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        return SemanticEntryBOManager.affirmCommonMistakeAddingAU( newIdiom.getSemanticentry().getIdentity() , commonMistakeId , newCommonMistakeBO );
    }

    public static void rejectCommonMistakeAdding( int idiomId , int commonmistakeId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectCommonMistakeAdding( newIdiom.getSemanticentry().getIdentity() , commonmistakeId );
    }

///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="LinguisticBenefit...">
    public int suggestAddLinguisticBenefit( LinguisticBenefitBO newLinguisticBenefitBO , IdiomBO newIdiomBO ) throws RawNotFoundException , EntryExistedException
    {
        int newLinguisticBenefitId = super.suggestAddLinguisticBenefit( newLinguisticBenefitBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        return newLinguisticBenefitId;
    }

    public void suggestUpdateLinguisticBenefit( LinguisticBenefitBO newLinguisticBenefitBO , LinguisticBenefitBO oldLinguisticBenefitBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestUpdateLinguisticBenefit( newLinguisticBenefitBO , oldLinguisticBenefitBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
    }

    public void suggestDeleteLinguisticBenefit( LinguisticBenefitBO newLinguisticBenefitBO , IdiomBO newIdiomBO ) throws RawNotFoundException
    {
        super.suggestDeleteLinguisticBenefit( newLinguisticBenefitBO , newIdiomBO.getSemanticCase().getSemanticEntryId() );
        IdiomBOManager.setNeedCheck( newIdiomBO.getIdiomId() );
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmLinguisticBenefitAdding( int idiomId , int exampleId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.affirmLinguisticBenefitAdding( newIdiom.getSemanticentry().getIdentity() , exampleId );
    }

    public static boolean affirmLinguisticBenefitAddingAU( int idiomId , int linguisticBenefitId , LinguisticBenefitBO newLinguisticBenefitBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        return SemanticEntryBOManager.affirmLinguisticBenefitAddingAU( newIdiom.getSemanticentry().getIdentity() , linguisticBenefitId , newLinguisticBenefitBO );
    }

    public static void rejectLinguisticBenefitAdding( int idiomId , int linguisticbenefitId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectLinguisticBenefitAdding( newIdiom.getSemanticentry().getIdentity() , linguisticbenefitId );
    }

    public static void affirmLinguisticBenefitUpdating( int idiomId , int linguisticBenefitId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.affirmLinguisticBenefitUpdating( newIdiom.getSemanticentry().getIdentity() , linguisticBenefitId );
    }

    public static boolean affirmLinguisticBenefitUpdatingAU( int idiomId , int linguisticBenefitId , LinguisticBenefitBO newLinguisticBenefitBO ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        return SemanticEntryBOManager.affirmLinguisticBenefitUpdatingAU( newIdiom.getSemanticentry().getIdentity() , linguisticBenefitId , newLinguisticBenefitBO );
    }

    public static void rejectLinguisticbenefitUpdating( int idiomId , int linguisticbenefitId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectLinguisticBenefitUpdating( newIdiom.getSemanticentry().getIdentity() , linguisticbenefitId );
    }

    public static void affirmLinguisticbenefitDeleting( int idiomId , int linguisticbenefitId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.affirmLinguisticBenefitDeleting( newIdiom.getSemanticentry().getIdentity() , linguisticbenefitId );
    }

    public static void rejectLinguisticbenefitDeleting( int idiomId , int linguisticbenefitId ) throws RawNotFoundException
    {
        Idiom newIdiom = IDIOM_DAO.getById( idiomId );
        SemanticEntryBOManager.rejectLinguisticBenefitDeleting( newIdiom.getSemanticentry().getIdentity() , linguisticbenefitId );
    }

///</editor-fold>

    public static void main( String[] str ) throws RawNotFoundException
    {
        User currUser = BLUtil.daoFactory.getUserDAO().getById( 5 );
        BOManagerUtil.setCurrentUser( currUser );
        /**
         * test affirmAdding()
         */
        affirmAdding( 258 );
    }
}
