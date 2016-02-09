package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.BusinessObjects.SemanticRelationBO;
import BusinessLogicLayer.BusinessObjects.SemanticScopeBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SemanticscopJPADAO;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticrelation;
import PersistenceLayer.Semanticrelationtype;
import PersistenceLayer.Semanticscop;
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
public class SemanticScopeBOManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        SemanticscopJPADAO dao = BLUtil.daoFactory.getSemanticscopDAO();
        List<Semanticscop> transObjs = dao.getAll();
        for (int i = 0; i < transObjs.size(); i++) {
            String trans = transObjs.get(i).getSemanticScop();
            results.add(trans);
        }
        return results;
    }

    //maxElementsSize: max elements for each semantic scope; -1 if you want all elements
    public static SemanticScopeBO getSemanticScope(Semanticentry semanticEntry, List<Semanticrelationtype> semanticRelationsTypesDO) {
        return getSemanticScope(semanticEntry, semanticRelationsTypesDO, SearchProperties.CompleteWordNetSearchOptions);
    }

    public static SemanticScopeBO getSemanticScope(Semanticentry semanticEntry, List<Semanticrelationtype> semanticRelationsTypesDO, SearchProperties options) {
        Semanticscop semanticScopeDO = semanticEntry.getSemanticscop();
        String type = SemanticEntryBOManager.getType(semanticEntry);
        return getSemanticScope(semanticScopeDO, semanticRelationsTypesDO, type, options);
    }

    public static SemanticScopeBO getSemanticScope(Semanticscop semanticScopeDO, List<Semanticrelationtype> semanticRelationsTypesDO, String type) {
        return getSemanticScope(semanticScopeDO, semanticRelationsTypesDO, type, SearchProperties.CompleteWordNetSearchOptions);
    }

    public static SemanticScopeBO getSemanticScope(Semanticscop semanticScopeDO, List<Semanticrelationtype> semanticRelationsTypesDO, String type, SearchProperties options) {

        SemanticScopeBO semanticScopeBO = new SemanticScopeBO();
        semanticScopeBO.setSemanticScopeId(semanticScopeDO.getIdentity());
        semanticScopeBO.setSemanticScopeTitle(semanticScopeDO.getSemanticScop());

        //get Semantic Scope elements:
        if (options.FindSemanticScopeElements) {
            Set<Semanticentry> semEntries = semanticScopeDO.getSemanticentries();
            List<String> elements = new ArrayList<String>();
            if (!semanticScopeBO.getSemanticScopeTitle().equals("عام")) {
                for (Iterator iter = semEntries.iterator(); iter.hasNext();) {
                    Semanticentry semEntry = (Semanticentry) iter.next();
                    String word = SemanticEntryBOManager.getWordStr(semEntry, type);
                    if (!elements.contains(word) && word != null) {
                        elements.add(word);
                    }
                    if (options.MaxElementsInSemanticScope >= 0) {
                        if (elements.size() > options.MaxElementsInSemanticScope) {
                            break;
                        }
                    }
                }
            } else {
                elements.add("عذراً،لم يتم ربط هذا المدخل دلالياً حتى الآن...");
            }
            semanticScopeBO.setElements(elements);
        }

        //set Semantic Relation:
        if (options.FindSemanticScopeRelations) {
            if (semanticRelationsTypesDO != null) {
                List<SemanticRelationBO> semanticRelations = new ArrayList<SemanticRelationBO>();
                if (!semanticScopeBO.getSemanticScopeTitle().equals("عام")) {
                    for (int i = 0; i < semanticRelationsTypesDO.size(); i++) {
                        Semanticrelationtype relationTypeDO = semanticRelationsTypesDO.get(i);
                        String relationType = relationTypeDO.getSemanticRelationType();

                        Semanticrelation semRelExample = new Semanticrelation();
                        semRelExample.setSemanticrelationtype(relationTypeDO);
                        semRelExample.setSemanticscopByFirstSemanticScopId(semanticScopeDO);
                        List<Semanticrelation> semanticRelationDO = BLUtil.daoFactory.getSemanticrelationDAO().getByExample(semRelExample);
                        List<SemanticScopeBO> relatedSemScopes = new ArrayList<SemanticScopeBO>();
                        for (int j = 0; j < semanticRelationDO.size(); j++) {
                            Semanticscop semScope = semanticRelationDO.get(j).getSemanticscopBySecondSemanticScopId();
                            SemanticScopeBO semScopeBO = SemanticScopeBOManager.getSemanticScope(semScope, null, type, options);
                            relatedSemScopes.add(semScopeBO);
                        }

                        SemanticRelationBO relationBO = new SemanticRelationBO();
                        relationBO.setRelationName(relationType);
                        relationBO.setSemanticScope(relatedSemScopes);

                        semanticRelations.add(relationBO);
                    }
                }
                semanticScopeBO.setSemanticRelation(semanticRelations);
            }
        }
        return semanticScopeBO;
    }

    public String get(Integer id, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String get(Semanticscop dataAccessObject, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Semanticscop dataAccessObject) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmDeleting(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestUpdating(Integer Id, String newObj) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmUpdating(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int suggestAdding(String semanticScope) {
        try {
            SemanticscopJPADAO dao = BLUtil.daoFactory.getSemanticscopDAO();
            Semanticscop newSemanticscop = new Semanticscop(semanticScope);
            newSemanticscop.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newSemanticscop.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newSemanticscop.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getSemanticscopDAO().insertWithCheck(newSemanticscop, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticScopeBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    static int add(String semanticScope) {
        try {
            SemanticscopJPADAO dao = BLUtil.daoFactory.getSemanticscopDAO();
            Semanticscop newSemanticscop = new Semanticscop(semanticScope);
            newSemanticscop.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            newSemanticscop.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newSemanticscop.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getSemanticscopDAO().insertWithCheck(newSemanticscop, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticScopeBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean affirmAddinging(String attribute) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
