/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Verbs.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import sarf.SarfDictionary;
import sarf.Validator;
import sarfDic.Midleware.KovRulesManager;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;

/**
 *
 * @author riad
 */
public class ActivePastGeneratorUn implements Generator {

    public static ActivePastGeneratorUn getInstance() {
        return new ActivePastGeneratorUn();
    }
    public List<Integer> formolaNo = new ArrayList<Integer>();
    public List<String> pastVerps = new ArrayList<String>();

    public List<String> processTrilateral(String root) {
        List alefAlternatives = Validator.getInstance().getTrilateralAlefAlternatives(root);
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(root);
        formolaNo = new ArrayList<Integer>();
        // if (alefAlternatives.isEmpty()) {
        //لا يوجد احتمالات للألف
        //   AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(root);
        // List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(root);
        return unaugmentedList;
        //  }
        //     else return null;
    }

    public List<String> executeSimpleGenerator(String rootStr, String conjugation) {
        List<String> result = new ArrayList<String>();
        pastVerps = new ArrayList<String>();
        List alefAlternatives = Validator.getInstance().getTrilateralAlefAlternatives(rootStr);
        if (!alefAlternatives.isEmpty()) {
            rootStr = alefAlternatives.get(0).toString();
        }
        //Active Past Conjugator API:
        sarf.verb.trilateral.unaugmented.active.ActivePastConjugator api = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        UnaugmentedTrilateralRoot root;
        String pastRoot;
        int forme;
        List apvList1 = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            //  pastRoot= api.createVerb(7, root).toString();
            //pastVerps.add(pastRoot);
            if (root.getConjugation().equals(conjugation)) {
                forme = Integer.parseInt(root.getConjugation()) - 1;
                formolaNo.add(forme);

                apvList1 = api.createVerbList(root);
                api.createVerb(7, root);
                //modification
                sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier modifier = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance();
                String tense = "Past";
                boolean active = true;
                //untitled.KovRulesManager ff= KovRulesManager.getInstance();
                sarfDic.Midleware.KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                // int kov = sarf.kov.KovRulesManager.getInstance().getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                sarf.verb.trilateral.unaugmented.ConjugationResult conjResult = modifier.build(root, kov, apvList1, tense, active);
                List apvList = conjResult.getFinalResult();
                pastRoot = apvList.get(7).toString();
                pastVerps.add(pastRoot);

                //apvList.size=12 <==> 12 Arabic pronoun, each item in this list is ActivePastVerb
                int n = apvList1.size();
                for (int j = 0; j < n; j++) {
                    if (apvList.get(j) == null) {
                        result.add("");
                        continue;
                    }
                    // sarf.verb.trilateral.unaugmented.active.ActivePastVerb apv1 = (sarf.verb.trilateral.unaugmented.active.ActivePastVerb) apvList.get(j);
                    String apv1Str = apvList.get(j).toString();
                    // result.add(apv1Str);
                    //if( ! result.contains(apv1Str))
                    result.add(apv1Str);
                }
            }
        }
        return result;
        //return apvList1;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
