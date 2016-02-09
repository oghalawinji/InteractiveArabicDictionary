/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import BusinessLogicLayer.BusinessObjects.AssimilateAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.DiminutiveBO;
import BusinessLogicLayer.BusinessObjects.ExaggerationBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.FemininityBO;
import BusinessLogicLayer.BusinessObjects.GerundBO;
import rest.lightObject.ResultItemType;
import rest.lightObject.ResultItem;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.PluralNounBO;
import BusinessLogicLayer.BusinessObjects.ProperAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.SemanticEntryBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.BusinessObjects.SemanticParticleBO;
import BusinessLogicLayer.BusinessObjects.SemanticVerbBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import Controller.SearchController;
import java.util.ArrayList;
import java.util.List;
import rest.lightObject.Idiom;
import rest.lightObject.Noun;
import rest.lightObject.Particle;
import rest.lightObject.SemanticNounTitle;
import rest.lightObject.SemanticParticleTitle;
import rest.lightObject.SemanticVerbTitle;
import rest.lightObject.Verb;

/**
 *
 * @author Omar
 */
public class RESTUtils {

    public static ResultItem getVerbResultItem(VerbBO verbEntry) {
        ResultItem item = new ResultItem(verbEntry.getDerivedVerbId(),
                verbEntry.getVocalizedVerb() + " " + verbEntry.getPresentForm(),
                ResultItemType.VERB,
                "");
        List<SemanticVerbBO> semanList = verbEntry.getSemanticCases();
        String description = "";
        for (int j = 0; j < semanList.size() && j < 1; j++) {
            List<MeaningBO> meaning = semanList.get(j).getMeanings();
            String str = meaning.get(0).getDescription();//+" :: " +meaning.get(0).getSource() + "</li>"
            String sp = semanList.get(j).getSpecialization();
            if (!sp.equals("_")) {
                str = "(في " + sp + ") " + str;
            }
            description += str;
        }
        item.setDescription(description);
        return item;
    }

    public static ResultItem getNounResultItem(NounBO nounEntry) {
        ResultItem item = new ResultItem(nounEntry.getDerivedNounId(),
                nounEntry.getVocalizedNoun() + " (" + nounEntry.getType() + ") ",
                ResultItemType.NOUN,
                "");
        String description = "";
        List<SemanticNounBO> semanList = nounEntry.getSemanticCases();
        for (int j = 0; j < semanList.size() && j < 1; j++) {
            List<MeaningBO> meaning = semanList.get(j).getMeanings();
            String str = "";
            if (!meaning.isEmpty()) {
                String mm = meaning.get(0).getDescription();
                if (mm.contains("جمع للاسم")
                        || mm.contains("صفة مشبهة لفعل")
                        || mm.contains("مبالغة للفعل")
                        || mm.contains("مصدر لفعل")
                        || mm.contains("اسم منسوب للاسم ")) {
                    String[] list = mm.split(" ");
                    for (int w = 0; w < list.length - 1; w++) {
                        str += list[w] + " ";
                    }
                    str += list[list.length - 1];
                } else {
                    str = mm;
                }
                description += str;
            }
        }
        item.setDescription(description);
        return item;
    }

    public static ResultItem getIdiomResultItem(IdiomBO idiomEntry) {
        ResultItem item = new ResultItem(idiomEntry.getIdiomId(),
                idiomEntry.getVocalizedIdiom(),
                ResultItemType.IDIOM,
                idiomEntry.getSemanticCase()
                .getMeanings()
                .get(0)
                .getDescription());
        return item;
    }

    public static ResultItem getParticleResultItem(ParticleBO particleEntry) {
        ResultItem item = new ResultItem(particleEntry.getDerivedParticleId(),
                particleEntry.getVocalizedParticle() + " (" + particleEntry.getParticletype() + ") ",
                ResultItemType.PARTICLE,
                "");
        String description = "";
        List<SemanticParticleBO> semanList = particleEntry.getSemanticCases();
        for (int j = 0; j < semanList.size() && j < 1; j++) {
            List<MeaningBO> meaning = semanList.get(j).getMeanings();
            description += meaning.get(0).getDescription();
        }
        item.setDescription(description);
        return item;
    }

    public static ResultItem getOptionResultItem(String option) {
        ResultItem item = new ResultItem(0,
                option,
                ResultItemType.OPTION,
                "");
        return item;
    }

    public static Verb getLightVerb(VerbBO vbo) {
        Verb lightVerb = new Verb();
        lightVerb.setId(vbo.getDerivedVerbId());
        lightVerb.setPattern(vbo.getPattern());
        lightVerb.setPresent(vbo.getPresentForm());
        lightVerb.setRoot(vbo.getRoot());
        lightVerb.setVocalized(vbo.getVocalizedVerb());
        lightVerb.setSemanticTitles(new ArrayList<SemanticVerbTitle>());
        List<SemanticVerbBO> cases = vbo.getSemanticCases();
        for (int i = 0; i < RESTConfig.Paging.PAGE_SIZE && i < cases.size(); i++) {
            lightVerb.getSemanticTitles().add(RESTUtils.getSemanticVerbTitle(vbo.getDerivedVerbId(), cases.get(i)));
        }

        return lightVerb;
    }

    private static SemanticVerbTitle getSemanticVerbTitle(Integer derivedWordId, SemanticVerbBO semVerb) {
        VerbBO vvbo = (VerbBO) SearchController.getSemanticWordInfos(derivedWordId, "verb", semVerb.getSemanticVerbId());
        SemanticVerbBO semVb = vvbo.getSemanticCases().get(0);
        List<GerundBO> gerunds = semVb.getAlteredGerunds();
        List<String> gerundsList = new ArrayList<String>();
        for (GerundBO gerund : gerunds) {
            gerundsList.add(gerund.getGerund().getVocalizedNoun());
        }

        String meaning = "";

        if (!semVb.getMeanings()
                .isEmpty()) {
            MeaningBO meaning1 = semVb.getMeanings().get(0);
            String str = meaning1.getDescription();
            String sp = semVb.getSpecialization();
            if (!sp.equals("_")) {
                str = "(في " + sp + ") " + str;
            }
            meaning = str + "[" + semVb.getTransitivityCase() + "][" + meaning1.getSource() + "]";
        }

        SemanticVerbTitle ret = new SemanticVerbTitle(
                semVerb.getSemanticEntryId(),
                semVerb.getSemanticVerbId(),
                gerundsList,
                meaning);
        List<String> examples = new ArrayList<String>();
        List<ExampleBO> examps = semVb.getExamples();

        for (int k = 0; k < examps.size() && k < 3; k++) {
            examples.add(examps.get(k).getExample() + "[" + examps.get(k).getSource() + "]");
        }
        ret.setExamples(examples);

        ret.setIdioms(semVb.getRelatedidioms());

        List<LinguisticBenefitBO> benefits = semVb.getLinguisticBenefits();
        List<String> benfs = new ArrayList<String>();
        for (LinguisticBenefitBO benefit : benefits) {
            benfs.add(benefit.getDescription() + "[" + benefit.getSource() + "]");
        }
        ret.setBenefits(benfs);

        List<CommonMistakeBO> mistakes = semVb.getCommonMistakes();
        List<String> mistakesList = new ArrayList<String>();
        for (CommonMistakeBO mistake : mistakes) {
            mistakesList.add(mistake.getDescription() + "[" + mistake.getSource() + "]");
        }
        ret.setCommonMistakes(mistakesList);

        //
        List<AssimilateAdjectiveBO> assimilateAdjs = semVb.getAssimilateAdjectives();
        List<String> assimList = new ArrayList<String>();

        for (AssimilateAdjectiveBO assimilateAdj : assimilateAdjs) {
            assimList.add(assimilateAdj.getAssimilateAdjective().getVocalizedNoun());
        }
        ret.setAssimilatedAdjs(assimList);

        List<ExaggerationBO> exaggerations = semVb.getExaggerations();
        List<String> exagList = new ArrayList<String>();
        for (ExaggerationBO exaggeration : exaggerations) {
            exagList.add(exaggeration.getExaggeration().getVocalizedNoun());
        }
        ret.setExaggerations(exagList);
        return ret;
    }

    public static List<SemanticVerbTitle> getSemanticTitlesPageForVerb(VerbBO vbo, int start) {
        List<SemanticVerbTitle> ret = new ArrayList<SemanticVerbTitle>();
        List<SemanticVerbBO> cases = vbo.getSemanticCases();
        for (int i = start; i < start + RESTConfig.Paging.PAGE_SIZE && i < cases.size(); i++) {
            ret.add(RESTUtils.getSemanticVerbTitle(vbo.getDerivedVerbId(), cases.get(i)));
        }
        return ret;
    }

    public static Noun getLightNoun(NounBO nbo) {
        Noun lightNoun = new Noun();
        lightNoun.setId(nbo.getDerivedNounId());
        lightNoun.setType(nbo.getType());
        lightNoun.setVocalized(nbo.getVocalizedNoun());
        lightNoun.setPattern(nbo.getPattern());
        lightNoun.setRoot(nbo.getRoot());
        lightNoun.setGender(nbo.getGender());
        lightNoun.setNumber(nbo.getNumber());
        lightNoun.setOrigin(nbo.getOrigin());
        lightNoun.setSemanticTitles(new ArrayList<SemanticNounTitle>());
        List<SemanticNounBO> cases = nbo.getSemanticCases();
        for (int i = 0; i < RESTConfig.Paging.PAGE_SIZE && i < cases.size(); i++) {
            lightNoun.getSemanticTitles().add(RESTUtils.getSemanticNounTitle(nbo.getDerivedNounId(), cases.get(i)));
        }

        return lightNoun;
    }

    private static SemanticNounTitle getSemanticNounTitle(Integer derivedWordId, SemanticNounBO semNoun1) {
        NounBO nou = (NounBO) SearchController.getSemanticWordInfos(derivedWordId, "noun", semNoun1.getSemanticNounId());
        SemanticNounBO semNoun = nou.getSemanticCases().get(0);
        List<MeaningBO> meaning = semNoun.getMeanings();
        SemanticNounTitle ret = new SemanticNounTitle();
        String meaningStr = "";
        for (MeaningBO meaning1 : meaning) {
            String mm = meaning1.getDescription();
            String str = "";
            if (mm.contains("جمع للاسم")
                    || mm.contains("صفة مشبهة لفعل")
                    || mm.contains("مبالغة للفعل")
                    || mm.contains("مصدر لفعل")
                    || mm.contains("اسم منسوب للاسم ")) {
                String[] list = mm.split(" ");
                for (int w = 0; w < list.length - 1; w++) {
                    str += list[w] + " ";
                }
                str += list[list.length - 1];
            } else {
                str = mm;
            }
            String sp = semNoun.getSpecialization();
            if (!sp.equals("_")) {
                str = "(في " + sp + ") " + str;
            }
            meaningStr += (str + "[" + meaning1.getSource() + "]");
        }
        ret.setMeaning(meaningStr);

        List<FemininityBO> femininities = semNoun.getFemininities();
        List<String> fems = new ArrayList<String>();
        for (FemininityBO femininitie : femininities) {
            fems.add(femininitie.getFemininity().getVocalizedNoun());
        }
        ret.setFemininities(fems);

        List<DiminutiveBO> diminutives = semNoun.getDiminutives();
        List<String> diminutivesList = new ArrayList<String>();
        for (DiminutiveBO diminutive : diminutives) {
            diminutivesList.add(diminutive.getDiminutive().getVocalizedNoun());
        }
        ret.setDiminutives(diminutivesList);

        List<ProperAdjectiveBO> properAdjs = semNoun.getProperAdjectives();
        List<String> properAdsList = new ArrayList<String>();
        for (ProperAdjectiveBO properAdj : properAdjs) {
            properAdsList.add(properAdj.getProperAdjective().getVocalizedNoun());
        }
        ret.setProperAdjs(properAdsList);

        List<ExampleBO> examps = semNoun.getExamples();
        List<String> examplesList = new ArrayList<String>();

        for (ExampleBO examp : examps) {
            examplesList.add(examp.getExample() + "[" + examp.getSource() + "]");
        }
        ret.setExamples(examplesList);

        ret.setIdioms(semNoun.getRelatedidioms());

        List<LinguisticBenefitBO> benefits = semNoun.getLinguisticBenefits();
        List<String> benfs = new ArrayList<String>();

        for (LinguisticBenefitBO benefit : benefits) {
            benfs.add(benefit.getDescription() + "[" + benefit.getSource() + "]");
        }
        ret.setLinguisticBenefits(benfs);

        List<CommonMistakeBO> mistakes = semNoun.getCommonMistakes();
        List<String> mistakesList = new ArrayList<String>();

        for (CommonMistakeBO mistake : mistakes) {
            mistakesList.add(mistake.getDescription() + "[" + mistake.getSource() + "]");
        }
        ret.setCommonMistakes(mistakesList);

        List<PluralNounBO> plurals = semNoun.getPlurals();
        List<String> pluralsList = new ArrayList<String>();

        for (PluralNounBO plural : plurals) {
            pluralsList.add(plural.getPlural() + " [" + plural.getPluralType() + "]");
        }
        ret.setPlurals(pluralsList);

        return ret;

    }

    public static List<SemanticNounTitle> getSemanticTitlesPageForNoun(NounBO nbo, int start) {
        List<SemanticNounTitle> ret = new ArrayList<SemanticNounTitle>();
        List<SemanticNounBO> cases = nbo.getSemanticCases();
        for (int i = start; i < start + RESTConfig.Paging.PAGE_SIZE && i < cases.size(); i++) {
            ret.add(RESTUtils.getSemanticNounTitle(nbo.getDerivedNounId(), cases.get(i)));
        }
        return ret;
    }

    public static Particle getLightParticle(ParticleBO part) {
        Particle ret = new Particle();
        ret.setRoot(part.getRoot());
        ret.setType(part.getParticletype());
        ret.setVocalized(part.getVocalizedParticle());
        List<SemanticParticleTitle> titles = new ArrayList<SemanticParticleTitle>();
        for (SemanticParticleBO semPart : part.getSemanticCases()) {
            titles.add(getSemanticParticleTitle(semPart));
        }
        ret.setSemanticTitles(titles);
        return ret;
    }

    private static SemanticParticleTitle getSemanticParticleTitle(SemanticParticleBO semPart) {
        SemanticParticleTitle ret = new SemanticParticleTitle();
        List<MeaningBO> meaning = semPart.getMeanings();
        List<String> meaningsList = new ArrayList<String>();
        for (MeaningBO meaning1 : meaning) {
            meaningsList.add(meaning1.getDescription() + "::" + meaning1.getSource());
        }
        ret.setMeanings(meaningsList);

        List<ExampleBO> examps = semPart.getExamples();
        List<String> examplesList = new ArrayList<String>();
        for (int k = 0; k < examps.size() && k < 3; k++) {
            examplesList.add(examps.get(k).getExample() + "[" + examps.get(k).getSource() + "]");
        }
        ret.setExamples(examplesList);

        ret.setIdioms(semPart.getRelatedidioms());

        List<LinguisticBenefitBO> benefits = semPart.getLinguisticBenefits();
        List<String> benfsList = new ArrayList<String>();
        for (LinguisticBenefitBO benefit : benefits) {
            benfsList.add(benefit.getDescription() + "[" + benefit.getSource() + "]");
        }
        ret.setBenefits(benfsList);

        List<CommonMistakeBO> mistakes = semPart.getCommonMistakes();
        List<String> mistakesList = new ArrayList<String>();
        for (CommonMistakeBO mistake : mistakes) {
            mistakesList.add(mistake.getDescription() + "[" + mistake.getSource() + "]");
        }
        ret.setCommonMistakes(mistakesList);
        return ret;
    }

    public static Idiom getLightIdiom(IdiomBO idiom) {
        Idiom ret = new Idiom();
        ret.setType(idiom.getType());
        ret.setVocalized(idiom.getVocalizedIdiom());
        SemanticEntryBO semIdiom = idiom.getSemanticCase();
        List< MeaningBO> meaning = semIdiom.getMeanings();
        List<String> meaningsList = new ArrayList<String>();
        for (MeaningBO meaning1 : meaning) {
            meaningsList.add(meaning1.getDescription() + "::" + meaning1.getSource());
        }
        ret.setMeanings(meaningsList);

        List<ExampleBO> examps = semIdiom.getExamples();
        List<String> examplesList = new ArrayList<String>();
        for (int k = 0; k < examps.size() && k < 3; k++) {
            examplesList.add(examps.get(k).getExample() + "[" + examps.get(k).getSource() + "]");
        }
        ret.setExamples(examplesList);
        return ret;
    }
}
