/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semanticparticle.findAll", query = "SELECT s FROM Semanticparticle s"),
    @NamedQuery(name = "Semanticparticle.findBySemanticParticleId", query = "SELECT s FROM Semanticparticle s WHERE s.semanticParticleId = :semanticParticleId"),
    @NamedQuery(name = "Semanticparticle.findByInfoStatus", query = "SELECT s FROM Semanticparticle s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticparticle.findByChechStatus", query = "SELECT s FROM Semanticparticle s WHERE s.chechStatus = :chechStatus")})
public class Semanticparticle extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticParticleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "derivedParticleId", referencedColumnName = "derivedParticleId")
    @ManyToOne(optional = false)
    private Derivedparticle derivedParticleId;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticparticle() {
    }

    public Semanticparticle(Integer semanticParticleId) {
        this.semanticParticleId = semanticParticleId;
    }

    public Semanticparticle(Semanticentry semanticentry, Derivedparticle derivedparticle) {
        this(semanticentry, new Suggestion(), derivedparticle, "S", 0);
    }

    public Semanticparticle(Semanticentry semanticentry, Suggestion suggestion, Derivedparticle derivedparticle, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.suggestionId = suggestion;
        this.derivedParticleId = derivedparticle;
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
        return semanticParticleId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticParticleId(id);
    }

    public void setSemanticParticleId(Integer semanticParticleId) {
        this.semanticParticleId = semanticParticleId;
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

    public Derivedparticle getDerivedparticle() {
        return derivedParticleId;
    }

    public void setDerivedParticle(Derivedparticle derivedParticleId) {
        this.derivedParticleId = derivedParticleId;
    }

    public Semanticentry getSemanticentry() {
        return semanticEntryId;
    }

    public void setSemanticEntry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
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
        hash += (semanticParticleId != null ? semanticParticleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticparticle)) {
            return false;
        }
        Semanticparticle other = (Semanticparticle) object;
        if ((this.semanticParticleId == null && other.semanticParticleId != null) || (this.semanticParticleId != null && !this.semanticParticleId.equals(other.semanticParticleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticparticle[ semanticParticleId=" + semanticParticleId + " ]";
    }

}
