/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.quadrilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.quadriliteral.ConjugationResult;

/**
 *
 * @author Gowzancha
 */
public class StandardGerundGeneratorUn implements Generator {

    public List<String> InDefiniteGerunds = new ArrayList<String>();
    public List<String> DefiniteGerunds = new ArrayList<String>();
    public List<String> AnnexedGerunds = new ArrayList<String>();

    public static StandardGerundGeneratorUn getInstance() {
        return new StandardGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, int PatternNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteGerunds(root, PatternNo);
        getInDefiniteGerunds(root, PatternNo);
        getAnnexedGerunds(root, PatternNo);
        return result;
    }

    public List<String> getDefiniteGerunds(String rootStr, int PatternNo) {
        QuadriliteralUnaugmentedGerundConjugator api = QuadriliteralUnaugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        List gerundList = api.createGerundList(root, PatternNo);
        sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, gerundList);
        List finalGerundList = conjResult.getFinalResult();
        int n = finalGerundList.size();
        for (int k = 0; k < n; k++) {
            if (finalGerundList.get(k) == null) {
                //result.add("");
                DefiniteGerunds.add("");
                continue;
            }
            String str = finalGerundList.get(k).toString();

            //result.add(str);
           DefiniteGerunds.add(str);
        }
        return DefiniteGerunds;
    }

    public List<String> getInDefiniteGerunds(String rootStr, int PatternNo) {
        QuadriliteralUnaugmentedGerundConjugator api = QuadriliteralUnaugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        List gerundList = api.createGerundList(root, PatternNo);
        sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, gerundList);
        List finalGerundList = conjResult.getFinalResult();
        int n = finalGerundList.size();
        for (int k = 0; k < n; k++) {
            if (finalGerundList.get(k) == null) {
                //result.add("");
                InDefiniteGerunds.add("");
                continue;
            }
            String str = finalGerundList.get(k).toString();

            //result.add(str);
            InDefiniteGerunds.add(str);
        }
        return InDefiniteGerunds;
    }

    public List<String> getAnnexedGerunds(String rootStr, int PatternNo) {
        QuadriliteralUnaugmentedGerundConjugator api = QuadriliteralUnaugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        List gerundList = api.createGerundList(root, PatternNo);
        sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, gerundList);
        List finalGerundList = conjResult.getFinalResult();
        int n = finalGerundList.size();
        for (int k = 0; k < n; k++) {
            if (finalGerundList.get(k) == null) {
                //result.add("");
                AnnexedGerunds.add("");
                continue;
            }
            String str = finalGerundList.get(k).toString();

            //result.add(str);
            AnnexedGerunds.add(str);
        }
        return AnnexedGerunds;
    }

    public static List getPatterns(String rootStr) {
        sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        List list = new ArrayList();
        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        if (kov==2||kov==3||kov==5||kov==6) { // الفعل مضاعف
        list.add("فَعْلَلَة");
        list.add("فِعْلال");
        }
        
        return list;
    }

    public static void main(String[] args) {
        List<String> tests = StandardGerundGeneratorUn.getInstance().executeSimpleGenerator("بحتن", 4);
        //List<String> tes = StandardGerundGeneratorUn.getInstance().getFormulas("ذهب", "4");

        /* for (int i = 0; i < tests.size(); i++) {
        System.out.println(tests.get(i));
        }*/
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
