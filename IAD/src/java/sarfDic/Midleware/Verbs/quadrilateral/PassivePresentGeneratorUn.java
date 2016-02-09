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
public class PassivePresentGeneratorUn implements Generator {

    public static PassivePresentGeneratorUn getInstance() {
        return new PassivePresentGeneratorUn();
    }
    public List<String> nominative = new ArrayList<String>();
    public List<String> emphasized = new ArrayList<String>();
    public List<String> jussive = new ArrayList<String>();
    public List<String> accusative = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        generateNominative(root);
        generateEmphasized(root);
        generateJussive(root);
        generateAccusative(root);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> generateNominative(String rootStr) {
        //Passive Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        List apvList1 = api.createNominativeVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = false;
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root, 1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is PassivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                nominative.add("");
                continue;
            }
            String apv1Str = apvList.get(j).toString();
            nominative.add(apv1Str);
        }
        return nominative;
    }

    public List<String> generateAccusative(String rootStr) {
     //Passive Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        List apvList1 = api.createAccusativeVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = false;
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root, 1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is PassivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                accusative.add("");
                continue;
            }
            String apv1Str = apvList.get(j).toString();
            accusative.add(apv1Str);
        }
        return accusative;
    }

    public List<String> generateEmphasized(String rootStr) {
        //Passive Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        List apvList1 = api.createEmphasizedVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = false;
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root, 1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is PassivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                emphasized.add("");
                continue;
            }
            String apv1Str = apvList.get(j).toString();
            emphasized.add(apv1Str);
        }
        return emphasized;
    }

    public List<String> generateJussive(String rootStr) {
        //Passive Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        List apvList1 = api.createJussiveVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = false;
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(), root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root, 1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is PassivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                jussive.add("");
                continue;
            }
            String apv1Str = apvList.get(j).toString();
            jussive.add(apv1Str);
        }
        return jussive;
    }


    public static void main(String[] args){
         List<String> tests = PassivePresentGeneratorUn.getInstance().executeSimpleGenerator("دحرج");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
