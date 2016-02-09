/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.ProperAdjectiveBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.ProperadjectiveJPADAO;
import PersistenceLayer.Properadjective;
import PersistenceLayer.Semanticnoun;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author riad
 */
public class ProperAdjectiveManager {

    private static ProperadjectiveJPADAO PRADJ_DAO = BLUtil.daoFactory.getProperadjectiveDAO();

    public static List<ProperAdjectiveBO> getProperAdjectiveForSemanticNoun(Semanticnoun semNoun) throws RawNotFoundException {
        List<ProperAdjectiveBO> properAdjectiveList = new ArrayList<ProperAdjectiveBO>();
        Set<Properadjective> properAdjectiveSet = semNoun.getProperadjectivesForNounId();
        for (Iterator iter = properAdjectiveSet.iterator(); iter.hasNext();) {
            Properadjective newProperAdjective = (Properadjective) iter.next();
            Semanticnoun adjNoun = newProperAdjective.getSemanticProperAdjective();
            ProperAdjectiveBO newProperAdjectiveBO = new ProperAdjectiveBO();
            newProperAdjectiveBO.setProperAdjectiveMeaning(SemanticNounBOManager.getSemanticNounBO(adjNoun));
            newProperAdjectiveBO.setProperAdjective(NounBOManager.getNounBO(adjNoun.getDerivednoun().getIdentity(), ""));
            newProperAdjectiveBO.setProperAdjectiveId(newProperAdjective.getIdentity());
            newProperAdjectiveBO.setStatus(newProperAdjective.getInfoStatus());
            properAdjectiveList.add(newProperAdjectiveBO);
        }
        return properAdjectiveList;
    }

    public static void suggestDeleting(int properAdjectiveId) throws RawNotFoundException {
        Properadjective newProperAdjective = BLUtil.daoFactory.getProperadjectiveDAO().getById(properAdjectiveId);

        newProperAdjective.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newProperAdjective.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newProperAdjective.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getProperadjectiveDAO().update(newProperAdjective);
    }

    static void affirmAdding(int properAdjectiveId) throws RawNotFoundException {
        Properadjective newPRADJ = PRADJ_DAO.getById(properAdjectiveId);
        if (newPRADJ.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newPRADJ.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newPRADJ.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newPRADJ.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newPRADJ.getSuggestion());
            }
            PRADJ_DAO.update(newPRADJ);
        }
    }

    static void rejectAdding(int properAdjectiveId) throws RawNotFoundException {
        Properadjective newPRADJ = PRADJ_DAO.getById(properAdjectiveId);
        if (newPRADJ.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newPRADJ.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newPRADJ.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newPRADJ.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newPRADJ.getSuggestion());
            }
            PRADJ_DAO.update(newPRADJ);
        }
    }

    static void affirmDeleting(int properAdjectiveId) throws RawNotFoundException {
        Properadjective newPRADJ = PRADJ_DAO.getById(properAdjectiveId);
        if (newPRADJ.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newPRADJ.setInfoStatus(WordStatus.NEED_DELETING);
            newPRADJ.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newPRADJ.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newPRADJ.getSuggestion());
            }
            PRADJ_DAO.update(newPRADJ);
        }
    }

    static void rejectDeleting(int properAdjectiveId) throws RawNotFoundException {
        Properadjective newPRADJ = PRADJ_DAO.getById(properAdjectiveId);
        if (newPRADJ.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newPRADJ.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newPRADJ.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newPRADJ.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newPRADJ.getSuggestion());
            }
            PRADJ_DAO.update(newPRADJ);
        }
    }
}
