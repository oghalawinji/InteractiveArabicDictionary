/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.gerund.trilateral.unaugmented.StandardTrilateralUnaugmentedSuffixContainer;

/**
 *
 * @author Gowzancha
 */
public class StandardGerundGeneratorUn implements Generator {

    public List<String> InDefiniteGerunds = new ArrayList<String>();
    public List<String> DefiniteGerunds = new ArrayList<String>();
    public List<String> AnnexedGerunds = new ArrayList<String>();

    public static StandardGerundGeneratorUn getInstance() {
        return new StandardGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root, String conjugation, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getDefiniteGerunds(root, conjugation, formulaNo);
        getInDefiniteGerunds(root, conjugation, formulaNo);
        getAnnexedGerunds(root, conjugation, formulaNo);
        return result;
    }

    public List<String> getDefiniteGerunds(String rootStr, String conjugation, int formulaNo) {
        TrilateralUnaugmentedGerundConjugator api = TrilateralUnaugmentedGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List formulas = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < formulas.size(); j++) {
                    if (j == formulaNo) {
                        StandardTrilateralUnaugmentedSuffixContainer.getInstance().selectDefiniteMode();
                        List gerundList = api.createGerundList(root, formulas.get(j).toString());

                        int n = gerundList.size();
                        for (int k = 0; k < n; k++) {
                            if (gerundList.get(k) == null) {
                                //result.add("");
                                DefiniteGerunds.add("");
                                continue;
                            }
                            String str = gerundList.get(k).toString();

                            //result.add(str);
                            DefiniteGerunds.add(str);
                        }
                    }
                }
            }
        }
        return DefiniteGerunds;
    }

    public List<String> getInDefiniteGerunds(String rootStr, String conjugation, int formulaNo) {
        TrilateralUnaugmentedGerundConjugator api = TrilateralUnaugmentedGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List formulas = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < formulas.size(); j++) {
                    if (j == formulaNo) {
                        StandardTrilateralUnaugmentedSuffixContainer.getInstance().selectInDefiniteMode();
                        List gerundList = api.createGerundList(root, formulas.get(j).toString());

                        int n = gerundList.size();
                        for (int k = 0; k < n; k++) {
                            if (gerundList.get(k) == null) {// ???!!!
                                //result.add("");
                                InDefiniteGerunds.add("");
                                continue;
                            }
                            String str = gerundList.get(k).toString();

                            //result.add(str);
                            InDefiniteGerunds.add(str);
                        }
                    }
                }
            }
        }
        return InDefiniteGerunds;
    }

    public List<String> getAnnexedGerunds(String rootStr, String conjugation, int formulaNo) {
       TrilateralUnaugmentedGerundConjugator api = TrilateralUnaugmentedGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                List formulas = api.getAppliedFormulaList(root);// list of gernuds patterns for this root
                for (int j = 0; j < formulas.size(); j++) {
                    if (j == formulaNo) {
                        StandardTrilateralUnaugmentedSuffixContainer.getInstance().selectAnnexedMode();
                        List gerundList = api.createGerundList(root, formulas.get(j).toString());

                        int n = gerundList.size();
                        for (int k = 0; k < n; k++) {
                            if (gerundList.get(k) == null) {
                                //result.add("");
                                AnnexedGerunds.add("");
                                continue;
                            }
                            String str = gerundList.get(k).toString();
                            //result.add(str);
                            AnnexedGerunds.add(str);
                        }
                    }
                }
            }
        }
        return AnnexedGerunds;
    }

     public List getFormulas(String rootStr, String conjugation) {
        try {
        TrilateralUnaugmentedGerundConjugator api = TrilateralUnaugmentedGerundConjugator.getInstance();

        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        List formulas=new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
            if (root.getConjugation().equals(conjugation)) {
                formulas = api.getAppliedFormulaList(root);
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
        //List<String> tests = StandardGerundGeneratorUn.getInstance().executeSimpleGenerator("ذهب", "3", 4);
        List<String> tes = StandardGerundGeneratorUn.getInstance().getFormulas("ذهب", "4");

       /* for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }*/
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
