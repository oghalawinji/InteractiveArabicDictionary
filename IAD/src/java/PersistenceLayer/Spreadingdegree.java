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
    @NamedQuery(name = "Spreadingdegree.findAll", query = "SELECT s FROM Spreadingdegree s"),
    @NamedQuery(name = "Spreadingdegree.findBySpreadingDegreeId", query = "SELECT s FROM Spreadingdegree s WHERE s.spreadingDegreeId = :spreadingDegreeId"),
    @NamedQuery(name = "Spreadingdegree.findBySpreadingDegree", query = "SELECT s FROM Spreadingdegree s WHERE s.spreadingDegree = :spreadingDegree"),
    @NamedQuery(name = "Spreadingdegree.findByInfoStatus", query = "SELECT s FROM Spreadingdegree s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Spreadingdegree.findByChechStatus", query = "SELECT s FROM Spreadingdegree s WHERE s.chechStatus = :chechStatus")})
public class Spreadingdegree extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer spreadingDegreeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String spreadingDegree;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(mappedBy = "spreadingDegreeId")
    private Set<Semanticentry> semanticentrySet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Spreadingdegree() {
    }

    public Spreadingdegree(Integer spreadingDegreeId) {
        this.spreadingDegreeId = spreadingDegreeId;
    }

    public Spreadingdegree(String spreadingDegree) {
        this(new Suggestion(), spreadingDegree, "S", 0);
    }

    public Spreadingdegree(Suggestion suggestion, String spreadingDegree, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.spreadingDegree = spreadingDegree;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Spreadingdegree(Suggestion suggestion, String spreadingDegree, String infoStatus, int chechStatus, Set<Semanticentry> semanticentries) {
        this.suggestionId = suggestion;
        this.spreadingDegree = spreadingDegree;
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
        return spreadingDegreeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSpreadingDegreeId(id);
    }

    public void setSpreadingDegreeId(Integer spreadingDegreeId) {
        this.spreadingDegreeId = spreadingDegreeId;
    }

    public String getSpreadingDegree() {
        return spreadingDegree;
    }

    public void setSpreadingDegree(String spreadingDegree) {
        this.spreadingDegree = spreadingDegree;
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
    public Set<Semanticentry> getSemanticentrySet() {
        return semanticentrySet;
    }

    public void setSemanticentrySet(Set<Semanticentry> semanticentrySet) {
        this.semanticentrySet = semanticentrySet;
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
        hash += (spreadingDegreeId != null ? spreadingDegreeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spreadingdegree)) {
            return false;
        }
        Spreadingdegree other = (Spreadingdegree) object;
        if ((this.spreadingDegreeId == null && other.spreadingDegreeId != null) || (this.spreadingDegreeId != null && !this.spreadingDegreeId.equals(other.spreadingDegreeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Spreadingdegree[ spreadingDegreeId=" + spreadingDegreeId + " ]";
    }

}
