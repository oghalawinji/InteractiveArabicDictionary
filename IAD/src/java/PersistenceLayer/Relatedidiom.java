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
    @NamedQuery(name = "Relatedidiom.findAll", query = "SELECT r FROM Relatedidiom r"),
    @NamedQuery(name = "Relatedidiom.findByRelatedIdiomId", query = "SELECT r FROM Relatedidiom r WHERE r.relatedIdiomId = :relatedIdiomId"),
    @NamedQuery(name = "Relatedidiom.findByInfoStatus", query = "SELECT r FROM Relatedidiom r WHERE r.infoStatus = :infoStatus"),
    @NamedQuery(name = "Relatedidiom.findByChechStatus", query = "SELECT r FROM Relatedidiom r WHERE r.chechStatus = :chechStatus")})
public class Relatedidiom extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer relatedIdiomId;
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
    @JoinColumn(name = "IdiomId", referencedColumnName = "idiomId")
    @ManyToOne(optional = false)
    private Idiom idiomId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Relatedidiom() {
    }

    public Relatedidiom(Integer relatedIdiomId) {
        this.relatedIdiomId = relatedIdiomId;
    }

    public Relatedidiom(Integer relatedIdiomId, String infoStatus, int chechStatus) {
        this.relatedIdiomId = relatedIdiomId;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return relatedIdiomId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setRelatedIdiomId(id);
    }

    public void setRelatedIdiomId(Integer relatedIdiomId) {
        this.relatedIdiomId = relatedIdiomId;
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

    public Semanticentry getSemanticEntry() {
        return semanticEntryId;
    }

    public void setSemanticEntry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public Idiom getIdiom() {
        return idiomId;
    }

    public void setIdiomId(Idiom idiomId) {
        this.idiomId = idiomId;
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
        hash += (relatedIdiomId != null ? relatedIdiomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relatedidiom)) {
            return false;
        }
        Relatedidiom other = (Relatedidiom) object;
        if ((this.relatedIdiomId == null && other.relatedIdiomId != null) || (this.relatedIdiomId != null && !this.relatedIdiomId.equals(other.relatedIdiomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Relatedidiom[ relatedIdiomId=" + relatedIdiomId + " ]";
    }

}
