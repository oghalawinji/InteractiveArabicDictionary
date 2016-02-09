/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.quadrilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import java.util.Collection;
import java.util.Iterator;
import sarf.AugmentationFormula;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.quadriliteral.ConjugationResult;


/**
 *
 * @author Gowzancha
 */
public class StandardGerundGeneratorAu implements Generator {

    public List<String> InDefiniteGerunds = new ArrayList<String>();
    public List<String> DefiniteGerunds = new ArrayList<String>();
    public List<String> AnnexedGerunds = new ArrayList<String>();

    public static StandardGerundGeneratorAu getInstance() {
        return new StandardGerundGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteGerunds(root, formulaNo);
        getInDefiniteGerunds(root, formulaNo);
        getAnnexedGerunds(root, formulaNo);
        return result;
    }

    public List<String> getDefiniteGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedGerundConjugator api = QuadriliteralAugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formulaNo, kov, gerundList);
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
            }
        }
        return DefiniteGerunds;
    }

    public List<String> getInDefiniteGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedGerundConjugator api = QuadriliteralAugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formulaNo, kov, gerundList);
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
            }
        }
        return InDefiniteGerunds;
    }

    public List<String> getAnnexedGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedGerundConjugator api = QuadriliteralAugmentedGerundConjugator.getInstance();
        sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier modifier = sarf.gerund.modifier.quadrilateral.QuadrilateralStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formulaNo, kov, gerundList);
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
            }
        }
        return AnnexedGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = StandardGerundGeneratorAu.getInstance().executeSimpleGenerator("زحزح", 1);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public int selectPatternFormNo(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean doSelectVocalization() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
