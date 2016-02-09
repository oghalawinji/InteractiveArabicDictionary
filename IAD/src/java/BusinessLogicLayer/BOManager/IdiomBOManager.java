/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.BusinessObjects.UpdatedIdiomBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.FilterDiacritics;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.IdiomJPADAO;
import DataAccessLayer.JPADAO.RelatedidiomJPADAO;
import PersistenceLayer.Idiom;
import PersistenceLayer.Relatedidiom;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
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
public class IdiomBOManager {

    public static IdiomBO getIdiom(Idiom idiom) {
        return getIdiom(idiom, SearchProperties.detailedSearchOptions);
    }

    public static IdiomBO getIdiom(int idiomId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);
        return getIdiom(newIdiom);
    }

    public static boolean hasNewValues(int idiomId) throws RawNotFoundException {
        Idiom oldIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);
        return hasNewValues(oldIdiom);
    }

    public static boolean hasNewValues(Idiom oldIdiom) {
        if (oldIdiom.getInfoStatus().equals("U")) {
            Suggestion updateSuggestion = oldIdiom.getSuggestion();
            if (updateSuggestion != null && !(updateSuggestion.getAffirmStatus().equals("A")) && updateSuggestion.getInfoStatus().equals("U")) {
                return true;
            }
        }
        return false;
    }

    public static IdiomBO getIdiom(Idiom idiom, SearchProperties options) {
        if (idiom.getInfoStatus().equals("U") && hasNewValues(idiom)) {
            UpdatedIdiomBO idiomBO = new UpdatedIdiomBO();
            idiomBO.setIdiomId(idiom.getIdentity());
            idiomBO.setIdiom(idiom.getIdiom());
            idiomBO.setVocalizedIdiom(idiom.getVocalizedIdiom());
            idiomBO.setStatus(idiom.getInfoStatus());

            try {
                Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiom.getSuggestion().getUpdateId());

                idiomBO.setNewIdiom(newIdiom.getIdiom());
                idiomBO.setNewIdiomId(newIdiom.getIdentity());
            } catch (RawNotFoundException ex) {
                Logger.getLogger(IdiomBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            idiomBO.setNewIdiom(null);
            //find semantic entry:
            if (options.FindSemanticInformations && idiom.getSemanticentry() != null) {
                SemanticEntryBO semanticCase = SemanticEntryBOManager.getSemanticEntryBO(idiom.getSemanticentry(), options);
                idiomBO.setSemanticCase(semanticCase);
            }
            return idiomBO;
        } else {
            IdiomBO idiomBO = new IdiomBO();
            idiomBO.setIdiomId(idiom.getIdentity());
            idiomBO.setIdiom(idiom.getIdiom());
            idiomBO.setVocalizedIdiom(idiom.getVocalizedIdiom());
            idiomBO.setStatus(idiom.getInfoStatus());
            //find semantic entry:
            if (options.FindSemanticInformations && idiom.getSemanticentry() != null) {
                SemanticEntryBO semanticCase = SemanticEntryBOManager.getSemanticEntryBO(idiom.getSemanticentry(), options);
                idiomBO.setSemanticCase(semanticCase);
            }
            return idiomBO;
        }
    }

    public static List<String> getRelatedIdiom(Semanticentry semEntry) {
        List<String> idiomList = new ArrayList<String>();
        Set<Relatedidiom> relatedIdiomSet = semEntry.getRelatedidioms();
        for (Iterator iter3 = relatedIdiomSet.iterator(); iter3.hasNext();) {
            String idiom = ((Relatedidiom) iter3.next()).getIdiom().getVocalizedIdiom();
            idiomList.add(idiom);
        }
        return idiomList;
    }

    public static void deleteIdiomRelation(Integer semanticEntryId, String idiom) {
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            Set<Relatedidiom> relatedIdiomSet = semEntry.getRelatedidioms();
            for (Iterator iter3 = relatedIdiomSet.iterator(); iter3.hasNext();) {
                Relatedidiom idiomRelation = (Relatedidiom) iter3.next();
                String idi = ((Relatedidiom) iter3.next()).getIdiom().getVocalizedIdiom();
                if (idiom.equals(idi)) {
                    RelatedidiomJPADAO dao = BLUtil.daoFactory.getRelatedidiomDAO();
                    dao.delete(idiomRelation.getIdentity());
                    return;
                }
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(IdiomBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteIdiom(Integer idiomId) {
        try {
            IdiomJPADAO idiomDao = BLUtil.daoFactory.getIdiomDAO();
            RelatedidiomJPADAO relationDAO = BLUtil.daoFactory.getRelatedidiomDAO();

            Idiom idiom = idiomDao.getById(idiomId);
            Set<Relatedidiom> relations = idiom.getRelatedidioms();
            Integer semEntryId = idiom.getSemanticentry().getIdentity();

            //delete relations with other words:
            for (Iterator iter = relations.iterator(); iter.hasNext();) {
                Relatedidiom related = (Relatedidiom) iter.next();
                relationDAO.delete(related.getIdentity());
            }
            //delete idiom's semanticEntry:
            SemanticEntryBOManager.deleteSemanticEntry(semEntryId);

            //delete idiom itself:
            idiomDao.delete(idiomId);

        } catch (RawNotFoundException ex) {
            Logger.getLogger(IdiomBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int suggestAdding(IdiomBO newIdiom) throws RawNotFoundException, EntryExistedException {
        String rawIdiom = FilterDiacritics.execute(newIdiom.getVocalizedIdiom());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        Idiom idiom = new Idiom();

        idiom.setVocalizedIdiom(newIdiom.getVocalizedIdiom());
        idiom.setIdiom(rawIdiom);

        idiom.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        idiom.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        idiom.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        int id = BLUtil.daoFactory.getIdiomDAO().insertWithCheck(idiom, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions, "التركيب موجود مسبقاً!");
        return id;
    }

    public void suggestUpdating(Integer oldIdiomId, IdiomBO newIdiom) throws RawNotFoundException {
        int tempIdiomId = this.addTempIdiom(newIdiom);

        Idiom oldIdiom = BLUtil.daoFactory.getIdiomDAO().getById(oldIdiomId);

        oldIdiom.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
        oldIdiom.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
        oldIdiom.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempIdiomId));

        BLUtil.daoFactory.getIdiomDAO().update(oldIdiom);
    }

    public int addTempIdiom(IdiomBO newIdiomBO) throws RawNotFoundException {
        String rawIdiom = FilterDiacritics.execute(newIdiomBO.getVocalizedIdiom());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        Idiom idiom = new Idiom();

        idiom.setVocalizedIdiom(newIdiomBO.getVocalizedIdiom());
        idiom.setIdiom(rawIdiom);

        idiom.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        idiom.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());

        int id = BLUtil.daoFactory.getIdiomDAO().insertWithCheck(idiom, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions);
        return id;
    }

    public void suggestDeleting(Integer idiomId) throws RawNotFoundException {
        Idiom idiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);

        idiom.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        idiom.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        idiom.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getIdiomDAO().update(idiom);
    }

    public IdiomBO getIdiomBO(int idiomId, SearchProperties simpleSearchOptions) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);

        return IdiomBOManager.getIdiom(newIdiom, simpleSearchOptions);
    }

    public static void setNeedCheck(int idiomId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);

        newIdiom.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public static List<IdiomBO> getNeedCheck() throws RawNotFoundException {
        List<Idiom> idioms = BLUtil.daoFactory.getIdiomDAO().getNeedCheck();
        List<IdiomBO> needCheckIdioms = new ArrayList<IdiomBO>();
        for (Idiom idiom : idioms) {
            needCheckIdioms.add(IdiomBOManager.getIdiom(idiom, new SearchProperties()));
        }
        return needCheckIdioms;
    }

    public static void affirmAdding(int derivedIdiomId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedIdiomId);
        newIdiom.setInfoStatus("S");
        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public static boolean affirmAddingAU(int derivedIdiomId, IdiomBO updatedIdiom) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedIdiomId);
        String rawIdiom = FilterDiacritics.execute(updatedIdiom.getVocalizedIdiom());

        if (BLUtil.daoFactory.getIdiomDAO().getByExample(new Idiom(null, rawIdiom, updatedIdiom.getVocalizedIdiom()), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, null).size() > 0) {
            return false;
        } else {
            newIdiom.setVocalizedIdiom(updatedIdiom.getVocalizedIdiom());
            newIdiom.setIdiom(rawIdiom);
            //newIdiom.setPronunciation (new Pronunciation ());
            newIdiom.setInfoStatus(BOManagerUtil.CONFIRM_STATUS.getInfoStatus());

            BOManagerUtil.AFFIRM_SUGGESTION(newIdiom.getSuggestion());
            BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
            return true;
        }
    }

    public static void clearCheck(int derivedIdiomId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedIdiomId);
        newIdiom.setChechStatus(0);
        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public static void rejectAdding(int idiomId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);
        SemanticIdiomBOManager.rejectAdding(newIdiom.getSemanticentry().getIdentity());
        BOManagerUtil.REJECT_SUGGESTION(newIdiom.getSuggestion());
        newIdiom.setInfoStatus("R");
        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public static void affirmUpdating(int idiomId) throws RawNotFoundException {
        Idiom oldIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);
        Suggestion updateSuggestion = oldIdiom.getSuggestion();
        // get update idiom id from old idiom suggestion.
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(updateSuggestion.getUpdateId());
        Idiom copyOfNewIdiom = new Idiom(null, newIdiom.getIdiom(), newIdiom.getVocalizedIdiom());
        // update idiom will store old idiom values before updates.
        newIdiom.setIdiom(oldIdiom.getIdiom());
        newIdiom.setVocalizedIdiom(oldIdiom.getVocalizedIdiom());
        newIdiom.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original idiom.
        oldIdiom.setIdiom(copyOfNewIdiom.getIdiom());
        oldIdiom.setVocalizedIdiom(copyOfNewIdiom.getVocalizedIdiom());
        oldIdiom.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION(oldIdiom.getSuggestion());

        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
        BLUtil.daoFactory.getIdiomDAO().update(oldIdiom);
    }

    private static void affirmUpdatingAfterAltering(Idiom oldIdiom) throws RawNotFoundException {
        Suggestion updateSuggestion = oldIdiom.getSuggestion();
        // get update idiom id from old idiom suggestion.
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(updateSuggestion.getUpdateId());
        Idiom copyOfNewIdiom = new Idiom(null, newIdiom.getIdiom(), newIdiom.getVocalizedIdiom());
        // update idiom will store old idiom values before updates.
        newIdiom.setIdiom(oldIdiom.getIdiom());
        newIdiom.setVocalizedIdiom(oldIdiom.getVocalizedIdiom());
        newIdiom.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original idiom.
        oldIdiom.setIdiom(copyOfNewIdiom.getIdiom());
        oldIdiom.setVocalizedIdiom(copyOfNewIdiom.getVocalizedIdiom());
        oldIdiom.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldIdiom.getSuggestion());

        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
        BLUtil.daoFactory.getIdiomDAO().update(oldIdiom);
    }

    public static void rejectUpdating(int derivedIdiomId) throws RawNotFoundException {
        Idiom originalDerivedidiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedIdiomId);
        Idiom updatedDerivedidiom = BLUtil.daoFactory.getIdiomDAO().getById(originalDerivedidiom.getSuggestion().getUpdateId());

        originalDerivedidiom.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(originalDerivedidiom.getSuggestion());
        BLUtil.daoFactory.getIdiomDAO().update(originalDerivedidiom);

        updatedDerivedidiom.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        BLUtil.daoFactory.getIdiomDAO().update(updatedDerivedidiom);
    }

    public static boolean affirmUpdatingAU(int derivedIdiomId, IdiomBO updateIdiom) throws RawNotFoundException {
        Idiom originalIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedIdiomId);
        Idiom updatedIdiom = BLUtil.daoFactory.getIdiomDAO().getById(originalIdiom.getSuggestion().getUpdateId());
        String rawIdiom = FilterDiacritics.execute(updateIdiom.getVocalizedIdiom());

        // if the updated idiom  already exists in the database then return false and the expert can decide what to do.
        if (BLUtil.daoFactory.getIdiomDAO().getConfirmedInstance(new Idiom(null, rawIdiom, updateIdiom.getVocalizedIdiom()), null).size() > 0) {
            //System.out.println( "the idiom already exists in the database....");
            return false;
        } else {
            updatedIdiom.setIdiom(rawIdiom);
            updatedIdiom.setVocalizedIdiom(updateIdiom.getVocalizedIdiom());
            BLUtil.daoFactory.getIdiomDAO().update(updatedIdiom);
            affirmUpdatingAfterAltering(originalIdiom);
            return true;
        }
    }

    public static void affirmDeleting(int derivedNounId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedNounId);
        SemanticIdiomBOManager.affirmDeleting(newIdiom.getSemanticentry().getIdentity());
        newIdiom.setInfoStatus(WordStatus.NEED_DELETING);
        newIdiom.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newIdiom.getSuggestion());
        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public static void rejectDeleting(int derivedVerbId) throws RawNotFoundException {
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(derivedVerbId);
        BOManagerUtil.REJECT_SUGGESTION(newIdiom.getSuggestion());
        newIdiom.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BLUtil.daoFactory.getIdiomDAO().update(newIdiom);
    }

    public int getCheckedIdiomWeight(int idiomId) throws RawNotFoundException {
        int weight = 0;
        Idiom newIdiom = BLUtil.daoFactory.getIdiomDAO().getById(idiomId);
        if (!newIdiom.getInfoStatus().equals("S")) {
            weight++;
        }
        Semanticentry semIdiom = newIdiom.getSemanticentry();
        if (!semIdiom.getInfoStatus().equals("S")) {
            weight++;
        }
        return weight;
    }

    public static void main(String[] arg) {
        try {
            User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
            BOManagerUtil.setCurrentUser(currUser);
            /**
             * *
             * test needCheck()
             */
            /*List<IdiomBO> needCheckIdioms = getNeedCheck();
             for ( IdiomBO idiom : needCheckIdioms )
             {
             System.out.print( "[" + idiom.getIdiomId() + "] , " );
             }*/
            /**
             * test affirmAddingAU()
             */
            /*IdiomBO newIdiomBO = new IdiomBO();
             newIdiomBO.setVocalizedIdiom( "testAAU" );
             affirmAddingAU( 267 , newIdiomBO );*/
            /**
             * test affirmUpdating()
             */
            //affirmUpdating( 2 );
            /**
             * test rejectUpdating()
             */
            //rejectUpdating( 3 );
            /**
             * test affirmUpdateAU()
             */
            /*IdiomBO newIdiomBO = new IdiomBO();
             newIdiomBO.setVocalizedIdiom( "testUAU" );
             affirmUpdatingAU( 4 , newIdiomBO );*/
            rejectDeleting(6);

        } catch (Exception ex) {
            Logger.getLogger(IdiomBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
