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
    @NamedQuery(name = "Linguisticbenefit.findAll", query = "SELECT l FROM Linguisticbenefit l"),
    @NamedQuery(name = "Linguisticbenefit.findByLinguisticBenefitId", query = "SELECT l FROM Linguisticbenefit l WHERE l.linguisticBenefitId = :linguisticBenefitId"),
    @NamedQuery(name = "Linguisticbenefit.findByLinguisticBenefit", query = "SELECT l FROM Linguisticbenefit l WHERE l.linguisticBenefit = :linguisticBenefit"),
    @NamedQuery(name = "Linguisticbenefit.findByInfoStatus", query = "SELECT l FROM Linguisticbenefit l WHERE l.infoStatus = :infoStatus"),
    @NamedQuery(name = "Linguisticbenefit.findByChechStatus", query = "SELECT l FROM Linguisticbenefit l WHERE l.chechStatus = :chechStatus")})
public class Linguisticbenefit extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer linguisticBenefitId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    private String linguisticBenefit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "sourceId", referencedColumnName = "sourceId")
    @ManyToOne(optional = false)
    private Source sourceId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linguisticBenefitId")
    private Set<Entrylinguisticbenefit> entrylinguisticbenefitSet;

    public Linguisticbenefit() {
    }

    public Linguisticbenefit(Integer linguisticBenefitId) {
        this.linguisticBenefitId = linguisticBenefitId;
    }

    public Linguisticbenefit(Source source, String linguisticBenefit) {
        this(source, new Suggestion(), linguisticBenefit, "S", 0);
    }

    public Linguisticbenefit(Source source, Suggestion suggestion, String linguisticBenefit, String infoStatus, int chechStatus) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.linguisticBenefit = linguisticBenefit;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Linguisticbenefit(Source source, Suggestion suggestion, String linguisticBenefit, String infoStatus, int chechStatus, Set<Entrylinguisticbenefit> entrylinguisticbenefits) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.linguisticBenefit = linguisticBenefit;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.entrylinguisticbenefitSet = entrylinguisticbenefits;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return linguisticBenefitId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setLinguisticBenefitId(id);
    }

    public void setLinguisticBenefitId(Integer linguisticBenefitId) {
        this.linguisticBenefitId = linguisticBenefitId;
    }

    public String getLinguisticBenefit() {
        return linguisticBenefit;
    }

    public void setLinguisticBenefit(String linguisticBenefit) {
        this.linguisticBenefit = linguisticBenefit;
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

    public Source getSource() {
        return sourceId;
    }

    public void setSource(Source sourceId) {
        this.sourceId = sourceId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Entrylinguisticbenefit> getEntrylinguisticbenefits() {
        return entrylinguisticbenefitSet;
    }

    public void setEntrylinguisticbenefitSet(Set<Entrylinguisticbenefit> entrylinguisticbenefitSet) {
        this.entrylinguisticbenefitSet = entrylinguisticbenefitSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linguisticBenefitId != null ? linguisticBenefitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Linguisticbenefit)) {
            return false;
        }
        Linguisticbenefit other = (Linguisticbenefit) object;
        if ((this.linguisticBenefitId == null && other.linguisticBenefitId != null) || (this.linguisticBenefitId != null && !this.linguisticBenefitId.equals(other.linguisticBenefitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Linguisticbenefit[ linguisticBenefitId=" + linguisticBenefitId + " ]";
    }

}
