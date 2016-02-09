/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.quadrilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.List;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;

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

    public List<String> executeSimpleGenerator(String rootStr) {
        List<String> result = new ArrayList<String>();
        //Passive Past Conjugator API:
        sarf.verb.quadriliteral.unaugmented.passive.PassivePastConjugator api = sarf.verb.quadriliteral.unaugmented.passive.PassivePastConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        List apvList1 = api.createVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();

        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root, 1, kov, apvList1, tense, active);
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
        return passivePastList;

    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        List<String> tests = PassivePastGeneratorUn.getInstance().executeSimpleGenerator("دحرج");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
