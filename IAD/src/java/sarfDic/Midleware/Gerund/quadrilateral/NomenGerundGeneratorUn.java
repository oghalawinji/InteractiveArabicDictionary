/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.quadrilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.quadriliteral.ConjugationResult;

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

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        getInDefiniteNomenGerunds(root);
        getDefiniteNomenGerunds(root);
        getAnnexedNomenGerunds(root);
        return result;
    }

    public List<String> getDefiniteNomenGerunds(String rootStr) {
        sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator api = sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        List gerundList = api.createGerundList(root);

       /*sarf.gerund.modifier.quadrilateral. modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
        ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
        List finalGerundList = conjResult.getFinalResult();
        */
        int n = gerundList.size();
        for (int k = 0; k < n; k++) {
            if (gerundList.get(k) == null) {
                //result.add("");
                DefiniteNomenGerunds.add("");
                continue;
            }
            String str = gerundList.get(k).toString();
            //result.add(str);
            DefiniteNomenGerunds.add(str);
        }
        return DefiniteNomenGerunds;
    }

    public List<String> getInDefiniteNomenGerunds(String rootStr) {
        sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator api = sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        List gerundList = api.createGerundList(root);

       /*sarf.gerund.modifier.quadrilateral. modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
        ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
        List finalGerundList = conjResult.getFinalResult();
        */
        int n = gerundList.size();
        for (int k = 0; k < n; k++) {
            if (gerundList.get(k) == null) {
                //result.add("");
                InDefiniteNomenGerunds.add("");
                continue;
            }
            String str = gerundList.get(k).toString();
            //result.add(str);
            InDefiniteNomenGerunds.add(str);
        }
        return InDefiniteNomenGerunds;
    }

    public List<String> getAnnexedNomenGerunds(String rootStr) {
        sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator api = sarf.gerund.quadrilateral.unaugmented.QuadriliteralUnaugmentedNomenGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        List gerundList = api.createGerundList(root);

       /*sarf.gerund.modifier.quadrilateral. modifier = sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
        ConjugationResult conjResult = modifier.build(root, kov, gerundList, appliedFormulaList.get(j).toString());
        List finalGerundList = conjResult.getFinalResult();
        */
        int n = gerundList.size();
        for (int k = 0; k < n; k++) {
            if (gerundList.get(k) == null) {
                //result.add("");
                AnnexedNomenGerunds.add("");
                continue;
            }
            String str = gerundList.get(k).toString();
            //result.add(str);
            AnnexedNomenGerunds.add(str);
        }
        return AnnexedNomenGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = NomenGerundGeneratorUn.getInstance().executeSimpleGenerator("زحزح");

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
