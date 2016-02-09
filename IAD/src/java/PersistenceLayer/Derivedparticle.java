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
    @NamedQuery(name = "Derivedparticle.findAll", query = "SELECT d FROM Derivedparticle d"),
    @NamedQuery(name = "Derivedparticle.findByDerivedParticleId", query = "SELECT d FROM Derivedparticle d WHERE d.derivedParticleId = :derivedParticleId"),
    @NamedQuery(name = "Derivedparticle.findByVocalizedParticle", query = "SELECT d FROM Derivedparticle d WHERE d.vocalizedParticle = :vocalizedParticle"),
    @NamedQuery(name = "Derivedparticle.findByInfoStatus", query = "SELECT d FROM Derivedparticle d WHERE d.infoStatus = :infoStatus"),
    @NamedQuery(name = "Derivedparticle.findByChechStatus", query = "SELECT d FROM Derivedparticle d WHERE d.chechStatus = :chechStatus")})
public class Derivedparticle extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer derivedParticleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String vocalizedParticle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "PronunciationId", referencedColumnName = "PronunciationId")
    @ManyToOne
    private Pronunciation pronunciationId;
    @JoinColumn(name = "particleTypeId", referencedColumnName = "particleTypeId")
    @ManyToOne(optional = false)
    private Particletype particleTypeId;
    @JoinColumn(name = "rawParticleId", referencedColumnName = "rawWordId")
    @ManyToOne(optional = false)
    private Rawword rawParticleId;
    @JoinColumn(name = "rootId", referencedColumnName = "rootId")
    @ManyToOne(optional = false)
    private Root rootId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "derivedParticleId")
    private Set<Semanticparticle> semanticparticleSet;

    public Derivedparticle() {
    }

    public Derivedparticle(Pronunciation pronunciation, Root root, Rawword rawword, Particletype particletype, String vocalizedParticle) {
        this(pronunciation, root, rawword, particletype, new Suggestion(), vocalizedParticle, "S", 0);
    }

    public Derivedparticle(Pronunciation pronunciation, Root root, Rawword rawword, Particletype particletype, Suggestion suggestion, String vocalizedParticle, String infoStatus, int chechStatus) {
        this.pronunciationId = pronunciation;
        this.rootId = root;
        this.rawParticleId = rawword;
        this.particleTypeId = particletype;
        this.suggestionId = suggestion;
        this.vocalizedParticle = vocalizedParticle;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Derivedparticle(Pronunciation pronunciation, Root root, Rawword rawword, Particletype particletype, Suggestion suggestion, String vocalizedParticle, String infoStatus, int chechStatus, Set<Semanticparticle> semanticparticles) {
        this.pronunciationId = pronunciation;
        this.rootId = root;
        this.rawParticleId = rawword;
        this.particleTypeId = particletype;
        this.suggestionId = suggestion;
        this.vocalizedParticle = vocalizedParticle;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.semanticparticleSet = semanticparticles;
    }

    @Override
    public Integer getIdentity() {
        return derivedParticleId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setDerivedParticleId(id);
    }

    public void setDerivedParticleId(Integer derivedParticleId) {
        this.derivedParticleId = derivedParticleId;
    }

    public String getVocalizedParticle() {
        return vocalizedParticle;
    }

    public void setVocalizedParticle(String vocalizedParticle) {
        this.vocalizedParticle = vocalizedParticle;
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

    public Pronunciation getPronunciation() {
        return pronunciationId;
    }

    public void setPronunciation(Pronunciation pronunciationId) {
        this.pronunciationId = pronunciationId;
    }

    public Particletype getParticletype() {
        return particleTypeId;
    }

    public void setParticletype(Particletype particleTypeId) {
        this.particleTypeId = particleTypeId;
    }

    public Rawword getRawword() {
        return rawParticleId;
    }

    public void setRawword(Rawword rawParticleId) {
        this.rawParticleId = rawParticleId;
    }

    public Root getRoot() {
        return rootId;
    }

    public void setRoot(Root rootId) {
        this.rootId = rootId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Semanticparticle> getSemanticparticles() {
        return semanticparticleSet;
    }

    public void setSemanticparticleSet(Set<Semanticparticle> semanticparticleSet) {
        this.semanticparticleSet = semanticparticleSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (derivedParticleId != null ? derivedParticleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Derivedparticle)) {
            return false;
        }
        Derivedparticle other = (Derivedparticle) object;
        if ((this.derivedParticleId == null && other.derivedParticleId != null) || (this.derivedParticleId != null && !this.derivedParticleId.equals(other.derivedParticleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Derivedparticle[ derivedParticleId=" + derivedParticleId + " ]";
    }

}
