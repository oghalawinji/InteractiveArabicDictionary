/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.trilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.SystemConstants;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.ConjugationResult;
import sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class PassivePresentGeneratorAu implements Generator, AugmentedTrilateralModifierListener {

    public static PassivePresentGeneratorAu getInstance() {
        return new PassivePresentGeneratorAu();
    }
    public List<String> nominative = new ArrayList<String>();
    public List<String> emphasized = new ArrayList<String>();
    public List<String> jussive = new ArrayList<String>();
    public List<String> accusative = new ArrayList<String>();
    public boolean vocalization = false;

    public List<String> executeSimpleGenerator(String root, int formulaNo, boolean vocal) {
        vocalization = vocal;
        List<String> result = new ArrayList<String>();
        getNominative(root, formulaNo, vocal);
        getEmphasized(root, formulaNo, vocal);
        getJussive(root, formulaNo, vocal);
        getAccusative(root, formulaNo, vocal);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getNominative(String rootStr, int formulaNo, boolean vocal) {
        vocalization = vocal;
        //Passive present Conjugator API:
        sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator api = sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedPassivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = false;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, PassivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        nominative.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))

                    nominative.add(apv1Str);
                }
            }
        }
        return nominative;
    }

    public List<String> getAccusative(String rootStr, int formulaNo, boolean vocal) {
        vocalization = vocal;
        //passive present Conjugator API:
        sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator api = sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedPassivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = false;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, PassivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        accusative.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))

                    accusative.add(apv1Str);
                }
            }
        }
        return accusative;
    }

    public List<String> getEmphasized(String rootStr, int formulaNo, boolean vocal) {
        vocalization = vocal;
        //passive present Conjugator API:
        sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator api = sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedPassivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = false;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, PassivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        emphasized.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))

                    emphasized.add(apv1Str);
                }
            }
        }
        return emphasized;
    }

    public List<String> getJussive(String rootStr, int formulaNo, boolean vocal) {
        vocalization = vocal;
        //passive present Conjugator API:
        sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator api = sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedPassivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = false;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, PassivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        jussive.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();

                    if (kov == 2) {// مضعف
                        if (formulaNo == 1 || formulaNo == 3 || formulaNo == 4 || formulaNo == 5 || formulaNo == 7 || formulaNo == 9 || formulaNo == 10) {
                            if (j == 0 || j == 1 || j == 2 || j == 7 || j == 8) {
                                apv1Str += " / " + list.get(j).toString();
                            }
                        }
                    }

                    jussive.add(apv1Str);
                }
            }
        }
        return jussive;
    }

    public boolean doSelectVocalization() {
        return vocalization;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = PassivePresentGeneratorAu.getInstance().executeSimpleGenerator("روح", 9, false);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
