/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.quadrilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.quadriliteral.modifier.passiveparticiple.*;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;
/**
 *
 * @author riad
 */
public class PassiveParticipleGeneratorAu implements Generator {

    public static PassiveParticipleGeneratorAu getInstance() {
        return new PassiveParticipleGeneratorAu();
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
        //API:
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                PassiveParticipleModifier modifier =PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(),kov, nounList);
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

    public List generateInDefinite(String rootStr, int formulaNo) {
        //API:
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                PassiveParticipleModifier modifier =PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(),kov, nounList);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        inDefinite.add("");
                        continue;
                    }
                    // else{
                    // if( ! result.contains(entry))
                    String str = finalNounList.get(j).toString();
                    //result.add(str);
                    inDefinite.add(str);
                }
                //}
            }
        }
        return inDefinite;
    }

    public List generateAnnexed(String rootStr, int formulaNo) {
        //API:
        sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.augmented.AugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                //for( int mode=1; mode <=3; mode++){
                List nounList = api.createNounList(root, formula.getFormulaNo());
                //modification
                PassiveParticipleModifier modifier =PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                ConjugationResult conjResult = modifier.build(root,formula.getFormulaNo(),kov, nounList);
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for (int j = 0; j < n; j++) {
                    if (finalNounList.get(j) == null) {
                        // result.add("");
                        annexed.add("");
                        continue;
                    }
                    // else{
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
        return true;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String [] args){
        PassiveParticipleGeneratorAu.getInstance().executeSimpleGenerator("زحزح", 1);
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
