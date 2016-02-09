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
    @NamedQuery(name = "Contextualverb.findAll", query = "SELECT c FROM Contextualverb c"),
    @NamedQuery(name = "Contextualverb.findByContextualVerbId", query = "SELECT c FROM Contextualverb c WHERE c.contextualVerbId = :contextualVerbId"),
    @NamedQuery(name = "Contextualverb.findByInfoStatus", query = "SELECT c FROM Contextualverb c WHERE c.infoStatus = :infoStatus"),
    @NamedQuery(name = "Contextualverb.findByChechStatus", query = "SELECT c FROM Contextualverb c WHERE c.chechStatus = :chechStatus")})
public class Contextualverb extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer contextualVerbId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "transitivityCaseId", referencedColumnName = "transitivityCaseId")
    @ManyToOne(optional = false)
    private Transitivitycase transitivityCaseId;
    @JoinColumn(name = "derivedVerbId", referencedColumnName = "derivedVerbId")
    @ManyToOne(optional = false)
    private Derivedverb derivedVerbId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contextualVerbId")
    private Set<Semanticverb> semanticverbSet;

    public Contextualverb() {
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public Contextualverb(Integer contextualVerbId) {
        this.contextualVerbId = contextualVerbId;
    }

    public Contextualverb(Derivedverb derivedverb, Transitivitycase transitivitycase) {
        this(derivedverb, new Suggestion(), transitivitycase, "S", 0);
    }

    public Contextualverb(Derivedverb derivedverb, Suggestion suggestion, Transitivitycase transitivitycase, String infoStatus, int chechStatus) {
        this.derivedVerbId = derivedverb;
        this.suggestionId = suggestion;
        this.transitivityCaseId = transitivitycase;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Contextualverb(Derivedverb derivedverb, Suggestion suggestion, Transitivitycase transitivitycase, String infoStatus, int chechStatus, Set<Semanticverb> semanticverbs) {
        this.derivedVerbId = derivedverb;
        this.suggestionId = suggestion;
        this.transitivityCaseId = transitivitycase;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.semanticverbSet = semanticverbs;
    }

    @Override
    public Integer getIdentity() {
        return contextualVerbId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setContextualVerbId(id);
    }

    public void setContextualVerbId(Integer contextualVerbId) {
        this.contextualVerbId = contextualVerbId;
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

    public Transitivitycase getTransitivitycase() {
        return transitivityCaseId;
    }

    public void setTransitivitycase(Transitivitycase transitivityCaseId) {
        this.transitivityCaseId = transitivityCaseId;
    }

    public Derivedverb getDerivedverb() {
        return derivedVerbId;
    }

    public void setDerivedverb(Derivedverb derivedVerbId) {
        this.derivedVerbId = derivedVerbId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
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
        hash += (contextualVerbId != null ? contextualVerbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contextualverb)) {
            return false;
        }
        Contextualverb other = (Contextualverb) object;
        if ((this.contextualVerbId == null && other.contextualVerbId != null) || (this.contextualVerbId != null && !this.contextualVerbId.equals(other.contextualVerbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Contextualverb[ contextualVerbId=" + contextualVerbId + " ]";
    }

}
