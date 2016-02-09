
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.UpdatedExampleBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.EntryexampleJPADAO;
import DataAccessLayer.JPADAO.ExampleJPADAO;
import PersistenceLayer.Entryexample;
import PersistenceLayer.Entrysound;
import PersistenceLayer.Example;
import PersistenceLayer.Examplesound;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Sound;
import PersistenceLayer.Source;
import Util.RawNotFoundException;
import java.util.ArrayList;
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
public class ExampleBOManager {

    private SourceBOManager newSourceBOManager;
    private static ExampleJPADAO EXAMPLE_DAO = BLUtil.daoFactory.getExampleDAO();

    public ExampleBOManager() {
        newSourceBOManager = new SourceBOManager();
    }

    public SourceBOManager getNewSourceBOManager() {
        return newSourceBOManager;
    }

    public void setNewSourceBOManager(SourceBOManager newSourceBOManager) {
        this.newSourceBOManager = newSourceBOManager;
    }

    public static List<ExampleBO> getExamplesOfSemanticEntry(Integer semanticEntryId) {
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            return getExamplesOfSemanticEntry(semEntry);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ExampleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<ExampleBO> getExamplesOfSemanticEntry(Semanticentry semEntry) {
        List<ExampleBO> exampleBOList = new ArrayList<ExampleBO>();
        Set<Entryexample> exampleSet = semEntry.getEntryexamples();
        for (Iterator iter3 = exampleSet.iterator(); iter3.hasNext();) {
            Example example = ((Entryexample) iter3.next()).getExample();
            exampleBOList.add(get(example));
        }
        return exampleBOList;
    }

    public static UpdatedExampleBO get(ExampleBO exampleBO) {
        UpdatedExampleBO newExampleBO = new UpdatedExampleBO();

        try {
            Example example = EXAMPLE_DAO.getById(exampleBO.getExampleId());
            Example updatedExample = EXAMPLE_DAO.getById(example.getSuggestion().getUpdateId());
            newExampleBO.setNewExample(updatedExample.getExample());
            newExampleBO.setNewSource(updatedExample.getSource().getSource());
            newExampleBO.setNewExampleId(updatedExample.getIdentity());
            newExampleBO.setNewSound(SoundManager.getSoundsOfExample(updatedExample));
            newExampleBO.setExampleId(example.getIdentity());
            newExampleBO.setExample(example.getExample());
            newExampleBO.setSource(example.getSource().getSource());
            newExampleBO.setStatus(example.getInfoStatus());
            newExampleBO.setSounds(SoundManager.getSoundsOfExample(example));
            return newExampleBO;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ExampleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ExampleBO get(Example example) {
        if (example.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            UpdatedExampleBO newExampleBO = new UpdatedExampleBO();

            try {
                Example updatedExample = EXAMPLE_DAO.getById(example.getSuggestion().getUpdateId());
                newExampleBO.setNewExample(updatedExample.getExample());
                newExampleBO.setNewSource(updatedExample.getSource().getSource());
                newExampleBO.setNewExampleId(updatedExample.getIdentity());
                newExampleBO.setNewSound(SoundManager.getSoundsOfExample(updatedExample));
            } catch (RawNotFoundException ex) {
                Logger.getLogger(ExampleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            newExampleBO.setExampleId(example.getIdentity());
            newExampleBO.setExample(example.getExample());
            newExampleBO.setSource(example.getSource().getSource());
            newExampleBO.setStatus(example.getInfoStatus());
            newExampleBO.setSounds(SoundManager.getSoundsOfExample(example));
            return newExampleBO;

        } else {
            ExampleBO newExampleBO = new ExampleBO();
            newExampleBO.setExampleId(example.getIdentity());
            newExampleBO.setExample(example.getExample());
            newExampleBO.setSource(example.getSource().getSource());
            newExampleBO.setStatus(example.getInfoStatus());
            newExampleBO.setSounds(SoundManager.getSoundsOfExample(example));
            return newExampleBO;
        }
    }

    public static void deleteExample(Integer semanticEntryId, Integer exampleId) {
        try {
            EntryexampleJPADAO entryExampleDAO = BLUtil.daoFactory.getEntryexampleDAO();
            ExampleJPADAO exampleDAO = EXAMPLE_DAO;
            Semanticentry semanticEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            Set<Entryexample> entryExamples = semanticEntry.getEntryexamples();
            for (Iterator iter = entryExamples.iterator(); iter.hasNext();) {
                Entryexample entryExampleRelation = (Entryexample) iter.next();
                Integer id = entryExampleRelation.getExample().getIdentity();
                if (id == exampleId) {
                    entryExampleDAO.delete(entryExampleRelation.getIdentity());
                    Example example = exampleDAO.getById(exampleId);
                    if (example.getEntryexamples().size() == 0) {
                        entryExampleDAO.delete(exampleId);
                    }
                    return;
                }
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ExampleBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int suggestAddExample(String exampleText, String source, byte[] sound) {
        try {
            Example example = new Example();
            example.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            example.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            example.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            example.setExample(exampleText);

            Integer sourceId = newSourceBOManager.suggestAdding(source);
            Map restrictions = BOManagerUtil.getAddRestrictions();
            restrictions.put("eq_source.sourceId", sourceId);
            Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);

            example.setSource(sourceObj);
            int exampleId = EXAMPLE_DAO.insertWithCheck(example, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            example.setExampleId(exampleId);
            if (sound != null) {
                Sound newSound = BLUtil.daoFactory.getSoundDAO().getById(SoundManager.suggestAddSound(sound));
                Map exsRestrictions = BOManagerUtil.getAddRestrictions();
                exsRestrictions.put("eq_example.exampleId", exampleId);
                exsRestrictions.put("eq_sound.soundId", newSound.getIdentity());
                Examplesound newExamplesound = new Examplesound(example, newSound);
                newExamplesound.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newExamplesound.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newExamplesound.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
                BLUtil.daoFactory.getExamplesoundDAO().insertWithCheck(newExamplesound, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, exsRestrictions);
            }
            return exampleId;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticEntryBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static void affirmAdding(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);
        if (newExample.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newExample.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newExample.getSuggestion());
            }
            EXAMPLE_DAO.update(newExample);
        }
    }

    public static boolean affirmAddingAU(int exampleId, ExampleBO updateExampleBO) throws RawNotFoundException {
        Example oldExample = EXAMPLE_DAO.getById(exampleId);
        Example newExample = new Example(new Source(updateExampleBO.getSource()), updateExampleBO.getExample());
        if (!oldExample.equals(newExample)) {
            //if the new Example does not exist, then update old example values
            if (EXAMPLE_DAO.getByExample(newExample, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES).size() == 0) {
                oldExample.setExample(newExample.getExample());
                oldExample.setSource(newExample.getSource());
                if (oldExample.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldExample.getSuggestion());
                }
                EXAMPLE_DAO.update(newExample);
                return true;
            }
        }
        return false;
    }

    public int suggestAddExample(ExampleBO ExampleBO) {

        return this.suggestAddExample(ExampleBO.getExample(), ExampleBO.getSource(), ExampleBO.getSound());
    }

    static void rejectAdding(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);
        if (newExample.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newExample.setInfoStatus(WordStatus.NEED_DELETING);
            newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newExample.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newExample.getSuggestion());
            }
            EXAMPLE_DAO.update(newExample);
        }
    }

    public int AddTempExample(ExampleBO exampleBO) {
        try {
            Example example = new Example();
            example.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
            example.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
            example.setExample(exampleBO.getExample());

            Integer sourceId = newSourceBOManager.suggestAdding(exampleBO.getSource());
            Map restrictions = BOManagerUtil.getAddRestrictions();
            restrictions.put("eq_source.sourceId", sourceId);
            Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);

            example.setSource(sourceObj);
            int exampleId = EXAMPLE_DAO.insertWithCheck(example, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            example.setExampleId(exampleId);

            if (exampleBO.getSound() != null) {
                Sound newSound = BLUtil.daoFactory.getSoundDAO().getById(SoundManager.suggestAddSound(exampleBO.getSounds().get(0)));
                Map exsRestrictions = BOManagerUtil.getAddRestrictions();
                exsRestrictions.put("eq_example.exampleId", exampleId);
                exsRestrictions.put("eq_sound.soundId", newSound.getIdentity());

                Examplesound newExamplesound = new Examplesound(example, newSound);
                newExamplesound.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newExamplesound.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newExamplesound.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
                BLUtil.daoFactory.getExamplesoundDAO().insertWithCheck(newExamplesound, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, exsRestrictions);
            }
            return exampleId;

        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticEntryBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void suggestUpdateExample(ExampleBO newExampleBO, ExampleBO oldExampleBO) throws RawNotFoundException {
        if (!new Example(new Source(newExampleBO.getSource()), newExampleBO.getExample()).equals(new Example(new Source(oldExampleBO.getSource()), oldExampleBO.getExample()))) {
            int newExampleId = this.AddTempExample(newExampleBO);

            List<Source> sourceObjs = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(oldExampleBO.getSource()), null);
            if (sourceObjs.size() > 0) {
                //Waleed
                Source sourceObj = sourceObjs.get(0);
                Map restrictions = new HashMap();
                restrictions.put("eq_source.sourceId", sourceObj.getIdentity());
                Example oldExample = EXAMPLE_DAO.getConfirmedInstance(new Example(sourceObj, oldExampleBO.getExample()), restrictions).get(0);

                oldExample.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newExampleId));
                oldExample.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldExample.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                EXAMPLE_DAO.update(oldExample);
            }
        }

    }

    public static void affirmUpdate(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);
        if (newExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newExample.getSuggestion() != null) {
                Example tempExample = EXAMPLE_DAO.getById(newExample.getSuggestion().getUpdateId());

                Example copyOfTempExample = new Example(tempExample.getSource(), tempExample.getExample());

                tempExample.setExample(newExample.getExample());
                tempExample.setSource(newExample.getSource());
                tempExample.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                EXAMPLE_DAO.update(tempExample);

                newExample.setExample(copyOfTempExample.getExample());
                newExample.setSource(copyOfTempExample.getSource());
                newExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION(newExample.getSuggestion());
                EXAMPLE_DAO.update(newExample);
            }
        }
    }

    public static boolean affirmUpdatingAU(int oldExampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Example oldExample = EXAMPLE_DAO.getById(oldExampleId);
        int newSourceId = SourceBOManager.suggestAdding(newExampleBO.getSource());
        Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
        Example newExample = new Example(newSource, newExampleBO.getExample());
        Map restrictions = new HashMap();
        restrictions.put("eq_source.sourceId", newSourceId);
        if (EXAMPLE_DAO.getConfirmedInstance(newExample, restrictions).size() == 0) {
            if (oldExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldExample.getSuggestion() != null) {
                    Example tempExample = EXAMPLE_DAO.getById(oldExample.getSuggestion().getUpdateId());

                    tempExample.setExample(oldExample.getExample());
                    tempExample.setSource(oldExample.getSource());
                    tempExample.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    EXAMPLE_DAO.update(tempExample);

                    oldExample.setExample(newExample.getExample());
                    oldExample.setSource(newSource);
                    oldExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldExample.getSuggestion());
                    EXAMPLE_DAO.update(oldExample);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectUpdating(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);
        if (newExample.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newExample.getSuggestion() != null) {
                Example tempExample = EXAMPLE_DAO.getById(newExample.getSuggestion().getUpdateId());
                tempExample.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                EXAMPLE_DAO.update(tempExample);

                newExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.REJECT_SUGGESTION(newExample.getSuggestion());
                EXAMPLE_DAO.update(newExample);
            }
        }
    }

    public void suggestDeleteExample(ExampleBO deletedExampleBO) throws RawNotFoundException {

        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(deletedExampleBO.getSource()), null).get(0);
        Map restrictions0 = new HashMap();
        restrictions0.put("eq_source.sourceId", sourceObj.getIdentity());

        Example example = EXAMPLE_DAO.getConfirmedInstance(new Example(sourceObj, deletedExampleBO.getExample()), restrictions0).get(0);
        Map restrictions1 = new HashMap();
        restrictions1.put("eq_example.exampleId", example.getIdentity());

        example.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        example.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        example.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        EXAMPLE_DAO.update(example);

        if (deletedExampleBO.getSound() != null) {
            Sound newSound = BLUtil.daoFactory.getSoundDAO().getConfirmedInstance(new Sound(deletedExampleBO.getSound()), null).get(0);

            Examplesound newExamplesound = BLUtil.daoFactory.getExamplesoundDAO().getConfirmedInstance(new Examplesound(example, newSound), restrictions1).get(0);

            //get all example sound relations which have the same sound (newSound)
            Map restrictions2 = new HashMap();
            restrictions2.put("eq_sound.soundId", newSound.getIdentity());
            List<Examplesound> exampleSoundList = BLUtil.daoFactory.getExamplesoundDAO().getByExample(newExamplesound, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions2);

            //get all entry sound relations which have the same sound (newSound)
            List<Entrysound> entrySoundList = BLUtil.daoFactory.getEntrysoundDAO().getByExample(new Entrysound(null, newSound), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions2);

            newExamplesound.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
            newExamplesound.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
            newExamplesound.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());
            BLUtil.daoFactory.getExamplesoundDAO().update(newExamplesound);

            if (exampleSoundList.size() <= 1 && entrySoundList.size() == 0)//if there is no other example or entry relate to newSound then suggest deleting newSound
            {
                newSound.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
                newSound.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
                newSound.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());
                BLUtil.daoFactory.getSoundDAO().update(newSound);
            }
        }
    }

    public static void affirmDeleting(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);

        if (newExample.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newExample.setInfoStatus(WordStatus.NEED_DELETING);
            newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newExample.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newExample.getSuggestion());
            }
            EXAMPLE_DAO.update(newExample);
        }
    }

    public static void rejectDeleting(int exampleId) throws RawNotFoundException {
        Example newExample = EXAMPLE_DAO.getById(exampleId);

        if (newExample.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newExample.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newExample.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newExample.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newExample.getSuggestion());
            }
            EXAMPLE_DAO.update(newExample);
        }
    }
}
