/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util;

import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import PersistenceLayer.Derivednoun;
import PersistenceLayer.Derivedparticle;
import PersistenceLayer.Derivedverb;


/**
 *
 * @author riad
 */
public class CompareWordsBO implements java.util.Comparator {
    public int compareDerivednoun(Derivednoun first, Derivednoun second) {
        int sdif = (first).getVocalizedNoun().length() - (second).getVocalizedNoun().length();
        return sdif;
    }
    
    public int compareDerivedverb(Derivedverb first, Derivedverb second) {
        int sdif = (first).getVocalizedVerb().length() - (second).getVocalizedVerb().length();
        return sdif;
    }
    
    public int comparDerivedparticle(Derivedparticle first, Derivedparticle second) {
        int sdif = (first).getVocalizedParticle().length() - (second).getVocalizedParticle().length();
        return sdif;
    }

    private int compareSemanticEntryBOList(SemanticEntryBO first, SemanticEntryBO second) {
        int sdif = (first).getSpreadingdegree().charAt(0) - (second).getSpreadingdegree().charAt(0);
        if( sdif != 0)
            return sdif;
        else
            return (first).getSemanticEntryId() - (second).getSemanticEntryId();
    }
    
    private int compareStringList(String first, String second) {
        //return (Distance.LD(first,base) - Distance.LD(second,base));
        return first.compareTo( second );
    }

    public int compare(Object o1, Object o2) {
        if( o1 instanceof Derivednoun )
            return compareDerivednoun((Derivednoun)o1, (Derivednoun)o2);
        else if( o1 instanceof Derivedverb )
            return compareDerivedverb((Derivedverb)o1,(Derivedverb) o2);
        else if( o1 instanceof Derivedparticle )
             return comparDerivedparticle( (Derivedparticle) o1, (Derivedparticle) o2);
         else if( o1 instanceof SemanticEntryBO )
             return compareSemanticEntryBOList((SemanticEntryBO)o1,(SemanticEntryBO) o2);
        else if( o1 instanceof String )
             return compareStringList((String)o1,(String) o2);

        else return 0;
    }
 
}
