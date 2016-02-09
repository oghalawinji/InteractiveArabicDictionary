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
    @NamedQuery(name = "Nounadjectiveaccompanier.findAll", query = "SELECT n FROM Nounadjectiveaccompanier n"),
    @NamedQuery(name = "Nounadjectiveaccompanier.findByNounAdjectiveAccompanierId", query = "SELECT n FROM Nounadjectiveaccompanier n WHERE n.nounAdjectiveAccompanierId = :nounAdjectiveAccompanierId"),
    @NamedQuery(name = "Nounadjectiveaccompanier.findByInfoStatus", query = "SELECT n FROM Nounadjectiveaccompanier n WHERE n.infoStatus = :infoStatus"),
    @NamedQuery(name = "Nounadjectiveaccompanier.findByChechStatus", query = "SELECT n FROM Nounadjectiveaccompanier n WHERE n.chechStatus = :chechStatus")})
public class Nounadjectiveaccompanier extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer nounAdjectiveAccompanierId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "adjectiveId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun adjectiveId;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Nounadjectiveaccompanier() {
    }

    public Nounadjectiveaccompanier(Integer nounAdjectiveAccompanierId) {
        this.nounAdjectiveAccompanierId = nounAdjectiveAccompanierId;
    }

    public Nounadjectiveaccompanier(Semanticnoun semanticAdjective, Semanticnoun semanticNoun) {
        this(semanticAdjective, semanticNoun, new Suggestion(), "S", 0);
    }

    public Nounadjectiveaccompanier(Semanticnoun semanticAdjective, Semanticnoun semanticNoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.adjectiveId = semanticAdjective;
        this.nounId = semanticNoun;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return nounAdjectiveAccompanierId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setNounAdjectiveAccompanierId(id);
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public void setNounAdjectiveAccompanierId(Integer nounAdjectiveAccompanierId) {
        this.nounAdjectiveAccompanierId = nounAdjectiveAccompanierId;
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

    public Semanticnoun getsemanticAdjective() {
        return adjectiveId;
    }

    public void setAdjective(Semanticnoun adjectiveId) {
        this.adjectiveId = adjectiveId;
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
        hash += (nounAdjectiveAccompanierId != null ? nounAdjectiveAccompanierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nounadjectiveaccompanier)) {
            return false;
        }
        Nounadjectiveaccompanier other = (Nounadjectiveaccompanier) object;
        if ((this.nounAdjectiveAccompanierId == null && other.nounAdjectiveAccompanierId != null) || (this.nounAdjectiveAccompanierId != null && !this.nounAdjectiveAccompanierId.equals(other.nounAdjectiveAccompanierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Nounadjectiveaccompanier[ nounAdjectiveAccompanierId=" + nounAdjectiveAccompanierId + " ]";
    }

}
