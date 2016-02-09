/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware.Verbs.trilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sarf.AugmentationFormula;
import sarf.SystemConstants;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.ConjugationResult;
import sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;

/**
 *
 * @author riad
 */
public class ActivePastGeneratorAu implements Generator,AugmentedTrilateralModifierListener{

    public static ActivePastGeneratorAu getInstance(){
        return new ActivePastGeneratorAu();
    }
    public List<String> pasts = new ArrayList<String>();
    public List<Integer> forms = new ArrayList<Integer>();
    public boolean vocalization=false;

    public List<String> executeSimpleGenerator(String rootStr,int formulaNo,boolean vocal) {
        vocalization=vocal;
        List<String> result = new ArrayList<String>();
        
        //Active Past Conjugator API:
        sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator api = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        AugmentedTrilateralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootStr);
        Collection formulas = root.getAugmentationList();
        

        for( Iterator iter = formulas.iterator();  iter.hasNext(); ){
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if(formula.getFormulaNo()==formulaNo){
            forms.add(formula.getFormulaNo());
            List list = AugmentedActivePastConjugator.getInstance().createVerbList(root, formula.getFormulaNo());
            pasts.add(list.get(7).toString());
            //modification
            sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier modifier = sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier.getInstance();
            boolean active = true;
            String tense = SystemConstants.PAST_TENSE;
            sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
            int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

            ConjugationResult conjResult = modifier.build(root, kov, formula.getFormulaNo(), list, tense, active, ActivePastGeneratorAu.this);
            List apvList = conjResult.getFinalResult();

            //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePastVerb
            int n = apvList.size();
            for( int j=0; j<n; j++){
                if( apvList.get(j) == null){
                    result.add("");
                    continue;
                }
                //sarf.verb.trilateral.unaugmented.active.ActivePastVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePastVerb) apvList.get(j);
                String apv1Str = apvList.get(j).toString();
              //  if( ! result.contains(apv1Str))
                result.add(apv1Str);
            }
        }
        }
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean doSelectVocalization() {
        return vocalization;
    }

    public static void main(String[] args) {
        List<String> tests = ActivePastGeneratorAu.getInstance().executeSimpleGenerator("جوب",9,false);
        for(int i=0;i<tests.size();i++){
        System.out.println(tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
