/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.quadrilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.SystemConstants;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator;
import sarf.verb.quadriliteral.modifier.QuadrilateralModifier;

/**
 *
 * @author riad
 */
public class ActivePresentGeneratorAu implements Generator {

    public static ActivePresentGeneratorAu getInstance() {
        return new ActivePresentGeneratorAu();
    }
    public List<String> nominative1 = new ArrayList<String>();
    public List<String> emphasized1 = new ArrayList<String>();
    public List<String> jussive1 = new ArrayList<String>();
    public List<String> accusative1 = new ArrayList<String>();
    public List<String> presents = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getNominative(root, formulaNo);
        getEmphasized(root, formulaNo);
        getJussive(root, formulaNo);
        getAccusative(root, formulaNo);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getNominative(String rootStr, int formulaNo) {
        //Active present Conjugator API:
        sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator api = sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(root, formula.getFormulaNo());
                presents.add(list.get(7).toString());
                //modification
                sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());

                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(),kov, list, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                        
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                    nominative1.add(apv1Str);
                }
            }
        }
        return nominative1;
    }

    public List getAccusative(String rootStr, int formulaNo) {
        //Active present Conjugator API:
        sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator api = sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedActivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(root, formula.getFormulaNo());

                //modification
                sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());

                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(),kov, list, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                    accusative1.add(apv1Str);
                }
            }
        }
        return accusative1;
    }

    public List getEmphasized(String rootStr, int formulaNo) {
         //Active present Conjugator API:
        sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator api = sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedActivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(root, formula.getFormulaNo());

                //modification
                sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());

                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(),kov, list, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                    emphasized1.add(apv1Str);
                }
            }
        }
        return emphasized1;
    }

    public List getJussive(String rootStr, int formulaNo) {
        //Active present Conjugator API:
        sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator api = sarf.verb.quadriliteral.augmented.active.present.AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedActivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(root, formula.getFormulaNo());

                //modification
                sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());

                ConjugationResult conjResult = modifier.build(root, formula.getFormulaNo(),kov, list, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                    jussive1.add(apv1Str);
                }
            }
        }
        return jussive1;
    }

    public boolean doSelectVocalization() {
        return true;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = ActivePresentGeneratorAu.getInstance().executeSimpleGenerator("بءبء",1);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
