/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import java.util.Collection;
import java.util.Iterator;
import sarf.AugmentationFormula;
import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugatorListener;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.ConjugationResult;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;
import sarfDic.Midleware.Generator;

/**
 *
 * @author Gowzancha
 *
 *  There is an ERROR:
 * 
 */


public class NomenGerundGeneratorAu implements Generator, AugmentedTrilateralModifierListener, TrilateralAugmentedGerundConjugatorListener {

    public List<String> InDefiniteNomenGerunds = new ArrayList<String>();
    public List<String> DefiniteNomenGerunds = new ArrayList<String>();
    public List<String> AnnexedNomenGerunds = new ArrayList<String>();
    public boolean vocalization=false;

    public static NomenGerundGeneratorAu getInstance() {
        return new NomenGerundGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo, boolean vocal) {
        vocalization=vocal;
        List<String> result = new ArrayList<String>();        
        getDefiniteNomenGerunds(root, formulaNo, vocal);
        getInDefiniteNomenGerunds(root, formulaNo, vocal);
        getAnnexedNomenGerunds(root, formulaNo, vocal);
        return result;
    }

    public List<String> getDefiniteNomenGerunds(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        TrilateralAugmentedNomenGerundConjugator api = TrilateralAugmentedNomenGerundConjugator.getInstance();

        AugmentedTrilateralRoot root=SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {

                 List gerundList = api.createGerundList(root, formulaNo);

                sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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
        
        return DefiniteNomenGerunds;
    }

    public List<String> getInDefiniteNomenGerunds(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        /*sarf.gerund.trilateral.augmented.nomen.*/TrilateralAugmentedNomenGerundConjugator api = /*sarf.gerund.trilateral.augmented.nomen.*/TrilateralAugmentedNomenGerundConjugator.getInstance();

        AugmentedTrilateralRoot root=SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                 sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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

        return InDefiniteNomenGerunds;
    }

    public List<String> getAnnexedNomenGerunds(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        /*sarf.gerund.trilateral.augmented.nomen.*/TrilateralAugmentedNomenGerundConjugator api = /*sarf.gerund.trilateral.augmented.nomen.*/TrilateralAugmentedNomenGerundConjugator.getInstance();

        AugmentedTrilateralRoot root=SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                 sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier modifier = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formulaNo, gerundList, this);
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

        return AnnexedNomenGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = NomenGerundGeneratorAu.getInstance().executeSimpleGenerator("جود", 1,false);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
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

    public int selectPatternFormNo(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
