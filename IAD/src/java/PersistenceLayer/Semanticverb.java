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
    @NamedQuery(name = "Semanticverb.findAll", query = "SELECT s FROM Semanticverb s"),
    @NamedQuery(name = "Semanticverb.findBySemanticVerbId", query = "SELECT s FROM Semanticverb s WHERE s.semanticVerbId = :semanticVerbId"),
    @NamedQuery(name = "Semanticverb.findByInfoStatus", query = "SELECT s FROM Semanticverb s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticverb.findByChechStatus", query = "SELECT s FROM Semanticverb s WHERE s.chechStatus = :chechStatus")})
public class Semanticverb extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticVerbId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verbId")
    private Set<Nounverbaccompanier> nounverbaccompanierSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verbId")
    private Set<Gerund> gerundSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verbId")
    private Set<Exaggeration> exaggerationSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticVerbId")
    private Set<Subjecttype> subjecttypeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verbId")
    private Set<Assimilateadjective> assimilateadjectiveSet;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "contextualVerbId", referencedColumnName = "contextualVerbId")
    @ManyToOne(optional = false)
    private Contextualverb contextualVerbId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticverb() {
    }

    public Semanticverb(Integer semanticVerbId) {
        this.semanticVerbId = semanticVerbId;
    }

    public Semanticverb(Semanticentry semanticentry, Contextualverb contextualverb) {
        this(semanticentry, contextualverb, new Suggestion(), "S", 0);
    }

    public Semanticverb(Semanticentry semanticentry, Contextualverb contextualverb, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.contextualVerbId = contextualverb;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Semanticverb(Semanticentry semanticentry, Contextualverb contextualverb, Suggestion suggestion, String infoStatus, int chechStatus, Set<Subjecttype> subjecttypes, Set<Nounverbaccompanier> nounverbaccompaniers, Set<Gerund> gerunds, Set<Assimilateadjective> assimilateadjectives, Set<Exaggeration> exaggerations) {
        this.semanticEntryId = semanticentry;
        this.contextualVerbId = contextualverb;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.subjecttypeSet = subjecttypes;
        this.nounverbaccompanierSet = nounverbaccompaniers;
        this.gerundSet = gerunds;
        this.assimilateadjectiveSet = assimilateadjectives;
        this.exaggerationSet = exaggerations;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return semanticVerbId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticVerbId(id);
    }

    public void setSemanticVerbId(Integer semanticVerbId) {
        this.semanticVerbId = semanticVerbId;
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
    public Set<Exaggeration> getExaggerations() {
        return exaggerationSet;
    }

    public void setExaggerationSet(Set<Exaggeration> exaggerationSet) {
        this.exaggerationSet = exaggerationSet;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypes() {
        return subjecttypeSet;
    }

    public void setSubjecttypeSet(Set<Subjecttype> subjecttypeSet) {
        this.subjecttypeSet = subjecttypeSet;
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

    public void setSemanticentry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public Contextualverb getContextualverb() {
        return contextualVerbId;
    }

    public void setContextualverb(Contextualverb contextualVerbId) {
        this.contextualVerbId = contextualVerbId;
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
        hash += (semanticVerbId != null ? semanticVerbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticverb)) {
            return false;
        }
        Semanticverb other = (Semanticverb) object;
        if ((this.semanticVerbId == null && other.semanticVerbId != null) || (this.semanticVerbId != null && !this.semanticVerbId.equals(other.semanticVerbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticverb[ semanticVerbId=" + semanticVerbId + " ]";
    }

}
