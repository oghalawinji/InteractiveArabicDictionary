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
import sarf.SarfDictionary;
import sarf.SystemConstants;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.augmented.imperative.AugmentedImperativeConjugator;
import sarf.verb.quadriliteral.modifier.QuadrilateralModifier;

/**
 *
 * @author riad
 */
public class ActiveImperativeGeneratorAu implements Generator {

    public static ActiveImperativeGeneratorAu getInstance() {
        return new ActiveImperativeGeneratorAu();
    }
    public List<String> normal = new ArrayList<String>();
    public List<String> emphsized = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root,int formulaNo ) {
        List<String> result = new ArrayList<String>();
        getNormal(root,formulaNo);
        getEmphsized(root,formulaNo);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getNormal(String rootStr, int formulaNo) {
        //Active imperative Conjugator API:
        sarf.verb.quadriliteral.augmented.imperative.AugmentedImperativeConjugator api = sarf.verb.quadriliteral.augmented.imperative.AugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:       
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedImperativeConjugator.getInstance().getNotEmphsizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                QuadrilateralModifier modifier = QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(),kov, list, tense, active);
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

    public List getEmphsized(String rootStr, int formulaNo) {
        //Active imperative Conjugator API:
        sarf.verb.quadriliteral.augmented.imperative.AugmentedImperativeConjugator api = sarf.verb.quadriliteral.augmented.imperative.AugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List list = AugmentedImperativeConjugator.getInstance().getEmphsizedConjugator().createVerbList(root, formula.getFormulaNo());
                //modification
                QuadrilateralModifier modifier = QuadrilateralModifier.getInstance();
                boolean active = true;
                String tense = SystemConstants.EMPHASIZED_IMPERATIVE_TENSE;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(),kov, list, tense, active);
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
        return true;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveImperativeGeneratorAu.getInstance().executeSimpleGenerator("زحزح",1);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
