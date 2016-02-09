 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.KovRulesManager;
import sarfDic.Midleware.SarfDictionary;
import sarf.SystemConstants;

/**
 *
 * @author riad
 */
public class ActiveImperativeGeneratorUn implements Generator {

    public static ActiveImperativeGeneratorUn getInstance() {
        return new ActiveImperativeGeneratorUn();
    }
    public List<String> normalImperative = new ArrayList<String>();
    public List<String> emphasizedImperative = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        generateNormalImperative(root, pattern);
        generateEmphasized(root, pattern);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List generateEmphasized(String rootStr, String pattern) {
        //Active Imperative Conjugator API:
        sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator api = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                List apvList1 = api.createEmphasizedVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = SystemConstants.EMPHASIZED_IMPERATIVE_TENSE;
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActiveImperativeVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                       
                        emphasizedImperative.add("");
                        continue;
                    }
                    //sarf.verb.trilateral.unaugmented.active.ActiveImperativeVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))
                   
                    emphasizedImperative.add(apv1Str);
                }
            }
        }
        return emphasizedImperative;
    }

    public List generateNormalImperative(String rootStr, String pattern) {
        //Active Imperative Conjugator API:
        sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator api = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                List apvList1 = api.createVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE;
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActiveImperativeVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                       
                        normalImperative.add("");
                        continue;
                    }
                    //sarf.verb.trilateral.unaugmented.active.ActiveImperativeVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))
                    
                    normalImperative.add(apv1Str);
                }
            }
        }
        return normalImperative;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveImperativeGeneratorUn.getInstance().executeSimpleGenerator("حكم","5");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
