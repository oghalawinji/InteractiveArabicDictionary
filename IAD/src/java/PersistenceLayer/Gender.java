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
    @NamedQuery(name = "Gender.findAll", query = "SELECT g FROM Gender g"),
    @NamedQuery(name = "Gender.findByGenderId", query = "SELECT g FROM Gender g WHERE g.genderId = :genderId"),
    @NamedQuery(name = "Gender.findByGender", query = "SELECT g FROM Gender g WHERE g.gender = :gender"),
    @NamedQuery(name = "Gender.findByInfoStatus", query = "SELECT g FROM Gender g WHERE g.infoStatus = :infoStatus"),
    @NamedQuery(name = "Gender.findByChechStatus", query = "SELECT g FROM Gender g WHERE g.chechStatus = :chechStatus")})
public class Gender extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer genderId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String gender;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genderId")
    private Set<Derivednoun> derivednounSet;

    public Gender() {
    }

    public Gender(Integer genderId) {
        this.genderId = genderId;
    }

    public Gender(String gender) {
        this(new Suggestion(), gender, "S", 0);
    }

    public Gender(Suggestion suggestion, String gender, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.gender = gender;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Gender(Suggestion suggestion, String gender, String infoStatus, int chechStatus, Set<Derivednoun> derivednouns) {
        this.suggestionId = suggestion;
        this.gender = gender;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivednounSet = derivednouns;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return genderId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setGenderId(id);
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (genderId != null ? genderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gender)) {
            return false;
        }
        Gender other = (Gender) object;
        if ((this.genderId == null && other.genderId != null) || (this.genderId != null && !this.genderId.equals(other.genderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Gender[ genderId=" + genderId + " ]";
    }

}
