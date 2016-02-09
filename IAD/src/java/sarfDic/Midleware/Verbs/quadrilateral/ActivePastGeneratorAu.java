/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.quadrilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.SystemConstants;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.augmented.active.past.AugmentedActivePastConjugator;

/**
 *
 * @author riad
 */
public class ActivePastGeneratorAu implements Generator {

    public static ActivePastGeneratorAu getInstance() {
        return new ActivePastGeneratorAu();
    }
    public List<String> pasts = new ArrayList<String>();
    public List<Integer> forms = new ArrayList<Integer>();

    public List<String> executeSimpleGenerator(String rootStr, int formulaNo) {
        List<String> result = new ArrayList<String>();

        //Active Past Conjugator API:
        sarf.verb.quadriliteral.augmented.active.past.AugmentedActivePastConjugator api = sarf.verb.quadriliteral.augmented.active.past.AugmentedActivePastConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();

        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                forms.add(formula.getFormulaNo());
                List list = AugmentedActivePastConjugator.getInstance().createVerbList(root, formula.getFormulaNo());
                pasts.add(list.get(7).toString());
                //modification
                sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PAST_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());

                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(), kov,list, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePastVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                        result.add("");
                        continue;
                    }
                    //sarf.verb.trilateral.unaugmented.active.ActivePastVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePastVerb) apvList.get(j);
                    String apv1Str = apvList.get(j).toString();
                    //  if( ! result.contains(apv1Str))
                    result.add(apv1Str);
                }
            }
        }
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean doSelectVocalization() {
        return true;
    }

    public static void main(String[] args) {
        List<String> tests = ActivePastGeneratorAu.getInstance().executeSimpleGenerator("بءبء",1);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
