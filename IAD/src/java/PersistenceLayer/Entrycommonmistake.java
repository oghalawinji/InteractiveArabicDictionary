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
    @NamedQuery(name = "Entrycommonmistake.findAll", query = "SELECT e FROM Entrycommonmistake e"),
    @NamedQuery(name = "Entrycommonmistake.findByEntryCommonMistakeId", query = "SELECT e FROM Entrycommonmistake e WHERE e.entryCommonMistakeId = :entryCommonMistakeId"),
    @NamedQuery(name = "Entrycommonmistake.findByInfoStatus", query = "SELECT e FROM Entrycommonmistake e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entrycommonmistake.findByChechStatus", query = "SELECT e FROM Entrycommonmistake e WHERE e.chechStatus = :chechStatus")})
public class Entrycommonmistake extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entryCommonMistakeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "commonMistakeId", referencedColumnName = "commonMistakeId")
    @ManyToOne(optional = false)
    private Commonmistake commonMistakeId;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entrycommonmistake() {
    }

    public Entrycommonmistake(Integer entryCommonMistakeId) {
        this.entryCommonMistakeId = entryCommonMistakeId;
    }

    public Entrycommonmistake(Semanticentry semanticentry, Commonmistake commonmistake) {
        this(semanticentry, commonmistake, new Suggestion(), "S", 0);
    }

    public Entrycommonmistake(Semanticentry semanticentry, Commonmistake commonmistake, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.commonMistakeId = commonmistake;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return entryCommonMistakeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntryCommonMistakeId(id);
    }

    public void setEntryCommonMistakeId(Integer entryCommonMistakeId) {
        this.entryCommonMistakeId = entryCommonMistakeId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
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

    public Commonmistake getCommonmistake() {
        return commonMistakeId;
    }

    public void setCommonmistake(Commonmistake commonMistakeId) {
        this.commonMistakeId = commonMistakeId;
    }

    public Semanticentry getSemanticentry() {
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
        hash += (entryCommonMistakeId != null ? entryCommonMistakeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrycommonmistake)) {
            return false;
        }
        Entrycommonmistake other = (Entrycommonmistake) object;
        if ((this.entryCommonMistakeId == null && other.entryCommonMistakeId != null) || (this.entryCommonMistakeId != null && !this.entryCommonMistakeId.equals(other.entryCommonMistakeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entrycommonmistake[ entryCommonMistakeId=" + entryCommonMistakeId + " ]";
    }

}
