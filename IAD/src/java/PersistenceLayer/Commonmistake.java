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
    @NamedQuery(name = "Commonmistake.findAll", query = "SELECT c FROM Commonmistake c"),
    @NamedQuery(name = "Commonmistake.findByCommonMistakeId", query = "SELECT c FROM Commonmistake c WHERE c.commonMistakeId = :commonMistakeId"),
    @NamedQuery(name = "Commonmistake.findByCommonMistake", query = "SELECT c FROM Commonmistake c WHERE c.commonMistake = :commonMistake"),
    @NamedQuery(name = "Commonmistake.findByInfoStatus", query = "SELECT c FROM Commonmistake c WHERE c.infoStatus = :infoStatus"),
    @NamedQuery(name = "Commonmistake.findByChechStatus", query = "SELECT c FROM Commonmistake c WHERE c.chechStatus = :chechStatus")})
public class Commonmistake extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer commonMistakeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    private String commonMistake;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commonMistakeId")
    private Set<Entrycommonmistake> entrycommonmistakeSet;

    public Commonmistake() {
    }

    public Commonmistake(Integer commonMistakeId) {
        this.commonMistakeId = commonMistakeId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public Commonmistake(Source source, String commonMistake) {
        this(source, new Suggestion(), commonMistake, "S", 0);
    }

    public Commonmistake(Source source, Suggestion suggestion, String commonMistake, String infoStatus, int chechStatus) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.commonMistake = commonMistake;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Commonmistake(Source source, Suggestion suggestion, String commonMistake, String infoStatus, int chechStatus, Set<Entrycommonmistake> entrycommonmistakes) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.commonMistake = commonMistake;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.entrycommonmistakeSet = entrycommonmistakes;
    }

    @Override
    public Integer getIdentity() {
        return commonMistakeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setCommonMistakeId(id);
    }

    public void setCommonMistakeId(Integer commonMistakeId) {
        this.commonMistakeId = commonMistakeId;
    }

    public String getCommonMistake() {
        return commonMistake;
    }

    public void setCommonMistake(String commonMistake) {
        this.commonMistake = commonMistake;
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
    public Set<Entrycommonmistake> getEntrycommonmistakes() {
        return entrycommonmistakeSet;
    }

    public void setEntrycommonmistakeSet(Set<Entrycommonmistake> entrycommonmistakeSet) {
        this.entrycommonmistakeSet = entrycommonmistakeSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commonMistakeId != null ? commonMistakeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commonmistake)) {
            return false;
        }
        Commonmistake other = (Commonmistake) object;
        if ((this.commonMistakeId == null && other.commonMistakeId != null) || (this.commonMistakeId != null && !this.commonMistakeId.equals(other.commonMistakeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Commonmistake[ commonMistakeId=" + commonMistakeId + " ]";
    }

}
