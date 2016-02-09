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
    @NamedQuery(name = "Origin.findAll", query = "SELECT o FROM Origin o"),
    @NamedQuery(name = "Origin.findByOriginId", query = "SELECT o FROM Origin o WHERE o.originId = :originId"),
    @NamedQuery(name = "Origin.findByOrigin", query = "SELECT o FROM Origin o WHERE o.origin = :origin"),
    @NamedQuery(name = "Origin.findByInfoStatus", query = "SELECT o FROM Origin o WHERE o.infoStatus = :infoStatus"),
    @NamedQuery(name = "Origin.findByChechStatus", query = "SELECT o FROM Origin o WHERE o.chechStatus = :chechStatus")})
public class Origin extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer originId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String origin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "originId")
    private Set<Derivednoun> derivednounSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Origin() {
    }

    public Origin(Integer originId) {
        this.originId = originId;
    }

    public Origin(String origin) {
        this(new Suggestion(), origin, "S", 0);
    }

    public Origin(Suggestion suggestion, String origin, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.origin = origin;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Origin(Suggestion suggestion, String origin, String infoStatus, int chechStatus, Set<Derivednoun> derivednouns) {
        this.suggestionId = suggestion;
        this.origin = origin;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivednounSet = derivednouns;
    }

    @Override
    public Integer getIdentity() {
        return originId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setOriginId(id);
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    @XmlTransient
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
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
        hash += (originId != null ? originId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Origin)) {
            return false;
        }
        Origin other = (Origin) object;
        if ((this.originId == null && other.originId != null) || (this.originId != null && !this.originId.equals(other.originId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Origin[ originId=" + originId + " ]";
    }

}
