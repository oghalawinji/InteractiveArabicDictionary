/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import BusinessLogicLayer.BusinessObjects.*;
import BusinessLogicLayer.BusinessObjects.SemanticScopeBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.SearchService.LexicalSearch.LexicalSearchManager;
import BusinessLogicLayer.SearchService.LexicalSearch.SearchByRootManager;
import BusinessLogicLayer.SearchService.SemanticSearch.WordNetSearch;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.FilterDiacritics;
import PersistenceLayer.Root;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class SearchController {

    public static List<EntryBO> searchByEntry(String rawWordX, boolean byRoot) throws UnsupportedEncodingException {
        try {
            String rawWord = rawWordX;//new String(rawWordX.getBytes(),"CP1256");
            if (byRoot) {
                return (SearchByRootManager.execute(rawWord, SearchProperties.simpleSearchOptions));
            } else {
                return (LexicalSearchManager.execute(rawWord, SearchProperties.simpleSearchOptions));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<EntryBO>();
        }
    }

    public static EntryBO getDerivedWordInfos(Integer derivedWordId, String type) {
        try {
            return LexicalSearchManager.getDerivedWordInfos(derivedWordId, type);
        } catch (Exception ex) {
            return null;
        }
    }

    public static EntryBO getSemanticWordInfos(Integer derivedWordId, String type, Integer semanticWordId) {
        try {
            return LexicalSearchManager.getSemanticWordInfos(derivedWordId, type, semanticWordId);
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<ExampleBO> getExamples(Integer semanticEntryId) {
        try {
            return LexicalSearchManager.getExamples(semanticEntryId);
        } catch (Exception ex) {
            return null;
        }
    }

    public static SemanticEntryBO getRelatedWords(String type, Integer semanticWordId) {
        try {
            return LexicalSearchManager.getRelatedWord(type, semanticWordId);
        } catch (Exception ex) {
            return null;
        }
    }

    public static SemanticScopeBO getWordNet(Integer semanticEntryId) {
        try {
            return WordNetSearch.execute(semanticEntryId, SearchProperties.LimitedWordNetSearchOptions);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(ex.getStackTrace());
            return null;
        }
    }

    public static byte[] getImages(Integer semanticEntryId) {
        try {
            List<byte[]> list = LexicalSearchManager.getImages(semanticEntryId);
            if (list == null) {
                return null;
            }

            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static byte[] getSoundForExample(Integer exampleId) {
        try {
            List<byte[]> list = LexicalSearchManager.getSoundOfExample(exampleId);
            if (list == null) {
                return null;
            }

            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<String> getOptions(String word, boolean type) {
        return LexicalSearchManager.getOptions(word, type);
    }

    public static List<String> getRoots(String newRoot) {
        String root = newRoot.trim();
        if (root.equals("")) {
            return null;
        }

        //delete diacritics if exist:
        root = FilterDiacritics.execute(root);

        List<EntryBO> list = new ArrayList<EntryBO>();

        root = root.replace('+', '_');
        root = root.replace('*', '%');

        if (!BusinessLogicLayer.SearchService.LexicalSearch.SearchByRootManager.valid(root)) {
            return null;
        }

        List<Root> rootList = BLUtil.daoFactory.getRootDAO().getRootsLike(root);
        List<String> rootStringsList = new ArrayList<String>();
        for (Root r : rootList) {
            rootStringsList.add(r.getRoot());
        }

        return rootStringsList;

    }
}
