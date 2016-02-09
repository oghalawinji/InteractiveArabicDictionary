/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.SearchService.LexicalSearch;

import BusinessLogicLayer.BOManager.BOManagerFactory;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.BOManager.NounBOManager;
import BusinessLogicLayer.BOManager.ParticleBOManager;
import BusinessLogicLayer.BOManager.VerbBOManager;
import BusinessLogicLayer.BusinessObjects.*;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import PersistenceLayer.*;
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
class SingleWordSearch {

    public static List<EntryBO> execute(String inputWord) throws RawNotFoundException {
        return execute(inputWord, SearchProperties.detailedSearchOptions);
    }

    public static List<EntryBO> execute(String inputWord, SearchProperties options) throws RawNotFoundException {
        //delete diacritics if exist:
        String filteredWord = FilterDiacritics.execute(inputWord);
        //search for unvocalized word:
        List<EntryBO> list = unvocalizedWordSearch(filteredWord, options);
        //unvalide search like '*'
        if (list == null) {
            return null;
        }
        //entry does not exist Case:
        if (list.isEmpty()) {
            return UnfoundEntrySearch.execute(filteredWord);
        }
        //if the input word is not vocalized return all results
        //else filter the results according to diacritics.
        if (filteredWord.equals(inputWord)) {
            return list;
        } else {
            return applyDiacritics(inputWord, list);
        }
    }

    private static boolean valid(String filteredWord) {
        String sp = "*%_+";
        for (int i = 0; i < filteredWord.length(); i++) {
            if (sp.indexOf(filteredWord.charAt(i)) == -1) {
                return true;
            }
        }
        return false;
    }

    private static List<EntryBO> unvocalizedWordSearch(String filteredWord1, SearchProperties options) throws RawNotFoundException {
        //check if the word is existed:
        //List<Rawword> rawWordList = BLUtil.daoFactory.getRawwordDAO().getByExample( new Rawword(filteredWord) );
        String filteredWord = filteredWord1.replace('+', '_');
        filteredWord = filteredWord.replace('*', '%');
        if (!valid(filteredWord)) {
            return null;
        }

        List<Rawword> rawWordList = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredWord);
        List<EntryBO> list = new ArrayList<EntryBO>();
        //unfound word:
        if (rawWordList.isEmpty()) {
            return list;
        } //found word case:
        else {
            for (int i = 0; i < rawWordList.size(); i++) {
                Rawword rawWord = rawWordList.get(i);
                SearchForVerbs(rawWord, list, options);
                SearchForNouns(rawWord, list, options);
                SearchForParticles(rawWord, list, options);
            }
            return list;
        }
    }

    private static void SearchForNouns(Rawword rawWord, List list, SearchProperties options) throws RawNotFoundException {
        //Retrieve Derived Nouns:
        Set<Derivednoun> derivedNounSet = rawWord.getDerivednouns();
        List<Derivednoun> derivedNounList = new ArrayList(derivedNounSet);
        Collections.sort(derivedNounList, new CompareWordsBO());
        for (Iterator iter = derivedNounList.iterator(); iter.hasNext();) {
            Derivednoun derivednoun = (Derivednoun) iter.next();
            NounBO noun = NounBOManager.getNounBO(derivednoun, options, "");
            //if( ! noun.getType().equals("مصدر") && ! noun.getNumber().equals("جمع") && ! noun.getNumber().equals("مثنى"))
            {
                //Save raw word infos:
                noun.setRawWordId(rawWord.getIdentity());
                noun.setRawWord(rawWord.getRawWord());
                list.add(noun);
            }
        }
    }

    private static void SearchForVerbs(Rawword rawWord, List list, SearchProperties options) {
        //Retrieve Derived Verbs:
        Set<Derivedverb> derivedVerbSet = rawWord.getDerivedverbs();
        List<Derivednoun> derivedVerbList = new ArrayList(derivedVerbSet);
        Collections.sort(derivedVerbList, new CompareWordsBO());

        for (Iterator iter = derivedVerbList.iterator(); iter.hasNext();) {
            Derivedverb derivedVerb = (Derivedverb) iter.next();
            VerbBO verb = BOManagerFactory.VERB_BOMANAGER.get(derivedVerb, options, "");
            //Save raw word infos:
            if (verb != null) {
                verb.setRawWordId(rawWord.getIdentity());
            }
            verb.setRawWord(rawWord.getRawWord());
            list.add(verb);
        }
    }

    private static void SearchForParticles(Rawword rawWord, List list, SearchProperties options) {
        //Retrieve Derived Particles:
        Set<Derivedparticle> derivedParticleSet = rawWord.getDerivedparticles();
        List<Derivednoun> derivedParticleList = new ArrayList(derivedParticleSet);
        Collections.sort(derivedParticleList, new CompareWordsBO());

        for (Iterator iter = derivedParticleList.iterator(); iter.hasNext();) {
            Derivedparticle derivedParticle = (Derivedparticle) iter.next();
            ParticleBO particle = ParticleBOManager.getParticleBO(derivedParticle, options);
            //Save raw word infos:
            particle.setRawWordId(rawWord.getIdentity());
            particle.setRawWord(rawWord.getRawWord());
            list.add(particle);
        }
    }

    private static List<EntryBO> applyDiacritics(String inputWord, List<EntryBO> list) {
        for (int i = 0; i < list.size(); i++) {
            EntryBO entry = list.get(i);
            String resultStr = entry.getVocalizedString();
            if (!accepted(resultStr, inputWord)) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    private static boolean accepted(String firstStr, String secondStr) {
        String firstLetters = FilterDiacritics.execute(firstStr);
        String secondLetters = FilterDiacritics.execute(secondStr);
        if (!firstLetters.equals(secondLetters)) {
            return false;
        }

        String str1 = firstStr;
        String str2 = secondStr;
        for (int letter = 0; letter < firstLetters.length(); letter++) {
            char ch = firstLetters.charAt(letter);

            String d1 = "";
            while (str1.charAt(0) != ch) {
                d1 += "" + str1.charAt(0);
                str1 = str1.substring(1);
            }

            String d2 = "";
            while (str2.charAt(0) != ch) {
                d2 += "" + str2.charAt(0);
                str2 = str2.substring(1);
            }

            if (!d1.equals(d2) && !d1.contains(d2) && !d2.contains(d1)) {
                return false;
            }

            str1 = str1.substring(1);
            str2 = str2.substring(1);
        }
        if (!str1.equals(str2) && !str1.contains(str2) && !str2.contains(str1)) {
            return false;
        }
        return true;
    }

}
