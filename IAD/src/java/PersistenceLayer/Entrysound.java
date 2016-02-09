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
    @NamedQuery(name = "Entrysound.findAll", query = "SELECT e FROM Entrysound e"),
    @NamedQuery(name = "Entrysound.findByEntrySoundId", query = "SELECT e FROM Entrysound e WHERE e.entrySoundId = :entrySoundId"),
    @NamedQuery(name = "Entrysound.findByInfoStatus", query = "SELECT e FROM Entrysound e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entrysound.findByChechStatus", query = "SELECT e FROM Entrysound e WHERE e.chechStatus = :chechStatus")})
public class Entrysound extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entrySoundId;
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
    @JoinColumn(name = "soundId", referencedColumnName = "soundId")
    @ManyToOne(optional = false)
    private Sound soundId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entrysound() {
    }

    public Entrysound(Integer entrySoundId) {
        this.entrySoundId = entrySoundId;
    }

    public Entrysound(Semanticentry semanticentry, Sound sound) {
        this(semanticentry, sound, new Suggestion(), "S", 0);
    }

    public Entrysound(Semanticentry semanticentry, Sound sound, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.soundId = sound;
        this.suggestionId = suggestion;
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
        return entrySoundId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntrySoundId(id);
    }

    public void setEntrySoundId(Integer entrySoundId) {
        this.entrySoundId = entrySoundId;
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

    public Sound getSound() {
        return soundId;
    }

    public void setSound(Sound soundId) {
        this.soundId = soundId;
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
        hash += (entrySoundId != null ? entrySoundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrysound)) {
            return false;
        }
        Entrysound other = (Entrysound) object;
        if ((this.entrySoundId == null && other.entrySoundId != null) || (this.entrySoundId != null && !this.entrySoundId.equals(other.entrySoundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entrysound[ entrySoundId=" + entrySoundId + " ]";
    }

}
