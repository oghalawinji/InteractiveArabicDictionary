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
    @NamedQuery(name = "Entrylinguisticbenefit.findAll", query = "SELECT e FROM Entrylinguisticbenefit e"),
    @NamedQuery(name = "Entrylinguisticbenefit.findByEntryLinguisticBenefitId", query = "SELECT e FROM Entrylinguisticbenefit e WHERE e.entryLinguisticBenefitId = :entryLinguisticBenefitId"),
    @NamedQuery(name = "Entrylinguisticbenefit.findByInfoStatus", query = "SELECT e FROM Entrylinguisticbenefit e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entrylinguisticbenefit.findByChechStatus", query = "SELECT e FROM Entrylinguisticbenefit e WHERE e.chechStatus = :chechStatus")})
public class Entrylinguisticbenefit extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entryLinguisticBenefitId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "linguisticBenefitId", referencedColumnName = "linguisticBenefitId")
    @ManyToOne(optional = false)
    private Linguisticbenefit linguisticBenefitId;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entrylinguisticbenefit() {
    }

    public Entrylinguisticbenefit(Integer entryLinguisticBenefitId) {
        this.entryLinguisticBenefitId = entryLinguisticBenefitId;
    }

    public Entrylinguisticbenefit(Semanticentry semanticentry, Linguisticbenefit linguisticbenefit) {
        this(semanticentry, linguisticbenefit, new Suggestion(), "S", 0);
    }

    public Entrylinguisticbenefit(Semanticentry semanticentry, Linguisticbenefit linguisticbenefit, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.linguisticBenefitId = linguisticbenefit;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return entryLinguisticBenefitId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntryLinguisticBenefitId(id);
    }

    public void setEntryLinguisticBenefitId(Integer entryLinguisticBenefitId) {
        this.entryLinguisticBenefitId = entryLinguisticBenefitId;
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

    public Linguisticbenefit getLinguisticbenefit() {
        return linguisticBenefitId;
    }

    public void setLinguisticbenefit(Linguisticbenefit linguisticBenefitId) {
        this.linguisticBenefitId = linguisticBenefitId;
    }

    public Semanticentry getSemanticEntry() {
        return semanticEntryId;
    }

    public void setSemanticentry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
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
        hash += (entryLinguisticBenefitId != null ? entryLinguisticBenefitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrylinguisticbenefit)) {
            return false;
        }
        Entrylinguisticbenefit other = (Entrylinguisticbenefit) object;
        if ((this.entryLinguisticBenefitId == null && other.entryLinguisticBenefitId != null) || (this.entryLinguisticBenefitId != null && !this.entryLinguisticBenefitId.equals(other.entryLinguisticBenefitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entrylinguisticbenefit[ entryLinguisticBenefitId=" + entryLinguisticBenefitId + " ]";
    }

}
