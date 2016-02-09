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
import sarf.Validator;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;

/**
 *
 * @author riad
 */
public class ActivePresentGeneratorUn implements Generator {

    public static ActivePresentGeneratorUn getInstance() {
        return new ActivePresentGeneratorUn();
    }
    public List<String> presentVerps = new ArrayList<String>();
    public List<String> NominativePresent = new ArrayList<String>();
    public List<String> EmphasizedPresent = new ArrayList<String>();
    public List<String> JussivePresent = new ArrayList<String>();
    public List<String> AccusativePresent = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        List alefAlternatives = Validator.getInstance().getQuadrilateralAlefAlternatives(root);
        if (!alefAlternatives.isEmpty()) {
            root = alefAlternatives.get(0).toString();
        }
        generateNominative(root);
        generateEmphasized(root);
        generateJussive(root);
        generateAccusative(root);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List generateNominative(String rootStr) {
        //Active Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator.getInstance();
        presentVerps = new ArrayList<String>();
        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        String presentRoot;

        // presentRoot= api.createNominativeVerb(7, root).toString();
        //presentVerps.add(presentRoot);
        List apvList1 = api.createNominativeVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        presentRoot = apvList.get(7).toString();
        presentVerps.add(presentRoot);
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                NominativePresent.add("");
                continue;
            }
            //sarf.verb.trilateral.unaugmented.active.ActivePresentVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
            String apv1Str = apvList.get(j).toString();
            //if( ! result.contains(apv1Str))
            NominativePresent.add(apv1Str);
        }
        return NominativePresent;
    }

    public List generateAccusative(String rootStr) {
        //Active Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator.getInstance();
        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        // presentRoot= api.createNominativeVerb(7, root).toString();
        //presentVerps.add(presentRoot);
        List apvList1 = api.createAccusativeVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                AccusativePresent.add("");
                continue;
            }
            //sarf.verb.trilateral.unaugmented.active.ActivePresentVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
            String apv1Str = apvList.get(j).toString();
            //if( ! result.contains(apv1Str))
            AccusativePresent.add(apv1Str);
        }
        return AccusativePresent;
    }

    public List generateEmphasized(String rootStr) {
        //Active Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator.getInstance();
        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        // presentRoot= api.createNominativeVerb(7, root).toString();
        //presentVerps.add(presentRoot);
        List apvList1 = api.createEmphasizedVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                EmphasizedPresent.add("");
                continue;
            }
            //sarf.verb.trilateral.unaugmented.active.ActivePresentVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
            String apv1Str = apvList.get(j).toString();
            //if( ! result.contains(apv1Str))
            EmphasizedPresent.add(apv1Str);
        }
        return EmphasizedPresent;

    }

    public List generateJussive(String rootStr) {
        //Active Present Conjugator API:
        sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator api = sarf.verb.quadriliteral.unaugmented.active.ActivePresentConjugator.getInstance();
        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        // presentRoot= api.createNominativeVerb(7, root).toString();
        //presentVerps.add(presentRoot);
        List apvList1 = api.createJussiveVerbList(root);
        //modification
        sarf.verb.quadriliteral.modifier.QuadrilateralModifier modifier = sarf.verb.quadriliteral.modifier.QuadrilateralModifier.getInstance();
        String tense = "Present";
        boolean active = true;
        sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        sarf.verb.quadriliteral.ConjugationResult conjResult = modifier.build(root,1, kov, apvList1, tense, active);
        List apvList = conjResult.getFinalResult();
        //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePresentVerb
        int n = apvList.size();
        for (int j = 0; j < n; j++) {
            if (apvList.get(j) == null) {
                JussivePresent.add("");
                continue;
            }
            //sarf.verb.trilateral.unaugmented.active.ActivePresentVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePresentVerb) apvList.get(j);
            String apv1Str = apvList.get(j).toString();
            //if( ! result.contains(apv1Str))
            JussivePresent.add(apv1Str);
        }
        return JussivePresent;

    }

public static void main(String[] args) {
        List<String> tests = ActivePresentGeneratorUn.getInstance().executeSimpleGenerator("بءبء");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
