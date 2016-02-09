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
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Transitivitycase.findAll", query = "SELECT t FROM Transitivitycase t"),
    @NamedQuery(name = "Transitivitycase.findByTransitivityCaseId", query = "SELECT t FROM Transitivitycase t WHERE t.transitivityCaseId = :transitivityCaseId"),
    @NamedQuery(name = "Transitivitycase.findByTransitivityCase", query = "SELECT t FROM Transitivitycase t WHERE t.transitivityCase = :transitivityCase"),
    @NamedQuery(name = "Transitivitycase.findByInfoStatus", query = "SELECT t FROM Transitivitycase t WHERE t.infoStatus = :infoStatus"),
    @NamedQuery(name = "Transitivitycase.findByChechStatus", query = "SELECT t FROM Transitivitycase t WHERE t.chechStatus = :chechStatus")})
public class Transitivitycase extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer transitivityCaseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String transitivityCase;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transitivityCaseId")
    private Set<Contextualverb> contextualverbSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transitivitycase")
    private Transitiveletter transitiveletter;

    public Transitivitycase() {
    }

    public Transitivitycase(Integer transitivityCaseId) {
        this.transitivityCaseId = transitivityCaseId;
    }

    public Transitivitycase(String transitivityCase) {
        this(new Suggestion(), transitivityCase, "S", 0);
    }

    public Transitivitycase(Suggestion suggestion, String transitivityCase, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.transitivityCase = transitivityCase;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

//    public Transitivitycase(Suggestion suggestion, String transitivityCase, String infoStatus, int chechStatus, Set<Contextualverb> contextualverbs, Set<Transitiveletter> transitiveletters) {
//        this.suggestionId = suggestion;
//        this.transitivityCase = transitivityCase;
//        this.infoStatus = infoStatus;
//        this.chechStatus = chechStatus;
//        this.contextualverbSet = contextualverbs;
//        this.transitiveletter = transitiveletters;
//    }
    @Override
    public Integer getIdentity() {
        return transitivityCaseId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setTransitivityCaseId(id);
    }

    public void setTransitivityCaseId(Integer transitivityCaseId) {
        this.transitivityCaseId = transitivityCaseId;
    }

    public String getTransitivityCase() {
        return transitivityCase;
    }

    public void setTransitivityCase(String transitivityCase) {
        this.transitivityCase = transitivityCase;
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
    public Set<Contextualverb> getContextualverbs() {
        return contextualverbSet;
    }

    public void setContextualverbSet(Set<Contextualverb> contextualverbSet) {
        this.contextualverbSet = contextualverbSet;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Transitiveletter getTransitiveletter() {
        return transitiveletter;
    }

    public void setTransitiveletter(Transitiveletter transitiveletter) {
        this.transitiveletter = transitiveletter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transitivityCaseId != null ? transitivityCaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transitivitycase)) {
            return false;
        }
        Transitivitycase other = (Transitivitycase) object;
        if ((this.transitivityCaseId == null && other.transitivityCaseId != null) || (this.transitivityCaseId != null && !this.transitivityCaseId.equals(other.transitivityCaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Transitivitycase[ transitivityCaseId=" + transitivityCaseId + " ]";
    }

}
