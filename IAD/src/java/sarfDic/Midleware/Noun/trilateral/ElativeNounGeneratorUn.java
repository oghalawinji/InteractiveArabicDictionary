/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import sarfDic.Midleware.KovRulesManager;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.trilateral.unaugmented.elative.ElativeSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;

/**
 *
 * @author Gowzancha
 */
public class ElativeNounGeneratorUn {

    public List<String> InDefiniteNouns = new ArrayList<String>();
    public List<String> DefiniteNouns = new ArrayList<String>();
    public List<String> AnnexedNouns = new ArrayList<String>();
    public List<String> NotAnnexedNouns = new ArrayList<String>();

    public static ElativeNounGeneratorUn getInstance() {
        return new ElativeNounGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, pattern);
        getInDefiniteNouns(root, pattern);
        getAnnexedNouns(root, pattern);
        getNotAnnexedNouns(root,pattern);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr, String pattern) {
        //API:
        ElativeNounConjugator api = ElativeNounConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                ElativeSuffixContainer.getInstance().selectDefiniteMode();

                List<String> formulas = api.getAppliedFormulaList(root);
                if (formulas != null) {
                    String formula = formulas.get(0);
                    List nounList = new ArrayList();
                    nounList = api.createNounList(root, formula);
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier modifier = sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, formula);
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            DefiniteNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //  if( ! result.contains(str))
                        //result.add(str);
                        DefiniteNouns.add(str);
                        // }

                    }
                }
            }
        }
        return DefiniteNouns;
    }

    public List getInDefiniteNouns(String rootStr, String pattern) {
        //API:
        ElativeNounConjugator api = ElativeNounConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                ElativeSuffixContainer.getInstance().selectInDefiniteMode();

                List<String> formulas = api.getAppliedFormulaList(root);
                if (formulas != null) {
                    String formula = formulas.get(0);
                    List nounList = new ArrayList();
                    nounList = api.createNounList(root, formula);
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier modifier = sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, formula);
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            InDefiniteNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //  if( ! result.contains(str))
                        //result.add(str);
                        InDefiniteNouns.add(str);
                        // }

                    }
                }
            }
        }
        return InDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr, String pattern) {
        //API:
        ElativeNounConjugator api = ElativeNounConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                ElativeSuffixContainer.getInstance().selectAnnexedMode();

                List<String> formulas = api.getAppliedFormulaList(root);

                for (int y = 0; y < formulas.size(); y++) {

                    String formula = formulas.get(y);
                    List nounList = new ArrayList();

                    nounList = api.createNounList(root, formula);

                    //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier modifier = sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            AnnexedNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //  if( ! result.contains(str))
                        //result.add(str);
                        AnnexedNouns.add(str);
                        // }

                    }
                }
            }
        }
        return AnnexedNouns;
    }

    public List getNotAnnexedNouns(String rootStr, String pattern) {
        //API:
        ElativeNounConjugator api = ElativeNounConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                ElativeSuffixContainer.getInstance().selectNotAnnexedMode();

                List<String> formulas = api.getAppliedFormulaList(root);

                for (int y = 0; y < formulas.size(); y++) {

                    String formula = formulas.get(y);
                    List nounList = new ArrayList();

                    nounList = api.createNounList(root, formula);

                    //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier modifier = sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier.getInstance();
                    KovRulesManager kovManager = KovRulesManager.getInstance();
                    int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                    ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            NotAnnexedNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //  if( ! result.contains(str))
                        //result.add(str);
                        NotAnnexedNouns.add(str);
                        // }

                    }
                }
            }
        }
        return NotAnnexedNouns;
    }




    public static void main(String[] args) {
        List<String> tests = ElativeNounGeneratorUn.getInstance().executeSimpleGenerator("حسن", "1");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
