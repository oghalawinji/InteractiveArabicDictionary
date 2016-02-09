/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

/**
 *
 * @author riad
 */
public abstract class DAOFactory {

    public abstract GenericDAO getActorDAO();

    public abstract GenericDAO getAnnexednounDAO();

    public abstract GenericDAO getAssimilateadjectiveDAO();

    public abstract GenericDAO getCommonmistakeDAO();

    public abstract GenericDAO getContextualverbDAO();

    public abstract GenericDAO getDerivednounDAO();

    public abstract GenericDAO getDerivedparticleDAO();

    public abstract GenericDAO getDerivedverbDAO();

    public abstract GenericDAO getDifficultydegreeDAO();

    public abstract GenericDAO getDiminutiveDAO();

    public abstract GenericDAO getEntrycommonmistakeDAO();

    public abstract GenericDAO getEntryexampleDAO();

    public abstract GenericDAO getEntryimageDAO();

    public abstract GenericDAO getEntrylinguisticbenefitDAO();

    public abstract GenericDAO getEntrysoundDAO();

    public abstract GenericDAO getEntryvideoDAO();

    public abstract GenericDAO getEpochDAO();

    public abstract GenericDAO getExaggerationDAO();

    public abstract GenericDAO getExampleDAO();

    public abstract GenericDAO getExamplesoundDAO();

    public abstract GenericDAO getFemininityDAO();

    public abstract GenericDAO getGenderDAO();

    public abstract GenericDAO getGerundDAO();

    public abstract GenericDAO getIdiomDAO();

    public abstract GenericDAO getImageDAO();

    public abstract GenericDAO getLinguisticbenefitDAO();

    public abstract GenericDAO getMeaningDAO();

    public abstract GenericDAO getNounadjectiveaccompanierDAO();

    public abstract GenericDAO getNountypeDAO();

    public abstract GenericDAO getNounverbaccompanierDAO();

    public abstract GenericDAO getNumberDAO();

    public abstract GenericDAO getOriginDAO();

    public abstract GenericDAO getParticletypeDAO();

    public abstract GenericDAO getPatternDAO();

    public abstract GenericDAO getPluralDAO();

    public abstract GenericDAO getPluraltypeDAO();

    public abstract GenericDAO getProperadjectiveDAO();

    public abstract GenericDAO getRawwordDAO();

    public abstract GenericDAO getRegionDAO();

    public abstract GenericDAO getRelatedidiomDAO();

    public abstract GenericDAO getRootDAO();

    public abstract GenericDAO getSemanticentryDAO();

    public abstract GenericDAO getSemanticnounDAO();

    public abstract GenericDAO getSemanticparticleDAO();

    public abstract GenericDAO getSemanticrelationDAO();

    public abstract GenericDAO getSemanticrelationtypeDAO();

    public abstract GenericDAO getSemanticscopDAO();

    public abstract GenericDAO getSemanticverbDAO();

    public abstract GenericDAO getSoundDAO();

    public abstract GenericDAO getSourceDAO();

    public abstract GenericDAO getSpecializationDAO();

    public abstract GenericDAO getSpreadingdegreeDAO();

    public abstract GenericDAO getSubjecttypeDAO();

    public abstract GenericDAO getTransitiveletterDAO();

    public abstract GenericDAO getTransitivitycaseDAO();

    public abstract GenericDAO getVideoDAO();

    public abstract GenericDAO getPronunciationDAO();

    public abstract GenericDAO getPrefixDAO();

    public abstract GenericDAO getSuffixDAO();

    public abstract GenericDAO getSuggestionDAO();

    public abstract GenericDAO getRoleDAO();

    public abstract GenericDAO getUserDAO();

    public abstract GenericDAO getTypeDAO();

    public static HibernateDAOFactory getHibernateDAOFactory() {
        return new HibernateDAOFactory();
    }

    public static JPADAOFactory getJPADAOFactory() {
        return new JPADAOFactory();
    }
}
