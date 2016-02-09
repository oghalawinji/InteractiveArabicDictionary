/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.SearchService.LexicalSearch;

import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.BOManager.IdiomBOManager;
import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import PersistenceLayer.Idiom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class IdiomSearch {

    static List<EntryBO> execute(String str, SearchProperties options) {
        return execute(str /*, SearchProperties.detailedSearchOptions*/);
    }

    public static List<EntryBO> execute( String enteredIdiom){

        //delete spaces after and before the string:
        String idiom = enteredIdiom.trim();
        if( idiom.equals(""))
            return new ArrayList<EntryBO>();
        //delete diacritics if exist:
        idiom = FilterDiacritics.execute(idiom);

        List<EntryBO> idiomBOList = new ArrayList<EntryBO>();
        List<Idiom> idiomList = BLUtil.daoFactory.getIdiomDAO().getIdiomsLike( idiom );
        for( int i=0; i<idiomList.size(); i++){
            IdiomBO idiomBO = IdiomBOManager.getIdiom( idiomList.get(i) );
            idiomBOList.add( idiomBO );
        }
        return idiomBOList;
    }

}
