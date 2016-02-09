/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.quadrilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;

import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.noun.quadriliteral.modifier.activeparticiple.*;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;

/**
 *
 * @author riad
 */
public class ActiveParticipleGeneratorAu implements Generator {

    public static ActiveParticipleGeneratorAu getInstance() {
        return new ActiveParticipleGeneratorAu();
    }
    public List<String> definite = new ArrayList<String>();
    public List<String> inDefinite = new ArrayList<String>();
    public List<String> annexed = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root, int formulaNo) {
        List<String> result = new ArrayList<String>();
        generateDefinite(root, formulaNo);
        generateInDefinite(root, formulaNo);
        generateAnnexed(root, formulaNo);
        return result;
    }

    public List generateDefinite(String rootStr, int formulaNo) {
        //API
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(), kov, nounList);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        definite.add("");
                        continue;
                    }

                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    definite.add(str);
                }
            }
        }
        return definite;
    }

    public List generateInDefinite(String rootStr, int formulaNo) {
        //API
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator.getInstance();
        //extract the root from dictionary:
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());

                ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(), kov, nounList);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        inDefinite.add("");
                        continue;
                    }

                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    inDefinite.add(str);
                }
            }
        }
        inDefinite.add(""+formulaNo);
        return inDefinite;
    }

    public List generateAnnexed(String rootStr, int formulaNo) {
        //API
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(), kov, nounList);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        annexed.add("");
                        continue;
                    }

                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    annexed.add(str);
                }
            }
        }
        return annexed;
    }

    public boolean doSelectVocalization() {
        return true;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = ActiveParticipleGeneratorAu.getInstance().executeSimpleGenerator("حبءن", 3);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
