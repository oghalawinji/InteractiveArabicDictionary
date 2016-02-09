/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suggestion.findAll", query = "SELECT s FROM Suggestion s"),
    @NamedQuery(name = "Suggestion.findBySuggestionId", query = "SELECT s FROM Suggestion s WHERE s.suggestionId = :suggestionId"),
    @NamedQuery(name = "Suggestion.findByEntryId", query = "SELECT s FROM Suggestion s WHERE s.entryId = :entryId"),
    @NamedQuery(name = "Suggestion.findByUpdateId", query = "SELECT s FROM Suggestion s WHERE s.updateId = :updateId"),
    @NamedQuery(name = "Suggestion.findByUser", query = "SELECT s FROM Suggestion s WHERE s.user = :user"),
    @NamedQuery(name = "Suggestion.findByAffirmuser", query = "SELECT s FROM Suggestion s WHERE s.affirmuser = :affirmuser"),
    @NamedQuery(name = "Suggestion.findByEntrytype", query = "SELECT s FROM Suggestion s WHERE s.entrytype = :entrytype"),
    @NamedQuery(name = "Suggestion.findByNote", query = "SELECT s FROM Suggestion s WHERE s.note = :note"),
    @NamedQuery(name = "Suggestion.findByDetails", query = "SELECT s FROM Suggestion s WHERE s.details = :details"),
    @NamedQuery(name = "Suggestion.findByInfostatus", query = "SELECT s FROM Suggestion s WHERE s.infostatus = :infostatus"),
    @NamedQuery(name = "Suggestion.findByAffirmstatus", query = "SELECT s FROM Suggestion s WHERE s.affirmstatus = :affirmstatus"),
    @NamedQuery(name = "Suggestion.findByDate", query = "SELECT s FROM Suggestion s WHERE s.date = :date"),
    @NamedQuery(name = "Suggestion.findByAffirmdate", query = "SELECT s FROM Suggestion s WHERE s.affirmdate = :affirmdate")})
public class Suggestion extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer suggestionId;
    private Integer entryId;
    private Integer updateId;
    @Basic(optional = false)
    @NotNull
    private int user;
    private Integer affirmuser;
    @Size(max = 45)
    private String entrytype;
    @Size(max = 200)
    private String note;
    @Size(max = 200)
    private String details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infostatus;
    @Size(max = 1)
    private String affirmstatus;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.DATE)
    private Date affirmdate;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Examplesound> examplesoundSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Root> rootSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Idiom> idiomSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Actor> actorSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Type> typeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Nounverbaccompanier> nounverbaccompanierSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Gerund> gerundSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Contextualverb> contextualverbSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Annexednoun> annexednounSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Commonmistake> commonmistakeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entryvideo> entryvideoSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Particletype> particletypeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Pluraltype> pluraltypeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Gender> genderSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Derivednoun> derivednounSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Video> videoSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Image> imageSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Number> numberSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Femininity> femininitySet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Linguisticbenefit> linguisticbenefitSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Derivedparticle> derivedparticleSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Sound> soundSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Diminutive> diminutiveSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entrycommonmistake> entrycommonmistakeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Transitivitycase> transitivitycaseSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticrelation> semanticrelationSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Example> exampleSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Region> regionSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Exaggeration> exaggerationSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Relatedidiom> relatedidiomSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Transitiveletter> transitiveletterSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticentry> semanticentrySet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Plural> pluralSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Difficultydegree> difficultydegreeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Pronunciation> pronunciationSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entrysound> entrysoundSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Spreadingdegree> spreadingdegreeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Specialization> specializationSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticscop> semanticscopSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Rawword> rawwordSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entryexample> entryexampleSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Subjecttype> subjecttypeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Epoch> epochSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Origin> originSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticrelationtype> semanticrelationtypeSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Derivedverb> derivedverbSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Meaning> meaningSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entryimage> entryimageSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Pattern> patternSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Source> sourceSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticparticle> semanticparticleSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Entrylinguisticbenefit> entrylinguisticbenefitSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Properadjective> properadjectiveSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Assimilateadjective> assimilateadjectiveSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticnoun> semanticnounSet;
    @OneToMany(mappedBy = "suggestionId")
    private Set<Semanticverb> semanticverbSet;

    public Suggestion() {
    }

    public Suggestion(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Suggestion(Integer suggestionId, int user, String infostatus, Date date) {
        this.suggestionId = suggestionId;
        this.user = user;
        this.infostatus = infostatus;
        this.date = date;
    }

    @Override
    public void execludeGeneralProperties() {

    }

    @Override
    public Integer getIdentity() {
        return suggestionId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSuggestionId(id);
    }

    public void setSuggestionId(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Integer getAffirmuser() {
        return affirmuser;
    }

    public void setAffirmuser(Integer affirmuser) {
        this.affirmuser = affirmuser;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getInfoStatus() {
        return infostatus;
    }

    public void setInfostatus(String infostatus) {
        this.infostatus = infostatus;
    }

    public String getAffirmStatus() {
        return affirmstatus;
    }

    public void setAffirmstatus(String affirmstatus) {
        this.affirmstatus = affirmstatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getAffirmdate() {
        return affirmdate;
    }

    public void setAffirmdate(Date affirmdate) {
        this.affirmdate = affirmdate;
    }

    @XmlTransient
    public Set<Examplesound> getExamplesounds() {
        return examplesoundSet;
    }

    public void setExamplesoundSet(Set<Examplesound> examplesoundSet) {
        this.examplesoundSet = examplesoundSet;
    }

    @XmlTransient
    public Set<Root> getRoots() {
        return rootSet;
    }

    public void setRootSet(Set<Root> rootSet) {
        this.rootSet = rootSet;
    }

    @XmlTransient
    public Set<Idiom> getIdioms() {
        return idiomSet;
    }

    public void setIdiomSet(Set<Idiom> idiomSet) {
        this.idiomSet = idiomSet;
    }

    @XmlTransient
    public Set<Actor> getActors() {
        return actorSet;
    }

    public void setActorSet(Set<Actor> actorSet) {
        this.actorSet = actorSet;
    }

    @XmlTransient
    public Set<Type> getTypes() {
        return typeSet;
    }

    public void setTypeSet(Set<Type> typeSet) {
        this.typeSet = typeSet;
    }

    @XmlTransient
    public Set<Nounverbaccompanier> getNounverbaccompaniers() {
        return nounverbaccompanierSet;
    }

    public void setNounverbaccompanierSet(Set<Nounverbaccompanier> nounverbaccompanierSet) {
        this.nounverbaccompanierSet = nounverbaccompanierSet;
    }

    @XmlTransient
    public Set<Gerund> getGerunds() {
        return gerundSet;
    }

    public void setGerundSet(Set<Gerund> gerundSet) {
        this.gerundSet = gerundSet;
    }

    @XmlTransient
    public Set<Contextualverb> getContextualverbs() {
        return contextualverbSet;
    }

    public void setContextualverbSet(Set<Contextualverb> contextualverbSet) {
        this.contextualverbSet = contextualverbSet;
    }

    @XmlTransient
    public Set<Annexednoun> getAnnexednouns() {
        return annexednounSet;
    }

    public void setAnnexednounSet(Set<Annexednoun> annexednounSet) {
        this.annexednounSet = annexednounSet;
    }

    @XmlTransient
    public Set<Commonmistake> getCommonmistakes() {
        return commonmistakeSet;
    }

    public void setCommonmistakeSet(Set<Commonmistake> commonmistakeSet) {
        this.commonmistakeSet = commonmistakeSet;
    }

    @XmlTransient
    public Set<Entryvideo> getEntryvideos() {
        return entryvideoSet;
    }

    public void setEntryvideoSet(Set<Entryvideo> entryvideoSet) {
        this.entryvideoSet = entryvideoSet;
    }

    @XmlTransient
    public Set<Particletype> getParticletypes() {
        return particletypeSet;
    }

    public void setParticletypeSet(Set<Particletype> particletypeSet) {
        this.particletypeSet = particletypeSet;
    }

    @XmlTransient
    public Set<Pluraltype> getPluraltypes() {
        return pluraltypeSet;
    }

    public void setPluraltypeSet(Set<Pluraltype> pluraltypeSet) {
        this.pluraltypeSet = pluraltypeSet;
    }

    @XmlTransient
    public Set<Gender> getGenders() {
        return genderSet;
    }

    public void setGenderSet(Set<Gender> genderSet) {
        this.genderSet = genderSet;
    }

    @XmlTransient
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
    }

    @XmlTransient
    public Set<Video> getVideos() {
        return videoSet;
    }

    public void setVideoSet(Set<Video> videoSet) {
        this.videoSet = videoSet;
    }

    @XmlTransient
    public Set<Image> getImages() {
        return imageSet;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    @XmlTransient
    public Set<Number> getNumbers() {
        return numberSet;
    }

    public void setNumberSet(Set<Number> numberSet) {
        this.numberSet = numberSet;
    }

    @XmlTransient
    public Set<Femininity> getFemininitySet() {
        return femininitySet;
    }

    public void setFemininitySet(Set<Femininity> femininitySet) {
        this.femininitySet = femininitySet;
    }

    @XmlTransient
    public Set<Linguisticbenefit> getLinguisticbenefits() {
        return linguisticbenefitSet;
    }

    public void setLinguisticbenefitSet(Set<Linguisticbenefit> linguisticbenefitSet) {
        this.linguisticbenefitSet = linguisticbenefitSet;
    }

    @XmlTransient
    public Set<Derivedparticle> getDerivedparticles() {
        return derivedparticleSet;
    }

    public void setDerivedparticleSet(Set<Derivedparticle> derivedparticleSet) {
        this.derivedparticleSet = derivedparticleSet;
    }

    @XmlTransient
    public Set<Sound> getSounds() {
        return soundSet;
    }

    public void setSoundSet(Set<Sound> soundSet) {
        this.soundSet = soundSet;
    }

    @XmlTransient
    public Set<Diminutive> getDiminutives() {
        return diminutiveSet;
    }

    public void setDiminutiveSet(Set<Diminutive> diminutiveSet) {
        this.diminutiveSet = diminutiveSet;
    }

    @XmlTransient
    public Set<Entrycommonmistake> getEntrycommonmistakes() {
        return entrycommonmistakeSet;
    }

    public void setEntrycommonmistakeSet(Set<Entrycommonmistake> entrycommonmistakeSet) {
        this.entrycommonmistakeSet = entrycommonmistakeSet;
    }

    @XmlTransient
    public Set<Transitivitycase> getTransitivitycases() {
        return transitivitycaseSet;
    }

    public void setTransitivitycaseSet(Set<Transitivitycase> transitivitycaseSet) {
        this.transitivitycaseSet = transitivitycaseSet;
    }

    @XmlTransient
    public Set<Semanticrelation> getSemanticrelations() {
        return semanticrelationSet;
    }

    public void setSemanticrelationSet(Set<Semanticrelation> semanticrelationSet) {
        this.semanticrelationSet = semanticrelationSet;
    }

    @XmlTransient
    public Set<Example> getExamples() {
        return exampleSet;
    }

    public void setExampleSet(Set<Example> exampleSet) {
        this.exampleSet = exampleSet;
    }

    @XmlTransient
    public Set<Nounadjectiveaccompanier> getNounadjectiveaccompaniers() {
        return nounadjectiveaccompanierSet;
    }

    public void setNounadjectiveaccompanierSet(Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet) {
        this.nounadjectiveaccompanierSet = nounadjectiveaccompanierSet;
    }

    @XmlTransient
    public Set<Region> getRegions() {
        return regionSet;
    }

    public void setRegionSet(Set<Region> regionSet) {
        this.regionSet = regionSet;
    }

    @XmlTransient
    public Set<Exaggeration> getExaggerations() {
        return exaggerationSet;
    }

    public void setExaggerationSet(Set<Exaggeration> exaggerationSet) {
        this.exaggerationSet = exaggerationSet;
    }

    @XmlTransient
    public Set<Relatedidiom> getRelatedidioms() {
        return relatedidiomSet;
    }

    public void setRelatedidiomSet(Set<Relatedidiom> relatedidiomSet) {
        this.relatedidiomSet = relatedidiomSet;
    }

    @XmlTransient
    public Set<Transitiveletter> getTransitiveletters() {
        return transitiveletterSet;
    }

    public void setTransitiveletterSet(Set<Transitiveletter> transitiveletterSet) {
        this.transitiveletterSet = transitiveletterSet;
    }

    @XmlTransient
    public Set<Semanticentry> getSemanticentrys() {
        return semanticentrySet;
    }

    public void setSemanticentrySet(Set<Semanticentry> semanticentrySet) {
        this.semanticentrySet = semanticentrySet;
    }

    @XmlTransient
    public Set<Plural> getPlurals() {
        return pluralSet;
    }

    public void setPluralSet(Set<Plural> pluralSet) {
        this.pluralSet = pluralSet;
    }

    @XmlTransient
    public Set<Difficultydegree> getDifficultydegrees() {
        return difficultydegreeSet;
    }

    public void setDifficultydegreeSet(Set<Difficultydegree> difficultydegreeSet) {
        this.difficultydegreeSet = difficultydegreeSet;
    }

    @XmlTransient
    public Set<Pronunciation> getPronunciations() {
        return pronunciationSet;
    }

    public void setPronunciationSet(Set<Pronunciation> pronunciationSet) {
        this.pronunciationSet = pronunciationSet;
    }

    @XmlTransient
    public Set<Entrysound> getEntrysounds() {
        return entrysoundSet;
    }

    public void setEntrysoundSet(Set<Entrysound> entrysoundSet) {
        this.entrysoundSet = entrysoundSet;
    }

    @XmlTransient
    public Set<Spreadingdegree> getSpreadingdegrees() {
        return spreadingdegreeSet;
    }

    public void setSpreadingdegreeSet(Set<Spreadingdegree> spreadingdegreeSet) {
        this.spreadingdegreeSet = spreadingdegreeSet;
    }

    @XmlTransient
    public Set<Specialization> getSpecializations() {
        return specializationSet;
    }

    public void setSpecializationSet(Set<Specialization> specializationSet) {
        this.specializationSet = specializationSet;
    }

    @XmlTransient
    public Set<Semanticscop> getSemanticscops() {
        return semanticscopSet;
    }

    public void setSemanticscopSet(Set<Semanticscop> semanticscopSet) {
        this.semanticscopSet = semanticscopSet;
    }

    @XmlTransient
    public Set<Rawword> getRawwords() {
        return rawwordSet;
    }

    public void setRawwordSet(Set<Rawword> rawwordSet) {
        this.rawwordSet = rawwordSet;
    }

    @XmlTransient
    public Set<Entryexample> getEntryexamples() {
        return entryexampleSet;
    }

    public void setEntryexampleSet(Set<Entryexample> entryexampleSet) {
        this.entryexampleSet = entryexampleSet;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypes() {
        return subjecttypeSet;
    }

    public void setSubjecttypeSet(Set<Subjecttype> subjecttypeSet) {
        this.subjecttypeSet = subjecttypeSet;
    }

    @XmlTransient
    public Set<Epoch> getEpochs() {
        return epochSet;
    }

    public void setEpochSet(Set<Epoch> epochSet) {
        this.epochSet = epochSet;
    }

    @XmlTransient
    public Set<Origin> getOrigins() {
        return originSet;
    }

    public void setOriginSet(Set<Origin> originSet) {
        this.originSet = originSet;
    }

    @XmlTransient
    public Set<Semanticrelationtype> getSemanticrelationtypes() {
        return semanticrelationtypeSet;
    }

    public void setSemanticrelationtypeSet(Set<Semanticrelationtype> semanticrelationtypeSet) {
        this.semanticrelationtypeSet = semanticrelationtypeSet;
    }

    @XmlTransient
    public Set<Derivedverb> getDerivedverbs() {
        return derivedverbSet;
    }

    public void setDerivedverbSet(Set<Derivedverb> derivedverbSet) {
        this.derivedverbSet = derivedverbSet;
    }

    @XmlTransient
    public Set<Meaning> getMeanings() {
        return meaningSet;
    }

    public void setMeaningSet(Set<Meaning> meaningSet) {
        this.meaningSet = meaningSet;
    }

    @XmlTransient
    public Set<Entryimage> getEntryimageSet() {
        return entryimageSet;
    }

    public void setEntryimageSet(Set<Entryimage> entryimageSet) {
        this.entryimageSet = entryimageSet;
    }

    @XmlTransient
    public Set<Pattern> getPatternSet() {
        return patternSet;
    }

    public void setPatternSet(Set<Pattern> patternSet) {
        this.patternSet = patternSet;
    }

    @XmlTransient
    public Set<Source> getSourceSet() {
        return sourceSet;
    }

    public void setSourceSet(Set<Source> sourceSet) {
        this.sourceSet = sourceSet;
    }

    @XmlTransient
    public Set<Semanticparticle> getSemanticparticleSet() {
        return semanticparticleSet;
    }

    public void setSemanticparticleSet(Set<Semanticparticle> semanticparticleSet) {
        this.semanticparticleSet = semanticparticleSet;
    }

    @XmlTransient
    public Set<Entrylinguisticbenefit> getEntrylinguisticbenefitSet() {
        return entrylinguisticbenefitSet;
    }

    public void setEntrylinguisticbenefitSet(Set<Entrylinguisticbenefit> entrylinguisticbenefitSet) {
        this.entrylinguisticbenefitSet = entrylinguisticbenefitSet;
    }

    @XmlTransient
    public Set<Properadjective> getProperadjectiveSet() {
        return properadjectiveSet;
    }

    public void setProperadjectiveSet(Set<Properadjective> properadjectiveSet) {
        this.properadjectiveSet = properadjectiveSet;
    }

    @XmlTransient
    public Set<Assimilateadjective> getAssimilateadjectiveSet() {
        return assimilateadjectiveSet;
    }

    public void setAssimilateadjectiveSet(Set<Assimilateadjective> assimilateadjectiveSet) {
        this.assimilateadjectiveSet = assimilateadjectiveSet;
    }

    @XmlTransient
    public Set<Semanticnoun> getSemanticnounSet() {
        return semanticnounSet;
    }

    public void setSemanticnounSet(Set<Semanticnoun> semanticnounSet) {
        this.semanticnounSet = semanticnounSet;
    }

    @XmlTransient
    public Set<Semanticverb> getSemanticverbSet() {
        return semanticverbSet;
    }

    public void setSemanticverbSet(Set<Semanticverb> semanticverbSet) {
        this.semanticverbSet = semanticverbSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suggestionId != null ? suggestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suggestion)) {
            return false;
        }
        Suggestion other = (Suggestion) object;
        if ((this.suggestionId == null && other.suggestionId != null) || (this.suggestionId != null && !this.suggestionId.equals(other.suggestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Suggestion[ suggestionId=" + suggestionId + " ]";
    }

}
