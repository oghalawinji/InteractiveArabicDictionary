/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.trilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class PassivePastGeneratorUn implements Generator {

    public String tense = "Past";
    public boolean active = false;
    public List<String> passivePastList = new ArrayList<String>();

    public static PassivePastGeneratorUn getInstance() {
        return new PassivePastGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String rootStr,String conjugation) {
        List<String> result = new ArrayList<String>();
        //Passive Past Conjugator API:
        sarf.verb.trilateral.unaugmented.passive.PassivePastConjugator api = sarf.verb.trilateral.unaugmented.passive.PassivePastConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root = null;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();

                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is passivePastVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                        result.add("");
                        passivePastList.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))
                    result.add(apv1Str);
                    passivePastList.add(apv1Str);
                }
            }
        }
        return passivePastList;

    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = PassivePastGeneratorUn.getInstance().executeSimpleGenerator("حكم","5");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i+"-"+tests.get(i));
        }
    }
}
