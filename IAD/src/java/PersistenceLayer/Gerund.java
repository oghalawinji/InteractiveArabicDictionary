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
    @NamedQuery(name = "Gerund.findAll", query = "SELECT g FROM Gerund g"),
    @NamedQuery(name = "Gerund.findByGerundId", query = "SELECT g FROM Gerund g WHERE g.gerundId = :gerundId"),
    @NamedQuery(name = "Gerund.findByInfoStatus", query = "SELECT g FROM Gerund g WHERE g.infoStatus = :infoStatus"),
    @NamedQuery(name = "Gerund.findByChechStatus", query = "SELECT g FROM Gerund g WHERE g.chechStatus = :chechStatus")})
public class Gerund extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer gerundId;
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
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Gerund() {
    }

    public Gerund(Integer gerundId) {
        this.gerundId = gerundId;
    }

    public Gerund(Semanticverb semanticverb, Semanticnoun semanticnoun) {
        this(semanticverb, semanticnoun, new Suggestion(), "S", 0);
    }

    public Gerund(Semanticverb semanticverb, Semanticnoun semanticnoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.verbId = semanticverb;
        this.nounId = semanticnoun;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return gerundId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setGerundId(id);
    }

    public void setGerundId(Integer gerundId) {
        this.gerundId = gerundId;
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
        hash += (gerundId != null ? gerundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gerund)) {
            return false;
        }
        Gerund other = (Gerund) object;
        if ((this.gerundId == null && other.gerundId != null) || (this.gerundId != null && !this.gerundId.equals(other.gerundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Gerund[ gerundId=" + gerundId + " ]";
    }

}
