/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BusinessObjects.PluralNounBO;
import PersistenceLayer.Plural;
import PersistenceLayer.Semanticnoun;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author riad
 */
class PluralManager {

    static List<PluralNounBO> getPluralForSemanticNoun(Semanticnoun semNoun) {
        List<PluralNounBO> pluralList = new ArrayList<PluralNounBO>();
        //Set<Plural> pluralSet = semNoun.getPluralsForPluralNounId();
        Set<Plural> pluralSet = semNoun.getPluralsForSingularNounId();
        for (Iterator iter = pluralSet.iterator(); iter.hasNext();) {
            Plural pluralDA = (Plural) iter.next();
            Semanticnoun pluralNoun = pluralDA.getSemanticnounByPluralNounId();
            String plural = pluralNoun.getDerivednoun().getVocalizedNoun();
            String pluralType = pluralDA.getPluraltype().getPluralType();

            PluralNounBO pluralBO = new PluralNounBO();
            pluralBO.setPlural(plural);
            pluralBO.setPluralType(pluralType);

            pluralList.add(pluralBO);
        }
        return pluralList;
    }

}
