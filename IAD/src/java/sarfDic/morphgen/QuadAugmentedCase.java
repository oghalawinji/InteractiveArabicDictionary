    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.morphgen;

import sarfDic.Midleware.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.kov.QuadrilateralKovRule;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;

/**
 *
 * @author riad
 */
public class QuadAugmentedCase {

    /**
     * @param args the command line arguments
     */
    private static List createEmptyList() {
        List result = new ArrayList(13);
        for (int i = 1; i <= 13; i++) {
            result.add("");
        }
        return result;
    }

    private static int getKOV(AugmentedQuadriliteralRoot root) {
        char c1 = root.getC1();
        char c2 = root.getC2();
        char c3 = root.getC3();
        char c4 = root.getC4();
        QuadrilateralKovRule rule = KovRulesManager.getInstance().getQuadrilateralKovRule(c1, c2, c3, c4);
        if (rule != null) {
            int kov = rule.getKov();
            return kov;
        } else {
            return 0;
        }
    }

    public static String getKovDesc(String rootStr) {
        AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        if (root != null) {
            QuadrilateralKovRule rule = KovRulesManager.getInstance().getQuadrilateralKovRule(rootStr.charAt(0), rootStr.charAt(1), rootStr.charAt(2), rootStr.charAt(3));
            if (rule != null) {
                String kov = rule.getDesc();
                return kov;
            }
            return "";
        }
        return "";
    }

    private static String getAugmentedPresent(int formulaNo, AugmentedQuadriliteralRoot root) {
        //مع الضمير هو
        //present text formatting
        String presentRootText = sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(root, 7, formulaNo).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, presentRootText);
        sarf.verb.quadriliteral.ConjugationResult conjResult = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance().build(root, formulaNo, getKOV(root), conjugations, "Present", true);
        presentRootText = conjResult.getFinalResult().get(7).toString();

        return presentRootText;
    }

    private static String getAugmentedPast(int formulaNo, AugmentedQuadriliteralRoot root) {
        //مع الضمير هو
        //past text formatting
        String pastRootText = sarf.verb.quadriliteral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(root, 7, formulaNo).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, pastRootText);
        sarf.verb.quadriliteral.ConjugationResult conjResult = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance().build(root, formulaNo, getKOV(root), conjugations, "Past", true);
        pastRootText = conjResult.getFinalResult().get(7).toString();

        return pastRootText;
    }

    public static String get(int pattern, String rootStr) {
        // TODO code application logic here
        AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        if (root != null) {
            Collection formulas = root.getAugmentationList();
            for (Iterator iter = formulas.iterator(); iter.hasNext();) {
                AugmentationFormula formula = (AugmentationFormula) iter.next();
                if (formula.getFormulaNo() == pattern) {
                    return QuadAugmentedCase.getAugmentedPast(pattern, root) + " " + QuadAugmentedCase.getAugmentedPresent(pattern, root);
                }
            }
        }
        return "";
    }

    public static String getTransitive(int pattern, String rootStr) {
        // TODO code application logic here
        AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        if (root != null) {
            Collection formulas = root.getAugmentationList();
            for (Iterator iter = formulas.iterator(); iter.hasNext();) {
                AugmentationFormula formula = (AugmentationFormula) iter.next();
                if (formula.getFormulaNo() == pattern) {
                    return "" + formula.getTransitive();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String res = get(2, "طءطء");
        System.out.println(res);
    }
}
