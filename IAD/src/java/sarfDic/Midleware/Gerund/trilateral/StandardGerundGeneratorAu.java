/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import java.util.Collection;
import java.util.Iterator;
import sarf.AugmentationFormula;
import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugatorListener;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.trilateral.augmented.ConjugationResult;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author Gowzancha
 */
public class StandardGerundGeneratorAu implements Generator, AugmentedTrilateralModifierListener, TrilateralAugmentedGerundConjugatorListener {

    public List<String> InDefiniteGerunds = new ArrayList<String>();
    public List<String> DefiniteGerunds = new ArrayList<String>();
    public List<String> AnnexedGerunds = new ArrayList<String>();
    public boolean vocalization=false;

    public static StandardGerundGeneratorAu getInstance() {
        return new StandardGerundGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo, int patternNo,boolean vocal) {
        vocalization=vocal;
        List<String> result = new ArrayList<String>();
        getDefiniteGerunds(root, formulaNo, patternNo, vocal);
        getInDefiniteGerunds(root, formulaNo, patternNo, vocal);
        getAnnexedGerunds(root, formulaNo, patternNo, vocal);
        return result;
    }

    public List<String> getDefiniteGerunds(String rootStr, int formulaNo, int patternNo,boolean vocal) {
               vocalization=vocal;
        TrilateralAugmentedGerundConjugator api = TrilateralAugmentedGerundConjugator.getInstance();

        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo, patternNo);

                sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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

    public List<String> getInDefiniteGerunds(String rootStr, int formulaNo, int patternNo,boolean vocal) {
               vocalization=vocal;
        /*sarf.gerund.trilateral.augmented.*/ TrilateralAugmentedGerundConjugator api = /*sarf.gerund.trilateral.augmented.*/ TrilateralAugmentedGerundConjugator.getInstance();
        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo, patternNo);
                sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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

    public List<String> getAnnexedGerunds(String rootStr, int formulaNo, int patternNo,boolean vocal) {
               vocalization=vocal;
        /*sarf.gerund.trilateral.augmented.*/ TrilateralAugmentedGerundConjugator api = /*sarf.gerund.trilateral.augmented.*/ TrilateralAugmentedGerundConjugator.getInstance();
        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo, patternNo);

                sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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

    public List getPatterns(String root, int formulaNo) {
        List list = new ArrayList();
        List exceptions = new ArrayList();
        exceptions.add("يسر");
        exceptions.add("يمن");
        exceptions.add("يدي");
        exceptions.add("يفع");
        exceptions.add("يوم");
        exceptions.add("يبس");
        exceptions.add("يعط");

        if (formulaNo == 3&&!exceptions.contains(root)) {
            list.add("مُفَاعَلَة");
            list.add("فِعَال");
        } else {
            if (formulaNo == 2&&root.endsWith("ء")) {
                list.add("تَفْعِيل");
                list.add("تَفْعِلَة");
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> tests = StandardGerundGeneratorAu.getInstance().executeSimpleGenerator("جود", 1, 1,true);

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
       return vocalization;
    }
}
