/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.GerundBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.GerundJPADAO;
import PersistenceLayer.Gerund;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticverb;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class GerundManager {

    private static GerundJPADAO GERUND_DAO = BLUtil.daoFactory.getGerundDAO();

    public static List<GerundBO> getGerunds(Semanticverb semVerb) {

        List<GerundBO> gerundList = new ArrayList<GerundBO>();
        Set<Gerund> gerundSet = semVerb.getGerunds();
        for (Iterator iter = gerundSet.iterator(); iter.hasNext();) {
            Gerund gerund = (Gerund) iter.next();
            SemanticNounBO newSemanticNounBO = SemanticNounBOManager.getSemanticNounBO(gerund.getSemanticnoun().getIdentity(), SearchProperties.simpleSearchOptions);
            NounBO newNounBO = NounBOManager.getNounBO(gerund.getSemanticnoun().getDerivednoun().getIdentity(), new SearchProperties(), "");
            GerundBO newGerundBO = new GerundBO();
            newGerundBO.setGerund(newNounBO);
            newGerundBO.setGerundMeaning(newSemanticNounBO);
            newGerundBO.setGerundId(gerund.getIdentity());
            newGerundBO.setStatus(gerund.getInfoStatus());
            gerundList.add(newGerundBO);
        }

        return gerundList;
    }

    public static void deleteGerund(Integer semanticVerb, String gerund) {
        try {
            Semanticverb semVerb = BLUtil.daoFactory.getSemanticverbDAO().getById(semanticVerb);
            Set<Gerund> gerundSet = semVerb.getGerunds();
            for (Iterator iter = gerundSet.iterator(); iter.hasNext();) {
                Gerund gerundRelation = (Gerund) iter.next();
                Semanticnoun gerundNoun = gerundRelation.getSemanticnoun();
                String ger = gerundNoun.getDerivednoun().getVocalizedNoun();
                if (gerund.equals(ger)) {
                    GerundJPADAO dao = BLUtil.daoFactory.getGerundDAO();
                    dao.delete(gerundRelation.getIdentity());
                }
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(GerundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void suggestDeleting(int gerundId) throws RawNotFoundException {
        Gerund newGerund = BLUtil.daoFactory.getGerundDAO().getById(gerundId);

        newGerund.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newGerund.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newGerund.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getGerundDAO().update(newGerund);
    }

    static void affirmAdding(int gerundId) throws RawNotFoundException {
        Gerund newGERUND = GERUND_DAO.getById(gerundId);
        if (newGERUND.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newGERUND.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newGERUND.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newGERUND.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newGERUND.getSuggestion());
            }
            GERUND_DAO.update(newGERUND);
        }
    }

    static void rejectAdding(int gerundId) throws RawNotFoundException {
        Gerund newGERUND = GERUND_DAO.getById(gerundId);
        if (newGERUND.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newGERUND.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newGERUND.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newGERUND.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newGERUND.getSuggestion());
            }
            GERUND_DAO.update(newGERUND);
        }
    }

    static void affirmDeleting(int gerundId) throws RawNotFoundException {
        Gerund newGERUND = GERUND_DAO.getById(gerundId);
        if (newGERUND.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newGERUND.setInfoStatus(WordStatus.NEED_DELETING);
            newGERUND.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newGERUND.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newGERUND.getSuggestion());
            }
            GERUND_DAO.update(newGERUND);
        }
    }

    static void rejectDeleting(int gerundId) throws RawNotFoundException {
        Gerund newGERUND = GERUND_DAO.getById(gerundId);
        if (newGERUND.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newGERUND.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newGERUND.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newGERUND.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newGERUND.getSuggestion());
            }
            GERUND_DAO.update(newGERUND);
        }
    }
}
