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
    @NamedQuery(name = "Pattern.findAll", query = "SELECT p FROM Pattern p"),
    @NamedQuery(name = "Pattern.findByPatternId", query = "SELECT p FROM Pattern p WHERE p.patternId = :patternId"),
    @NamedQuery(name = "Pattern.findByPattern", query = "SELECT p FROM Pattern p WHERE p.pattern = :pattern"),
    @NamedQuery(name = "Pattern.findByInfoStatus", query = "SELECT p FROM Pattern p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Pattern.findByChechStatus", query = "SELECT p FROM Pattern p WHERE p.chechStatus = :chechStatus")})
public class Pattern extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer patternId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String pattern;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patternId")
    private Set<Derivednoun> derivednounSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patternId")
    private Set<Derivedverb> derivedverbSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Pattern() {
    }

    public Pattern(Integer patternId) {
        this.patternId = patternId;
    }

    public Pattern(String pattern) {
        this(new Suggestion(), pattern, "S", 0);
    }

    public Pattern(Suggestion suggestion, String pattern, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.pattern = pattern;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Pattern(Suggestion suggestion, String pattern, String infoStatus, int chechStatus, Set<Derivednoun> derivednouns, Set<Derivedverb> derivedverbs) {
        this.suggestionId = suggestion;
        this.pattern = pattern;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivednounSet = derivednouns;
        this.derivedverbSet = derivedverbs;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return patternId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setPatternId(id);
    }

    public void setPatternId(Integer patternId) {
        this.patternId = patternId;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
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
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
    }

    @XmlTransient
    public Set<Derivedverb> getDerivedverbs() {
        return derivedverbSet;
    }

    public void setDerivedverbSet(Set<Derivedverb> derivedverbSet) {
        this.derivedverbSet = derivedverbSet;
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
        hash += (patternId != null ? patternId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pattern)) {
            return false;
        }
        Pattern other = (Pattern) object;
        if ((this.patternId == null && other.patternId != null) || (this.patternId != null && !this.patternId.equals(other.patternId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Pattern[ patternId=" + patternId + " ]";
    }

}
