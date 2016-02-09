/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.NounAccompanierBO;
import BusinessLogicLayer.BusinessObjects.VerbAccompanierBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.NounverbaccompanierJPADAO;
import PersistenceLayer.Nounverbaccompanier;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticverb;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author riad
 */
public class NounAccompanierBOManager {

    private static NounverbaccompanierJPADAO NVA_DAO = BLUtil.daoFactory.getNounverbaccompanierDAO();

    static List<NounAccompanierBO> getNounAccompaniersForSemanticVerb(Semanticverb semVerb) {
        List<NounAccompanierBO> nounList = new ArrayList<NounAccompanierBO>();
        Set<Nounverbaccompanier> nounVerbAccompanierSet = semVerb.getNounverbaccompaniers();
        for (Iterator iter = nounVerbAccompanierSet.iterator(); iter.hasNext();) {
            Nounverbaccompanier newNounverbaccompanier = (Nounverbaccompanier) iter.next();
            Semanticnoun semNoun = newNounverbaccompanier.getSemanticnoun();
            NounAccompanierBO newNounAccompanierBO = new NounAccompanierBO();
            newNounAccompanierBO.setAccompanierNounMeaning(SemanticNounBOManager.getSemanticNounBO(semNoun.getIdentity(), SearchProperties.simpleSearchOptions));
            newNounAccompanierBO.setAccompanierNoun(NounBOManager.getNounBO(semNoun.getDerivednoun().getIdentity(), new SearchProperties(), ""));
            newNounAccompanierBO.setStatus(newNounverbaccompanier.getInfoStatus());
            newNounAccompanierBO.setNounAccompanierId(newNounverbaccompanier.getIdentity());
            nounList.add(newNounAccompanierBO);
        }
        return nounList;
    }

    static List<VerbAccompanierBO> getVerbAccompaniersForSemanticNoun(Semanticnoun semNoun) throws RawNotFoundException {
        List<VerbAccompanierBO> verbList = new ArrayList<VerbAccompanierBO>();
        Set<Nounverbaccompanier> nounVerbAccompanierSet = semNoun.getNounverbaccompaniers();
        for (Iterator iter = nounVerbAccompanierSet.iterator(); iter.hasNext();) {
            Nounverbaccompanier newNounverbaccompanier = (Nounverbaccompanier) iter.next();
            Semanticverb semVeb = newNounverbaccompanier.getSemanticverb();
            VerbAccompanierBO newVerbAccompanierBO = new VerbAccompanierBO();
            newVerbAccompanierBO.setVerbAccompanierMeaning(SemanticVerbBOManager.getSemanticVerbBO(semVeb.getIdentity(), SearchProperties.simpleSearchOptions));
            newVerbAccompanierBO.setVerbAccompanier(VerbBOManager.get(semVeb.getContextualverb().getDerivedverb().getIdentity(), new SearchProperties(), ""));
            newVerbAccompanierBO.setStatus(newNounverbaccompanier.getInfoStatus());
            newVerbAccompanierBO.setVerbAccompanierId(newNounverbaccompanier.getIdentity());
            verbList.add(newVerbAccompanierBO);
        }
        return verbList;
    }

    public static void suggestDeleting(int nounAccompanierId) throws RawNotFoundException {
        Nounverbaccompanier newNounverbaccompanier = BLUtil.daoFactory.getNounverbaccompanierDAO().getById(nounAccompanierId);

        newNounverbaccompanier.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newNounverbaccompanier.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newNounverbaccompanier.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        NVA_DAO.update(newNounverbaccompanier);
    }

    static void affirmAdding(int nounAccompanierId) throws RawNotFoundException {
        Nounverbaccompanier newNVA = NVA_DAO.getById(nounAccompanierId);
        if (newNVA.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newNVA.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newNVA.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newNVA.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newNVA.getSuggestion());
            }
            NVA_DAO.update(newNVA);
        }
    }

    static void rejectAdding(int nounAccompanierId) throws RawNotFoundException {
        Nounverbaccompanier newNVA = NVA_DAO.getById(nounAccompanierId);
        if (newNVA.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newNVA.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newNVA.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newNVA.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newNVA.getSuggestion());
            }
            NVA_DAO.update(newNVA);
        }
    }

    static void affirmDeleting(int nounAccompanierId) throws RawNotFoundException {
        Nounverbaccompanier newNVA = NVA_DAO.getById(nounAccompanierId);
        if (newNVA.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newNVA.setInfoStatus(WordStatus.NEED_DELETING);
            newNVA.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newNVA.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newNVA.getSuggestion());
            }
            NVA_DAO.update(newNVA);
        }
    }

    static void rejectDeleting(int nounAccompanierId) throws RawNotFoundException {
        Nounverbaccompanier newNVA = NVA_DAO.getById(nounAccompanierId);
        if (newNVA.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newNVA.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newNVA.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newNVA.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newNVA.getSuggestion());
            }
            NVA_DAO.update(newNVA);
        }
    }
}
