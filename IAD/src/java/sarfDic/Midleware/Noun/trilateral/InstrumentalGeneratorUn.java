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
public class InstrumentalGeneratorUn implements Generator {

    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();

    public static InstrumentalGeneratorUn getInstance() {
        return new InstrumentalGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String conjugation, int formulaNo) {
        List<String> result = new ArrayList<String>();
        //getDefiniteStendardNouns(root, conjugation);
        getInDefiniteNouns(root, conjugation, formulaNo);
        //getAnnexedStendardNouns(root, conjugation);
        return result;
    }

    public List getInDefiniteNouns(String rootStr, String conjugation, int formulaNo) {
        //API:
        sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator apiSta = sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator.getInstance();
        NonStandardInstrumentalConjugator apiNon = NonStandardInstrumentalConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectInDefiniteMode();

                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);

                List<String> formulasSta = nounsObject.getStandardInstrumentals();
                List<String> formulasNon = nounsObject.getNonStandardInstrumentals();
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = new ArrayList();
                        if (f <= 3) {
                            nounList = apiSta.createNounList(root, formulas.get(f));
                        } else {
                            nounList = apiNon.createNounList(root, formulas.get(f));
                        }

                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier modifier = sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(f));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                inDefiniteNouns.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //result.add(str);
                            inDefiniteNouns.add(str);

                        }
                    }
                }
            }
        }
        return inDefiniteNouns;
    }

    public List getDefiniteNouns(String rootStr, String conjugation, int formulaNo) {
        //API:
        sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator apiSta = sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator.getInstance();
        NonStandardInstrumentalConjugator apiNon = NonStandardInstrumentalConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();

                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);

                List<String> formulasSta = nounsObject.getStandardInstrumentals();
                List<String> formulasNon = nounsObject.getNonStandardInstrumentals();
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = new ArrayList();
                        if (f <= 3) {
                            nounList = apiSta.createNounList(root, formulas.get(f));
                        } else {
                            nounList = apiNon.createNounList(root, formulas.get(f));
                        }

                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier modifier = sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(f));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                definiteNouns.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //result.add(str);
                            definiteNouns.add(str);

                        }
                    }
                }
            }
        }
        return definiteNouns;
    }

    public List getAnnexedNouns(String rootStr, String conjugation, int formulaNo) {
        //API:
        sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator apiSta = sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator.getInstance();
        NonStandardInstrumentalConjugator apiNon = NonStandardInstrumentalConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectAnnexedMode();

                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);

                List<String> formulasSta = nounsObject.getStandardInstrumentals();
                List<String> formulasNon = nounsObject.getNonStandardInstrumentals();
                List<String> formulas = new ArrayList<String>();

                for (int f = 0; f < formulasSta.size(); f++) {
                    formulas.add(formulasSta.get(f));
                }
                for (int f = 0; f < formulasNon.size(); f++) {
                    formulas.add(formulasNon.get(f));
                }

                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = new ArrayList();
                        if (f <= 3) {
                            nounList = apiSta.createNounList(root, formulas.get(f));
                        } else {
                            nounList = apiNon.createNounList(root, formulas.get(f));
                        }

                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier modifier = sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier.getInstance();
                        KovRulesManager kovManager = KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(f));
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                annexedNouns.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //result.add(str);
                            annexedNouns.add(str);

                        }
                    }
                }
            }
        }
        return annexedNouns;
    }

    public List getFormulas(String rootStr, String pattern) {

        try {

            List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
            sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

            List<String> formulas = new ArrayList<String>();
            
            for (int i = 0; i < list.size(); i++) {
                root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);

                if (root.getConjugation().equals(pattern)) {

                    TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
                    List<String> formulasSta = nounsObject.getStandardInstrumentals();
                    List<String> formulasNon = nounsObject.getNonStandardInstrumentals();

                    for (int f = 0; f < formulasSta.size(); f++) {
                        formulas.add(formulasSta.get(f));
                    }

                    for (int f = 0; f < formulasNon.size(); f++) {
                        formulas.add(formulasNon.get(f));
                    }

                }
            }
            return formulas;
        } catch (Exception ex) {
            List formulas = new ArrayList();
            formulas.add(ex.toString());
            return formulas;
        }

    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        InstrumentalGeneratorUn.getInstance().executeSimpleGenerator("حسب", "1", 4);
    }
}
