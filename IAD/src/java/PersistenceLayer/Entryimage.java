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
    @NamedQuery(name = "Entryimage.findAll", query = "SELECT e FROM Entryimage e"),
    @NamedQuery(name = "Entryimage.findByEntryImageId", query = "SELECT e FROM Entryimage e WHERE e.entryImageId = :entryImageId"),
    @NamedQuery(name = "Entryimage.findByInfoStatus", query = "SELECT e FROM Entryimage e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entryimage.findByChechStatus", query = "SELECT e FROM Entryimage e WHERE e.chechStatus = :chechStatus")})
public class Entryimage extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entryImageId;
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
    @JoinColumn(name = "imageId", referencedColumnName = "imageId")
    @ManyToOne(optional = false)
    private Image imageId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entryimage() {
    }

    public Entryimage(Integer entryImageId) {
        this.entryImageId = entryImageId;
    }

    public Entryimage(Integer entryImageId, String infoStatus, int chechStatus) {
        this.entryImageId = entryImageId;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return entryImageId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntryImageId(id);
    }

    public void setEntryImageId(Integer entryImageId) {
        this.entryImageId = entryImageId;
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

    public Semanticentry getSemanticEntry() {
        return semanticEntryId;
    }

    public void setSemanticEntry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
    }

    public Image getImage() {
        return imageId;
    }

    public void setImageId(Image imageId) {
        this.imageId = imageId;
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
        hash += (entryImageId != null ? entryImageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entryimage)) {
            return false;
        }
        Entryimage other = (Entryimage) object;
        if ((this.entryImageId == null && other.entryImageId != null) || (this.entryImageId != null && !this.entryImageId.equals(other.entryImageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entryimage[ entryImageId=" + entryImageId + " ]";
    }

}
