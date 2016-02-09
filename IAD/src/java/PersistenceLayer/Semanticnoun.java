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
    @NamedQuery(name = "Semanticnoun.findAll", query = "SELECT s FROM Semanticnoun s"),
    @NamedQuery(name = "Semanticnoun.findBySemanticNounId", query = "SELECT s FROM Semanticnoun s WHERE s.semanticNounId = :semanticNounId"),
    @NamedQuery(name = "Semanticnoun.findByInfoStatus", query = "SELECT s FROM Semanticnoun s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticnoun.findByChechStatus", query = "SELECT s FROM Semanticnoun s WHERE s.chechStatus = :chechStatus")})
public class Semanticnoun extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticNounId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Nounverbaccompanier> nounverbaccompanierSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Gerund> gerundSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Annexednoun> annexednounSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annexedId")
    private Set<Annexednoun> annexednounSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feminineNounId")
    private Set<Femininity> femininitySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Femininity> femininitySet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diminutiveNounId")
    private Set<Diminutive> diminutiveSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Diminutive> diminutiveSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adjectiveId")
    private Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exaggerationNounId")
    private Set<Exaggeration> exaggerationSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pluralNounId")
    private Set<Plural> pluralSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "singularNounId")
    private Set<Plural> pluralSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "properAdjectiveNounId")
    private Set<Properadjective> properadjectiveSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nounId")
    private Set<Properadjective> properadjectiveSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adjectiveId")
    private Set<Assimilateadjective> assimilateadjectiveSet;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "derivedNounId", referencedColumnName = "derivedNounId")
    @ManyToOne(optional = false)
    private Derivednoun derivedNounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticnoun() {
    }

    public Semanticnoun(Integer semanticNounId) {
        this.semanticNounId = semanticNounId;
    }

    public Semanticnoun(Derivednoun derivednoun, Semanticentry semanticentry) {
        this(derivednoun, semanticentry, new Suggestion(), "S", 0);
    }

    public Semanticnoun(Derivednoun derivednoun, Semanticentry semanticentry, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.derivedNounId = derivednoun;
        this.semanticEntryId = semanticentry;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Semanticnoun(Derivednoun derivednoun, Semanticentry semanticentry, Suggestion suggestion, String infoStatus, int chechStatus, Set<Annexednoun> annexednounsForAnnexedId, Set<Femininity> femininitiesForFeminineNounId, Set<Properadjective> properadjectivesForProperAdjectiveNounId, Set<Nounverbaccompanier> nounverbaccompaniers, Set<Nounadjectiveaccompanier> nounadjectiveaccompaniersForAdjectiveId, Set<Plural> pluralsForSingularNounId, Set<Assimilateadjective> assimilateadjectives, Set<Annexednoun> annexednounsForNounId, Set<Nounadjectiveaccompanier> nounadjectiveaccompaniersForNounId, Set<Properadjective> properadjectivesForNounId, Set<Diminutive> diminutivesForNounId, Set<Gerund> gerunds, Set<Exaggeration> exaggerations, Set<Femininity> femininitiesForNounId, Set<Diminutive> diminutivesForDiminutiveNounId, Set<Plural> pluralsForPluralNounId) {
        this.derivedNounId = derivednoun;
        this.semanticEntryId = semanticentry;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.annexednounSet1 = annexednounsForAnnexedId;
        this.femininitySet = femininitiesForFeminineNounId;
        this.properadjectiveSet = properadjectivesForProperAdjectiveNounId;
        this.nounverbaccompanierSet = nounverbaccompaniers;
        this.nounadjectiveaccompanierSet = nounadjectiveaccompaniersForAdjectiveId;
        this.pluralSet1 = pluralsForSingularNounId;
        this.assimilateadjectiveSet = assimilateadjectives;
        this.annexednounSet = annexednounsForNounId;
        this.nounadjectiveaccompanierSet1 = nounadjectiveaccompaniersForNounId;
        this.properadjectiveSet1 = properadjectivesForNounId;
        this.diminutiveSet1 = diminutivesForNounId;
        this.gerundSet = gerunds;
        this.exaggerationSet = exaggerations;
        this.femininitySet1 = femininitiesForNounId;
        this.diminutiveSet = diminutivesForDiminutiveNounId;
        this.pluralSet = pluralsForPluralNounId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return semanticNounId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticNounId(id);
    }

    public void setSemanticNounId(Integer semanticNounId) {
        this.semanticNounId = semanticNounId;
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
    public Set<Annexednoun> getAnnexednounsForNounId() {
        return annexednounSet;
    }

    public void setAnnexednounSet(Set<Annexednoun> annexednounSet) {
        this.annexednounSet = annexednounSet;
    }

    @XmlTransient
    public Set<Annexednoun> getAnnexednounSet1() {
        return annexednounSet1;
    }

    public void setAnnexednounSet1(Set<Annexednoun> annexednounSet1) {
        this.annexednounSet1 = annexednounSet1;
    }

    @XmlTransient
    public Set<Femininity> getFemininitySet() {
        return femininitySet;
    }

    public void setFemininitySet(Set<Femininity> femininitySet) {
        this.femininitySet = femininitySet;
    }

    @XmlTransient
    public Set<Femininity> getFemininitiesForNounId() {
        return femininitySet1;
    }

    public void setFemininitySet1(Set<Femininity> femininitySet1) {
        this.femininitySet1 = femininitySet1;
    }

    @XmlTransient
    public Set<Diminutive> getDiminutivesForNounId() {
        return diminutiveSet;
    }

    public void setDiminutiveSet(Set<Diminutive> diminutiveSet) {
        this.diminutiveSet = diminutiveSet;
    }

    @XmlTransient
    public Set<Diminutive> getDiminutiveSet1() {
        return diminutiveSet1;
    }

    public void setDiminutiveSet1(Set<Diminutive> diminutiveSet1) {
        this.diminutiveSet1 = diminutiveSet1;
    }

    @XmlTransient
    public Set<Nounadjectiveaccompanier> getNounadjectiveaccompaniers() {
        return nounadjectiveaccompanierSet;
    }

    public void setNounadjectiveaccompanierSet(Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet) {
        this.nounadjectiveaccompanierSet = nounadjectiveaccompanierSet;
    }

    @XmlTransient
    public Set<Nounadjectiveaccompanier> getNounadjectiveaccompaniersForNounId() {
        return nounadjectiveaccompanierSet1;
    }

    public void setNounadjectiveaccompanierSet1(Set<Nounadjectiveaccompanier> nounadjectiveaccompanierSet1) {
        this.nounadjectiveaccompanierSet1 = nounadjectiveaccompanierSet1;
    }

    @XmlTransient
    public Set<Exaggeration> getExaggerations() {
        return exaggerationSet;
    }

    public void setExaggerationSet(Set<Exaggeration> exaggerationSet) {
        this.exaggerationSet = exaggerationSet;
    }

    @XmlTransient
    public Set<Plural> getPluralsForSingularNounId() {
        return pluralSet;
    }

    public void setPluralSet(Set<Plural> pluralSet) {
        this.pluralSet = pluralSet;
    }

    @XmlTransient
    public Set<Plural> getPluralSet1() {
        return pluralSet1;
    }

    public void setPluralSet1(Set<Plural> pluralSet1) {
        this.pluralSet1 = pluralSet1;
    }

    @XmlTransient
    public Set<Properadjective> getProperadjectives() {
        return properadjectiveSet;
    }

    public void setProperadjectiveSet(Set<Properadjective> properadjectiveSet) {
        this.properadjectiveSet = properadjectiveSet;
    }

    @XmlTransient
    public Set<Properadjective> getProperadjectivesForNounId() {
        return properadjectiveSet1;
    }

    public void setProperadjectiveSet1(Set<Properadjective> properadjectiveSet1) {
        this.properadjectiveSet1 = properadjectiveSet1;
    }

    @XmlTransient
    public Set<Assimilateadjective> getAssimilateadjectives() {
        return assimilateadjectiveSet;
    }

    public void setAssimilateadjectiveSet(Set<Assimilateadjective> assimilateadjectiveSet) {
        this.assimilateadjectiveSet = assimilateadjectiveSet;
    }

    public Semanticentry getSemanticentry() {
        return semanticEntryId;
    }

    public void setSemanticEntry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public Derivednoun getDerivednoun() {
        return derivedNounId;
    }

    public void setDerivednoun(Derivednoun derivedNounId) {
        this.derivedNounId = derivedNounId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticNounId != null ? semanticNounId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticnoun)) {
            return false;
        }
        Semanticnoun other = (Semanticnoun) object;
        if ((this.semanticNounId == null && other.semanticNounId != null) || (this.semanticNounId != null && !this.semanticNounId.equals(other.semanticNounId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticnoun[ semanticNounId=" + semanticNounId + " ]";
    }

}
