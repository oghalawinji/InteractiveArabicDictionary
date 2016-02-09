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
    @NamedQuery(name = "Nounverbaccompanier.findAll", query = "SELECT n FROM Nounverbaccompanier n"),
    @NamedQuery(name = "Nounverbaccompanier.findByNounVerbAccompanieId", query = "SELECT n FROM Nounverbaccompanier n WHERE n.nounVerbAccompanieId = :nounVerbAccompanieId"),
    @NamedQuery(name = "Nounverbaccompanier.findByInfoStatus", query = "SELECT n FROM Nounverbaccompanier n WHERE n.infoStatus = :infoStatus"),
    @NamedQuery(name = "Nounverbaccompanier.findByChechStatus", query = "SELECT n FROM Nounverbaccompanier n WHERE n.chechStatus = :chechStatus")})
public class Nounverbaccompanier extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer nounVerbAccompanieId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @JoinColumn(name = "verbId", referencedColumnName = "semanticVerbId")
    @ManyToOne(optional = false)
    private Semanticverb verbId;

    public Nounverbaccompanier() {
    }

    public Nounverbaccompanier(Integer nounVerbAccompanieId) {
        this.nounVerbAccompanieId = nounVerbAccompanieId;
    }

    public Nounverbaccompanier(Semanticverb semanticverb, Semanticnoun semanticnoun) {
        this(semanticverb, semanticnoun, new Suggestion(), "S", 0);
    }

    public Nounverbaccompanier(Semanticverb semanticverb, Semanticnoun semanticnoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.verbId = semanticverb;
        this.nounId = semanticnoun;
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
        return nounVerbAccompanieId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setNounVerbAccompanieId(id);
    }

    public void setNounVerbAccompanieId(Integer nounVerbAccompanieId) {
        this.nounVerbAccompanieId = nounVerbAccompanieId;
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

    public Semanticverb getSemanticverb() {
        return verbId;
    }

    public void setVerb(Semanticverb verbId) {
        this.verbId = verbId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nounVerbAccompanieId != null ? nounVerbAccompanieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nounverbaccompanier)) {
            return false;
        }
        Nounverbaccompanier other = (Nounverbaccompanier) object;
        if ((this.nounVerbAccompanieId == null && other.nounVerbAccompanieId != null) || (this.nounVerbAccompanieId != null && !this.nounVerbAccompanieId.equals(other.nounVerbAccompanieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Nounverbaccompanier[ nounVerbAccompanieId=" + nounVerbAccompanieId + " ]";
    }

}
