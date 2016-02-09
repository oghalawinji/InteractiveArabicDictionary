/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.SearchService.SemanticSearch;

import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.BOManager.SemanticScopeBOManager;
import BusinessLogicLayer.BusinessObjects.SemanticScopeBO;
import BusinessLogicLayer.SearchProperties;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticrelationtype;
import PersistenceLayer.Semanticscop;
import Util.RawNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class WordNetSearch {

    public static SemanticScopeBO execute( Integer semanticEntryId){
        return execute(semanticEntryId, SearchProperties.CompleteWordNetSearchOptions);
    }

    public static SemanticScopeBO execute( Integer semanticEntryId , SearchProperties options){
        try {
            Semanticentry semanticEntryDA = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            List<Semanticrelationtype> semanticRelationsTypesDO = BLUtil.daoFactory.getSemanticrelationtypeDAO().getAll();
            return SemanticScopeBOManager.getSemanticScope(semanticEntryDA, semanticRelationsTypesDO, options);            
        } catch (RawNotFoundException ex) {
            Logger.getLogger(WordNetSearch.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public static SemanticScopeBO execute( String semanticScope , String type){
        return execute(semanticScope, type, SearchProperties.CompleteWordNetSearchOptions);
    }

    public static SemanticScopeBO execute( String semanticScope , String type, SearchProperties options){
        //type = verb | noun | particle
        List<Semanticscop> semanticScopesDA = BLUtil.daoFactory.getSemanticscopDAO().getByExample( new Semanticscop(semanticScope));
        if( semanticScopesDA.isEmpty() )
            return null;
        Semanticscop semanticScopeDO = semanticScopesDA.get(0);

        List<Semanticrelationtype> semanticRelationsTypesDO = BLUtil.daoFactory.getSemanticrelationtypeDAO().getAll();

        return SemanticScopeBOManager.getSemanticScope(semanticScopeDO, semanticRelationsTypesDO , type, options);
    }
}
