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
import sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class ActiveImperativeGeneratorAu implements Generator, AugmentedTrilateralModifierListener {

    public static ActiveImperativeGeneratorAu getInstance() {
        return new ActiveImperativeGeneratorAu();
    }
    public List<String> normal = new ArrayList<String>();
    public List<String> emphsized = new ArrayList<String>();
    public boolean vocalization=false;

    public List<String> executeSimpleGenerator(String root,int formulaNo, boolean vocal ) {
        vocalization=vocal;
        List<String> result = new ArrayList<String>();
        getNormal(root,formulaNo,vocal);
        getEmphsized(root,formulaNo,vocal);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getNormal(String rootStr, int formulaNo,boolean vocal) {
            vocalization=vocal;
        //Active imperative Conjugator API:
        sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator api = sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedImperativeConjugator.getInstance().getNotEmphsizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActiveImperativeGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                       
                        normal.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                  
                    normal.add(apv1Str);
                }
            }
        }
        return normal;
    }

    public List getEmphsized(String rootStr, int formulaNo,boolean vocal) {
            vocalization=vocal;
        //Active imperative Conjugator API:
        sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator api = sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedImperativeConjugator.getInstance().getEmphsizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.EMPHASIZED_IMPERATIVE_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActiveImperativeGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
               
                        emphsized.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))
                 
                    emphsized.add(apv1Str);
                }
            }
        }
        return emphsized;
    }

    public boolean doSelectVocalization() {
        return vocalization;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveImperativeGeneratorAu.getInstance().executeSimpleGenerator("جوب",9,true);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
