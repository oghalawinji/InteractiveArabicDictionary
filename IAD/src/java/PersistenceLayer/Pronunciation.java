/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Pronunciation.findAll", query = "SELECT p FROM Pronunciation p"),
    @NamedQuery(name = "Pronunciation.findByPronunciationId", query = "SELECT p FROM Pronunciation p WHERE p.pronunciationId = :pronunciationId"),
    @NamedQuery(name = "Pronunciation.findByPhonetic", query = "SELECT p FROM Pronunciation p WHERE p.phonetic = :phonetic"),
    @NamedQuery(name = "Pronunciation.findByInfoStatus", query = "SELECT p FROM Pronunciation p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Pronunciation.findByChechStatus", query = "SELECT p FROM Pronunciation p WHERE p.chechStatus = :chechStatus")})
public class Pronunciation extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer pronunciationId;
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] pronounciation;
    @Size(max = 45)
    private String phonetic;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(mappedBy = "pronunciationId")
    private Set<Idiom> idiomSet;
    @OneToMany(mappedBy = "pronunciationId")
    private Set<Derivednoun> derivednounSet;
    @OneToMany(mappedBy = "pronunciationId")
    private Set<Derivedparticle> derivedparticleSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(mappedBy = "pronunciationId")
    private Set<Derivedverb> derivedverbSet;

    public Pronunciation() {
    }

    public Pronunciation(Integer pronunciationId) {
        this.pronunciationId = pronunciationId;
    }

    public Pronunciation(Integer pronunciationId, byte[] pronounciation, String infoStatus, int chechStatus) {
        this.pronunciationId = pronunciationId;
        this.pronounciation = pronounciation;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return pronunciationId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setPronunciationId(id);
    }

    public void setPronunciationId(Integer pronunciationId) {
        this.pronunciationId = pronunciationId;
    }

    public byte[] getPronounciation() {
        return pronounciation;
    }

    public void setPronounciation(byte[] pronounciation) {
        this.pronounciation = pronounciation;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
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
        hash += (pronunciationId != null ? pronunciationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pronunciation)) {
            return false;
        }
        Pronunciation other = (Pronunciation) object;
        if ((this.pronunciationId == null && other.pronunciationId != null) || (this.pronunciationId != null && !this.pronunciationId.equals(other.pronunciationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Pronunciation[ pronunciationId=" + pronunciationId + " ]";
    }

}
