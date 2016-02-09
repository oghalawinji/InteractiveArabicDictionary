/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.SearchService.LexicalSearch;

import BusinessLogicLayer.BOManager.BOManagerFactory;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.BOManager.NounBOManager;
import BusinessLogicLayer.BOManager.ParticleBOManager;
import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import PersistenceLayer.Derivednoun;
import PersistenceLayer.Derivedparticle;
import PersistenceLayer.Derivedverb;
import PersistenceLayer.Root;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author riad
 */
public class SearchByRootManager {

    public static List<EntryBO> execute( String enteredRoot ) throws RawNotFoundException{
        return execute(enteredRoot, SearchProperties.detailedSearchOptions);
    }

    public static boolean valid(String root) {
        if( root.equals("___") ||  root.equals("+++"))
            return false;
        String sp = "*%";
        for( int i=0; i<root.length();i++){
            if( sp.indexOf(root.charAt(i)) == -1 )
                return true;
        }
        return false;
    }

    public static List<EntryBO> execute( String enteredRoot , SearchProperties options) throws RawNotFoundException{
        //delete spaces after and before the string:
        String root = enteredRoot.trim();
        if( root.equals(""))
            return null;

        //delete diacritics if exist:
        root = FilterDiacritics.execute(root);

        List<EntryBO> list = new ArrayList<EntryBO>();

        root = root.replace('+', '_');
        root = root.replace('*', '%');

        if( ! valid(root))
            return null;

        List<Root> rootList = BLUtil.daoFactory.getRootDAO().getRootsLike(root);
        if( rootList.isEmpty()){
            return null;
        }
        else{//existed entry
            //Retrieve raw Word:
            for( int i=0; i<rootList.size(); i++){
                Root rootObj = (Root) rootList.get(i);
                //Retrieve found verbs:
                Set<Derivedverb> derivedVerbsSet = rootObj.getDerivedverbs();
                List<Derivednoun> derivedVerbs = new ArrayList(derivedVerbsSet);
                Collections.sort(derivedVerbs, new CompareWordsBO());
                for( Iterator iter = derivedVerbs.iterator(); iter.hasNext();){
                    Derivedverb derivedVerb = (Derivedverb)iter.next();
                    VerbBO verbBO = BOManagerFactory.VERB_BOMANAGER.get( derivedVerb, options , "");
                    list.add(verbBO);
                }
                //Retrieve found nouns:
                Set<Derivednoun> derivedNounSet = rootObj.getDerivednouns();
                List<Derivednoun> derivedNouns = new ArrayList(derivedNounSet);
                Collections.sort(derivedNouns, new CompareWordsBO());
                for( Iterator iter = derivedNouns.iterator(); iter.hasNext();){
                    Derivednoun derivedNoun = (Derivednoun)iter.next();
                    NounBO nounBO = NounBOManager.getNounBO( derivedNoun,options,"" );
                    list.add(nounBO);
                }
                //Retrieve found particles:
                Set<Derivedparticle> derivedParticlesSet = rootObj.getDerivedparticles();
                List<Derivednoun> derivedParticles = new ArrayList(derivedParticlesSet);
                Collections.sort(derivedParticles, new CompareWordsBO());
                for( Iterator iter = derivedParticles.iterator(); iter.hasNext();){
                    Derivedparticle derivedParticle = (Derivedparticle)iter.next();
                    ParticleBO particleBO = ParticleBOManager.getParticleBO( derivedParticle,options);
                    list.add(particleBO);
                }
            }
            return list;
        }
    }
}
