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
    @NamedQuery(name = "Properadjective.findAll", query = "SELECT p FROM Properadjective p"),
    @NamedQuery(name = "Properadjective.findByProperAdjectiveId", query = "SELECT p FROM Properadjective p WHERE p.properAdjectiveId = :properAdjectiveId"),
    @NamedQuery(name = "Properadjective.findByInfoStatus", query = "SELECT p FROM Properadjective p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Properadjective.findByChechStatus", query = "SELECT p FROM Properadjective p WHERE p.chechStatus = :chechStatus")})
public class Properadjective extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer properAdjectiveId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "properAdjectiveNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun properAdjectiveNounId;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Properadjective() {
    }

    public Properadjective(Integer properAdjectiveId) {
        this.properAdjectiveId = properAdjectiveId;
    }

    public Properadjective(Semanticnoun semanticProperAdjective, Semanticnoun semanticNoun) {
        this(semanticProperAdjective, semanticNoun, new Suggestion(), "S", 0);
    }

    public Properadjective(Semanticnoun semanticProperAdjective, Semanticnoun semanticNoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.properAdjectiveNounId = semanticProperAdjective;
        this.nounId = semanticNoun;
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
        return properAdjectiveId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setProperAdjectiveId(id);
    }

    public void setProperAdjectiveId(Integer properAdjectiveId) {
        this.properAdjectiveId = properAdjectiveId;
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

    public Semanticnoun getSemanticProperAdjective() {
        return properAdjectiveNounId;
    }

    public void setProperAdjectiveNoun(Semanticnoun properAdjectiveNounId) {
        this.properAdjectiveNounId = properAdjectiveNounId;
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
        hash += (properAdjectiveId != null ? properAdjectiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Properadjective)) {
            return false;
        }
        Properadjective other = (Properadjective) object;
        if ((this.properAdjectiveId == null && other.properAdjectiveId != null) || (this.properAdjectiveId != null && !this.properAdjectiveId.equals(other.properAdjectiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Properadjective[ properAdjectiveId=" + properAdjectiveId + " ]";
    }

}
