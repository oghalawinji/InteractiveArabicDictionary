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
public class PassiveParticipleGeneratorUn implements Generator {
    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();
    public static PassiveParticipleGeneratorUn getInstance(){
        return new PassiveParticipleGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root ,String pattern) {
        List<String> result = new ArrayList<String>();
        getInDefiniteNouns(root, pattern);
        getDefiniteNouns(root, pattern);
        getAnnexedNouns(root, pattern);
        return result;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getDefiniteNouns(String rootStr,String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for( int i=0; i<list.size(); i++){
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot)list.get(i);
            if(root.getConjugation().equals(pattern)){
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            List nounList = api.createNounList(root,"");
            //for( int j=0; j<nounList.size();j++){
                //modification
                sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, nounList,"مَفْعُول");
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for( int k=0; k<n; k++){
                    if( finalNounList.get(k) == null){
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
        return definiteNouns;
    }
    public List getInDefiniteNouns(String rootStr,String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for( int i=0; i<list.size(); i++){
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot)list.get(i);
            if(root.getConjugation().equals(pattern)){
            GenericNounSuffixContainer.getInstance().selectInDefiniteMode();
            List nounList = api.createNounList(root,"");
           // for( int j=0; j<nounList.size();j++){
                //modification
                sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, nounList,"مَفْعُول");
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for( int k=0; k<n; k++){
                    if( finalNounList.get(k) == null){
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
        return inDefiniteNouns;
    }

    public List getAnnexedNouns(String rootStr,String pattern) {
        //Active Past Conjugator API:
        sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator api = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator.getInstance();

        //extract the root from dictionary:
        //the result will be a list of available forms for this unagmented verb(1st, 2nd, ....,6th).
        List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
        sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
        for( int i=0; i<list.size(); i++){
            root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot)list.get(i);
            if(root.getConjugation().equals(pattern)){
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            List nounList = api.createNounList(root,"");
            //for( int j=0; j<nounList.size();j++){
                //modification
                sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier modifier = sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance();
                KovRulesManager kovManager = KovRulesManager.getInstance();
                int kov = kovManager.getTrilateralKov(root.getC1(), root.getC2(), root.getC3());
                ConjugationResult conjResult = modifier.build(root, kov, nounList,"مَفْعُول");
                List finalNounList = conjResult.getFinalResult();

                int n = finalNounList.size();
                for( int k=0; k<n; k++){
                    if( finalNounList.get(k) == null){
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
        return annexedNouns;
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args){
        PassiveParticipleGeneratorUn.getInstance().executeSimpleGenerator("ذهب","3");
    }
}
