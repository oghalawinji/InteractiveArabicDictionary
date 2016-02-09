/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.SearchService.LexicalSearch;

import BusinessLogicLayer.BOManager.BOManagerFactory;
import BusinessLogicLayer.BOManager.ExampleBOManager;
import BusinessLogicLayer.BOManager.IdiomBOManager;
import BusinessLogicLayer.BOManager.ImageManager;
import BusinessLogicLayer.BOManager.NounBOManager;
import BusinessLogicLayer.BOManager.ParticleBOManager;
import BusinessLogicLayer.BOManager.SemanticNounBOManager;
import BusinessLogicLayer.BOManager.SemanticParticleBOManager;
import BusinessLogicLayer.BOManager.SemanticVerbBOManager;
import BusinessLogicLayer.BOManager.SoundManager;
import BusinessLogicLayer.BOManager.VerbBOManager;
import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.Stemmer.SpellChecking;
import Util.RawNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class LexicalSearchManager {

    public static List<EntryBO> execute(String enteredString) throws RawNotFoundException {
        return execute(enteredString, SearchProperties.detailedSearchOptions);
    }

    public static List<EntryBO> execute(String enteredString, SearchProperties options) throws RawNotFoundException {
        //delete spaces after and before the string:
        String str = enteredString.trim();
        if (str.equals("")) {
            return null;
        }

        //Determine the type of the lexical search:
        if (str.indexOf(' ') == -1)//single word.
        {
            return SingleWordSearch.execute(str, options);
        } else//more than one word.
        {
            return IdiomSearch.execute(str, options);
        }

    }

    public static EntryBO getDerivedWordInfos(Integer derivedWordId, String type) {
        if (type.equals("verb")) {
            try {
                return BOManagerFactory.VERB_BOMANAGER.get(derivedWordId, SearchProperties.detailedSearchOptions, "");
            } catch (RawNotFoundException ex) {
                Logger.getLogger(LexicalSearchManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (type.equals("noun")) {
            return NounBOManager.getNounBO(derivedWordId, SearchProperties.detailedSearchOptions, "");
        }
        if (type.equals("particle")) {
            return ParticleBOManager.getParticleBO(derivedWordId, SearchProperties.detailedSearchOptions);
        }
        if (type.equals("idiom")) {
            try {
                return IdiomBOManager.getIdiom(derivedWordId); //Idiom(SearchProperties.commonSearchOptions );//ParticleBO(derivedWordId, SearchProperties.commonSearchOptions);
            } catch (RawNotFoundException ex) {
                Logger.getLogger(LexicalSearchManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }//Idiom(SearchProperties.commonSearchOptions );//ParticleBO(derivedWordId, SearchProperties.commonSearchOptions);
        } else {
            return null;
        }
    }

    public static List<ExampleBO> getExamples(Integer semanticEntryId) {
        return ExampleBOManager.getExamplesOfSemanticEntry(semanticEntryId);
    }

    public static List<byte[]> getImages(Integer semanticEntryId) {
        return ImageManager.getImage(semanticEntryId);
    }

    public static List<byte[]> getSoundOfExample(Integer exampleId) {
        return SoundManager.getSoundOfExample(exampleId);
    }

    public static SemanticEntryBO getRelatedWord(String type, Integer semanticWordId) {
        if (type.equals("verb")) {
            return SemanticVerbBOManager.getSemanticVerbBO(semanticWordId, SearchProperties.AjaxRelatedWordSearchOptions);
        }
        if (type.equals("noun")) {
            return SemanticNounBOManager.getSemanticNounBO(semanticWordId, SearchProperties.AjaxRelatedWordSearchOptions);
        }
        if (type.equals("particle")) {
            return SemanticParticleBOManager.getSemanticParticleBO(semanticWordId, SearchProperties.AjaxRelatedWordSearchOptions);
        } else {
            return null;
        }
    }

    public static EntryBO getSemanticWordInfos(Integer derivedWordId, String type, Integer semanticWordId) {
        if (type.equals("verb")) {
            return BOManagerFactory.VERB_BOMANAGER.get(derivedWordId, semanticWordId, SearchProperties.detailedSearchOptions, "");
        }
        if (type.equals("noun")) {
            return NounBOManager.getNounBO(derivedWordId, semanticWordId, SearchProperties.detailedSearchOptions, "");
        }
        if (type.equals("particle")) {
            return ParticleBOManager.getParticleBO(derivedWordId, semanticWordId, SearchProperties.detailedSearchOptions);
        } else {
            return null;
        }
    }

    public static List<String> getOptions(String word, boolean byRoot) {
        return SpellChecking.findOptions(word, byRoot);
    }

}
