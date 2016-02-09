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
    @NamedQuery(name = "Diminutive.findAll", query = "SELECT d FROM Diminutive d"),
    @NamedQuery(name = "Diminutive.findByDiminutiveId", query = "SELECT d FROM Diminutive d WHERE d.diminutiveId = :diminutiveId"),
    @NamedQuery(name = "Diminutive.findByInfoStatus", query = "SELECT d FROM Diminutive d WHERE d.infoStatus = :infoStatus"),
    @NamedQuery(name = "Diminutive.findByChechStatus", query = "SELECT d FROM Diminutive d WHERE d.chechStatus = :chechStatus")})
public class Diminutive extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer diminutiveId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "diminutiveNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun diminutiveNounId;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Diminutive() {
    }

    public Diminutive(Integer diminutiveId) {
        this.diminutiveId = diminutiveId;
    }

    public Diminutive(Semanticnoun semanticDiminutive, Semanticnoun semanticNoun) {
        this(semanticDiminutive, semanticNoun, new Suggestion(), "S", 0);
    }

    public Diminutive(Semanticnoun semanticDiminutive, Semanticnoun semanticNoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.diminutiveNounId = semanticDiminutive;
        this.nounId = semanticNoun;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return diminutiveId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setDiminutiveId(id);
    }

    public void setDiminutiveId(Integer diminutiveId) {
        this.diminutiveId = diminutiveId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
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

    public Semanticnoun getSemanticDiminutive() {
        return diminutiveNounId;
    }

    public void setDiminutiveNoun(Semanticnoun diminutiveNounId) {
        this.diminutiveNounId = diminutiveNounId;
    }

    public Semanticnoun getNoun() {
        return nounId;
    }

    public void setNoun(Semanticnoun nounId) {
        this.nounId = nounId;
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
        hash += (diminutiveId != null ? diminutiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diminutive)) {
            return false;
        }
        Diminutive other = (Diminutive) object;
        if ((this.diminutiveId == null && other.diminutiveId != null) || (this.diminutiveId != null && !this.diminutiveId.equals(other.diminutiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Diminutive[ diminutiveId=" + diminutiveId + " ]";
    }

}
