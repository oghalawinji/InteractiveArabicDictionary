 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.quadrilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.KovRulesManager;
import sarfDic.Midleware.SarfDictionary;
import sarf.SystemConstants;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;

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

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        generateNormalImperative(root);
        generateEmphasized(root);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List generateEmphasized(String rootStr) {
        //Active Imperative Conjugator API:
        sarf.verb.quadriliteral.unaugmented.UnaugmentedImperativeConjugator api = sarf.verb.quadriliteral.unaugmented.UnaugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:        
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        List apvList1 = api.createEmphasizedVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier =sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = SystemConstants.EMPHASIZED_IMPERATIVE_TENSE;
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
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
        return emphasizedImperative;
    }

    public List generateNormalImperative(String rootStr) {
        //Active Imperative Conjugator API:
        sarf.verb.quadriliteral.unaugmented.UnaugmentedImperativeConjugator api = sarf.verb.quadriliteral.unaugmented.UnaugmentedImperativeConjugator.getInstance();

        //extract the root from dictionary:
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        List apvList1 = api.createVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier =sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE;
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
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
        return normalImperative;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveImperativeGeneratorUn.getInstance().executeSimpleGenerator("دحرج");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }

    
}
