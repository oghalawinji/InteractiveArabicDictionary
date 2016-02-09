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
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class ActivePresentGeneratorAu implements Generator, AugmentedTrilateralModifierListener {

    public static ActivePresentGeneratorAu getInstance() {
        return new ActivePresentGeneratorAu();
    }
    public List<String> nominative1 = new ArrayList<String>();// مرفوع
    public List<String> emphasized1 = new ArrayList<String>();// مؤكد
    public List<String> jussive1 = new ArrayList<String>();// مجزوم
    public List<String> accusative1 = new ArrayList<String>();// منصوب
    public List<String> presents = new ArrayList<String>();
    public boolean vocalization=false;

    public List<String> executeSimpleGenerator(String root, int formulaNo, boolean vocal) {
        vocalization=vocal;
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

    public List getNominative(String rootStr, int formulaNo, boolean vocal) {
         vocalization=vocal;
        //Active present Conjugator API:
       AugmentedActivePresentConjugator api = AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = api.getNominativeConjugator().createVerbList(root, formula.getFormulaNo());
                presents.add(list.get(7).toString());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActivePresentGeneratorAu.this);
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

    public List getAccusative(String rootStr, int formulaNo, boolean vocal) {
         vocalization=vocal;
        //Active present Conjugator API:
        AugmentedActivePresentConjugator api = AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = api.getAccusativeConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                      
                        accusative1.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //  if( ! result.contains(apv1Str))
                   
                    accusative1.add(apv1Str);
                }
            }
        }
        return accusative1;
    }

    public List getEmphasized(String rootStr, int formulaNo, boolean vocal) {
         vocalization=vocal;
        //Active present Conjugator API:
        AugmentedActivePresentConjugator api = AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = api.getEmphasizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                       
                        emphasized1.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))
                   
                    emphasized1.add(apv1Str);
                }
            }
        }
        return emphasized1;
    }

    public List getJussive(String rootStr, int formulaNo, boolean vocal) {
         vocalization=vocal;
        //Active present Conjugator API:
        AugmentedActivePresentConjugator api = AugmentedActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = api.getJussiveConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.PRESENT_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActivePresentGeneratorAu.this);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                       
                        jussive1.add("");
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
                   
                    jussive1.add(apv1Str);
                }
            }
        }
        return jussive1;
    }

    public static void main(String[] args) {
        List<String> tests = ActivePresentGeneratorAu.getInstance().executeSimpleGenerator("جوب", 9,false);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public boolean doSelectVocalization() {
        return vocalization;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
