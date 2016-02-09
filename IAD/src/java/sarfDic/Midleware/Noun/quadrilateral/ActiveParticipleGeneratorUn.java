/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.quadrilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.quadriliteral.modifier.activeparticiple.*;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;
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

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root);
        getInDefiniteNouns(root);
        getAnnexedNouns(root);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr) {
        //Active Past Conjugator API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);
        
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                List nounList = api.createNounList(root);
                //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                    ConjugationResult conjResult = modifier.build(root, 0,kov, nounList);
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
                    
                //}
            }        
        return DefiniteNouns;
    }

    public List getInDefiniteNouns(String rootStr) {
        //Active Past Conjugator API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

                GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
                List nounList = api.createNounList(root);
                //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                    ConjugationResult conjResult = modifier.build(root, 0,kov, nounList);
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            InDefiniteNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //if( ! result.contains(str))
                        //result.add(str);
                        InDefiniteNouns.add(str);

                //}
            }
        return InDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr) {
       //Active Past Conjugator API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralActiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

                GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                List nounList = api.createNounList(root);
                //for (int j = 0; j < nounList.size(); j++) {
                    //modification
                    ActiveParticipleModifier modifier = ActiveParticipleModifier.getInstance();
                    sarfDic.Midleware.KovRulesManager kovManager = sarfDic.Midleware.KovRulesManager.getInstance();
                    int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
                    ConjugationResult conjResult = modifier.build(root, 0,kov, nounList);
                    List finalNounList = conjResult.getFinalResult();

                    int n = finalNounList.size();
                    for (int k = 0; k < n; k++) {
                        if (finalNounList.get(k) == null) {
                            //result.add("");
                            AnnexedNouns.add("");
                            continue;
                        }
                        String str = finalNounList.get(k).toString();
                        //if( ! result.contains(str))
                        //result.add(str);
                        AnnexedNouns.add(str);

                //}
            }
        return AnnexedNouns;
    }

    public static void main(String[] args) {
        List<String> tests = ActiveParticipleGeneratorUn.getInstance().executeSimpleGenerator("زحزح");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + "-" + tests.get(i));
        }
    }
    
}
