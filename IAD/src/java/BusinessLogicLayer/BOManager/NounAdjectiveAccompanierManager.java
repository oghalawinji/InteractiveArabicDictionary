/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.AdjectiveAccompanierBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.NounadjectiveaccompanierJPADAO;
import PersistenceLayer.Nounadjectiveaccompanier;
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
public class NounAdjectiveAccompanierManager {

    private static NounadjectiveaccompanierJPADAO ADJACC_DAO = BLUtil.daoFactory.getNounadjectiveaccompanierDAO();

    public static List<AdjectiveAccompanierBO> getAdjectiveAccompaniersForSemanticNoun(Semanticnoun semNoun) throws RawNotFoundException {
        List<AdjectiveAccompanierBO> adjectiveList = new ArrayList<AdjectiveAccompanierBO>();
        Set<Nounadjectiveaccompanier> nounAdjAccompanierSet = semNoun.getNounadjectiveaccompaniersForNounId();
        for (Iterator iter = nounAdjAccompanierSet.iterator(); iter.hasNext();) {
            Nounadjectiveaccompanier newNounadjectiveaccompanier = (Nounadjectiveaccompanier) iter.next();
            AdjectiveAccompanierBO newAdjectiveAccompanierBO = new AdjectiveAccompanierBO();
            Semanticnoun semAdj = newNounadjectiveaccompanier.getsemanticAdjective();
            newAdjectiveAccompanierBO.setAdjectiveAccompanierMeaning(SemanticNounBOManager.getSemanticNounBO(semAdj));
            newAdjectiveAccompanierBO.setAdjectiveAccompanier(NounBOManager.getNounBO(semAdj.getDerivednoun().getIdentity(), ""));
            newAdjectiveAccompanierBO.setAdjectiveAccompanierId(newNounadjectiveaccompanier.getIdentity());
            newAdjectiveAccompanierBO.setStatus(newNounadjectiveaccompanier.getInfoStatus());
            adjectiveList.add(newAdjectiveAccompanierBO);
        }
        return adjectiveList;
    }

    public static void suggestDeleting(int adjectiveAccompanierId) throws RawNotFoundException {
        Nounadjectiveaccompanier newNounadjectiveaccompanier = BLUtil.daoFactory.getNounadjectiveaccompanierDAO().getById(adjectiveAccompanierId);

        newNounadjectiveaccompanier.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newNounadjectiveaccompanier.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newNounadjectiveaccompanier.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getNounadjectiveaccompanierDAO().update(newNounadjectiveaccompanier);
    }

    static void affirmAdding(int nounAdjectiveAccompanierId) throws RawNotFoundException {
        Nounadjectiveaccompanier newADJACC = ADJACC_DAO.getById(nounAdjectiveAccompanierId);
        if (newADJACC.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newADJACC.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newADJACC.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newADJACC.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newADJACC.getSuggestion());
            }
            ADJACC_DAO.update(newADJACC);
        }
    }

    static void rejectAdding(int nounAdjectiveAccompanierId) throws RawNotFoundException {
        Nounadjectiveaccompanier newADJACC = ADJACC_DAO.getById(nounAdjectiveAccompanierId);
        if (newADJACC.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newADJACC.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newADJACC.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newADJACC.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newADJACC.getSuggestion());
            }
            ADJACC_DAO.update(newADJACC);
        }
    }

    static void affirmDeleting(int nounAdjectiveAccompanierId) throws RawNotFoundException {
        Nounadjectiveaccompanier newADJACC = ADJACC_DAO.getById(nounAdjectiveAccompanierId);
        if (newADJACC.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newADJACC.setInfoStatus(WordStatus.NEED_DELETING);
            newADJACC.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newADJACC.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newADJACC.getSuggestion());
            }
            ADJACC_DAO.update(newADJACC);
        }
    }

    static void rejectDeleting(int nounAdjectiveAccompanierId) throws RawNotFoundException {
        Nounadjectiveaccompanier newADJACC = ADJACC_DAO.getById(nounAdjectiveAccompanierId);
        if (newADJACC.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newADJACC.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newADJACC.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newADJACC.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newADJACC.getSuggestion());
            }
            ADJACC_DAO.update(newADJACC);
        }
    }
}
