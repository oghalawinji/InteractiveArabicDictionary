/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.*;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;
//import sarf.noun.trilateral.unaugmented.exaggeration.*;

/**
 *
 * @author riad
 */
public class ExaggerationGeneratorUn implements Generator {

    public List<String> definiteExaggeration = new ArrayList<String>();
    public List<String> inDefiniteExaggeration = new ArrayList<String>();
    public List<String> annexedExaggeration = new ArrayList<String>();

    public static ExaggerationGeneratorUn getInstance() {
        return new ExaggerationGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String pattern, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, pattern, formulaNo);
        getInDefiniteNouns(root, pattern, formulaNo);
        getAnnexedNouns(root, pattern, formulaNo);
        //  getDefiniteNonStendardNouns(root, result);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr, String pattern, int formulaNo) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator apiSta = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance();
        NonStandardExaggerationConjugator apiNon = NonStandardExaggerationConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();

                List<String> formulasSta = apiSta.getAppliedFormulaList(root);
                List<String> formulasNon = apiNon.getAppliedFormulaList(root);
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();
                        if (y == 0) { // Standard
                            nounList = apiSta.createNounList(root, formula);
                        } else { // NonStandard
                            nounList = apiNon.createNounList(root, formula);
                        }
                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier modifier = sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                definiteExaggeration.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //  if( ! result.contains(str))
                            //result.add(str);
                            definiteExaggeration.add(str);
                            // }
                        }
                    }
                }
            }
        }
        return definiteExaggeration;
    }

    public List getInDefiniteNouns(String rootStr, String pattern, int formulaNo) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator apiSta = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance();
        NonStandardExaggerationConjugator apiNon = NonStandardExaggerationConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectInDefiniteMode();

                List<String> formulasSta = apiSta.getAppliedFormulaList(root);
                List<String> formulasNon = apiNon.getAppliedFormulaList(root);
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();
                        if (y == 0) {
                            nounList = apiSta.createNounList(root, formula);
                        } else {
                            nounList = apiNon.createNounList(root, formula);
                        }
                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier modifier = sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                inDefiniteExaggeration.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //  if( ! result.contains(str))
                            //result.add(str);
                            inDefiniteExaggeration.add(str);
                            // }
                        }
                    }
                }
            }
        }
        return inDefiniteExaggeration;
    }

    public List getAnnexedNouns(String rootStr, String pattern, int formulaNo) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator apiSta = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance();
        NonStandardExaggerationConjugator apiNon = NonStandardExaggerationConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(pattern)) {
                GenericNounSuffixContainer.getInstance().selectAnnexedMode();

                List<String> formulasSta = apiSta.getAppliedFormulaList(root);
                List<String> formulasNon = apiNon.getAppliedFormulaList(root);
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int y = 0; y < formulas.size(); y++) {
                    if (y == formulaNo) {
                        String formula = formulas.get(y);
                        List nounList = new ArrayList();
                        if (y == 0) {
                            nounList = apiSta.createNounList(root, formula);
                        } else {
                            nounList = apiNon.createNounList(root, formula);
                        }
                        //for (int j = 0; j < nounList.size(); j++) {
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier modifier = sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(y));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                annexedExaggeration.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //  if( ! result.contains(str))
                            //result.add(str);
                            annexedExaggeration.add(str);
                            // }
                        }
                    }
                }
            }
        }
        return annexedExaggeration;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getFormulas(String rootStr, String pattern) {

        try {
            sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator apiSta = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance();
            sarfDic.Midleware.Noun.trilateral.NonStandardExaggerationConjugator apiNon = sarfDic.Midleware.Noun.trilateral.NonStandardExaggerationConjugator.getInstance();

            //extract the root from dictionary:
            //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
            List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
            sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

            List formulasSta = new ArrayList();
            List formulasNon = new LinkedList<String>();
            List formulas = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
                if (root.getConjugation().equals(pattern)) {

                    formulasSta = apiSta.getAppliedFormulaList(root);
                    
                    formulasNon = apiNon.getAppliedFormulaList(root);

                    //if (formulasSta != null) {
                        for (int f = 0; f < formulasSta.size(); f++) {
                            formulas.add(formulasSta.get(f));
                        }
                    //}
                    //if (formulasNon != null) {
                        for (int f = 0; f < formulasNon.size(); f++) {
                            formulas.add(formulasNon.get(f));
                        }
                    //}
                }
            }
            return formulas;
        } catch (Exception ex) {
            List formulas = new ArrayList();
            formulas.add(ex.toString());
            return formulas;
        }

    }

    public static void main(String[] args) {
        //List<String> tests = ExaggerationGeneratorUn.getInstance().executeSimpleGenerator("ركض", "1",0);
        List foormulas = ExaggerationGeneratorUn.getInstance().getFormulas("ركض", "1");
        /*        for (int i = 0; i < tests.size(); i++) {
        System.out.println(i + "-" + tests.get(i));
        }*/
    }
}

