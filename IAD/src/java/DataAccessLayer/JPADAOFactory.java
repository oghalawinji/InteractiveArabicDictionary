/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import DataAccessLayer.JPADAO.*;

/**
 *
 * @author Omar
 */
public class JPADAOFactory extends DAOFactory {

    @Override
    public ActorJPADAO getActorDAO() {
        return new ActorJPADAO();
    }

    @Override
    public AnnexednounJPADAO getAnnexednounDAO() {
        return new AnnexednounJPADAO();
    }

    @Override
    public AssimilateadjectiveJPADAO getAssimilateadjectiveDAO() {
        return new AssimilateadjectiveJPADAO();
    }

    @Override
    public CommonmistakeJPADAO getCommonmistakeDAO() {
        return new CommonmistakeJPADAO();
    }

    @Override
    public ContextualverbJPADAO getContextualverbDAO() {
        return new ContextualverbJPADAO();
    }

    @Override
    public DerivednounJPADAO getDerivednounDAO() {
        return new DerivednounJPADAO();
    }

    @Override
    public DerivedparticleJPADAO getDerivedparticleDAO() {
        return new DerivedparticleJPADAO();
    }

    @Override
    public DerivedverbJPADAO getDerivedverbDAO() {
        return new DerivedverbJPADAO();
    }

    @Override
    public DifficultydegreeJPADAO getDifficultydegreeDAO() {
        return new DifficultydegreeJPADAO();
    }

    @Override
    public DiminutiveJPADAO getDiminutiveDAO() {
        return new DiminutiveJPADAO();
    }

    @Override
    public EntrycommonmistakeJPADAO getEntrycommonmistakeDAO() {
        return new EntrycommonmistakeJPADAO();
    }

    @Override
    public EntryexampleJPADAO getEntryexampleDAO() {
        return new EntryexampleJPADAO();
    }

    @Override
    public EntryimageJPADAO getEntryimageDAO() {
        return new EntryimageJPADAO();
    }

    @Override
    public EntrylinguisticbenefitJPADAO getEntrylinguisticbenefitDAO() {
        return new EntrylinguisticbenefitJPADAO();
    }

    @Override
    public EntrysoundJPADAO getEntrysoundDAO() {
        return new EntrysoundJPADAO();
    }

    @Override
    public EntryvideoJPADAO getEntryvideoDAO() {
        return new EntryvideoJPADAO();
    }

    @Override
    public EpochJPADAO getEpochDAO() {
        return new EpochJPADAO();
    }

    @Override
    public ExaggerationJPADAO getExaggerationDAO() {
        return new ExaggerationJPADAO();
    }

    @Override
    public ExampleJPADAO getExampleDAO() {
        return new ExampleJPADAO();
    }

    @Override
    public ExamplesoundJPADAO getExamplesoundDAO() {
        return new ExamplesoundJPADAO();
    }

    @Override
    public FemininityJPADAO getFemininityDAO() {
        return new FemininityJPADAO();
    }

    @Override
    public GenderJPADAO getGenderDAO() {
        return new GenderJPADAO();
    }

    @Override
    public GerundJPADAO getGerundDAO() {
        return new GerundJPADAO();
    }

    @Override
    public IdiomJPADAO getIdiomDAO() {
        return new IdiomJPADAO();
    }

    @Override
    public ImageJPADAO getImageDAO() {
        return new ImageJPADAO();
    }

    @Override
    public LinguisticbenefitJPADAO getLinguisticbenefitDAO() {
        return new LinguisticbenefitJPADAO();
    }

    @Override
    public MeaningJPADAO getMeaningDAO() {
        return new MeaningJPADAO();
    }

    @Override
    public NounadjectiveaccompanierJPADAO getNounadjectiveaccompanierDAO() {
        return new NounadjectiveaccompanierJPADAO();
    }

    @Override
    public TypeJPADAO getNountypeDAO() {
        return new TypeJPADAO();
    }

    @Override
    public NounverbaccompanierJPADAO getNounverbaccompanierDAO() {
        return new NounverbaccompanierJPADAO();
    }

    @Override
    public NumberJPADAO getNumberDAO() {
        return new NumberJPADAO();
    }

    @Override
    public OriginJPADAO getOriginDAO() {
        return new OriginJPADAO();
    }

    @Override
    public ParticletypeJPADAO getParticletypeDAO() {
        return new ParticletypeJPADAO();
    }

    @Override
    public PatternJPADAO getPatternDAO() {
        return new PatternJPADAO();
    }

    @Override
    public PluralJPADAO getPluralDAO() {
        return new PluralJPADAO();
    }

    @Override
    public PluraltypeJPADAO getPluraltypeDAO() {
        return new PluraltypeJPADAO();
    }

    @Override
    public ProperadjectiveJPADAO getProperadjectiveDAO() {
        return new ProperadjectiveJPADAO();
    }

    @Override
    public RawwordJPADAO getRawwordDAO() {
        return new RawwordJPADAO();
    }

    @Override
    public RegionJPADAO getRegionDAO() {
        return new RegionJPADAO();
    }

    @Override
    public RelatedidiomJPADAO getRelatedidiomDAO() {
        return new RelatedidiomJPADAO();
    }

    @Override
    public RootJPADAO getRootDAO() {
        return new RootJPADAO();
    }

    @Override
    public SemanticentryJPADAO getSemanticentryDAO() {
        return new SemanticentryJPADAO();
    }

    @Override
    public SemanticnounJPADAO getSemanticnounDAO() {
        return new SemanticnounJPADAO();
    }

    @Override
    public SemanticparticleJPADAO getSemanticparticleDAO() {
        return new SemanticparticleJPADAO();
    }

    @Override
    public SemanticrelationJPADAO getSemanticrelationDAO() {
        return new SemanticrelationJPADAO();
    }

    @Override
    public SemanticrelationtypeJPADAO getSemanticrelationtypeDAO() {
        return new SemanticrelationtypeJPADAO();
    }

    @Override
    public SemanticscopJPADAO getSemanticscopDAO() {
        return new SemanticscopJPADAO();
    }

    @Override
    public SemanticverbJPADAO getSemanticverbDAO() {
        return new SemanticverbJPADAO();
    }

    @Override
    public SoundJPADAO getSoundDAO() {
        return new SoundJPADAO();
    }

    @Override
    public SourceJPADAO getSourceDAO() {
        return new SourceJPADAO();
    }

    @Override
    public SpecializationJPADAO getSpecializationDAO() {
        return new SpecializationJPADAO();
    }

    @Override
    public SpreadingdegreeJPADAO getSpreadingdegreeDAO() {
        return new SpreadingdegreeJPADAO();
    }

    @Override
    public SubjecttypeJPADAO getSubjecttypeDAO() {
        return new SubjecttypeJPADAO();
    }

    @Override
    public TransitiveletterJPADAO getTransitiveletterDAO() {
        return new TransitiveletterJPADAO();
    }

    @Override
    public TransitivitycaseJPADAO getTransitivitycaseDAO() {
        return new TransitivitycaseJPADAO();
    }

    @Override
    public VideoJPADAO getVideoDAO() {
        return new VideoJPADAO();
    }

    @Override
    public PrefixJPADAO getPrefixDAO() {
        return new PrefixJPADAO();
    }

    @Override
    public SuffixJPADAO getSuffixDAO() {
        return new SuffixJPADAO();
    }

    @Override
    public PronunciationJPADAO getPronunciationDAO() {
        return new PronunciationJPADAO();
    }

    @Override
    public SuggestionJPADAO getSuggestionDAO() {
        return new SuggestionJPADAO();
    }

    @Override
    public RoleJPADAO getRoleDAO() {
        return new RoleJPADAO();
    }

    @Override
    public UserJPADAO getUserDAO() {
        return new UserJPADAO();
    }

    @Override
    public TypeJPADAO getTypeDAO() {
        return new TypeJPADAO();
    }
}
