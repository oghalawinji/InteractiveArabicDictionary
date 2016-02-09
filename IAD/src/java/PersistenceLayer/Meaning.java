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
    @NamedQuery(name = "Meaning.findAll", query = "SELECT m FROM Meaning m"),
    @NamedQuery(name = "Meaning.findByMeaningId", query = "SELECT m FROM Meaning m WHERE m.meaningId = :meaningId"),
    @NamedQuery(name = "Meaning.findByMeaning", query = "SELECT m FROM Meaning m WHERE m.meaning = :meaning"),
    @NamedQuery(name = "Meaning.findByInfoStatus", query = "SELECT m FROM Meaning m WHERE m.infoStatus = :infoStatus"),
    @NamedQuery(name = "Meaning.findByChechStatus", query = "SELECT m FROM Meaning m WHERE m.chechStatus = :chechStatus")})
public class Meaning extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer meaningId;
    @Size(max = 300)
    private String meaning;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "sourceId", referencedColumnName = "sourceId")
    @ManyToOne(optional = false)
    private Source sourceId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Meaning() {
    }

    public Meaning(Semanticentry semanticentry, Source source, String meaning) {
        this(semanticentry, source, new Suggestion(), meaning, "S", 0);
    }

    public Meaning(Semanticentry semanticentry, Source source, Suggestion suggestion, String meaning, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.meaning = meaning;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Meaning(Integer meaningId) {
        this.meaningId = meaningId;
    }

    @Override
    public Integer getIdentity() {
        return meaningId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setMeaningId(id);
    }

    public void setMeaningId(Integer meaningId) {
        this.meaningId = meaningId;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
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

    public Semanticentry getSemanticentry() {
        return semanticEntryId;
    }

    public void setSemanticentry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meaningId != null ? meaningId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meaning)) {
            return false;
        }
        Meaning other = (Meaning) object;
        if ((this.meaningId == null && other.meaningId != null) || (this.meaningId != null && !this.meaningId.equals(other.meaningId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Meaning[ meaningId=" + meaningId + " ]";
    }

}
