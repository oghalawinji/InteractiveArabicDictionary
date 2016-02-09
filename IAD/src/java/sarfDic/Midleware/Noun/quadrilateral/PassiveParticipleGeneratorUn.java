/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.quadrilateral;

import sarfDic.Midleware.Generator;
import sarfDic.Midleware.KovRulesManager;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.quadriliteral.modifier.passiveparticiple.*;
import sarf.verb.quadriliteral.ConjugationResult;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;

/**
 *
 * @author riad
 */
public class PassiveParticipleGeneratorUn implements Generator {

    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();

    public static PassiveParticipleGeneratorUn getInstance() {
        return new PassiveParticipleGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        getInDefiniteNouns(root);
        getDefiniteNouns(root);
        getAnnexedNouns(root);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr) {
        //API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        List nounList = api.createNounList(root);
        //for( int j=0; j<nounList.size();j++){
        //modification
        PassiveParticipleModifier modifier = PassiveParticipleModifier.getInstance();
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, nounList);
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
        return definiteNouns;
    }


    public List getInDefiniteNouns(String rootStr) {
        //API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
        List nounList = api.createNounList(root);
        //for( int j=0; j<nounList.size();j++){
        //modification
        PassiveParticipleModifier modifier = PassiveParticipleModifier.getInstance();
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, nounList);
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
        return inDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr) {
           //API:
        sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator api = sarf.noun.quadriliteral.unaugmented.UnaugmentedQuadriliteralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        UnaugmentedQuadriliteralRoot root = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootStr);

        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        List nounList = api.createNounList(root);
        //for( int j=0; j<nounList.size();j++){
        //modification
        PassiveParticipleModifier modifier = PassiveParticipleModifier.getInstance();
        KovRulesManager kovManager = KovRulesManager.getInstance();
        int kov = kovManager.getQuadrilateralKov(root.getC1(), root.getC2(), root.getC3(),root.getC4());
        ConjugationResult conjResult = modifier.build(root, 0, kov, nounList);
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
        return annexedNouns;
    }
  

    public static void main(String[] args) {
        PassiveParticipleGeneratorUn.getInstance().executeSimpleGenerator("زحزح");
    }
}
