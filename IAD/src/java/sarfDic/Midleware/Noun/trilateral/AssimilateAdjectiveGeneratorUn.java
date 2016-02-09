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
import sarf.noun.trilateral.unaugmented.assimilate.*;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;

/**
 *
 * @author Gowzancha
 */
public class AssimilateAdjectiveGeneratorUn {

    public List<String> InDefiniteNouns = new ArrayList<String>();
    public List<String> DefiniteNouns = new ArrayList<String>();
    public List<String> AnnexedNouns = new ArrayList<String>();

    public static AssimilateAdjectiveGeneratorUn getInstance() {
        return new AssimilateAdjectiveGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, pattern, formulaNo);
        getInDefiniteNouns(root, pattern, formulaNo);
        getAnnexedNouns(root, pattern, formulaNo);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr, String pattern, int formulaNo) {
        //API:
        AssimilateAdjectiveConjugator api = AssimilateAdjectiveConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                AssimilateFormulaCSuffixContainer.getInstance().selectDefiniteMode();
                AssimilateFormulaE1SuffixContainer.getInstance().selectDefiniteMode();
                AssimilateFormulaE2SuffixContainer.getInstance().selectDefiniteMode();

                List<String> formulas = api.getAppliedFormulaList(root);

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();

                        nounList = api.createNounList(root, formula);

                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier modifier = sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
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
        }
        return DefiniteNouns;
    }

    public List getInDefiniteNouns(String rootStr, String pattern, int formulaNo) {
        //API:
        AssimilateAdjectiveConjugator api = AssimilateAdjectiveConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                AssimilateFormulaCSuffixContainer.getInstance().selectInDefiniteMode();
                AssimilateFormulaE1SuffixContainer.getInstance().selectDefiniteMode();
                AssimilateFormulaE2SuffixContainer.getInstance().selectDefiniteMode();

                List<String> formulas = api.getAppliedFormulaList(root);

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();

                        nounList = api.createNounList(root, formula);

                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier modifier = sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
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
        }
        return InDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr, String pattern, int formulaNo) {
        //API:
        AssimilateAdjectiveConjugator api = AssimilateAdjectiveConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                AssimilateFormulaCSuffixContainer.getInstance().selectAnnexedMode();
                AssimilateFormulaE1SuffixContainer.getInstance().selectDefiniteMode();
                AssimilateFormulaE2SuffixContainer.getInstance().selectDefiniteMode();

                List<String> formulas = api.getAppliedFormulaList(root);

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();

                        nounList = api.createNounList(root, formula);

                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier modifier = sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier.getInstance();
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
        }
        return AnnexedNouns;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getFormulas(String rootStr, String pattern) {
        //API:
        AssimilateAdjectiveConjugator api = AssimilateAdjectiveConjugator.getInstance();
        List formulas = new ArrayList();
        //extract the root from dictionary:
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                formulas = api.getAppliedFormulaList(root);
            }
        }
        return formulas;
    }

    public static void main(String[] args) {
        List<String> tests = AssimilateAdjectiveGeneratorUn.getInstance().executeSimpleGenerator("ءسف", "4", 2);
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
}
