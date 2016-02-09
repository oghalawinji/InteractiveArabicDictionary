package DictionaryBeans;

import BusinessLogicLayer.BOManager.NounBOManager;
import BusinessLogicLayer.BOManager.VerbBOManager;
import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.SearchService.LexicalSearch.IdiomSearch;
import Controller.*;
import DictionaryBeans.Util.BadWordException;
import DictionaryBeans.Util.BeansUtil;
import Util.RawNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author xp
 */
@Stateless
public class SearchBean {

    private String word = "";
    private boolean isRoot = false;
    private int i; // determine the user selection in first resualt page
    private int j; // determine the user selection in second resualt page ( determine one specific meaning )
    private int currentSemanticId;
    private String searchBy = "entry";
    private SearchController newSearchController;
    private NounBOManager newNounBOManager;
    private VerbBOManager newVerbBOManager;

    public SearchBean() {
        newSearchController = new SearchController();
        newNounBOManager = new NounBOManager();
        newVerbBOManager = new VerbBOManager();

    }

    /// <editor-fold defaultstate="collapsed" desc="getters and setters...">
    public SearchController getNewSearchController() {
        return newSearchController;
    }

    public void setNewSearchController(SearchController newSearchController) {
        this.newSearchController = newSearchController;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {

        this.searchBy = searchBy;
        if (searchBy.equals("root")) {
            setIsRoot(true);
        } else {
            setIsRoot(false);
        }

    }

    public int getCurrentSemanticId() {
        return currentSemanticId;
    }

    public void setCurrentSemanticId(int currentSemanticId) {
        this.currentSemanticId = currentSemanticId;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public void setWord(String word) throws BadWordException {
        this.word = BeansUtil.getFormatedText(word);
    }

    public boolean isIsRoot() {
        return isRoot;
    }

    public String getWord() {
        return word;
    }
    ///</editor-fold>

    public List<EntryBO> searchBy(String word, String searchType) throws UnsupportedEncodingException {
        List<EntryBO> results = SearchController.searchByEntry(word, (searchType.compareTo("root") == 0));

        List<EntryBO> verbs = new ArrayList<EntryBO>();
        List<EntryBO> nouns = new ArrayList<EntryBO>();
        List<EntryBO> particles = new ArrayList<EntryBO>();
        List<EntryBO> idioms = new ArrayList<EntryBO>();

        if (searchType.compareTo("root") == 0) {
            return results;
        }
        for (EntryBO entry : results) {
            if (entry instanceof VerbBO) {
                verbs.add((VerbBO) entry);
            }
            if (entry instanceof NounBO) {
                nouns.add((NounBO) entry);
            }
            if (entry instanceof ParticleBO) {
                particles.add((ParticleBO) entry);
            }
            if (entry instanceof IdiomBO) {
                idioms.add(entry);
            }
        }
        if (searchType.compareTo("verb") == 0) {
            return verbs;
        }
        if (searchType.compareTo("noun") == 0) {
            return nouns;
        }
        if (searchType.compareTo("particle") == 0) {
            return particles;
        }
        if (searchType.compareTo("idiom") == 0) {
            return idioms;
        }

        return null;

    }

    public List<NounBO> listAjaxNouns(String noun) throws RawNotFoundException {
        return newNounBOManager.fastListNounLike(noun, 10);
        //return newNounBOManager.listNounLike( noun );
    }

    public List<VerbBO> listAjaxVerbs(String verb) throws RawNotFoundException {
        return newVerbBOManager.fastListVerbLike(verb, 10);
    }

    public List<EntryBO> idiomSearch(String idiom) {
        return IdiomSearch.execute(idiom);
    }
}
