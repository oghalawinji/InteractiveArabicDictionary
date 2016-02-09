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
public class MeemGerundGeneratorUn {

    public List<String> InDefiniteMeemGerunds = new ArrayList<String>();
    public List<String> DefiniteMeemGerunds = new ArrayList<String>();
    public List<String> AnnexedMeemGerunds = new ArrayList<String>();

    public static MeemGerundGeneratorUn getInstance() {
        return new MeemGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        getInDefiniteMeemGerunds(root, pattern);
        getDefiniteMeemGerunds(root, pattern);
        getAnnexedMeemGerunds(root, pattern);
        return result;
    }

    public List<String> getDefiniteMeemGerunds(String rootStr, String pattern) {
        MeemGerundConjugator api = MeemGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {

                    GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());                   

                    sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            DefiniteMeemGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        DefiniteMeemGerunds.add(str);
                    }
                }
            }
        }
        return DefiniteMeemGerunds;
    }

    public List<String> getInDefiniteMeemGerunds(String rootStr, String pattern) {
        MeemGerundConjugator api = MeemGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());

                    sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            InDefiniteMeemGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        InDefiniteMeemGerunds.add(str);
                    }
                }
            }
        }
        return InDefiniteMeemGerunds;
    }

    public List<String> getAnnexedMeemGerunds(String rootStr, String pattern) {
       MeemGerundConjugator api = MeemGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
               List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                    List gerundList = api.createGerundList(root, appliedFormulaList.get(j).toString());

                    sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier modifier = sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            AnnexedMeemGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        AnnexedMeemGerunds.add(str);
                    }
                }
            }
        }
        return AnnexedMeemGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = MeemGerundGeneratorUn.getInstance().executeSimpleGenerator("ذهب", "3");

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
