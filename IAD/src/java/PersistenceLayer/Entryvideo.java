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
    @NamedQuery(name = "Entryvideo.findAll", query = "SELECT e FROM Entryvideo e"),
    @NamedQuery(name = "Entryvideo.findByEntryVideoId", query = "SELECT e FROM Entryvideo e WHERE e.entryVideoId = :entryVideoId"),
    @NamedQuery(name = "Entryvideo.findByInfoStatus", query = "SELECT e FROM Entryvideo e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entryvideo.findByChechStatus", query = "SELECT e FROM Entryvideo e WHERE e.chechStatus = :chechStatus")})
public class Entryvideo extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entryVideoId;
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
    @JoinColumn(name = "videoId", referencedColumnName = "videoId")
    @ManyToOne(optional = false)
    private Video videoId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entryvideo() {
    }

    public Entryvideo(Integer entryVideoId) {
        this.entryVideoId = entryVideoId;
    }

    public Entryvideo(Integer entryVideoId, String infoStatus, int chechStatus) {
        this.entryVideoId = entryVideoId;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return entryVideoId;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntryVideoId(id);
    }

    public void setEntryVideoId(Integer entryVideoId) {
        this.entryVideoId = entryVideoId;
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

    public Video getVideo() {
        return videoId;
    }

    public void setVideo(Video videoId) {
        this.videoId = videoId;
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
        hash += (entryVideoId != null ? entryVideoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entryvideo)) {
            return false;
        }
        Entryvideo other = (Entryvideo) object;
        if ((this.entryVideoId == null && other.entryVideoId != null) || (this.entryVideoId != null && !this.entryVideoId.equals(other.entryVideoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entryvideo[ entryVideoId=" + entryVideoId + " ]";
    }

}
