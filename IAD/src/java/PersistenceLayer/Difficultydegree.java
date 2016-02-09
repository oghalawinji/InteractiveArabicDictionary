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
    @NamedQuery(name = "Difficultydegree.findAll", query = "SELECT d FROM Difficultydegree d"),
    @NamedQuery(name = "Difficultydegree.findByDifficultyDegreeId", query = "SELECT d FROM Difficultydegree d WHERE d.difficultyDegreeId = :difficultyDegreeId"),
    @NamedQuery(name = "Difficultydegree.findByDifficultyDegree", query = "SELECT d FROM Difficultydegree d WHERE d.difficultyDegree = :difficultyDegree"),
    @NamedQuery(name = "Difficultydegree.findByInfoStatus", query = "SELECT d FROM Difficultydegree d WHERE d.infoStatus = :infoStatus"),
    @NamedQuery(name = "Difficultydegree.findByChechStatus", query = "SELECT d FROM Difficultydegree d WHERE d.chechStatus = :chechStatus")})
public class Difficultydegree extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer difficultyDegreeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String difficultyDegree;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(mappedBy = "difficultyDegreeId")
    private Set<Semanticentry> semanticentrySet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Difficultydegree() {
    }

    public Difficultydegree(String difficultyDegree) {
        this(new Suggestion(), difficultyDegree, "S", 0);
    }

    public Difficultydegree(Suggestion suggestion, String difficultyDegree, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.difficultyDegree = difficultyDegree;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Difficultydegree(Suggestion suggestion, String difficultyDegree, String infoStatus, int chechStatus, Set<Semanticentry> semanticentries) {
        this.suggestionId = suggestion;
        this.difficultyDegree = difficultyDegree;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.semanticentrySet = semanticentries;
    }

    @Override
    public Integer getIdentity() {
        return difficultyDegreeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setDifficultyDegreeId(id);
    }

    public void setDifficultyDegreeId(Integer difficultyDegreeId) {
        this.difficultyDegreeId = difficultyDegreeId;
    }

    public String getDifficultyDegree() {
        return difficultyDegree;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public void setDifficultyDegree(String difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
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
    public Set<Semanticentry> getSemanticentrys() {
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
        hash += (difficultyDegreeId != null ? difficultyDegreeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Difficultydegree)) {
            return false;
        }
        Difficultydegree other = (Difficultydegree) object;
        if ((this.difficultyDegreeId == null && other.difficultyDegreeId != null) || (this.difficultyDegreeId != null && !this.difficultyDegreeId.equals(other.difficultyDegreeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Difficultydegree[ difficultyDegreeId=" + difficultyDegreeId + " ]";
    }

}
