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
public class PassivePresentGeneratorUn implements Generator {

    public static PassivePresentGeneratorUn getInstance() {
        return new PassivePresentGeneratorUn();
    }
    public List<String> nominative = new ArrayList<String>();
    public List<String> emphasized = new ArrayList<String>();
    public List<String> jussive = new ArrayList<String>();
    public List<String> accusative = new ArrayList<String>();

    public List<String> executeSimpleGenerator(String root, String conjugation) {
        List<String> result = new ArrayList<String>();
        generateNominative(root, conjugation);// Raf3
        generateEmphasized(root, conjugation);// Tawked
        generateJussive(root, conjugation);// Jazm
        generateAccusative(root, conjugation);// Nasb
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> generateNominative(String rootStr, String conjugation) {
        //Passive Present Conjugator API:
        sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createNominativeVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = false;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is PassivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        nominative.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    // if( ! result.contains(apv1Str))

                    nominative.add(apv1Str);
                }
            }
        }
        return nominative;
    }

    public List<String> generateAccusative(String rootStr, String conjugation) {
        //passive Present Conjugator API:
        sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createAccusativeVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = false;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is passivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        accusative.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))

                    accusative.add(apv1Str);
                }
            }
        }
        return accusative;
    }

    public List<String> generateEmphasized(String rootStr, String conjugation) {
        //passive Present Conjugator API:
        sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createEmphasizedVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = false;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is passivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        emphasized.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();
                    //if( ! result.contains(apv1Str))

                    emphasized.add(apv1Str);
                }
            }
        }
        return emphasized;
    }

    public List<String> generateJussive(String rootStr, String conjugation) {
        //passive Present Conjugator API:
        sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator api = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createJussiveVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = false;
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());

                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is passivePresentVerb
                int n = apvList.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {

                        jussive.add("");
                        continue;
                    }
                    String apv1Str = apvList.get(j).toString();

                    if (kov == 2) {// مضعف
                        if (j == 0 || j == 1 || j == 2 || j == 7 || j == 8) {
                            apv1Str += " / " + apvList1.get(j).toString();
                        }
                    }
                    jussive.add(apv1Str);
                }
            }
        }
        return jussive;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
