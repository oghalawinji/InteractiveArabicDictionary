/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.quadrilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import java.util.Collection;
import java.util.Iterator;
import sarf.AugmentationFormula;
import sarf.noun.GenericNounSuffixContainer;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;

/**
 *
 * @author Gowzancha
 *
 *  There is an ERROR:
 * 
 */


public class NomenGerundGeneratorAu {

    public List<String> InDefiniteNomenGerunds = new ArrayList<String>();
    public List<String> DefiniteNomenGerunds = new ArrayList<String>();
    public List<String> AnnexedNomenGerunds = new ArrayList<String>();

    public static NomenGerundGeneratorAu getInstance() {
        return new NomenGerundGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo) {
        List<String> result = new ArrayList<String>();
        getInDefiniteNomenGerunds(root, formulaNo);
        getDefiniteNomenGerunds(root, formulaNo);
        getAnnexedNomenGerunds(root, formulaNo);
        return result;
    }

    public List<String> getDefiniteNomenGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedNomenGerundConjugator api = QuadriliteralAugmentedNomenGerundConjugator.getInstance();
        AugmentedQuadriliteralRoot root=SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);
                
                    int n = gerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (gerundList.get(k) == null) {
                            //result.add("");
                            DefiniteNomenGerunds.add("");
                            continue;
                        }
                        String str = gerundList.get(k).toString();
                        //result.add(str);
                        DefiniteNomenGerunds.add(str);
                    }
                }
            }
        
        return DefiniteNomenGerunds;
    }

    public List<String> getInDefiniteNomenGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedNomenGerundConjugator api = QuadriliteralAugmentedNomenGerundConjugator.getInstance();
        AugmentedQuadriliteralRoot root=SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                    int n = gerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (gerundList.get(k) == null) {
                            //result.add("");
                            InDefiniteNomenGerunds.add("");
                            continue;
                        }
                        String str = gerundList.get(k).toString();
                        //result.add(str);
                        InDefiniteNomenGerunds.add(str);
                    }
                }
            }

        return InDefiniteNomenGerunds;
    }

    public List<String> getAnnexedNomenGerunds(String rootStr, int formulaNo) {
        QuadriliteralAugmentedNomenGerundConjugator api = QuadriliteralAugmentedNomenGerundConjugator.getInstance();
        AugmentedQuadriliteralRoot root=SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootStr);
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        Collection<AugmentationFormula> formulas = root.getAugmentationList();
        for (Iterator iter = formulas.iterator(); iter.hasNext();) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            if (formula.getFormulaNo() == formulaNo) {
                List gerundList = api.createGerundList(root, formulaNo);

                    int n = gerundList.size();
                    for (int k = 0; k < n; k++) {
                        if (gerundList.get(k) == null) {
                            //result.add("");
                            AnnexedNomenGerunds.add("");
                            continue;
                        }
                        String str = gerundList.get(k).toString();
                        //result.add(str);
                        AnnexedNomenGerunds.add(str);
                    }
                }
            }

        return AnnexedNomenGerunds;
    }

    public static void main(String[] args) {
        List<String> tests = NomenGerundGeneratorAu.getInstance().executeSimpleGenerator("زحزح",1);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
