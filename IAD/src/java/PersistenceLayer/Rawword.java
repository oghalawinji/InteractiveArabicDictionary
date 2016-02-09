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
    @NamedQuery(name = "Rawword.findAll", query = "SELECT r FROM Rawword r"),
    @NamedQuery(name = "Rawword.findByRawWordId", query = "SELECT r FROM Rawword r WHERE r.rawWordId = :rawWordId"),
    @NamedQuery(name = "Rawword.findByRawWord", query = "SELECT r FROM Rawword r WHERE r.rawWord = :rawWord"),
    @NamedQuery(name = "Rawword.findByInfoStatus", query = "SELECT r FROM Rawword r WHERE r.infoStatus = :infoStatus"),
    @NamedQuery(name = "Rawword.findByChechStatus", query = "SELECT r FROM Rawword r WHERE r.chechStatus = :chechStatus")})
public class Rawword extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer rawWordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String rawWord;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rawNounId")
    private Set<Derivednoun> derivednounSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rawParticleId")
    private Set<Derivedparticle> derivedparticleSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rawWordId")
    private Set<Derivedverb> derivedverbSet;

    public Rawword() {
    }

    public Rawword(Integer rawWordId) {
        this.rawWordId = rawWordId;
    }

    public Rawword(String rawWord) {
        this(new Suggestion(), rawWord, "S", 0);
    }

    public Rawword(Suggestion suggestion, String rawWord, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.rawWord = rawWord;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Rawword(Suggestion suggestion, String rawWord, String infoStatus, int chechStatus, Set<Derivedverb> derivedverbs, Set<Derivednoun> derivednouns, Set<Derivedparticle> derivedparticles) {
        this.suggestionId = suggestion;
        this.rawWord = rawWord;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivedverbSet = derivedverbs;
        this.derivednounSet = derivednouns;
        this.derivedparticleSet = derivedparticles;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return rawWordId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setRawWordId(id);
    }

    public void setRawWordId(Integer rawWordId) {
        this.rawWordId = rawWordId;
    }

    public String getRawWord() {
        return rawWord;
    }

    public void setRawWord(String rawWord) {
        this.rawWord = rawWord;
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
    public Set<Derivedparticle> getDerivedparticles() {
        return derivedparticleSet;
    }

    public void setDerivedparticleSet(Set<Derivedparticle> derivedparticleSet) {
        this.derivedparticleSet = derivedparticleSet;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Derivedverb> getDerivedverbs() {
        return derivedverbSet;
    }

    public void setDerivedverbSet(Set<Derivedverb> derivedverbSet) {
        this.derivedverbSet = derivedverbSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rawWordId != null ? rawWordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rawword)) {
            return false;
        }
        Rawword other = (Rawword) object;
        if ((this.rawWordId == null && other.rawWordId != null) || (this.rawWordId != null && !this.rawWordId.equals(other.rawWordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Rawword[ rawWordId=" + rawWordId + " ]";
    }

}
