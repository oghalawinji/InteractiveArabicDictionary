    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.morphgen;

import sarfDic.Midleware.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.kov.TrilateralKovRule;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;

/**
 *
 * @author riad
 */
public class TriAugmentedCase {

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

    private static int getKOV(AugmentedTrilateralRoot root) {
        char c1 = root.getC1();
        char c2 = root.getC2();
        char c3 = root.getC3();
        TrilateralKovRule rule = KovRulesManager.getInstance().getTrilateralKovRule(c1, c2, c3);
        if (rule != null) {
            int kov = rule.getKov();
            return kov;
        } else {
            return 0;
        }
    }

    public static String getKovDesc(String rootStr) {
        AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        if (root != null) {
            TrilateralKovRule rule = KovRulesManager.getInstance().getTrilateralKovRule(rootStr.charAt(0), rootStr.charAt(1), rootStr.charAt(2));
            if (rule != null) {
                String kov = rule.getDesc();
                return kov;
            }
            return "";
        }
        return "";
    }

    private static String getAugmentedPresent(int formulaNo, AugmentedTrilateralRoot root) {
        //مع الضمير هو
        //present text formatting
        String presentRootText = sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(root, 7, formulaNo).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, presentRootText);
        sarf.verb.trilateral.augmented.ConjugationResult conjResult = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance().build(root, getKOV(root), formulaNo, conjugations, "Present", true, null);
        presentRootText = conjResult.getFinalResult().get(7).toString();
        return presentRootText;
    }

    private static String getAugmentedPast(int formulaNo, AugmentedTrilateralRoot root) {
        //مع الضمير هو
        //past text formatting
        String pastRootText = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(root, 7, formulaNo).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, pastRootText);
        sarf.verb.trilateral.augmented.ConjugationResult conjResult = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance().build(root, getKOV(root), formulaNo, conjugations, "Past", true, null);
        pastRootText = conjResult.getFinalResult().get(7).toString();
        return pastRootText;
    }

    public static String get(int pattern, String rootStr) {
        // TODO code application logic here
        AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        if (root != null) {
            AugmentationFormula formula = root.getAugmentationFormula(pattern);
            if (formula != null) {
                int conj = formula.getFormulaNo();
                if (conj == pattern) {
                    return TriAugmentedCase.getAugmentedPast(pattern, root) + " " + TriAugmentedCase.getAugmentedPresent(pattern, root);
                }
            }
        }
        return "";
    }

    public static String getTransitive(int pattern, String rootStr) {
        // TODO code application logic here
        AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        if (root != null) {
            AugmentationFormula formula = root.getAugmentationFormula(pattern);
            if (formula != null) {
                int conj = formula.getFormulaNo();
                if (conj == pattern) {
                    return "" + formula.getTransitive();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String res = get(2, "كتب");
        System.out.println(res);
    }
}
