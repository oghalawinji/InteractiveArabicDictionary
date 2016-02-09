/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.trilateral.unaugmented.modifier.ConjugationResult;
//import sarf.noun.trilateral.unaugmented.timeandplace.TimeAndPlaceConjugator;

/**
 *
 * @author riad
 */
public class TimeAndPlaceGeneratorUn implements Generator {

    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();

    public static TimeAndPlaceGeneratorUn getInstance() {
        return new TimeAndPlaceGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String conjugation, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, conjugation, formulaNo);
        getInDefiniteNouns(root, conjugation, formulaNo);
        getAnnexedNouns(root, conjugation, formulaNo);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr, String conjugation, int formulaNo) {
        //Active Past Conjugator API:
        TimeAndPlaceConjugator api = TimeAndPlaceConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
                List<String> formulas = nounsObject.getTimeAndPlaces();
                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = api.createNounList(root, formulas.get(f));
                        //for( int j=0; j<nounList.size();j++){
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier modifier = sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier.getInstance();
                        sarfDic.Midleware.KovRulesManager kovManager =sarfDic.Midleware.KovRulesManager.getInstance();
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
                            //if( ! result.contains(str))
                            //result.add(str);
                            definiteNouns.add(str);
                        }
                    }
                }
            }
        }
        return definiteNouns;
    }

    public List getInDefiniteNouns(String rootStr, String conjugation, int formulaNo) {
        //API:
        TimeAndPlaceConjugator api = TimeAndPlaceConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
                List formulas = nounsObject.getTimeAndPlaces();
                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = api.createNounList(root, formulas.get(f).toString());
                        //for( int j=0; j<nounList.size();j++){
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier modifier = sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier.getInstance();
                        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                        int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                        ConjugationResult conjResult = modifier.build(root, kov, nounList, formulas.get(f).toString());
                        List finalNounList = conjResult.getFinalResult();

                        int n = finalNounList.size();
                        for (int k = 0; k < n; k++) {
                            if (finalNounList.get(k) == null) {
                                //result.add("");
                                inDefiniteNouns.add("");
                                continue;
                            }
                            String str = finalNounList.get(k).toString();
                            //if( ! result.contains(str))
                            //result.add(str);
                            inDefiniteNouns.add(str);
                        }
                    }
                }
            }
        }
        return inDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr, String conjugation, int formulaNo) {
        //Active Past Conjugator API:
        TimeAndPlaceConjugator api = TimeAndPlaceConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
                List<String> formulas = nounsObject.getTimeAndPlaces();
                for (int f = 0; f < formulas.size(); f++) {
                    if (f == formulaNo) {
                        List nounList = api.createNounList(root, formulas.get(f));
                        //for( int j=0; j<nounList.size();j++){
                        //modification
                        sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier modifier = sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier.getInstance();
                        sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
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
                            //if( ! result.contains(str))
                            //result.add(str);
                            annexedNouns.add(str);
                        }
                    }
                }
            }
        }
        return annexedNouns;
    }

    private void setMode(int mode) {
        if (mode == 1) {
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        }
        if (mode == 2) {
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        }
        if (mode == 3) {
            GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        }

    }

    public List getFormulas(String rootStr, String conjugation) {
        List formulas = new ArrayList();
        try {
            List list = sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
            sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;

            for (int i = 0; i < list.size(); i++) {
                root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
                if (root.getConjugation().equals(conjugation)) {

                    TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);

                    formulas = nounsObject.getTimeAndPlaces();

                }
            }
            return formulas;
        } catch (Exception ex) {
            formulas.add(ex.toString());
            return formulas;
        }
    }

    public static void main(String[] args) {
        TimeAndPlaceGeneratorUn.getInstance().executeSimpleGenerator("ذهب", "3",0);

    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
