/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;

/**
 *
 * @author riad
 */
public class ActiveParticipleGeneratorUn implements Generator {

    public List<String> InDefiniteNouns = new ArrayList<String>();
    public List<String> DefiniteNouns = new ArrayList<String>();
    public List<String> AnnexedNouns = new ArrayList<String>();

    public static ActiveParticipleGeneratorUn getInstance() {
        return new ActiveParticipleGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, pattern);
        getInDefiniteNouns(root, pattern);
        getAnnexedNouns(root, pattern);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr, String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {// here we should determine the conjugation
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                List nounList = api.createNounList(root, "");
                //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, "فَاعِل");
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            DefiniteNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //if( ! result.contains(str))
                        //result.add(str);
                        DefiniteNouns.add(str);
                    }
                //}
            }
        }
        return DefiniteNouns;
    }

    public List getInDefiniteNouns(String rootStr, String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                List nounList = api.createNounList(root, "");
                //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, "فَاعِل");
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            InDefiniteNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        // if( ! result.contains(str))
                        // result.add(str);
                        InDefiniteNouns.add(str);
                    }
                //}
            }
        }
        return InDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr, String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                List nounList = api.createNounList(root, "");
               // for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, "فَاعِل");
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            // result.add("");
                            AnnexedNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        // if( ! result.contains(str))
                        // result.add(str);
                        AnnexedNouns.add(str);
                    }
               // }
            }
        }
        return AnnexedNouns;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveParticipleGeneratorUn.getInstance().executeSimpleGenerator("ذهب", "4");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
