/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

/**
 *
 * @author riad
 */
public class HibernateDAOFactory {

//    @Override
//    public ActorHibernateDAO getActorDAO() {
//        return new ActorHibernateDAO();
//    }
//
//    @Override
//    public AnnexednounHibernateDAO getAnnexednounDAO() {
//        return new AnnexednounHibernateDAO();
//    }
//
//    @Override
//    public AssimilateadjectiveHibernateDAO getAssimilateadjectiveDAO() {
//        return new AssimilateadjectiveHibernateDAO();
//    }
//
//    @Override
//    public CommonmistakeHibernateDAO getCommonmistakeDAO() {
//        return new CommonmistakeHibernateDAO();
//    }
//
//    @Override
//    public ContextualverbHibernateDAO getContextualverbDAO() {
//        return new ContextualverbHibernateDAO();
//    }
//
//    @Override
//    public DerivednounHibernateDAO getDerivednounDAO() {
//        return new DerivednounHibernateDAO();
//    }
//
//    @Override
//    public DerivedparticleHibernateDAO getDerivedparticleDAO() {
//        return new DerivedparticleHibernateDAO();
//    }
//
//    @Override
//    public DerivedverbHibernateDAO getDerivedverbDAO() {
//        return new DerivedverbHibernateDAO();
//    }
//
//    @Override
//    public DifficultydegreeHibernateDAO getDifficultydegreeDAO() {
//        return new DifficultydegreeHibernateDAO();
//    }
//
//    @Override
//    public DiminutiveHibernateDAO getDiminutiveDAO() {
//        return new DiminutiveHibernateDAO();
//    }
//
//    @Override
//    public EntrycommonmistakeHibernateDAO getEntrycommonmistakeDAO() {
//        return new EntrycommonmistakeHibernateDAO();
//    }
//
//    @Override
//    public EntryexampleHibernateDAO getEntryexampleDAO() {
//        return new EntryexampleHibernateDAO();
//    }
//
//    @Override
//    public EntryimageHibernateDAO getEntryimageDAO() {
//        return new EntryimageHibernateDAO();
//    }
//
//    @Override
//    public EntrylinguisticbenefitHibernateDAO getEntrylinguisticbenefitDAO() {
//        return new EntrylinguisticbenefitHibernateDAO();
//    }
//
//    @Override
//    public EntrysoundHibernateDAO getEntrysoundDAO() {
//        return new EntrysoundHibernateDAO();
//    }
//
//    @Override
//    public EntryvideoHibernateDAO getEntryvideoDAO() {
//        return new EntryvideoHibernateDAO();
//    }
//
//    @Override
//    public EpochHibernateDAO getEpochDAO() {
//        return new EpochHibernateDAO();
//    }
//
//    @Override
//    public ExaggerationHibernateDAO getExaggerationDAO() {
//        return new ExaggerationHibernateDAO();
//    }
//
//    @Override
//    public ExampleHibernateDAO getExampleDAO() {
//        return new ExampleHibernateDAO();
//    }
//
//    @Override
//    public ExamplesoundHibernateDAO getExamplesoundDAO() {
//        return new ExamplesoundHibernateDAO();
//    }
//
//    @Override
//    public FemininityHibernateDAO getFemininityDAO() {
//        return new FemininityHibernateDAO();
//    }
//
//    @Override
//    public GenderHibernateDAO getGenderDAO() {
//        return new GenderHibernateDAO();
//    }
//
//    @Override
//    public GerundHibernateDAO getGerundDAO() {
//        return new GerundHibernateDAO();
//    }
//
//    @Override
//    public IdiomHibernateDAO getIdiomDAO() {
//        return new IdiomHibernateDAO();
//    }
//
//    @Override
//    public ImageHibernateDAO getImageDAO() {
//        return new ImageHibernateDAO();
//    }
//
//    @Override
//    public LinguisticbenefitHibernateDAO getLinguisticbenefitDAO() {
//        return new LinguisticbenefitHibernateDAO();
//    }
//
//    @Override
//    public MeaningHibernateDAO getMeaningDAO() {
//        return new MeaningHibernateDAO();
//    }
//
//    @Override
//    public NounadjectiveaccompanierHibernateDAO getNounadjectiveaccompanierDAO() {
//        return new NounadjectiveaccompanierHibernateDAO();
//    }
//
//    @Override
//    public NountypeHibernateDAO getNountypeDAO() {
//        return new NountypeHibernateDAO();
//    }
//
//    @Override
//    public NounverbaccompanierHibernateDAO getNounverbaccompanierDAO() {
//        return new NounverbaccompanierHibernateDAO();
//    }
//
//    @Override
//    public NumberHibernateDAO getNumberDAO() {
//        return new NumberHibernateDAO();
//    }
//
//    @Override
//    public OriginHibernateDAO getOriginDAO() {
//        return new OriginHibernateDAO();
//    }
//
//    @Override
//    public ParticletypeHibernateDAO getParticletypeDAO() {
//        return new ParticletypeHibernateDAO();
//    }
//
//    @Override
//    public PatternHibernateDAO getPatternDAO() {
//        return new PatternHibernateDAO();
//    }
//
//    @Override
//    public PluralHibernateDAO getPluralDAO() {
//        return new PluralHibernateDAO();
//    }
//
//    @Override
//    public PluraltypeHibernateDAO getPluraltypeDAO() {
//        return new PluraltypeHibernateDAO();
//    }
//
//    @Override
//    public ProperadjectiveHibernateDAO getProperadjectiveDAO() {
//        return new ProperadjectiveHibernateDAO();
//    }
//
//    @Override
//    public RawwordHibernateDAO getRawwordDAO() {
//        return new RawwordHibernateDAO();
//    }
//
//    @Override
//    public RegionHibernateDAO getRegionDAO() {
//        return new RegionHibernateDAO();
//    }
//
//    @Override
//    public RelatedidiomHibernateDAO getRelatedidiomDAO() {
//        return new RelatedidiomHibernateDAO();
//    }
//
//    @Override
//    public RootHibernateDAO getRootDAO() {
//        return new RootHibernateDAO();
//    }
//
//    @Override
//    public SemanticentryHibernateDAO getSemanticentryDAO() {
//        return new SemanticentryHibernateDAO();
//    }
//
//    @Override
//    public SemanticnounHibernateDAO getSemanticnounDAO() {
//        return new SemanticnounHibernateDAO();
//    }
//
//    @Override
//    public SemanticparticleHibernateDAO getSemanticparticleDAO() {
//        return new SemanticparticleHibernateDAO();
//    }
//
//    @Override
//    public SemanticrelationHibernateDAO getSemanticrelationDAO() {
//        return new SemanticrelationHibernateDAO();
//    }
//
//    @Override
//    public SemanticrelationtypeHibernateDAO getSemanticrelationtypeDAO() {
//        return new SemanticrelationtypeHibernateDAO();
//    }
//
//    @Override
//    public SemanticscopHibernateDAO getSemanticscopDAO() {
//        return new SemanticscopHibernateDAO();
//    }
//
//    @Override
//    public SemanticverbHibernateDAO getSemanticverbDAO() {
//        return new SemanticverbHibernateDAO();
//    }
//
//    @Override
//    public SoundHibernateDAO getSoundDAO() {
//        return new SoundHibernateDAO();
//    }
//
//    @Override
//    public SourceHibernateDAO getSourceDAO() {
//        return new SourceHibernateDAO();
//    }
//
//    @Override
//    public SpecializationHibernateDAO getSpecializationDAO() {
//        return new SpecializationHibernateDAO();
//    }
//
//    @Override
//    public SpreadingdegreeHibernateDAO getSpreadingdegreeDAO() {
//        return new SpreadingdegreeHibernateDAO();
//    }
//
//    @Override
//    public SubjecttypeHibernateDAO getSubjecttypeDAO() {
//        return new SubjecttypeHibernateDAO();
//    }
//
//    @Override
//    public TransitiveletterHibernateDAO getTransitiveletterDAO() {
//        return new TransitiveletterHibernateDAO();
//    }
//
//    @Override
//    public TransitivitycaseHibernateDAO getTransitivitycaseDAO() {
//        return new TransitivitycaseHibernateDAO();
//    }
//
//    @Override
//    public VideoHibernateDAO getVideoDAO() {
//        return new VideoHibernateDAO();
//    }
//
//    @Override
//    public PrefixHibernateDAO getPrefixDAO() {
//        return new PrefixHibernateDAO();
//    }
//
//    @Override
//    public SuffixHibernateDAO getSuffixDAO() {
//        return new SuffixHibernateDAO();
//    }
//
//    @Override
//    public PronunciationDAO getPronunciationDAO() {
//        return new PronunciationDAO();
//    }
//
//    @Override
//    public SuggestionDAO getSuggestionDAO() {
//        return new SuggestionDAO();
//    }
//
//    @Override
//    public RoleDAO getRoleDAO() {
//        return new RoleDAO();
//    }
//
//    @Override
//    public UserDAO getUserDAO() {
//        return new UserDAO();
//    }
//
//    @Override
//    public TypeHibernateDAO getTypeDAO() {
//        return new TypeHibernateDAO();
//    }
}
