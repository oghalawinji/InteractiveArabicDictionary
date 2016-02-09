package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.BusinessObjects.SemanticVerbBO;
import BusinessLogicLayer.BusinessObjects.UpdatedSemanticEntryBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SemanticentryJPADAO;
import PersistenceLayer.Commonmistake;
import PersistenceLayer.Difficultydegree;
import PersistenceLayer.Entrycommonmistake;
import PersistenceLayer.Entryexample;
import PersistenceLayer.Entrylinguisticbenefit;
import PersistenceLayer.Epoch;
import PersistenceLayer.Example;
import PersistenceLayer.Idiom;
import PersistenceLayer.Linguisticbenefit;
import PersistenceLayer.Meaning;
import PersistenceLayer.Region;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticparticle;
import PersistenceLayer.Semanticscop;
import PersistenceLayer.Semanticverb;
import PersistenceLayer.Source;
import PersistenceLayer.Specialization;
import PersistenceLayer.Spreadingdegree;
import PersistenceLayer.Suggestion;
import Util.RawNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class SemanticEntryBOManager {

    private ExampleBOManager newExampleBOManager;
    private CommonMistakeBOManager newCommonMistakeBOManager;
    private LinguisticBenefitBOManager newLinguisticBenefitBOManager;
    private static SemanticentryJPADAO SEMANTIC_ENTRY_DAO = BLUtil.daoFactory.getSemanticentryDAO();

    public SemanticEntryBOManager() {
        newExampleBOManager = new ExampleBOManager();
        newCommonMistakeBOManager = new CommonMistakeBOManager();
        newLinguisticBenefitBOManager = new LinguisticBenefitBOManager();
    }

    public static boolean hasNewValues(Semanticentry newSemanticEntry) {
        Suggestion newSuggestion = newSemanticEntry.getSuggestion();
        if (newSuggestion != null && newSuggestion.getInfoStatus().equals("U") && !(newSuggestion.getAffirmStatus().equals("A"))) {
            return true;
        }
        return false;
    }

    public static boolean hasNewValues(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        return hasNewValues(newSemanticEntry);
    }

    static SemanticEntryBO getSemanticEntryBO(Semanticentry semEntry) {
        return getSemanticEntryBO(semEntry, SearchProperties.detailedSearchOptions);
    }

    static SemanticEntryBO getSemanticEntryBO(Semanticentry semEntry, SearchProperties options) {
        if (semEntry.getInfoStatus().equals("U")) {
            UpdatedSemanticEntryBO semanticEntryBO = new UpdatedSemanticEntryBO();
            if (hasNewValues(semEntry)) {
                try {
                    Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semEntry.getSuggestion().getUpdateId());
                    semanticEntryBO.setNewDifficultydegree(tempSemanticentry.getDifficultydegree().getDifficultyDegree());
                    semanticEntryBO.setNewEpoch(tempSemanticentry.getEpoch().getEpoch());
                    semanticEntryBO.setNewRegion(tempSemanticentry.getRegion().getRegion());
                    semanticEntryBO.setNewSemanticscop(tempSemanticentry.getSemanticscop().getSemanticScop());
                    semanticEntryBO.setNewSpecialization(tempSemanticentry.getSpecialization().getSpecialization());
                    semanticEntryBO.setNewSpreadingdegree(tempSemanticentry.getSpreadingdegree().getSpreadingDegree());
                } catch (RawNotFoundException ex) {
                    Logger.getLogger(SemanticParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            semanticEntryBO.setSemanticEntryId(semEntry.getIdentity());
            semanticEntryBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
            semanticEntryBO.setEpoch(semEntry.getEpoch().getEpoch());
            semanticEntryBO.setRegion(semEntry.getRegion().getRegion());
            semanticEntryBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
            semanticEntryBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
            semanticEntryBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
            semanticEntryBO.setStatus(semEntry.getInfoStatus());
            semanticEntryBO.setChecked(semEntry.getChechStatus() == 1 ? true : false);
            //Semantic Entry Related Infos:
            if (options.FindMeanings) {
                semanticEntryBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
            }
            if (options.FindCommonMistakes) {
                semanticEntryBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
            }
            if (options.FindLinguisticBenifits) {
                semanticEntryBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
            }
            if (options.FindExamples) {
                semanticEntryBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
            }
            if (options.FindRelatedIdiom) {
                semanticEntryBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
            }

            //multimedias:
            if (options.FindImages) {
                semanticEntryBO.setImages(ImageManager.getImage(semEntry));
            }
            if (options.FindSounds) {
                semanticEntryBO.setSounds(SoundManager.getSound(semEntry));
            }
            if (options.FindVideos) {
                semanticEntryBO.setVideos(VideoManager.getVideo(semEntry));
            }
            return semanticEntryBO;
        }
        SemanticEntryBO semanticEntryBO = new SemanticEntryBO();

        semanticEntryBO.setSemanticEntryId(semEntry.getIdentity());
        semanticEntryBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
        semanticEntryBO.setEpoch(semEntry.getEpoch().getEpoch());
        semanticEntryBO.setRegion(semEntry.getRegion().getRegion());
        semanticEntryBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
        semanticEntryBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
        semanticEntryBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
        semanticEntryBO.setStatus(semEntry.getInfoStatus());
        semanticEntryBO.setChecked(semEntry.getChechStatus() == 1 ? true : false);
        //Semantic Entry Related Infos:
        if (options.FindMeanings) {
            semanticEntryBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
        }
        if (options.FindCommonMistakes) {
            semanticEntryBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
        }
        if (options.FindLinguisticBenifits) {
            semanticEntryBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
        }
        if (options.FindExamples) {
            semanticEntryBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
        }
        if (options.FindRelatedIdiom) {
            semanticEntryBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
        }

        //multimedias:
        if (options.FindImages) {
            semanticEntryBO.setImages(ImageManager.getImage(semEntry));
        }
        if (options.FindSounds) {
            semanticEntryBO.setSounds(SoundManager.getSound(semEntry));
        }
        if (options.FindVideos) {
            semanticEntryBO.setVideos(VideoManager.getVideo(semEntry));
        }
        return semanticEntryBO;
    }

    public static SemanticEntryBO getSemanticEntryBO(int semanticEntryId, SearchProperties searchProperties) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        return getSemanticEntryBO(newSemanticentry, searchProperties);
    }

    public static String getWordStr(Semanticentry semEntry, String type) {

        if (type.equals("particle")) {
            Set<Semanticparticle> semParticles = semEntry.getSemanticparticles();
            if (!semParticles.isEmpty()) {
                String particle = ((Semanticparticle) semParticles.toArray()[0]).getDerivedparticle().getVocalizedParticle();
                return particle;
            }
        } else if (type.equals("verb")) {
            Set<Semanticverb> semVerbs = semEntry.getSemanticverbs();
            if (!semVerbs.isEmpty()) {
                String verb = ((Semanticverb) semVerbs.toArray()[0]).getContextualverb().getDerivedverb().getVocalizedVerb();
                return verb;
            }
        } else //if( type.equals("noun")) //the default
        {
            Set<Semanticnoun> semNouns = semEntry.getSemanticnouns();
            if (!semNouns.isEmpty()) {
                String noun = ((Semanticnoun) semNouns.toArray()[0]).getDerivednoun().getVocalizedNoun();
                return noun;
            }
        }
        return null;
    }

    public static String getType(Semanticentry semanticEntry) {
        String word = getWordStr(semanticEntry, "particle");
        if (word != null) {
            return "particle";
        }

        word = getWordStr(semanticEntry, "verb");
        if (word != null) {
            return "verb";
        }

        return "noun";

    }

    static void deleteSemanticEntry(Integer semEntryId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void suggestDeleting(Integer Id) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(Id);
        newSemanticentry.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newSemanticentry.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newSemanticentry.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
    }

    public boolean suggestDeleting(Semanticentry root) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void affirmDeleting(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        if (newSemanticentry.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            //later we must implement these parts as functions in the accordant classes
            if (newSemanticentry.getDifficultydegree().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getDifficultydegree().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getDifficultydegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getDifficultydegree().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getDifficultydegree().getSuggestion());
                }
                BLUtil.daoFactory.getDifficultydegreeDAO().update(newSemanticentry.getDifficultydegree());
            }

            if (newSemanticentry.getRegion().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getRegion().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getRegion().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getRegion().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getRegion().getSuggestion());
                }
                BLUtil.daoFactory.getRegionDAO().update(newSemanticentry.getRegion());
            }

            if (newSemanticentry.getEpoch().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getEpoch().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getEpoch().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getEpoch().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getEpoch().getSuggestion());
                }
                BLUtil.daoFactory.getEpochDAO().update(newSemanticentry.getEpoch());
            }

            if (newSemanticentry.getSemanticscop().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSemanticscop().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getSemanticscop().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSemanticscop().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getSemanticscop().getSuggestion());
                }
                BLUtil.daoFactory.getSemanticscopDAO().update(newSemanticentry.getSemanticscop());
            }

            if (newSemanticentry.getSpecialization().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSpecialization().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getSpecialization().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpecialization().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getSpecialization().getSuggestion());
                }
                BLUtil.daoFactory.getSpecializationDAO().update(newSemanticentry.getSpecialization());
            }

            if (newSemanticentry.getSpreadingdegree().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSpreadingdegree().setInfoStatus(WordStatus.NEED_DELETING);
                newSemanticentry.getSpreadingdegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpreadingdegree().getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getSpreadingdegree().getSuggestion());
                }
                BLUtil.daoFactory.getSpreadingdegreeDAO().update(newSemanticentry.getSpreadingdegree());
            }

            if (newSemanticentry.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getSuggestion());
            }
            newSemanticentry.setInfoStatus(WordStatus.NEED_DELETING);
            newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_ENTRY_DAO.update(newSemanticentry);
        }
    }

    public static void suggestUpdating(Integer semanticEntryId, SemanticEntryBO semanticEntryBO) throws RawNotFoundException {
        int tempSemanticEntryId = addTempSemanticEntry(semanticEntryBO);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        newSemanticentry.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
        newSemanticentry.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
        newSemanticentry.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempSemanticEntryId));

        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
    }

    public static void update(Integer semanticEntryId, SemanticEntryBO semanticEntryBO) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        int regionId = RegionManager.add(semanticEntryBO.getRegion());
        Region regionObj = BLUtil.daoFactory.getRegionDAO().getById(regionId);

        int epochId = EpochManager.add(semanticEntryBO.getEpoch());
        Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById(epochId);

        int difficultyId = DifficultyDegreeManager.add(semanticEntryBO.getDifficultydegree());
        Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById(difficultyId);

        int scopId = SemanticScopeBOManager.add(semanticEntryBO.getSemanticscop());
        Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById(scopId);

        int specializationId = SpecializationManager.add(semanticEntryBO.getSpecialization());
        Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById(specializationId);

        int spreadingId = SpreadingDegreeManager.add(semanticEntryBO.getSpreadingdegree());
        Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById(spreadingId);

        newSemanticentry.setEpoch(epochObj);
        newSemanticentry.setRegion(regionObj);
        newSemanticentry.setDifficultydegree(difficultydegreeObj);
        newSemanticentry.setSemanticscop(semanticscopObj);
        newSemanticentry.setSpecialization(specializationObj);
        newSemanticentry.setSpreadingdegree(spreadingdegreeObj);

        newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        Suggestion newSuggestion = newSemanticentry.getSuggestion();
        if (newSuggestion != null) {
            BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newSuggestion);
        }
        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
    }

    /*public static void update( Integer semanticEntryId , Semanticentry newSemanticentry ) throws RawNotFoundException
     {
     Semanticentry oldSemanticentry = SEMANTIC_ENTRY_DAO.getById( semanticEntryId );
     int regionId = RegionManager.add( semanticEntryBO.getRegion() );
     Region regionObj = BLUtil.daoFactory.getRegionDAO().getById( regionId );
    
     int epochId = EpochManager.add( semanticEntryBO.getEpoch() );
     Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById( epochId );
    
     int difficultyId = DifficultyDegreeManager.add( semanticEntryBO.getDifficultydegree() );
     Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById( difficultyId );
    
     int scopId = SemanticScopeBOManager.add( semanticEntryBO.getSemanticscop() );
     Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById( scopId );
    
     int specializationId = SpecializationManager.add( semanticEntryBO.getSpecialization() );
     Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById( specializationId );
    
     int spreadingId = SpreadingDegreeManager.add( semanticEntryBO.getSpreadingdegree() );
     Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById( spreadingId );
    
     oldSemanticentry.setEpoch( newSemanticentry.getEpoch() );
     oldSemanticentry.setRegion( newSemanticentry.getRegion() );
     oldSemanticentry.setDifficultydegree( newSemanticentry.getDifficultydegree() );
     oldSemanticentry.setSemanticscop( newSemanticentry.getSemanticscop() );
     oldSemanticentry.setSpecialization( newSemanticentry.getSpecialization() );
     oldSemanticentry.setSpreadingdegree( newSemanticentry.getSpreadingdegree() );
    
     oldSemanticentry.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
     oldSemanticentry.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
     Suggestion newSuggestion = oldSemanticentry.getSuggestion();
     if ( newSuggestion != null )
     {
     BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING( newSuggestion );
     }
     SEMANTIC_ENTRY_DAO.update( oldSemanticentry );
     }*/
    public static int addTempSemanticEntry(SemanticEntryBO semanticEntryBO) throws RawNotFoundException {
        Semanticentry semanticEntry = new Semanticentry();
        int regionId = RegionManager.suggestAdding(semanticEntryBO.getRegion());
        Region regionObj = BLUtil.daoFactory.getRegionDAO().getById(regionId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_region.regionId", regionId);

        int epochId = EpochManager.suggestAdding(semanticEntryBO.getEpoch());
        Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById(epochId);
        restrictions.put("eq_epoch.epochId", epochId);

        int difficultyId = DifficultyDegreeManager.suggestAdding(semanticEntryBO.getDifficultydegree());
        Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById(difficultyId);
        restrictions.put("eq_difficultydegree.difficultyDegreeId", difficultyId);

        int scopId = SemanticScopeBOManager.suggestAdding(semanticEntryBO.getSemanticscop());
        Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById(scopId);
        restrictions.put("eq_semanticscop.semanticScopId", scopId);

        int specializationId = SpecializationManager.suggestAdding(semanticEntryBO.getSpecialization());
        Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById(specializationId);
        restrictions.put("eq_specialization.specializationId", specializationId);

        int spreadingId = SpreadingDegreeManager.suggestAdding(semanticEntryBO.getSpreadingdegree());
        Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById(spreadingId);
        restrictions.put("eq_spreadingdegree.spreadingDegreeId", spreadingId);

        semanticEntry.setEpoch(epochObj);
        semanticEntry.setRegion(regionObj);
        semanticEntry.setDifficultydegree(difficultydegreeObj);
        semanticEntry.setSemanticscop(semanticscopObj);
        semanticEntry.setSpecialization(specializationObj);
        semanticEntry.setSpreadingdegree(spreadingdegreeObj);
        semanticEntry.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        semanticEntry.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());

        //int id = SEMANTIC_ENTRY_DAO.insertWithCheck( semanticEntry , restrictions );
        int id = SEMANTIC_ENTRY_DAO.insert(semanticEntry);
        return id;
    }

    public static void affirmUpdating(int semanticEntryId) throws RawNotFoundException {
        Semanticentry oldSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(oldSemanticentry.getSuggestion().getUpdateId());
        Semanticentry copyOfNewSemanticentry = new Semanticentry();
        copyOfNewSemanticentry.setEpoch(newSemanticentry.getEpoch());
        copyOfNewSemanticentry.setRegion(newSemanticentry.getRegion());
        copyOfNewSemanticentry.setDifficultydegree(newSemanticentry.getDifficultydegree());
        copyOfNewSemanticentry.setSemanticscop(newSemanticentry.getSemanticscop());
        copyOfNewSemanticentry.setSpecialization(newSemanticentry.getSpecialization());
        copyOfNewSemanticentry.setSpreadingdegree(newSemanticentry.getSpreadingdegree());

        newSemanticentry.setEpoch(oldSemanticentry.getEpoch());
        newSemanticentry.setRegion(oldSemanticentry.getRegion());
        newSemanticentry.setDifficultydegree(oldSemanticentry.getDifficultydegree());
        newSemanticentry.setSemanticscop(oldSemanticentry.getSemanticscop());
        newSemanticentry.setSpecialization(oldSemanticentry.getSpecialization());
        newSemanticentry.setSpreadingdegree(oldSemanticentry.getSpreadingdegree());
        newSemanticentry.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);

        oldSemanticentry.setEpoch(copyOfNewSemanticentry.getEpoch());
        oldSemanticentry.setRegion(copyOfNewSemanticentry.getRegion());
        oldSemanticentry.setDifficultydegree(copyOfNewSemanticentry.getDifficultydegree());
        oldSemanticentry.setSemanticscop(copyOfNewSemanticentry.getSemanticscop());
        oldSemanticentry.setSpecialization(copyOfNewSemanticentry.getSpecialization());
        oldSemanticentry.setSpreadingdegree(copyOfNewSemanticentry.getSpreadingdegree());

        oldSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        oldSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(oldSemanticentry.getSuggestion());

        SEMANTIC_ENTRY_DAO.update(oldSemanticentry);
        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
    }

    public static boolean affirmUpdatingAU(int semanticEntryId, SemanticEntryBO semanticEntryBO) throws RawNotFoundException {
        int regionId = RegionManager.suggestAdding(semanticEntryBO.getRegion());
        Region regionObj = BLUtil.daoFactory.getRegionDAO().getById(regionId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_region.regionId", regionId);

        int epochId = EpochManager.suggestAdding(semanticEntryBO.getEpoch());
        Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById(epochId);
        restrictions.put("eq_epoch.epochId", epochId);

        int difficultyId = DifficultyDegreeManager.suggestAdding(semanticEntryBO.getDifficultydegree());
        Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById(difficultyId);
        restrictions.put("eq_difficultydegree.difficultyDegreeId", difficultyId);

        int scopId = SemanticScopeBOManager.suggestAdding(semanticEntryBO.getSemanticscop());
        Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById(scopId);
        restrictions.put("eq_semanticscop.semanticScopId", scopId);

        int specializationId = SpecializationManager.suggestAdding(semanticEntryBO.getSpecialization());
        Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById(specializationId);
        restrictions.put("eq_specialization.specializationId", specializationId);

        int spreadingId = SpreadingDegreeManager.suggestAdding(semanticEntryBO.getSpreadingdegree());
        Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById(spreadingId);
        restrictions.put("eq_spreadingdegree.spreadingDegreeId", spreadingId);

        /*if ( SEMANTIC_ENTRY_DAO.getConfirmedInstance( new Semanticentry() , restrictions ).size() > 0 )
         {
         return false;
         }
         else
         {*/
        Semanticentry oldSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Semanticentry tempSemanticentry = SEMANTIC_ENTRY_DAO.getById(oldSemanticentry.getSuggestion().getUpdateId());
        tempSemanticentry.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);

        oldSemanticentry.setEpoch(epochObj);
        oldSemanticentry.setRegion(regionObj);
        oldSemanticentry.setDifficultydegree(difficultydegreeObj);
        oldSemanticentry.setSemanticscop(semanticscopObj);
        oldSemanticentry.setSpecialization(specializationObj);
        oldSemanticentry.setSpreadingdegree(spreadingdegreeObj);
        oldSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        oldSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldSemanticentry.getSuggestion());
        SEMANTIC_ENTRY_DAO.update(oldSemanticentry);
        return true;
        //}
    }

    public static void rejectUpdating(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Semanticentry tempSemanticentry = SEMANTIC_ENTRY_DAO.getById(newSemanticentry.getSuggestion().getUpdateId());

        newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

        BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSuggestion());

        tempSemanticentry.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        tempSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
        SEMANTIC_ENTRY_DAO.update(tempSemanticentry);
    }

    public static int suggestAdding(SemanticEntryBO semanticEntryBO) throws EntryExistedException, RawNotFoundException {
        Semanticentry semanticEntry = new Semanticentry();
        int regionId = RegionManager.suggestAdding(semanticEntryBO.getRegion());
        Region regionObj = BLUtil.daoFactory.getRegionDAO().getById(regionId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_region.regionId", regionId);

        int epochId = EpochManager.suggestAdding(semanticEntryBO.getEpoch());
        Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById(epochId);
        restrictions.put("eq_epoch.epochId", epochId);

        int difficultyId = DifficultyDegreeManager.suggestAdding(semanticEntryBO.getDifficultydegree());
        Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById(difficultyId);
        restrictions.put("eq_difficultydegree.difficultyDegreeId", difficultyId);

        int scopId = SemanticScopeBOManager.suggestAdding(semanticEntryBO.getSemanticscop());
        Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById(scopId);
        restrictions.put("eq_semanticscop.semanticScopId", scopId);

        int specializationId = SpecializationManager.suggestAdding(semanticEntryBO.getSpecialization());
        Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById(specializationId);
        restrictions.put("eq_specialization.specializationId", specializationId);

        int spreadingId = SpreadingDegreeManager.suggestAdding(semanticEntryBO.getSpreadingdegree());
        Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById(spreadingId);
        restrictions.put("eq_spreadingdegree.spreadingDegreeId", spreadingId);

        semanticEntry.setEpoch(epochObj);
        semanticEntry.setRegion(regionObj);
        semanticEntry.setDifficultydegree(difficultydegreeObj);
        semanticEntry.setSemanticscop(semanticscopObj);
        semanticEntry.setSpecialization(specializationObj);
        semanticEntry.setSpreadingdegree(spreadingdegreeObj);
        semanticEntry.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        semanticEntry.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        semanticEntry.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
//        int id = SEMANTIC_ENTRY_DAO.insertWithCheck( semanticEntry , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES ,
//                                                                          restrictions , "المدخل الدلالي موجود مسبقاً." );
        int id = SEMANTIC_ENTRY_DAO.insert(semanticEntry);
        for (MeaningBO newMeaningBO : semanticEntryBO.getMeanings()) {
            MeaningBOManager.suggestAdding(newMeaningBO, id);
        }

        return id;
    }

    public static void rejectDeleting(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        if (newSemanticentry.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            if (newSemanticentry.getDifficultydegree().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getDifficultydegree().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getDifficultydegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getDifficultydegree().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getDifficultydegree().getSuggestion());
                }
                BLUtil.daoFactory.getDifficultydegreeDAO().update(newSemanticentry.getDifficultydegree());
            }

            if (newSemanticentry.getRegion().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getRegion().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getRegion().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getRegion().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getRegion().getSuggestion());
                }
                BLUtil.daoFactory.getRegionDAO().update(newSemanticentry.getRegion());
            }

            if (newSemanticentry.getEpoch().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getEpoch().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getEpoch().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getEpoch().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getEpoch().getSuggestion());
                }
                BLUtil.daoFactory.getEpochDAO().update(newSemanticentry.getEpoch());
            }

            if (newSemanticentry.getSemanticscop().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSemanticscop().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getSemanticscop().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSemanticscop().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSemanticscop().getSuggestion());
                }
                BLUtil.daoFactory.getSemanticscopDAO().update(newSemanticentry.getSemanticscop());
            }

            if (newSemanticentry.getSpecialization().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSpecialization().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getSpecialization().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpecialization().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSpecialization().getSuggestion());
                }
                BLUtil.daoFactory.getSpecializationDAO().update(newSemanticentry.getSpecialization());
            }

            if (newSemanticentry.getSpreadingdegree().getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
                newSemanticentry.getSpreadingdegree().setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.getSpreadingdegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpreadingdegree().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSpreadingdegree().getSuggestion());
                }
                BLUtil.daoFactory.getSpreadingdegreeDAO().update(newSemanticentry.getSpreadingdegree());
            }

            if (newSemanticentry.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSuggestion());
            }
            newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_ENTRY_DAO.update(newSemanticentry);
        }
    }

    static int add(SemanticEntryBO semanticEntryBO) throws RawNotFoundException {
        Semanticentry semanticEntry = new Semanticentry();
        int regionId = RegionManager.add(semanticEntryBO.getRegion());
        Region regionObj = BLUtil.daoFactory.getRegionDAO().getById(regionId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_region.regionId", regionId);

        int epochId = EpochManager.add(semanticEntryBO.getEpoch());
        Epoch epochObj = BLUtil.daoFactory.getEpochDAO().getById(epochId);
        restrictions.put("eq_epoch.epochId", epochId);

        int difficultyId = DifficultyDegreeManager.add(semanticEntryBO.getDifficultydegree());
        Difficultydegree difficultydegreeObj = BLUtil.daoFactory.getDifficultydegreeDAO().getById(difficultyId);
        restrictions.put("eq_difficultydegree.difficultyDegreeId", difficultyId);

        int scopId = SemanticScopeBOManager.add(semanticEntryBO.getSemanticscop());
        Semanticscop semanticscopObj = BLUtil.daoFactory.getSemanticscopDAO().getById(scopId);
        restrictions.put("eq_semanticscop.semanticScopId", scopId);

        int specializationId = SpecializationManager.add(semanticEntryBO.getSpecialization());
        Specialization specializationObj = BLUtil.daoFactory.getSpecializationDAO().getById(specializationId);
        restrictions.put("eq_specialization.specializationId", specializationId);

        int spreadingId = SpreadingDegreeManager.add(semanticEntryBO.getSpreadingdegree());
        Spreadingdegree spreadingdegreeObj = BLUtil.daoFactory.getSpreadingdegreeDAO().getById(spreadingId);
        restrictions.put("eq_spreadingdegree.spreadingDegreeId", spreadingId);

        semanticEntry.setEpoch(epochObj);
        semanticEntry.setRegion(regionObj);
        semanticEntry.setDifficultydegree(difficultydegreeObj);
        semanticEntry.setSemanticscop(semanticscopObj);
        semanticEntry.setSpecialization(specializationObj);
        semanticEntry.setSpreadingdegree(spreadingdegreeObj);
        semanticEntry.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        semanticEntry.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        semanticEntry.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
//        int id = SEMANTIC_ENTRY_DAO.insertWithCheck( semanticEntry , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES ,
//                                                                          restrictions , "المدخل الدلالي موجود مسبقاً." );
        int id = SEMANTIC_ENTRY_DAO.insert(semanticEntry);
        for (MeaningBO newMeaningBO : semanticEntryBO.getMeanings()) {
            MeaningBOManager.addMeaning(newMeaningBO, id);
        }

        return id;
    }

    static void affirmAdding(Integer semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newSemanticentry.getSuggestion());
        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
    }

    public static void rejectAdding(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        if (newSemanticentry.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            if (newSemanticentry.getDifficultydegree().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getDifficultydegree().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getDifficultydegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getDifficultydegree().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getDifficultydegree().getSuggestion());
                }
                BLUtil.daoFactory.getDifficultydegreeDAO().update(newSemanticentry.getDifficultydegree());
            }

            if (newSemanticentry.getRegion().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getRegion().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getRegion().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getRegion().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getRegion().getSuggestion());
                }
                BLUtil.daoFactory.getRegionDAO().update(newSemanticentry.getRegion());
            }

            if (newSemanticentry.getEpoch().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getEpoch().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getEpoch().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getEpoch().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getEpoch().getSuggestion());
                }
                BLUtil.daoFactory.getEpochDAO().update(newSemanticentry.getEpoch());
            }

            if (newSemanticentry.getSemanticscop().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getSemanticscop().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getSemanticscop().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSemanticscop().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSemanticscop().getSuggestion());
                }
                BLUtil.daoFactory.getSemanticscopDAO().update(newSemanticentry.getSemanticscop());
            }

            if (newSemanticentry.getSpecialization().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getSpecialization().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getSpecialization().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpecialization().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSpecialization().getSuggestion());
                }
                BLUtil.daoFactory.getSpecializationDAO().update(newSemanticentry.getSpecialization());
            }

            if (newSemanticentry.getSpreadingdegree().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newSemanticentry.getSpreadingdegree().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newSemanticentry.getSpreadingdegree().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                if (newSemanticentry.getSpreadingdegree().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSpreadingdegree().getSuggestion());
                }
                BLUtil.daoFactory.getSpreadingdegreeDAO().update(newSemanticentry.getSpreadingdegree());
            }

            if (newSemanticentry.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticentry.getSuggestion());
            }
            newSemanticentry.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_ENTRY_DAO.update(newSemanticentry);
        }
    }

    /// <editor-fold defaultstate="collapsed" desc="Meanings....">
    public static int suggestAddMeaning(MeaningBO newMeaningBO, int semanticEntryId) throws RawNotFoundException, EntryExistedException {
        int meaningId = MeaningBOManager.suggestAdding(newMeaningBO, semanticEntryId);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        newSemanticentry.setChechStatus(WordStatus.NEED_CHECK_STATUS);
        SEMANTIC_ENTRY_DAO.update(newSemanticentry);
        return meaningId;
    }

    public static void affirmMeaningAdding(int meaningId) throws RawNotFoundException {
        MeaningBOManager.affirmAdding(meaningId);
    }

    public static boolean affirmMeaningAddingAU(int meaningId, MeaningBO updateMeaning) throws RawNotFoundException {
        return MeaningBOManager.affirmAddingAU(meaningId, updateMeaning);
    }

    public static void rejectMeaningAdding(int meaningId) throws RawNotFoundException {
        MeaningBOManager.rejectAdding(meaningId);
    }

    public static void suggestUpdateMeaning(MeaningBO newMeaningBO, MeaningBO oldMeaningBO) throws RawNotFoundException {
        MeaningBOManager.suggestUpdating(newMeaningBO, oldMeaningBO);
    }

    public static void affirmMeaningUpdating(int meaningId) throws RawNotFoundException {
        MeaningBOManager.affirmUpdating(meaningId);
    }

    public static boolean affirmMeaningUpdatingAU(int meaningId, MeaningBO newMeaningBO) throws RawNotFoundException {
        return MeaningBOManager.affirmUpdatingAU(meaningId, newMeaningBO);
    }

    public static void rejectMeaningUpdating(int meaningId) throws RawNotFoundException {
        MeaningBOManager.rejectUpdating(meaningId);
    }

    public static void suggestDeleteMeaning(MeaningBO newMeaningBO) throws RawNotFoundException {
        MeaningBOManager.suggestDeleting(newMeaningBO);
    }

    public static void affirmMeaningDeleting(int semanticEntryId, int meaningId) throws RawNotFoundException {
        MeaningBOManager.affirmDeleting(meaningId);
    }

    public static void rejectMeaningDeleting(int semanticEntryId, int meaningId) throws RawNotFoundException {
        MeaningBOManager.rejectDeleting(meaningId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Examples....">
    public int suggestAddExample(ExampleBO newExampleBO, int semanticEntryId) throws RawNotFoundException, EntryExistedException {
        int exampleId = newExampleBOManager.suggestAddExample(newExampleBO);

        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        Entryexample newEntryexample = new Entryexample();
        newEntryexample.setExample(newExample);
        newEntryexample.setSemanticentry(newSemanticentry);
        newEntryexample.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newEntryexample.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newEntryexample.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        String message = "المثال موجود مسبقاً!";
        return BLUtil.daoFactory.getEntryexampleDAO().insertWithCheck(newEntryexample, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions, message);
    }

    public static void affirmExampleAdding(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getAddedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);

            if (newEntryExample.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryExample.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryExample.getSuggestion());
                }
                BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
            }
        }
        ExampleBOManager.affirmAdding(exampleId);
    }

    /**
     * this function needs a deep review later
     *
     * @param semanticEntryId
     * @param exampleId
     * @param updateExample
     * @return
     * @throws RawNotFoundException
     */
    public static boolean affirmExampleAddingAU(int semanticEntryId, int exampleId, ExampleBO updateExample) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);

        boolean updated = ExampleBOManager.affirmAddingAU(exampleId, updateExample);
        if (updated) {
            Map restrictions = new HashMap();
            restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
            restrictions.put("eq_example.exampleId", exampleId);
            List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getAddedInstance(new Entryexample(), restrictions);
            if (entryexamplesList != null && entryexamplesList.size() > 0) {
                Entryexample newEntryExample = entryexamplesList.get(0);

                if (newEntryExample.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                    newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                    if (newEntryExample.getSuggestion() != null) {
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newEntryExample.getSuggestion());
                    }
                    BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectExampleAdding(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getAddedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);

            if (newEntryExample.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryExample.setInfoStatus(WordStatus.NEED_DELETING);
                newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryExample.getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newEntryExample.getSuggestion());
                }
                BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
            }
        }
        ExampleBOManager.rejectAdding(exampleId);
    }

    public void suggestUpdateExample(ExampleBO newExampleBO, ExampleBO oldExampleBO, int semanticEntryId) throws RawNotFoundException {
        /*Source source = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( oldExampleBO.getSource() ) , null ).get( 0 );
         Map restriction1 = new HashMap();
         restriction1.put( "eq_source.sourceId" , source.getIdentity() );
         Example example = BLUtil.daoFactory.getExampleDAO().getConfirmedInstance( new Example( source , oldExampleBO.getExample() ) , restriction1 ).get( 0 );*/
        Example example = BLUtil.daoFactory.getExampleDAO().getById(oldExampleBO.getExampleId());
        String ss = example.getExample();
        Map restrictions2 = new HashMap();
        restrictions2.put("eq_example.exampleId", example.getIdentity());
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        List<Entryexample> newEntryexamples = BLUtil.daoFactory.getEntryexampleDAO().getConfirmedInstance(new Entryexample(newSemanticentry, example), restrictions2);

        if (newEntryexamples.size() > 0) {
            //Waleed
            Entryexample newEntryexample = newEntryexamples.get(0);
            Map restrictions3 = new HashMap();
            restrictions3.put("eq_example.exampleId", example.getIdentity());
            List<Entryexample> entryExampleList = BLUtil.daoFactory.getEntryexampleDAO().getConfirmedInstance(newEntryexample, restrictions3);
            if (entryExampleList.size() == 1) {
                newExampleBOManager.suggestUpdateExample(newExampleBO, oldExampleBO);
                newEntryexample.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                newEntryexample.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

                BLUtil.daoFactory.getEntryexampleDAO().update(newEntryexample);
            } else if (entryExampleList.size() > 1) {
                int tempExampleId = newExampleBOManager.AddTempExample(newExampleBO);
                Example tempExample = BLUtil.daoFactory.getExampleDAO().getById(tempExampleId);

                Entryexample tempEntryExample = new Entryexample(newSemanticentry, tempExample);
                tempEntryExample.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
                tempEntryExample.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
                tempEntryExample.setSuggestion(null);
                Map restriction4 = new HashMap();
                restriction4.put("eq_example.exampleId", tempExample.getIdentity());
                restriction4.put("eq_semanticentry.semanticEntryId", semanticEntryId);

                int tempEntryExampleId = BLUtil.daoFactory.getEntryexampleDAO().insertWithCheck(tempEntryExample, null, restriction4);

                newEntryexample.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                newEntryexample.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                newEntryexample.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempEntryExampleId));
                BLUtil.daoFactory.getEntryexampleDAO().update(newEntryexample);
            }
        }
    }

    public static void affirmExampleUpdating(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getUpdatedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);

            if (newEntryExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryExample.getSuggestion() != null) {
                    Entryexample tempEntryExample = BLUtil.daoFactory.getEntryexampleDAO().getById(newEntryExample.getSuggestion().getUpdateId());
                    Example tempExample = tempEntryExample.getExample();
                    tempExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getExampleDAO().update(tempExample);

                    tempEntryExample.setExample(newExample);
                    tempEntryExample.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntryexampleDAO().update(tempEntryExample);

                    newEntryExample.setExample(tempExample);
                    newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryExample.getSuggestion());
                    BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
                } else {
                    newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
                    ExampleBOManager.affirmUpdate(exampleId);
                }
            }
        }
    }

    public static boolean affirmExampleUpdatingAU(int semanticEntryId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example oldExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getUpdatedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample oldEntryExample = entryexamplesList.get(0);

            if (oldEntryExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldEntryExample.getSuggestion() != null) {
                    Entryexample tempEntryExample = BLUtil.daoFactory.getEntryexampleDAO().getById(oldEntryExample.getSuggestion().getUpdateId());
                    Example tempExample = tempEntryExample.getExample();
                    int newSourceId = SourceBOManager.suggestAdding(newExampleBO.getSource());
                    Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
                    Map restrictions1 = new HashMap();
                    restrictions1.put("eq_source.sourceId", newSourceId);

                    if (BLUtil.daoFactory.getExampleDAO().getConfirmedInstance(new Example(newSource, newExampleBO.getExample()), restrictions1).size() == 0) {
                        tempExample.setSource(newSource);
                        tempExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        tempExample.setExample(newExampleBO.getExample());
                        BLUtil.daoFactory.getExampleDAO().update(tempExample);

                        tempEntryExample.setExample(oldExample);
                        tempEntryExample.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                        tempEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BLUtil.daoFactory.getEntryexampleDAO().update(tempEntryExample);

                        oldEntryExample.setExample(tempExample);
                        oldEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        oldEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldEntryExample.getSuggestion());
                        BLUtil.daoFactory.getEntryexampleDAO().update(oldEntryExample);
                        return true;
                    }
                } else {
                    oldEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntryexampleDAO().update(oldEntryExample);
                    ExampleBOManager.affirmUpdatingAU(exampleId, newExampleBO);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectExampleUpdating(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getUpdatedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);

            if (newEntryExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryExample.getSuggestion() != null && newEntryExample.getSuggestion().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                    Entryexample tempEntryExample = BLUtil.daoFactory.getEntryexampleDAO().getById(newEntryExample.getSuggestion().getUpdateId());
                    Example tempExample = tempEntryExample.getExample();
                    tempExample.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getExampleDAO().update(tempExample);

                    tempEntryExample.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntryexampleDAO().update(tempEntryExample);

                    newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.REJECT_SUGGESTION(newEntryExample.getSuggestion());
                    BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
                } else {
                    newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
                    ExampleBOManager.rejectUpdating(exampleId);
                }
            }
        }
    }

    public void suggestDeleteExample(ExampleBO newExampleBO, int semanticEntryId) throws RawNotFoundException {
        /*Source source = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( newExampleBO.getSource() ) , null ).get( 0 );
         Map restrictions = new HashMap();
         restrictions.put( "eq_source.sourceId" , source.getIdentity() );
        
         Example example = BLUtil.daoFactory.getExampleDAO().getConfirmedInstance( new Example( source , newExampleBO.getExample() ) , restrictions ).get( 0 );*/

        Example example = BLUtil.daoFactory.getExampleDAO().getById(newExampleBO.getExampleId());

        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Map restrictions2 = new HashMap();
        restrictions2.put("eq_example.exampleId", example.getIdentity());
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Entryexample newEntryexample = BLUtil.daoFactory.getEntryexampleDAO().getConfirmedInstance(new Entryexample(newSemanticentry, example), restrictions2).get(0);

        newEntryexample.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newEntryexample.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newEntryexample.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());
        BLUtil.daoFactory.getEntryexampleDAO().update(newEntryexample);
        //insure that there is no other entry share the same example before we delete it
        Map resrtrictions3 = new HashMap();
        resrtrictions3.put("eq_example.exampleId", example.getIdentity());
        List<Entryexample> entryExampleList = BLUtil.daoFactory.getEntryexampleDAO().getByExample(newEntryexample, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, resrtrictions3);
        if (entryExampleList.size() <= 1) {
            newExampleBOManager.suggestDeleteExample(newExampleBO);
        }
    }

    public static void affirmExampleDeleting(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getUpdatedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);
            newEntryExample.setInfoStatus(WordStatus.NEED_DELETING);
            newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryExample.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newEntryExample.getSuggestion());
            }
            BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
            ExampleBOManager.affirmDeleting(exampleId);
        }
    }

    public static void rejectExampleDeleting(int semanticEntryId, int exampleId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Example newExample = BLUtil.daoFactory.getExampleDAO().getById(exampleId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_example.exampleId", exampleId);
        List<Entryexample> entryexamplesList = BLUtil.daoFactory.getEntryexampleDAO().getUpdatedInstance(new Entryexample(), restrictions);
        if (entryexamplesList != null && entryexamplesList.size() > 0) {
            Entryexample newEntryExample = entryexamplesList.get(0);
            newEntryExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newEntryExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryExample.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newEntryExample.getSuggestion());
            }
            BLUtil.daoFactory.getEntryexampleDAO().update(newEntryExample);
            ExampleBOManager.rejectDeleting(exampleId);
        }
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Common Mistakes....">
    public int suggestAddCommonMistake(CommonMistakeBO commonMistakeBO, int semanticEntryId) throws RawNotFoundException, EntryExistedException {
        int commonMistakeId = newCommonMistakeBOManager.suggestAddCommonMistake(commonMistakeBO);
        Commonmistake addedCommonMistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonMistakeId);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonMistakeId);

        Entrycommonmistake newEntrycommonmistake = new Entrycommonmistake(newSemanticentry, addedCommonMistake);
        newEntrycommonmistake.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newEntrycommonmistake.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newEntrycommonmistake.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        String message = "الخطأ الشائع موجود مسبقاً!";
        BLUtil.daoFactory.getEntrycommonmistakeDAO().insertWithCheck(newEntrycommonmistake, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions, message);
        return commonMistakeId;
    }

    public static void affirmCommonMistakeAdding(int semanticEntryId, int commonmistakeId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getAddedInstance(new Entrycommonmistake(), restrictions);
        if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
            Entrycommonmistake newEntryCommonmistake = entrycommonmistakesList.get(0);

            if (newEntryCommonmistake.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryCommonmistake.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryCommonmistake.getSuggestion());
                }
                BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
            }
        }
        CommonMistakeBOManager.affirmAdding(commonmistakeId);
    }

    public static boolean affirmCommonMistakeAddingAU(int semanticEntryId, int commonmistakeId, CommonMistakeBO updateCommonmistake) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);

        boolean updated = CommonMistakeBOManager.affirmAddingAU(commonmistakeId, updateCommonmistake);
        if (updated) {
            Map restrictions = new HashMap();
            restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
            restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
            List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getAddedInstance(new Entrycommonmistake(), restrictions);
            if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
                Entrycommonmistake newEntryCommonmistake = entrycommonmistakesList.get(0);

                if (newEntryCommonmistake.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                    newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                    if (newEntryCommonmistake.getSuggestion() != null) {
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newEntryCommonmistake.getSuggestion());
                    }
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectCommonMistakeAdding(int semanticEntryId, int commonmistakeId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getAddedInstance(new Entrycommonmistake(), restrictions);
        if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
            Entrycommonmistake newEntryCommonmistake = entrycommonmistakesList.get(0);

            if (newEntryCommonmistake.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryCommonmistake.setInfoStatus(WordStatus.NEED_DELETING);
                newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryCommonmistake.getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newEntryCommonmistake.getSuggestion());
                }
                BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
            }
        }
        CommonMistakeBOManager.rejectAdding(commonmistakeId);
    }

    public void suggestDeleteCommonMistake(CommonMistakeBO deletedCommonMistakeBO, int semanticEntryId) throws RawNotFoundException {
        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( deletedCommonMistakeBO.getSource() ) , null ).get( 0 );
         Map restrictions1 = new HashMap();
         restrictions1.put( "eq_source.sourceId" , sourceObj.getIdentity() );
         Commonmistake commonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getConfirmedInstance( new Commonmistake( sourceObj , deletedCommonMistakeBO.getDescription() ) , restrictions1 ).get( 0 );*/
        Commonmistake commonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(deletedCommonMistakeBO.getCommonMistakeId());

        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions2 = new HashMap();
        restrictions2.put("eq_commonmistake.commonMistakeId", commonmistake.getIdentity());
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Entrycommonmistake newEntrycommonmistake = BLUtil.daoFactory.getEntrycommonmistakeDAO().getConfirmedInstance(new Entrycommonmistake(newSemanticentry, commonmistake), restrictions2).get(0);

        newEntrycommonmistake.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newEntrycommonmistake.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newEntrycommonmistake.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntrycommonmistake);
        Map restrictions3 = new HashMap();

        //get all entry common mistake relations that have same common mistake.
        restrictions3.put("eq_commonmistake.commonMistakeId", commonmistake.getIdentity());
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getNotDeletedInstance(newEntrycommonmistake, restrictions3);

        if (entrycommonmistakesList.size() == 0) {
            newCommonMistakeBOManager.suggestDeletecommonMistake(deletedCommonMistakeBO);
        }
    }

    public void suggestUpdateCommonMistake(CommonMistakeBO newCommonMistakeBO, CommonMistakeBO oldCommonMistakeBO, int semanticEntryId) throws RawNotFoundException {
        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( oldCommonMistakeBO.getSource() ) , null ).get( 0 );
         Map restrictions1 = new HashMap();
         restrictions1.put( "eq_source.sourceId" , sourceObj.getIdentity() );
         Commonmistake commonMistake = BLUtil.daoFactory.getCommonmistakeDAO().getConfirmedInstance( new Commonmistake( sourceObj , oldCommonMistakeBO.getDescription() ) , restrictions1 ).get( 0 );*/
        Commonmistake commonMistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(oldCommonMistakeBO.getCommonMistakeId());
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions2 = new HashMap();
        restrictions2.put("eq_commonmistake.commonMistakeId", commonMistake.getIdentity());
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Entrycommonmistake newEntrycommonmistake = BLUtil.daoFactory.getEntrycommonmistakeDAO().getConfirmedInstance(new Entrycommonmistake(newSemanticentry, commonMistake), restrictions2).get(0);

        Map restrictions3 = new HashMap();

        //get all entry common mistake relations that have same common mistake.
        restrictions3.put("eq_commonmistake.commonMistakeId", commonMistake.getIdentity());
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getConfirmedInstance(newEntrycommonmistake, restrictions3);

        if (entrycommonmistakesList.size() == 1) {
            newCommonMistakeBOManager.suggestUpdateCommonMistake(newCommonMistakeBO, oldCommonMistakeBO);
            newEntrycommonmistake.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            newEntrycommonmistake.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntrycommonmistake);
        } else if (entrycommonmistakesList.size() > 1) {
            int newCommonMistakeId = newCommonMistakeBOManager.AddTempCommonMistake(newCommonMistakeBO);
            Commonmistake tempCommonMistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(newCommonMistakeId);
            Entrycommonmistake tempEntrycommonmistake = new Entrycommonmistake(newSemanticentry, tempCommonMistake);
            tempEntrycommonmistake.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
            tempEntrycommonmistake.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
            tempEntrycommonmistake.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

            Map restrictions4 = new HashMap();
            restrictions4.put("eq_commonmistake.commonMistakeId", tempCommonMistake.getIdentity());
            restrictions4.put("eq_semanticentry.semanticEntryId", semanticEntryId);

            int tempEntryCommonMistakeId = BLUtil.daoFactory.getEntrycommonmistakeDAO().insertWithCheck(tempEntrycommonmistake, null, restrictions4);

            newEntrycommonmistake.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            newEntrycommonmistake.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            newEntrycommonmistake.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempEntryCommonMistakeId));

            BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntrycommonmistake);
        }
    }

    public static void affirmCommonMistakeUpdating(int semanticEntryId, int commonmistakeId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getUpdatedInstance(new Entrycommonmistake(), restrictions);
        if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
            Entrycommonmistake newEntryCommonmistake = entrycommonmistakesList.get(0);

            if (newEntryCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryCommonmistake.getSuggestion() != null) {
                    Entrycommonmistake tempEntryCommonmistake = BLUtil.daoFactory.getEntrycommonmistakeDAO().getById(newEntryCommonmistake.getSuggestion().getUpdateId());
                    Commonmistake tempCommonmistake = tempEntryCommonmistake.getCommonmistake();
                    tempCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getCommonmistakeDAO().update(tempCommonmistake);

                    tempEntryCommonmistake.setCommonmistake(newCommonmistake);
                    tempEntryCommonmistake.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(tempEntryCommonmistake);

                    newEntryCommonmistake.setCommonmistake(tempCommonmistake);
                    newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryCommonmistake.getSuggestion());
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
                } else {
                    newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
                    CommonMistakeBOManager.affirmUpdate(commonmistakeId);
                }
            }
        }
    }

    public static boolean affirmCommonMistakeUpdatingAU(int semanticEntryId, int commonmistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake oldCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getUpdatedInstance(new Entrycommonmistake(), restrictions);
        if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
            Entrycommonmistake oldEntryCommonmistake = entrycommonmistakesList.get(0);

            if (oldEntryCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldEntryCommonmistake.getSuggestion() != null) {
                    Entrycommonmistake tempEntryCommonmistake = BLUtil.daoFactory.getEntrycommonmistakeDAO().getById(oldEntryCommonmistake.getSuggestion().getUpdateId());
                    Commonmistake tempCommonmistake = tempEntryCommonmistake.getCommonmistake();
                    int newSourceId = SourceBOManager.suggestAdding(newCommonMistakeBO.getSource());
                    Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
                    Map restrictions1 = new HashMap();
                    restrictions1.put("eq_source.sourceId", newSourceId);

                    if (BLUtil.daoFactory.getCommonmistakeDAO().getConfirmedInstance(new Commonmistake(newSource, newCommonMistakeBO.getDescription()), restrictions1).size() == 0) {
                        tempCommonmistake.setSource(newSource);
                        tempCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        tempCommonmistake.setCommonMistake(newCommonMistakeBO.getDescription());
                        BLUtil.daoFactory.getCommonmistakeDAO().update(tempCommonmistake);

                        tempEntryCommonmistake.setCommonmistake(oldCommonmistake);
                        tempEntryCommonmistake.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                        tempEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BLUtil.daoFactory.getEntrycommonmistakeDAO().update(tempEntryCommonmistake);

                        oldEntryCommonmistake.setCommonmistake(tempCommonmistake);
                        oldEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        oldEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldEntryCommonmistake.getSuggestion());
                        BLUtil.daoFactory.getEntrycommonmistakeDAO().update(oldEntryCommonmistake);
                        return true;
                    }
                } else {
                    oldEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(oldEntryCommonmistake);
                    CommonMistakeBOManager.affirmUpdatingAU(commonmistakeId, newCommonMistakeBO);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectCommonmistakeUpdating(int semanticEntryId, int commonmistakeId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(commonmistakeId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_commonmistake.commonMistakeId", commonmistakeId);
        List<Entrycommonmistake> entrycommonmistakesList = BLUtil.daoFactory.getEntrycommonmistakeDAO().getUpdatedInstance(new Entrycommonmistake(), restrictions);
        if (entrycommonmistakesList != null && entrycommonmistakesList.size() > 0) {
            Entrycommonmistake newEntryCommonmistake = entrycommonmistakesList.get(0);

            if (newEntryCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryCommonmistake.getSuggestion() != null) {
                    Entrycommonmistake tempEntryCommonmistake = BLUtil.daoFactory.getEntrycommonmistakeDAO().getById(newEntryCommonmistake.getSuggestion().getUpdateId());
                    Commonmistake tempCommonmistake = tempEntryCommonmistake.getCommonmistake();
                    tempCommonmistake.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getCommonmistakeDAO().update(tempCommonmistake);

                    tempEntryCommonmistake.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(tempEntryCommonmistake);

                    newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.REJECT_SUGGESTION(newEntryCommonmistake.getSuggestion());
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
                } else {
                    newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrycommonmistakeDAO().update(newEntryCommonmistake);
                    CommonMistakeBOManager.rejectUpdating(commonmistakeId);
                }
            }
        }
    }

    public static void affirmCommonmistakeDeleting(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryCommonmistake = entrylinguisticbenefitsList.get(0);
            newEntryCommonmistake.setInfoStatus(WordStatus.NEED_DELETING);
            newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryCommonmistake.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newEntryCommonmistake.getSuggestion());
            }
            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryCommonmistake);
            CommonMistakeBOManager.affirmDeleting(linguisticbenefitId);
        }
    }

    public static void rejectCommonmistakeDeleting(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Commonmistake newCommonmistake = BLUtil.daoFactory.getCommonmistakeDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryCommonmistake = entrylinguisticbenefitsList.get(0);
            newEntryCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newEntryCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryCommonmistake.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newEntryCommonmistake.getSuggestion());
            }
            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryCommonmistake);
            CommonMistakeBOManager.rejectDeleting(linguisticbenefitId);
        }
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Linguistic Benefits....">
    public int suggestAddLinguisticBenefit(LinguisticBenefitBO linguisticBenefitBO, int semanticEntryId) throws RawNotFoundException, EntryExistedException {
        int linguisticBenefitId = newLinguisticBenefitBOManager.suggestAddLinguisticBenefit(linguisticBenefitBO);
        Linguisticbenefit addedLinguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticBenefitId);
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticBenefitId);

        Entrylinguisticbenefit newEntrylinguisticbenefit = new Entrylinguisticbenefit(newSemanticentry, addedLinguisticBenefit);
        newEntrylinguisticbenefit.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newEntrylinguisticbenefit.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newEntrylinguisticbenefit.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        String message = "الفائدة اللغوية موجودة مسبقاً!";
        BLUtil.daoFactory.getEntrylinguisticbenefitDAO().insertWithCheck(newEntrylinguisticbenefit, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions, message);
        return linguisticBenefitId;
    }

    public static void affirmLinguisticBenefitAdding(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getAddedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

            if (newEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryLinguisticbenefit.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
                }
                BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
            }
        }
        LinguisticBenefitBOManager.affirmAdding(linguisticbenefitId);
    }

    public static boolean affirmLinguisticBenefitAddingAU(int semanticEntryId, int linguisticbenefitId, LinguisticBenefitBO updateLinguisticbenefit) throws RawNotFoundException {
        boolean updated = LinguisticBenefitBOManager.affirmAddingAU(linguisticbenefitId, updateLinguisticbenefit);
        if (updated) {
            Map restrictions = new HashMap();
            restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
            restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
            List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getAddedInstance(new Entrylinguisticbenefit(), restrictions);
            if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
                Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

                if (newEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                    newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                    if (newEntryLinguisticbenefit.getSuggestion() != null) {
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newEntryLinguisticbenefit.getSuggestion());
                    }
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectLinguisticBenefitAdding(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getAddedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

            if (newEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newEntryLinguisticbenefit.setInfoStatus(WordStatus.NEED_DELETING);
                newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newEntryLinguisticbenefit.getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
                }
                BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
            }
        }
        LinguisticBenefitBOManager.rejectAdding(linguisticbenefitId);
    }

    public void suggestDeleteLinguisticBenefit(LinguisticBenefitBO deletedLinguisticBenefitBO, int semanticEntryId) throws RawNotFoundException {
        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( deletedLinguisticBenefitBO.getSource() ) , null ).get( 0 );
         Map restrictions1 = new HashMap();
         restrictions1.put( "eq_source.sourceId" , sourceObj.getIdentity() );
         Linguisticbenefit LinguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getConfirmedInstance( new Linguisticbenefit( sourceObj , deletedLinguisticBenefitBO.getDescription() ) , restrictions1 ).get( 0 );*/
        Linguisticbenefit linguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(deletedLinguisticBenefitBO.getLinguisticBenefitId());

        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions2 = new HashMap();
        restrictions2.put("eq_linguisticbenefit.linguisticBenefitId", linguisticBenefit.getIdentity());
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Entrylinguisticbenefit newEntrylinguisticbenefit = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getConfirmedInstance(new Entrylinguisticbenefit(newSemanticentry, linguisticBenefit), restrictions2).get(0);

        newEntrylinguisticbenefit.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newEntrylinguisticbenefit.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newEntrylinguisticbenefit.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntrylinguisticbenefit);
        Map restrictions3 = new HashMap();

        //get all entry common mistake relations that have same common mistake.
        restrictions3.put("eq_linguisticbenefit.linguisticBenefitId", linguisticBenefit.getIdentity());
        List<Entrylinguisticbenefit> entryLinguisticBenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getNotDeletedInstance(newEntrylinguisticbenefit, restrictions3);

        if (entryLinguisticBenefitsList.size() == 0) {
            newLinguisticBenefitBOManager.suggestDeletelinguisticBenefit(deletedLinguisticBenefitBO);
        }
    }

    public void suggestUpdateLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, LinguisticBenefitBO oldLinguisticBenefitBO, int semanticEntryId) throws RawNotFoundException {
        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( oldLinguisticBenefitBO.getSource() ) , null ).get( 0 );
         Map restrictions1 = new HashMap();
         restrictions1.put( "eq_source.sourceId" , sourceObj.getIdentity() );
         Linguisticbenefit linguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getConfirmedInstance( new Linguisticbenefit( sourceObj , oldLinguisticBenefitBO.getDescription() ) , restrictions1 ).get( 0 );*/
        Linguisticbenefit linguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(oldLinguisticBenefitBO.getLinguisticBenefitId());

        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);

        Map restrictions2 = new HashMap();
        restrictions2.put("eq_linguisticbenefit.linguisticBenefitId", linguisticBenefit.getIdentity());
        restrictions2.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Entrylinguisticbenefit newEntrylinguisticbenefit = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getConfirmedInstance(new Entrylinguisticbenefit(newSemanticentry, linguisticBenefit), restrictions2).get(0);

        Map restrictions3 = new HashMap();

        //get all entry common mistake relations that have same common mistake.
        restrictions3.put("eq_linguisticbenefit.linguisticBenefitId", linguisticBenefit.getIdentity());
        List<Entrylinguisticbenefit> entryLinguisticBenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getConfirmedInstance(newEntrylinguisticbenefit, restrictions3);

        if (entryLinguisticBenefitsList.size() == 1) {
            newLinguisticBenefitBOManager.suggestUpdating(newLinguisticBenefitBO, oldLinguisticBenefitBO);
            newEntrylinguisticbenefit.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            newEntrylinguisticbenefit.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntrylinguisticbenefit);
        } else if (entryLinguisticBenefitsList.size() > 1) {
            int newLinguisticBenefitId = newLinguisticBenefitBOManager.AddTempLinguisticBenefit(newLinguisticBenefitBO);
            Linguisticbenefit tempLinguisticBenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(newLinguisticBenefitId);

            Entrylinguisticbenefit tempEntrylinguisticbenefit = new Entrylinguisticbenefit(newSemanticentry, tempLinguisticBenefit);
            tempEntrylinguisticbenefit.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
            tempEntrylinguisticbenefit.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
            tempEntrylinguisticbenefit.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

            Map restrictions4 = new HashMap();
            restrictions4.put("eq_linguisticbenefit.linguisticBenefitId", tempLinguisticBenefit.getIdentity());
            restrictions4.put("eq_semanticentry.semanticEntryId", semanticEntryId);

            int tempEntrylinguisticbenefitId = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().insertWithCheck(tempEntrylinguisticbenefit, null, restrictions4);

            newEntrylinguisticbenefit.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            newEntrylinguisticbenefit.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            newEntrylinguisticbenefit.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempEntrylinguisticbenefitId));

            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntrylinguisticbenefit);
        }
    }

    public static void affirmLinguisticBenefitUpdating(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

            if (newEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryLinguisticbenefit.getSuggestion() != null) {
                    Entrylinguisticbenefit tempEntryLinguisticbenefit = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getById(newEntryLinguisticbenefit.getSuggestion().getUpdateId());
                    Linguisticbenefit tempLinguisticbenefit = tempEntryLinguisticbenefit.getLinguisticbenefit();
                    tempLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getLinguisticbenefitDAO().update(tempLinguisticbenefit);

                    tempEntryLinguisticbenefit.setLinguisticbenefit(newLinguisticbenefit);
                    tempEntryLinguisticbenefit.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(tempEntryLinguisticbenefit);

                    newEntryLinguisticbenefit.setLinguisticbenefit(tempLinguisticbenefit);
                    newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
                } else {
                    newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
                    LinguisticBenefitBOManager.affirmUpdating(linguisticbenefitId);
                }
            }
        }
    }

    public static boolean affirmLinguisticBenefitUpdatingAU(int semanticEntryId, int linguisticbenefitId, LinguisticBenefitBO newLinguisticbenefitBO) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit oldLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit oldEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

            if (oldEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldEntryLinguisticbenefit.getSuggestion() != null) {
                    Entrylinguisticbenefit tempEntryLinguisticbenefit = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getById(oldEntryLinguisticbenefit.getSuggestion().getUpdateId());
                    Linguisticbenefit tempLinguisticbenefit = tempEntryLinguisticbenefit.getLinguisticbenefit();
                    int newSourceId = SourceBOManager.suggestAdding(newLinguisticbenefitBO.getSource());
                    Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
                    Map restrictions1 = new HashMap();
                    restrictions1.put("eq_source.sourceId", newSourceId);

                    if (BLUtil.daoFactory.getLinguisticbenefitDAO().getConfirmedInstance(new Linguisticbenefit(newSource, newLinguisticbenefitBO.getDescription()), restrictions1).size() == 0) {
                        tempLinguisticbenefit.setSource(newSource);
                        tempLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        tempLinguisticbenefit.setLinguisticBenefit(newLinguisticbenefitBO.getDescription());
                        BLUtil.daoFactory.getLinguisticbenefitDAO().update(tempLinguisticbenefit);

                        tempEntryLinguisticbenefit.setLinguisticbenefit(oldLinguisticbenefit);
                        tempEntryLinguisticbenefit.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                        tempEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(tempEntryLinguisticbenefit);

                        oldEntryLinguisticbenefit.setLinguisticbenefit(tempLinguisticbenefit);
                        oldEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                        oldEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldEntryLinguisticbenefit.getSuggestion());
                        BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(oldEntryLinguisticbenefit);
                        return true;
                    }
                } else {
                    oldEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(oldEntryLinguisticbenefit);
                    LinguisticBenefitBOManager.affirmUpdatingAU(linguisticbenefitId, newLinguisticbenefitBO);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectLinguisticBenefitUpdating(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);

            if (newEntryLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (newEntryLinguisticbenefit.getSuggestion() != null) {
                    Entrylinguisticbenefit tempEntryLinguisticbenefit = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getById(newEntryLinguisticbenefit.getSuggestion().getUpdateId());
                    Linguisticbenefit tempLinguisticbenefit = tempEntryLinguisticbenefit.getLinguisticbenefit();
                    tempLinguisticbenefit.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getLinguisticbenefitDAO().update(tempLinguisticbenefit);

                    tempEntryLinguisticbenefit.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                    tempEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(tempEntryLinguisticbenefit);

                    newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.REJECT_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
                } else {
                    newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
                    LinguisticBenefitBOManager.rejectUpdating(linguisticbenefitId);
                }
            }
        }
    }

    public static void affirmLinguisticBenefitDeleting(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);
            newEntryLinguisticbenefit.setInfoStatus(WordStatus.NEED_DELETING);
            newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryLinguisticbenefit.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
            }
            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
            LinguisticBenefitBOManager.affirmDeleting(linguisticbenefitId);
        }
    }

    public static void rejectLinguisticBenefitDeleting(int semanticEntryId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        Linguisticbenefit newLinguisticbenefit = BLUtil.daoFactory.getLinguisticbenefitDAO().getById(linguisticbenefitId);
        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        restrictions.put("eq_linguisticbenefit.linguisticBenefitId", linguisticbenefitId);
        List<Entrylinguisticbenefit> entrylinguisticbenefitsList = BLUtil.daoFactory.getEntrylinguisticbenefitDAO().getUpdatedInstance(new Entrylinguisticbenefit(), restrictions);
        if (entrylinguisticbenefitsList != null && entrylinguisticbenefitsList.size() > 0) {
            Entrylinguisticbenefit newEntryLinguisticbenefit = entrylinguisticbenefitsList.get(0);
            newEntryLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newEntryLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newEntryLinguisticbenefit.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newEntryLinguisticbenefit.getSuggestion());
            }
            BLUtil.daoFactory.getEntrylinguisticbenefitDAO().update(newEntryLinguisticbenefit);
            LinguisticBenefitBOManager.rejectDeleting(linguisticbenefitId);
        }
    }
    //</editor-fold>

    public static void grantAllMeaning(int grantSemanticEntryId, int granteeSemanticEntryId) throws RawNotFoundException {
        Semanticentry grantSemanticEntry = SEMANTIC_ENTRY_DAO.getById(grantSemanticEntryId);
        Semanticentry granteeSemanticEntry = SEMANTIC_ENTRY_DAO.getById(granteeSemanticEntryId);

        Set<Meaning> meaningSet = grantSemanticEntry.getMeanings();
        for (Iterator<Meaning> iter = meaningSet.iterator(); iter.hasNext();) {
            Meaning meaning = iter.next();
            meaning.setSemanticentry(granteeSemanticEntry);
            BLUtil.daoFactory.getMeaningDAO().insert(meaning);
        }
    }

    public static void grantAllExamples(int grantSemanticEntryId, int granteeSemanticEntryId) throws RawNotFoundException {
        Semanticentry grantSemanticEntry = SEMANTIC_ENTRY_DAO.getById(grantSemanticEntryId);
        Semanticentry granteeSemanticEntry = SEMANTIC_ENTRY_DAO.getById(granteeSemanticEntryId);

        Set<Entryexample> entryExamplesSet = grantSemanticEntry.getEntryexamples();

        for (Iterator<Entryexample> iter = entryExamplesSet.iterator(); iter.hasNext();) {
            Entryexample entryexample = iter.next();
            entryexample.setSemanticentry(granteeSemanticEntry);
            BLUtil.daoFactory.getEntryexampleDAO().insert(entryexample);
        }
    }

    public static void grantAllCommonMistakes(int grantSemanticEntryId, int granteeSemanticEntryId) throws RawNotFoundException {
        Semanticentry grantSemanticEntry = SEMANTIC_ENTRY_DAO.getById(grantSemanticEntryId);
        Semanticentry granteeSemanticEntry = SEMANTIC_ENTRY_DAO.getById(granteeSemanticEntryId);

        Set<Entrycommonmistake> entrycommonmistakesSet = grantSemanticEntry.getEntrycommonmistakes();
        for (Iterator<Entrycommonmistake> iter = entrycommonmistakesSet.iterator(); iter.hasNext();) {
            Entrycommonmistake entrycommonmistake = iter.next();
            entrycommonmistake.setSemanticentry(granteeSemanticEntry);
            BLUtil.daoFactory.getEntrycommonmistakeDAO().insert(entrycommonmistake);
        }
    }

    public static void grantAllLinguisticBenefits(int grantSemanticEntryId, int granteeSemanticEntryId) throws RawNotFoundException {
        Semanticentry grantSemanticEntry = SEMANTIC_ENTRY_DAO.getById(grantSemanticEntryId);
        Semanticentry granteeSemanticEntry = SEMANTIC_ENTRY_DAO.getById(granteeSemanticEntryId);

        Set<Entrylinguisticbenefit> entrylinguisticbenefitsSet = grantSemanticEntry.getEntrylinguisticbenefits();

        for (Iterator<Entrylinguisticbenefit> iter = entrylinguisticbenefitsSet.iterator(); iter.hasNext();) {
            Entrylinguisticbenefit entrylinguisticbenefit = iter.next();
            entrylinguisticbenefit.setSemanticentry(granteeSemanticEntry);
            SEMANTIC_ENTRY_DAO.insert(granteeSemanticEntry);
        }
    }

    public static int getCheckedSemEntryWeight(int semanticEntryId) throws RawNotFoundException {
        Semanticentry newSemanticentry = SEMANTIC_ENTRY_DAO.getById(semanticEntryId);
        int weight = 0;

        if ("IUD".contains(newSemanticentry.getInfoStatus())) {
            weight++;
        }
        Set<Meaning> meanings = newSemanticentry.getMeanings();
        for (Meaning meaning : meanings) {
            if ("IUD".contains(meaning.getInfoStatus())) {
                weight++;
            }
        }
        Set<Entryexample> entryExamples = newSemanticentry.getEntryexamples();
        for (Entryexample entryExample : entryExamples) {
            if ("IUD".contains(entryExample.getInfoStatus())) {
                weight++;
            }
        }
        Set<Entrycommonmistake> entryCommonmistakes = newSemanticentry.getEntrycommonmistakes();
        for (Entrycommonmistake entryCommonmistake : entryCommonmistakes) {
            if ("IUD".contains(entryCommonmistake.getInfoStatus())) {
                weight++;
            }
        }
        Set<Entrylinguisticbenefit> entryLinguisticbenefits = newSemanticentry.getEntrylinguisticbenefits();
        for (Entrylinguisticbenefit entryLinguisticbenefit : entryLinguisticbenefits) {
            if ("IUD".contains(entryLinguisticbenefit.getInfoStatus())) {
                weight++;
            }
        }
        return weight;
    }
//    public static void main(String[]str) throws RawNotFoundException
//    {
//        affirmExampleUpdating( 987 , 457 );
//    }
}
