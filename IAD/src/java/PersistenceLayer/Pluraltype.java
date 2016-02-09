/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pluraltype.findAll", query = "SELECT p FROM Pluraltype p"),
    @NamedQuery(name = "Pluraltype.findByPluralTypeId", query = "SELECT p FROM Pluraltype p WHERE p.pluralTypeId = :pluralTypeId"),
    @NamedQuery(name = "Pluraltype.findByPluralType", query = "SELECT p FROM Pluraltype p WHERE p.pluralType = :pluralType"),
    @NamedQuery(name = "Pluraltype.findByInfoStatus", query = "SELECT p FROM Pluraltype p WHERE p.infoStatus = :infoStatus"),
    @NamedQuery(name = "Pluraltype.findByChechStatus", query = "SELECT p FROM Pluraltype p WHERE p.chechStatus = :chechStatus")})
public class Pluraltype extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer pluralTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String pluralType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pluralTypeId")
    private Set<Plural> pluralSet;

    public Pluraltype() {
    }

    public Pluraltype(Integer pluralTypeId) {
        this.pluralTypeId = pluralTypeId;
    }

    public Pluraltype(Integer pluralTypeId, String pluralType, String infoStatus, int chechStatus) {
        this.pluralTypeId = pluralTypeId;
        this.pluralType = pluralType;
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
        return pluralTypeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setPluralTypeId(id);
    }

    public void setPluralTypeId(Integer pluralTypeId) {
        this.pluralTypeId = pluralTypeId;
    }

    public String getPluralType() {
        return pluralType;
    }

    public void setPluralType(String pluralType) {
        this.pluralType = pluralType;
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

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Plural> getPlurals() {
        return pluralSet;
    }

    public void setPluralSet(Set<Plural> pluralSet) {
        this.pluralSet = pluralSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pluralTypeId != null ? pluralTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pluraltype)) {
            return false;
        }
        Pluraltype other = (Pluraltype) object;
        if ((this.pluralTypeId == null && other.pluralTypeId != null) || (this.pluralTypeId != null && !this.pluralTypeId.equals(other.pluralTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Pluraltype[ pluralTypeId=" + pluralTypeId + " ]";
    }

}
