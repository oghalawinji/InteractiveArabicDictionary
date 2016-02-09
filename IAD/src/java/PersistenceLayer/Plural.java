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
    @NamedQuery(name = "Plural.findAll", query = "SELECT p FROM Plural p"),
    @NamedQuery(name = "Plural.findByPluralCoupleId", query = "SELECT p FROM Plural p WHERE p.pluralCoupleId = :pluralCoupleId"),
    @NamedQuery(name = "Plural.findByInfoStatus", query = "SELECT p FROM Plural p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Plural.findByChechStatus", query = "SELECT p FROM Plural p WHERE p.chechStatus = :chechStatus")})
public class Plural extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer pluralCoupleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "pluralTypeId", referencedColumnName = "pluralTypeId")
    @ManyToOne(optional = false)
    private Pluraltype pluralTypeId;
    @JoinColumn(name = "pluralNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun pluralNounId;
    @JoinColumn(name = "singularNounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun singularNounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Plural() {
    }

    public Plural(Integer pluralCoupleId) {
        this.pluralCoupleId = pluralCoupleId;
    }

    public Plural(Integer pluralCoupleId, String infoStatus, int chechStatus) {
        this.pluralCoupleId = pluralCoupleId;
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
        return pluralCoupleId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setPluralCoupleId(id);
    }

    public void setPluralCoupleId(Integer pluralCoupleId) {
        this.pluralCoupleId = pluralCoupleId;
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

    public Pluraltype getPluraltype() {
        return pluralTypeId;
    }

    public void setPluralType(Pluraltype pluralTypeId) {
        this.pluralTypeId = pluralTypeId;
    }

    public Semanticnoun getSemanticnounByPluralNounId() {
        return pluralNounId;
    }

    public void setPluralNoun(Semanticnoun pluralNounId) {
        this.pluralNounId = pluralNounId;
    }

    public Semanticnoun getSingularNoun() {
        return singularNounId;
    }

    public void setSingularNoun(Semanticnoun singularNounId) {
        this.singularNounId = singularNounId;
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
        hash += (pluralCoupleId != null ? pluralCoupleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plural)) {
            return false;
        }
        Plural other = (Plural) object;
        if ((this.pluralCoupleId == null && other.pluralCoupleId != null) || (this.pluralCoupleId != null && !this.pluralCoupleId.equals(other.pluralCoupleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Plural[ pluralCoupleId=" + pluralCoupleId + " ]";
    }

}
