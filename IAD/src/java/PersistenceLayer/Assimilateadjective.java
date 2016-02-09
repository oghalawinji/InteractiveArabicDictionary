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
    @NamedQuery(name = "Assimilateadjective.findAll", query = "SELECT a FROM Assimilateadjective a"),
    @NamedQuery(name = "Assimilateadjective.findByAssimilateAdjectiveId", query = "SELECT a FROM Assimilateadjective a WHERE a.assimilateAdjectiveId = :assimilateAdjectiveId"),
    @NamedQuery(name = "Assimilateadjective.findByInfoStatus", query = "SELECT a FROM Assimilateadjective a WHERE a.infoStatus = :infoStatus"),
    @NamedQuery(name = "Assimilateadjective.findByChechStatus", query = "SELECT a FROM Assimilateadjective a WHERE a.chechStatus = :chechStatus")})
public class Assimilateadjective extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer assimilateAdjectiveId;
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
    @JoinColumn(name = "adjectiveId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun adjectiveId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Assimilateadjective() {
    }

    public Assimilateadjective(Integer assimilateAdjectiveId) {
        this.assimilateAdjectiveId = assimilateAdjectiveId;
    }

    public Assimilateadjective(Semanticverb semanticverb, Semanticnoun semanticnoun) {
        this(semanticverb, semanticnoun, new Suggestion(), "S", 0);
    }

    public Assimilateadjective(Semanticverb semanticverb, Semanticnoun semanticnoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.verbId = semanticverb;
        this.adjectiveId = semanticnoun;
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
        return assimilateAdjectiveId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setAssimilateAdjectiveId(id);
    }

    public void setAssimilateAdjectiveId(Integer assimilateAdjectiveId) {
        this.assimilateAdjectiveId = assimilateAdjectiveId;
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
        return adjectiveId;
    }

    public void setAdjective(Semanticnoun adjectiveId) {
        this.adjectiveId = adjectiveId;
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
        hash += (assimilateAdjectiveId != null ? assimilateAdjectiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assimilateadjective)) {
            return false;
        }
        Assimilateadjective other = (Assimilateadjective) object;
        if ((this.assimilateAdjectiveId == null && other.assimilateAdjectiveId != null) || (this.assimilateAdjectiveId != null && !this.assimilateAdjectiveId.equals(other.assimilateAdjectiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Assimilateadjective[ assimilateAdjectiveId=" + assimilateAdjectiveId + " ]";
    }

}
