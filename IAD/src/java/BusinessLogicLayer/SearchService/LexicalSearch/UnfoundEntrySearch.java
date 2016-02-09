/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.SearchService.LexicalSearch;

import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.Util.BLUtil;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author riad
 */
class UnfoundEntrySearch {

    static List<EntryBO> execute(String filteredWord) throws RawNotFoundException {
        List<String> stems = BLUtil.stemmer.getStem(filteredWord);
        if( stems.size() == 0){
            //correction...
            return null;
        }
        else{
            if( stems.get(0).equals(filteredWord)){
                if( stems.size()>1)
                    return SingleWordSearch.execute(stems.get(1));
                else
                    return null;
            }
            else{
                return SingleWordSearch.execute(stems.get(0));
            }
        }
    }

}
