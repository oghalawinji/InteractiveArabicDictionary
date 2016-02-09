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
    @NamedQuery(name = "Exaggeration.findAll", query = "SELECT e FROM Exaggeration e"),
    @NamedQuery(name = "Exaggeration.findByExaggerationId", query = "SELECT e FROM Exaggeration e WHERE e.exaggerationId = :exaggerationId"),
    @NamedQuery(name = "Exaggeration.findByInfoStatus", query = "SELECT e FROM Exaggeration e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Exaggeration.findByChechStatus", query = "SELECT e FROM Exaggeration e WHERE e.chechStatus = :chechStatus")})
public class Exaggeration extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer exaggerationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "verbId", referencedColumnName = "semanticVerbId")
    @ManyToOne(optional = false)
    private Semanticverb verbId;
    @JoinColumn(name = "exaggerationNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun exaggerationNounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Exaggeration() {
    }

    public Exaggeration(Integer exaggerationId) {
        this.exaggerationId = exaggerationId;
    }

    public Exaggeration(Semanticverb semanticverb, Semanticnoun semanticnoun) {
        this(semanticverb, semanticnoun, new Suggestion(), "S", 0);
    }

    public Exaggeration(Semanticverb semanticverb, Semanticnoun semanticnoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.verbId = semanticverb;
        this.exaggerationNounId = semanticnoun;
        this.suggestionId = suggestion;
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
        return exaggerationId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setExaggerationId(id);
    }

    public void setExaggerationId(Integer exaggerationId) {
        this.exaggerationId = exaggerationId;
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

    public Semanticverb getVerb() {
        return verbId;
    }

    public void setVerb(Semanticverb verbId) {
        this.verbId = verbId;
    }

    public Semanticnoun getSemanticnoun() {
        return exaggerationNounId;
    }

    public void setExaggerationNoun(Semanticnoun exaggerationNounId) {
        this.exaggerationNounId = exaggerationNounId;
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
        hash += (exaggerationId != null ? exaggerationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exaggeration)) {
            return false;
        }
        Exaggeration other = (Exaggeration) object;
        if ((this.exaggerationId == null && other.exaggerationId != null) || (this.exaggerationId != null && !this.exaggerationId.equals(other.exaggerationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Exaggeration[ exaggerationId=" + exaggerationId + " ]";
    }

}
