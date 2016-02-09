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
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.ConjugationResult;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class PassiveParticipleGeneratorAu implements Generator, AugmentedTrilateralModifierListener {

    public static PassiveParticipleGeneratorAu getInstance() {
        return new PassiveParticipleGeneratorAu();
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

    public List generateDefinite(String rootStr, int formulaNo,boolean vocal) {
          vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        definite.add("");
                        continue;
                    }
                    // else{
                    // if( ! result.contains(entry))
                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    definite.add(str);
                }
                //}
            }
        }
        return definite;
    }

    public List generateInDefinite(String rootStr, int formulaNo,boolean vocal) {
          vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        //result.add("");
                        inDefinite.add("");
                        continue;
                    }
                    //else{
                    // if( ! result.contains(entry))
                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    inDefinite.add(str);
                }
            }
            // }
        }
        return inDefinite;
    }

    public List generateAnnexed(String rootStr, int formulaNo, boolean vocal) {
          vocalization=vocal;
        //Active Past Conjugator API:
        sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), nounList, this);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        //result.add("");
                        annexed.add("");
                        continue;
                    }
                    //else{
                    // if( ! result.contains(entry))
                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    annexed.add(str);
                }
                //}
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

    public static void main(String [] args){
        PassiveParticipleGeneratorAu.getInstance().executeSimpleGenerator("جوب", 9,false);
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
