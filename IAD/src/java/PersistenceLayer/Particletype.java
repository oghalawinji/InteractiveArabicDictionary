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
    @NamedQuery(name = "Particletype.findAll", query = "SELECT p FROM Particletype p"),
    @NamedQuery(name = "Particletype.findByParticleTypeId", query = "SELECT p FROM Particletype p WHERE p.particleTypeId = :particleTypeId"),
    @NamedQuery(name = "Particletype.findByParticleType", query = "SELECT p FROM Particletype p WHERE p.particleType = :particleType"),
    @NamedQuery(name = "Particletype.findByInfoStatus", query = "SELECT p FROM Particletype p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Particletype.findByChechStatus", query = "SELECT p FROM Particletype p WHERE p.chechStatus = :chechStatus")})
public class Particletype extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer particleTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String particleType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "particleTypeId")
    private Set<Derivedparticle> derivedparticleSet;

    public Particletype() {
    }

    public Particletype(Integer particleTypeId) {
        this.particleTypeId = particleTypeId;
    }

    public Particletype(String particleType) {
        this(new Suggestion(), particleType, "S", 0);
    }

    public Particletype(Suggestion suggestion, String particleType, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.particleType = particleType;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Particletype(Suggestion suggestion, String particleType, String infoStatus, int chechStatus, Set<Derivedparticle> derivedparticles) {
        this.suggestionId = suggestion;
        this.particleType = particleType;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
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
        return particleTypeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setParticleTypeId(id);
    }

    public void setParticleTypeId(Integer particleTypeId) {
        this.particleTypeId = particleTypeId;
    }

    public String getParticleType() {
        return particleType;
    }

    public void setParticleType(String particleType) {
        this.particleType = particleType;
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

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Derivedparticle> getDerivedparticles() {
        return derivedparticleSet;
    }

    public void setDerivedparticleSet(Set<Derivedparticle> derivedparticleSet) {
        this.derivedparticleSet = derivedparticleSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (particleTypeId != null ? particleTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Particletype)) {
            return false;
        }
        Particletype other = (Particletype) object;
        if ((this.particleTypeId == null && other.particleTypeId != null) || (this.particleTypeId != null && !this.particleTypeId.equals(other.particleTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Particletype[ particleTypeId=" + particleTypeId + " ]";
    }

}
