/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Semanticentry.findAll", query = "SELECT s FROM Semanticentry s"),
    @NamedQuery(name = "Semanticentry.findBySemanticEntryId", query = "SELECT s FROM Semanticentry s WHERE s.semanticEntryId = :semanticEntryId"),
    @NamedQuery(name = "Semanticentry.findByInfoStatus", query = "SELECT s FROM Semanticentry s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticentry.findByChechStatus", query = "SELECT s FROM Semanticentry s WHERE s.chechStatus = :chechStatus")})
public class Semanticentry extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticEntryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(mappedBy = "semanticEntryId")
    private Set<Idiom> idiomSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entryvideo> entryvideoSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entrycommonmistake> entrycommonmistakeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Relatedidiom> relatedidiomSet;
    @JoinColumn(name = "specializationId", referencedColumnName = "specializationId")
    @ManyToOne
    private Specialization specializationId;
    @JoinColumn(name = "epochId", referencedColumnName = "epochId")
    @ManyToOne
    private Epoch epochId;
    @JoinColumn(name = "regionId", referencedColumnName = "regionId")
    @ManyToOne
    private Region regionId;
    @JoinColumn(name = "difficultyDegreeId", referencedColumnName = "difficultyDegreeId")
    @ManyToOne
    private Difficultydegree difficultyDegreeId;
    @JoinColumn(name = "spreadingDegreeId", referencedColumnName = "spreadingDegreeId")
    @ManyToOne
    private Spreadingdegree spreadingDegreeId;
    @JoinColumn(name = "semanticScopId", referencedColumnName = "semanticScopId")
    @ManyToOne
    private Semanticscop semanticScopId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entrysound> entrysoundSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entryexample> entryexampleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Meaning> meaningSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entryimage> entryimageSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Semanticparticle> semanticparticleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Entrylinguisticbenefit> entrylinguisticbenefitSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Semanticnoun> semanticnounSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticEntryId")
    private Set<Semanticverb> semanticverbSet;

    public Semanticentry() {
    }

    public Semanticentry(Integer semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public Semanticentry(Integer semanticEntryId, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticEntryId;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return semanticEntryId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticEntryId(id);
    }

    public void setSemanticEntryId(Integer semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public String getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
    }

    public int getChechStatus() {
        return chechStatus;
    }

    public void setChechStatus(int chechStatus) {
        this.chechStatus = chechStatus;
    }

    @XmlTransient
    public Set<Idiom> getIdioms() {
        return idiomSet;
    }

    public void setIdiomSet(Set<Idiom> idiomSet) {
        this.idiomSet = idiomSet;
    }

    @XmlTransient
    public Set<Entryvideo> getEntryvideos() {
        return entryvideoSet;
    }

    public void setEntryvideoSet(Set<Entryvideo> entryvideoSet) {
        this.entryvideoSet = entryvideoSet;
    }

    @XmlTransient
    public Set<Entrycommonmistake> getEntrycommonmistakes() {
        return entrycommonmistakeSet;
    }

    public void setEntrycommonmistakeSet(Set<Entrycommonmistake> entrycommonmistakeSet) {
        this.entrycommonmistakeSet = entrycommonmistakeSet;
    }

    @XmlTransient
    public Set<Relatedidiom> getRelatedidioms() {
        return relatedidiomSet;
    }

    public void setRelatedidiomSet(Set<Relatedidiom> relatedidiomSet) {
        this.relatedidiomSet = relatedidiomSet;
    }

    public Specialization getSpecialization() {
        return specializationId;
    }

    public void setSpecialization(Specialization specializationId) {
        this.specializationId = specializationId;
    }

    public Epoch getEpoch() {
        return epochId;
    }

    public void setEpoch(Epoch epochId) {
        this.epochId = epochId;
    }

    public Region getRegion() {
        return regionId;
    }

    public void setRegion(Region regionId) {
        this.regionId = regionId;
    }

    public Difficultydegree getDifficultydegree() {
        return difficultyDegreeId;
    }

    public void setDifficultydegree(Difficultydegree difficultyDegreeId) {
        this.difficultyDegreeId = difficultyDegreeId;
    }

    public Spreadingdegree getSpreadingdegree() {
        return spreadingDegreeId;
    }

    public void setSpreadingdegree(Spreadingdegree spreadingDegreeId) {
        this.spreadingDegreeId = spreadingDegreeId;
    }

    public Semanticscop getSemanticscop() {
        return semanticScopId;
    }

    public void setSemanticscop(Semanticscop semanticScopId) {
        this.semanticScopId = semanticScopId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Entrysound> getEntrysounds() {
        return entrysoundSet;
    }

    public void setEntrysoundSet(Set<Entrysound> entrysoundSet) {
        this.entrysoundSet = entrysoundSet;
    }

    @XmlTransient
    public Set<Entryexample> getEntryexamples() {
        return entryexampleSet;
    }

    public void setEntryexampleSet(Set<Entryexample> entryexampleSet) {
        this.entryexampleSet = entryexampleSet;
    }

    @XmlTransient
    public Set<Meaning> getMeanings() {
        return meaningSet;
    }

    public void setMeaningSet(Set<Meaning> meaningSet) {
        this.meaningSet = meaningSet;
    }

    @XmlTransient
    public Set<Entryimage> getEntryimages() {
        return entryimageSet;
    }

    public void setEntryimageSet(Set<Entryimage> entryimageSet) {
        this.entryimageSet = entryimageSet;
    }

    @XmlTransient
    public Set<Semanticparticle> getSemanticparticles() {
        return semanticparticleSet;
    }

    public void setSemanticparticleSet(Set<Semanticparticle> semanticparticleSet) {
        this.semanticparticleSet = semanticparticleSet;
    }

    @XmlTransient
    public Set<Entrylinguisticbenefit> getEntrylinguisticbenefits() {
        return entrylinguisticbenefitSet;
    }

    public void setEntrylinguisticbenefitSet(Set<Entrylinguisticbenefit> entrylinguisticbenefitSet) {
        this.entrylinguisticbenefitSet = entrylinguisticbenefitSet;
    }

    @XmlTransient
    public Set<Semanticnoun> getSemanticnouns() {
        return semanticnounSet;
    }

    public void setSemanticnounSet(Set<Semanticnoun> semanticnounSet) {
        this.semanticnounSet = semanticnounSet;
    }

    @XmlTransient
    public Set<Semanticverb> getSemanticverbs() {
        return semanticverbSet;
    }

    public void setSemanticverbSet(Set<Semanticverb> semanticverbSet) {
        this.semanticverbSet = semanticverbSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticEntryId != null ? semanticEntryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticentry)) {
            return false;
        }
        Semanticentry other = (Semanticentry) object;
        if ((this.semanticEntryId == null && other.semanticEntryId != null) || (this.semanticEntryId != null && !this.semanticEntryId.equals(other.semanticEntryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticentry[ semanticEntryId=" + semanticEntryId + " ]";
    }

}
