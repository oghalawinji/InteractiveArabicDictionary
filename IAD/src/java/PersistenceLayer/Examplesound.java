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
    @NamedQuery(name = "Examplesound.findAll", query = "SELECT e FROM Examplesound e"),
    @NamedQuery(name = "Examplesound.findByExampleSoundId", query = "SELECT e FROM Examplesound e WHERE e.exampleSoundId = :exampleSoundId"),
    @NamedQuery(name = "Examplesound.findByInfoStatus", query = "SELECT e FROM Examplesound e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Examplesound.findByChechStatus", query = "SELECT e FROM Examplesound e WHERE e.chechStatus = :chechStatus")})
public class Examplesound extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer exampleSoundId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;

    public Examplesound(Example exampleId, Sound soundId) {
        this.soundId = soundId;
        this.exampleId = exampleId;
    }
    @JoinColumn(name = "soundId", referencedColumnName = "soundId")
    @ManyToOne(optional = false)
    private Sound soundId;
    @JoinColumn(name = "exampleId", referencedColumnName = "exampleId")
    @ManyToOne(optional = false)
    private Example exampleId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Examplesound() {
    }

    public Examplesound(Integer exampleSoundId) {
        this.exampleSoundId = exampleSoundId;
    }

    public Examplesound(Integer exampleSoundId, String infoStatus, int chechStatus) {
        this.exampleSoundId = exampleSoundId;
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
        return exampleSoundId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setExampleSoundId(id);
    }

    public void setExampleSoundId(Integer exampleSoundId) {
        this.exampleSoundId = exampleSoundId;
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

    public Sound getSound() {
        return soundId;
    }

    public void setSound(Sound soundId) {
        this.soundId = soundId;
    }

    public Example getExample() {
        return exampleId;
    }

    public void setExample(Example exampleId) {
        this.exampleId = exampleId;
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
        hash += (exampleSoundId != null ? exampleSoundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examplesound)) {
            return false;
        }
        Examplesound other = (Examplesound) object;
        if ((this.exampleSoundId == null && other.exampleSoundId != null) || (this.exampleSoundId != null && !this.exampleSoundId.equals(other.exampleSoundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Examplesound[ exampleSoundId=" + exampleSoundId + " ]";
    }

}
