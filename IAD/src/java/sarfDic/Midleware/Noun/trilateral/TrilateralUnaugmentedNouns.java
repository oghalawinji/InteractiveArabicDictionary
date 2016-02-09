/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware.Noun.trilateral;

/**
 *
 * @author Gowzancha
 */
import java.util.*;

import sarf.verb.trilateral.unaugmented.*;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class TrilateralUnaugmentedNouns {

   //مبالغة أسماء الفاعل
    private List standardExaggerations;
    private List nonStandardExaggerations;
    //أسماء الزمان والمكان
    private List timeAndPlaces;

    //أسماء الآلة
    private List standardInstrumentals;
    private List nonStandardInstrumentals;

    //أسماء التفضيل
    private List elatives;
    //الصفات المشبهة
    private List assimilates;

    private UnaugmentedTrilateralRoot root;

    public TrilateralUnaugmentedNouns(UnaugmentedTrilateralRoot root) {
        standardExaggerations = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance().getAppliedFormulaList(root);
        nonStandardExaggerations = NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(root);

        timeAndPlaces = TimeAndPlaceConjugator.getInstance().getAppliedFormulaList(root);

        standardInstrumentals = sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator.getInstance().getAppliedFormulaList(root);
        nonStandardInstrumentals = NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(root);

        elatives = ElativeNounConjugator.getInstance().getAppliedFormulaList(root);
        assimilates = AssimilateAdjectiveConjugator.getInstance().getAppliedFormulaList(root);
    }

    public List getAssimilates() {
        return assimilates;
    }

    public List getElatives() {
        return elatives;
    }

    public List getNonStandardExaggerations() {
        return nonStandardExaggerations;
    }

    public List getNonStandardInstrumentals() {
        return nonStandardInstrumentals;
    }

    public UnaugmentedTrilateralRoot getRoot() {
        return root;
    }

    public List getStandardExaggerations() {
        return standardExaggerations;
    }

    public List getStandardInstrumentals() {
        return standardInstrumentals;
    }

    public List getTimeAndPlaces() {
        return timeAndPlaces;
    }
}


