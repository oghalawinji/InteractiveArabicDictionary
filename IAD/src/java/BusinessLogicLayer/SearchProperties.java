/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer;

/**
 *
 * @author riad
 */
public class SearchProperties {

    //defiened options:
    public static SearchProperties simpleSearchOptions = SearchProperties.getSimpleSearchOptions();
    public static SearchProperties commonSearchOptions = SearchProperties.getCommonSearchOption();
    public static SearchProperties detailedSearchOptions = SearchProperties.getDetailedLexicalSearchOption();
    public static SearchProperties LimitedWordNetSearchOptions = SearchProperties.getLimitedWordNetSearchOption();
    public static SearchProperties AjaxRelatedWordSearchOptions = SearchProperties.getRelatedWordSearchOption();
    public static SearchProperties CompleteWordNetSearchOptions = SearchProperties.getCompleteWordNetSearchOption();

    //find semantic infos:
    public boolean FindSemanticInformations = false;
    public boolean FindMeanings = false;
    public boolean FindExamples = false;
    public boolean FindCommonMistakes = false;
    public boolean FindLinguisticBenifits = false;
    public boolean FindRelatedIdiom = false;
    public boolean FindNoun = false;

    //find semantic groups:
    public boolean FindRelatedAssimilatedAdjectives = false;
    public boolean FindRelatedExaggerations = false;
    public boolean FindRelatedGerunds = false;
    public boolean FindNounVerbAccompaniers = false;
    public boolean FindNounAdjectiveAccompaniers = false;
    public boolean FindNounDiminutives = false;
    public boolean FindProperAdjectives = false;
    public boolean FindAnnexedNouns = false;
    public boolean FindFimininities = false;
    public boolean FindPlural = false;
    //Multimedia:
    public boolean FindImages = false;
    public boolean FindSounds = false;
    public boolean FindVideos = false;
    //semantic:
    public int MaxElementsInSemanticScope = -1;
    public boolean FindSemanticScopeElements = false;
    public boolean FindSemanticScopeRelations = false;

    private void SearchProperties() {
    }

    private static SearchProperties getTitlesSearchOptions() {
        return new SearchProperties();
    }

    private static SearchProperties getSimpleSearchOptions() {
        SearchProperties options = getTitlesSearchOptions();
        options.FindSemanticInformations = true;
        options.FindMeanings = true;
        options.FindNoun = true;
        return options;
    }

    private static SearchProperties getCommonSearchOption() {
        SearchProperties options = getSimpleSearchOptions();
        options.FindExamples = true;

        options.FindImages = true;
        options.FindSounds = true;
        options.FindVideos = true;

        return options;
    }

    private static SearchProperties getRelatedWordSearchOption() {
        SearchProperties options = getTitlesSearchOptions();
        options.FindSemanticInformations = true;
        options.FindMeanings = true;
        options.FindRelatedAssimilatedAdjectives = true;
        options.FindRelatedExaggerations = true;
        options.FindNounVerbAccompaniers = true;
        options.FindNounAdjectiveAccompaniers = true;
        options.FindAnnexedNouns = true;
        return options;

    }

    private static SearchProperties getDetailedLexicalSearchOption() {
        SearchProperties options = getCommonSearchOption();

        options.FindCommonMistakes = true;
        options.FindLinguisticBenifits = true;
        options.FindRelatedIdiom = true;

        options.FindRelatedAssimilatedAdjectives = true;
        options.FindRelatedExaggerations = true;
        options.FindRelatedGerunds = true;
        options.FindNounVerbAccompaniers = true;
        options.FindNounAdjectiveAccompaniers = true;
        options.FindNounDiminutives = true;
        options.FindProperAdjectives = true;
        options.FindAnnexedNouns = true;
        options.FindFimininities = true;
        options.FindPlural = true;

        options.FindImages = true;
        options.FindSounds = true;
        options.FindVideos = true;

        return options;
    }

    private static SearchProperties getLimitedWordNetSearchOption() {
        SearchProperties options = getSimpleSearchOptions();
        options.MaxElementsInSemanticScope = 5;
        options.FindSemanticScopeElements = true;
        options.FindSemanticScopeRelations = true;
        return options;
    }

    private static SearchProperties getCompleteWordNetSearchOption() {
        SearchProperties options = getLimitedWordNetSearchOption();
        options.MaxElementsInSemanticScope = -1;
        return options;
    }
}
