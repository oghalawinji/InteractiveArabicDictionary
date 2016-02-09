/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.gerund.trilateral.unaugmented.StandardTrilateralUnaugmentedSuffixContainer;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;

/**
 *
 * @author Gowzancha
 */
public class QualityGerundGeneratorUn {

    public List<String> InDefiniteQualityGerunds = new ArrayList<String>();
    public List<String> DefiniteQualityGerunds = new ArrayList<String>();
    public List<String> AnnexedQualityGerunds = new ArrayList<String>();

    public static QualityGerundGeneratorUn getInstance() {
        return new QualityGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        getInDefiniteQualityGerunds(root, pattern);
        getDefiniteQualityGerunds(root, pattern);
        getAnnexedQualityGerunds(root, pattern);
        return result;
    }

    public List<String> getDefiniteQualityGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.QualityGerundConjugator api = sarf.gerund.trilateral.unaugmented.QualityGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
              // List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
               // for (int j = 0; j < appliedFormulaList.size(); j++) {

                    GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                    List gerundList = api.createGerundList(root, "");

                    sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier modifier =  sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, "فِعْلَة");
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            DefiniteQualityGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        DefiniteQualityGerunds.add(str);
                    }
               // }
            }
        }
        return DefiniteQualityGerunds;
    }

    public List<String> getInDefiniteQualityGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.QualityGerundConjugator api = sarf.gerund.trilateral.unaugmented.QualityGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
              // List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
               // for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                    List gerundList = api.createGerundList(root, "");

                    sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier modifier =  sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList, "فِعْلَة");
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            InDefiniteQualityGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        InDefiniteQualityGerunds.add(str);
                    }
               // }
            }
        }
        return InDefiniteQualityGerunds;
    }

    public List<String> getAnnexedQualityGerunds(String rootStr, String pattern) {
        sarf.gerund.trilateral.unaugmented.QualityGerundConjugator api = sarf.gerund.trilateral.unaugmented.QualityGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
              // List appliedFormulaList = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
               // for (int j = 0; j < appliedFormulaList.size(); j++) {
                    GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                    List gerundList = api.createGerundList(root, "");

                    sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier modifier =  sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, gerundList,"فِعْلَة" );
                    List finalGerundList = conjResult.getFinalResult();

                    int n = finalGerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalGerundList.get(k) == null) {
                            //result.add("");
                            AnnexedQualityGerunds.add("");
                            continue;
                        }
                        String str = finalGerundList.get(k).toString();
                        //result.add(str);
                        AnnexedQualityGerunds.add(str);
                    }
                //}
            }
        }
        return AnnexedQualityGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = QualityGerundGeneratorUn.getInstance().executeSimpleGenerator("حكم", "1");

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
