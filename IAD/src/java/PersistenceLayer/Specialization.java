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
    @NamedQuery(name = "Specialization.findAll", query = "SELECT s FROM Specialization s"),
    @NamedQuery(name = "Specialization.findBySpecializationId", query = "SELECT s FROM Specialization s WHERE s.specializationId = :specializationId"),
    @NamedQuery(name = "Specialization.findBySpecialization", query = "SELECT s FROM Specialization s WHERE s.specialization = :specialization"),
    @NamedQuery(name = "Specialization.findByInfoStatus", query = "SELECT s FROM Specialization s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Specialization.findByChechStatus", query = "SELECT s FROM Specialization s WHERE s.chechStatus = :chechStatus")})
public class Specialization extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer specializationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String specialization;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(mappedBy = "specializationId")
    private Set<Semanticentry> semanticentrySet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Specialization() {
    }

    public Specialization(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public Specialization(String specialization) {
        this(new Suggestion(), specialization, "S", 0);
    }

    public Specialization(Suggestion suggestion, String specialization, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.specialization = specialization;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Specialization(Suggestion suggestion, String specialization, String infoStatus, int chechStatus, Set<Semanticentry> semanticentries) {
        this.suggestionId = suggestion;
        this.specialization = specialization;
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
        return specializationId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSpecializationId(id);
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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
        hash += (specializationId != null ? specializationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialization)) {
            return false;
        }
        Specialization other = (Specialization) object;
        if ((this.specializationId == null && other.specializationId != null) || (this.specializationId != null && !this.specializationId.equals(other.specializationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Specialization[ specializationId=" + specializationId + " ]";
    }

}
