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
    @NamedQuery(name = "Femininity.findAll", query = "SELECT f FROM Femininity f"),
    @NamedQuery(name = "Femininity.findByFemininityId", query = "SELECT f FROM Femininity f WHERE f.femininityId = :femininityId"),
    @NamedQuery(name = "Femininity.findByInfoStatus", query = "SELECT f FROM Femininity f WHERE f.infoStatus = :infoStatus"),
    @NamedQuery(name = "Femininity.findByChechStatus", query = "SELECT f FROM Femininity f WHERE f.chechStatus = :chechStatus")})
public class Femininity extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer femininityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "feminineNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun feminineNounId;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Femininity() {
    }

    public Femininity(Integer femininityId) {
        this.femininityId = femininityId;
    }

    public Femininity(Semanticnoun semanticFemininity, Semanticnoun semanticNoun) {
        this(semanticFemininity, semanticNoun, new Suggestion(), "S", 0);
    }

    public Femininity(Semanticnoun semanticFemininity, Semanticnoun semanticNoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.feminineNounId = semanticFemininity;
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
        return femininityId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setFemininityId(id);
    }

    public void setFemininityId(Integer femininityId) {
        this.femininityId = femininityId;
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

    public Semanticnoun getSemanticFemininity() {
        return feminineNounId;
    }

    public void setFeminineNoun(Semanticnoun feminineNounId) {
        this.feminineNounId = feminineNounId;
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
        hash += (femininityId != null ? femininityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Femininity)) {
            return false;
        }
        Femininity other = (Femininity) object;
        if ((this.femininityId == null && other.femininityId != null) || (this.femininityId != null && !this.femininityId.equals(other.femininityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Femininity[ femininityId=" + femininityId + " ]";
    }

}
