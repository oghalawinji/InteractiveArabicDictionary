/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
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
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r"),
    @NamedQuery(name = "Region.findByRegionId", query = "SELECT r FROM Region r WHERE r.regionId = :regionId"),
    @NamedQuery(name = "Region.findByRegion", query = "SELECT r FROM Region r WHERE r.region = :region"),
    @NamedQuery(name = "Region.findByInfoStatus", query = "SELECT r FROM Region r WHERE r.infoStatus = :infoStatus"),
    @NamedQuery(name = "Region.findByChechStatus", query = "SELECT r FROM Region r WHERE r.chechStatus = :chechStatus")})
public class Region extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer regionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String region;
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
    @OneToMany(mappedBy = "regionId")
    private Set<Semanticentry> semanticentrySet;

    public Region() {
    }

    public Region(Integer regionId) {
        this.regionId = regionId;
    }

    public Region(String region) {
        this(new Suggestion(), region, "S", 0);
    }

    public Region(Suggestion suggestion, String region, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.region = region;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Region(Suggestion suggestion, String region, String infoStatus, int chechStatus, Set<Semanticentry> semanticentries) {
        this.suggestionId = suggestion;
        this.region = region;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.semanticentrySet = semanticentries;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return regionId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setRegionId(id);
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
    public Set<Semanticentry> getSemanticentrys() {
        return semanticentrySet;
    }

    public void setSemanticentrySet(Set<Semanticentry> semanticentrySet) {
        this.semanticentrySet = semanticentrySet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionId != null ? regionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.regionId == null && other.regionId != null) || (this.regionId != null && !this.regionId.equals(other.regionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Region[ regionId=" + regionId + " ]";
    }

}
