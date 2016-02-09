/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;

/**
 *
 * @author Gowzancha
 */
public class NomenGerundGeneratorUn {

    public List<String> InDefiniteNomenGerunds = new ArrayList<String>();
    public List<String> DefiniteNomenGerunds = new ArrayList<String>();
    public List<String> AnnexedNomenGerunds = new ArrayList<String>();

    public static NomenGerundGeneratorUn getInstance() {
        return new NomenGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        getInDefiniteNomenGerunds(root, pattern);
        getDefiniteNomenGerunds(root, pattern);
        getAnnexedNomenGerunds(root, pattern);
        return result;
    }

    public List<String> getDefiniteNomenGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator api = sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {

                    GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());                   

                    sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            DefiniteNomenGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        DefiniteNomenGerunds.add(str);
                    }
                }
            }
        }
        return DefiniteNomenGerunds;
    }

    public List<String> getInDefiniteNomenGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator api = sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());

                    sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            InDefiniteNomenGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        InDefiniteNomenGerunds.add(str);
                    }
                }
            }
        }
        return InDefiniteNomenGerunds;
    }

    public List<String> getAnnexedNomenGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator api = sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());

                    sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            AnnexedNomenGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        AnnexedNomenGerunds.add(str);
                    }
                }
            }
        }
        return AnnexedNomenGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = NomenGerundGeneratorUn.getInstance().executeSimpleGenerator("حكم", "1");

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
