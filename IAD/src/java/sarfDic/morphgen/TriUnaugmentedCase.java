    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.morphgen;

import sarfDic.Midleware.*;
import java.util.ArrayList;
import java.util.List;
import sarf.kov.TrilateralKovRule;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;

/**
 *
 * @author riad
 */
public class TriUnaugmentedCase {

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

    private static int getKOV(UnaugmentedTrilateralRoot root) {
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
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        if (!list.isEmpty()) {
            TrilateralKovRule rule = KovRulesManager.getInstance().getTrilateralKovRule(rootStr.charAt(0), rootStr.charAt(1), rootStr.charAt(2));
            if (rule != null) {
                String kov = rule.getDesc();
                return kov;
            }
            return "";
        }
        return "";
    }

    private static String getUnaugmentedPresent(int index, UnaugmentedTrilateralRoot root) {
        //مع الضمير هو
        //present text formatting
        String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, presentRootText);
        sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance().build(root, getKOV(root), conjugations, "Present", true);
        presentRootText = conjResult.getFinalResult().get(7).toString();

        return presentRootText;
    }

    private static String getUnaugmentedPast(int index, UnaugmentedTrilateralRoot root) {
        //past text formatting
        String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
        List conjugations = createEmptyList();
        conjugations.set(7, pastRootText);
        sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance().build(root, getKOV(root), conjugations, "Past", true);
        pastRootText = conjResult.getFinalResult().get(7).toString();
        return pastRootText;
    }

    public static String get(int pattern, String rootStr) {
        // TODO code application logic here
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        if (list != null) {
            sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
            for (int i = 0; i < list.size(); i++) {
                root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
                String conj = root.getConjugation();
                if (conj.equals("" + pattern)) {
                    return TriUnaugmentedCase.getUnaugmentedPast(pattern, root) + " " + TriUnaugmentedCase.getUnaugmentedPresent(pattern, root);
                }
            }
        }
        return "";
    }

    public static String getTransitive(int pattern, String rootStr) {
        // TODO code application logic here
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        if (list != null) {
            sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
            for (int i = 0; i < list.size(); i++) {
                root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
                String conj = root.getConjugation();
                if (conj.equals("" + pattern)) {
                    return root.getTransitive();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String res = get(1, "كتب");
        System.out.println(res);
    }
}
