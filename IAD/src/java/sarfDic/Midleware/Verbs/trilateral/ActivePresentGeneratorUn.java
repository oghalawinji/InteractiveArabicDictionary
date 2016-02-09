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
import sarf.Validator;

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

    public List<String> executeSimpleGenerator(String root, String conjugation) {
        List<String> result = new ArrayList<String>();
        List alefAlternatives = Validator.getInstance().getTrilateralAlefAlternatives(root);
        if (!alefAlternatives.isEmpty()) {
            root = alefAlternatives.get(0).toString();
        }
        generateNominative(root, conjugation);
        generateEmphasized(root, conjugation);
        generateJussive(root, conjugation);
        generateAccusative(root, conjugation);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List generateNominative(String rootStr, String conjugation) {
        //Active Present Conjugator API:
        sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator api = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance();
        presentVerps = new ArrayList<String>();
        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        String presentRoot;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                // presentRoot= api.createNominativeVerb(7, root).toString();
                //presentVerps.add(presentRoot);
                List apvList1 = api.createNominativeVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
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
            }
        }
        return NominativePresent;
    }

    public List generateAccusative(String rootStr, String conjugation) {
        //Active Present Conjugator API:
        sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator api = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createAccusativeVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
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
                    // if( ! result.contains(apv1Str))
             
                    AccusativePresent.add(apv1Str);
                }
            }
        }
        return AccusativePresent;
    }

    public List generateEmphasized(String rootStr, String conjugation) {
        //Active Present Conjugator API:
        sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator api = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createEmphasizedVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
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
                    // if( ! result.contains(apv1Str))
             
                    EmphasizedPresent.add(apv1Str);
                }
            }
        }
        return EmphasizedPresent;
    }

    public List generateJussive(String rootStr, String conjugation) {
        //Active Present Conjugator API:
        sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator api = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List apvList1 = api.createJussiveVerbList(root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Present";
                boolean active = true;
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
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

                    if(kov==2){// مضعف
                    if (j == 0 || j == 1 || j == 2 || j == 7 || j == 8) {
                            apv1Str += " / " + apvList1.get(j).toString();
                        }
                }
                    
                    //if( ! result.contains(apv1Str))
            
                    JussivePresent.add(apv1Str);
                }
            }
        }
        return JussivePresent;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public static void main(String[] args) {
        List<String> tests = ActivePresentGeneratorUn.getInstance().executeSimpleGenerator("قلل","2");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
