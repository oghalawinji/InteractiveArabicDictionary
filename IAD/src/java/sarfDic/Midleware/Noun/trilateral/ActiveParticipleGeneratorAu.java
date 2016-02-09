/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;

import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;

import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class ActiveParticipleGeneratorAu implements Generator, AugmentedTrilateralModifierListener {

    public static ActiveParticipleGeneratorAu getInstance() {
        return new ActiveParticipleGeneratorAu();
    }
    public List<String> definite = new ArrayList<String>();
    public List<String> inDefinite = new ArrayList<String>();
    public List<String> annexed = new ArrayList<String>();
    public boolean vocalization=false;

    public List<String> executeSimpleGenerator(String root, int formulaNo, boolean vocal) {
        vocalization=vocal;
        List<String> result = new ArrayList<String>();
        generateDefinite(root, formulaNo, vocal);
        generateInDefinite(root, formulaNo, vocal);
        generateAnnexed(root, formulaNo, vocal);
        return result;
    }

    public List generateDefinite(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this augmented verb(1st, 2nd, ....,6th).
        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        //for(int mode=1; mode<=3; mode++){

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.augmented.ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
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

    public List generateInDefinite(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        //for(int mode=1; mode<=3; mode++){

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.augmented.ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        //result.add("");
                        inDefinite.add("");
                        continue;
                    }


                    // else{
                    String str = finalNounList.get(j).toString();
                    //if( ! result.contains(str))
                    //result.add(str);
                    inDefinite.add(str);
                    //}
                }
            }
        }
        return inDefinite;
    }

    public List generateAnnexed(String rootStr, int formulaNo, boolean vocal) {
                vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        sarf.verb.trilateral.augmented.AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        //for(int mode=1; mode<=3; mode++){

        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.augmented.ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        //result.add("");
                        annexed.add("");
                        continue;
                    }


                    //else{
                    String str = finalNounList.get(j).toString();
                    // if( ! result.contains(str))
                    //result.add(str);
                    annexed.add(str);
                    // }
                }
            }
        }
        return annexed;
    }

    public boolean doSelectVocalization() {
        return vocalization;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = ActiveParticipleGeneratorAu.getInstance().executeSimpleGenerator("جوب", 9,false);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
