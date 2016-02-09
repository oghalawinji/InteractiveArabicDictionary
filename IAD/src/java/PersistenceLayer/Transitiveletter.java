/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Transitiveletter.findAll", query = "SELECT t FROM Transitiveletter t"),
    @NamedQuery(name = "Transitiveletter.findByTransitiveLetter", query = "SELECT t FROM Transitiveletter t WHERE t.transitiveLetter = :transitiveLetter"),
    @NamedQuery(name = "Transitiveletter.findByTransitivityCaseId", query = "SELECT t FROM Transitiveletter t WHERE t.transitivityCaseId = :transitivityCaseId"),
    @NamedQuery(name = "Transitiveletter.findByInfoStatus", query = "SELECT t FROM Transitiveletter t WHERE t.infoStatus = :infoStatus"),
    @NamedQuery(name = "Transitiveletter.findByChechStatus", query = "SELECT t FROM Transitiveletter t WHERE t.chechStatus = :chechStatus")})
public class Transitiveletter extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String transitiveLetter;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer transitivityCaseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "transitivityCaseId", referencedColumnName = "transitivityCaseId", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Transitivitycase transitivitycase;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Transitiveletter() {
    }

    public Transitiveletter(Integer transitivityCaseId) {
        this.transitivityCaseId = transitivityCaseId;
    }

    public Transitiveletter(Integer transitivityCaseId, String transitiveLetter, String infoStatus, int chechStatus) {
        this.transitivityCaseId = transitivityCaseId;
        this.transitiveLetter = transitiveLetter;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public String getTransitiveLetter() {
        return transitiveLetter;
    }

    public void setTransitiveLetter(String transitiveLetter) {
        this.transitiveLetter = transitiveLetter;
    }

    @Override
    public Integer getIdentity() {
        return transitivityCaseId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setTransitivityCaseId(id);
    }

    public void setTransitivityCaseId(Integer transitivityCaseId) {
        this.transitivityCaseId = transitivityCaseId;
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

    public Transitivitycase getTransitivitycase() {
        return transitivitycase;
    }

    public void setTransitivitycase(Transitivitycase transitivitycase) {
        this.transitivitycase = transitivitycase;
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
        hash += (transitivityCaseId != null ? transitivityCaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transitiveletter)) {
            return false;
        }
        Transitiveletter other = (Transitiveletter) object;
        if ((this.transitivityCaseId == null && other.transitivityCaseId != null) || (this.transitivityCaseId != null && !this.transitivityCaseId.equals(other.transitivityCaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Transitiveletter[ transitivityCaseId=" + transitivityCaseId + " ]";
    }

}
