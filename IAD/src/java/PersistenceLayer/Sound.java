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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Sound.findAll", query = "SELECT s FROM Sound s"),
    @NamedQuery(name = "Sound.findBySoundId", query = "SELECT s FROM Sound s WHERE s.soundId = :soundId"),
    @NamedQuery(name = "Sound.findByInfoStatus", query = "SELECT s FROM Sound s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Sound.findByChechStatus", query = "SELECT s FROM Sound s WHERE s.chechStatus = :chechStatus")})
public class Sound extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer soundId;
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] sound;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "soundId")
    private Set<Examplesound> examplesoundSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "soundId")
    private Set<Entrysound> entrysoundSet;

    public Sound() {
    }

    public Sound(Integer soundId) {
        this.soundId = soundId;
    }

    public Sound(byte[] sound) {
        this(new Suggestion(), sound, "S", 0);
    }

    public Sound(Suggestion suggestion, byte[] sound, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.sound = sound;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Sound(Suggestion suggestion, byte[] sound, String infoStatus, int chechStatus, Set<Entrysound> entrysounds, Set<Examplesound> examplesounds) {
        this.suggestionId = suggestion;
        this.sound = sound;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.entrysoundSet = entrysounds;
        this.examplesoundSet = examplesounds;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return soundId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSoundId(id);
    }

    public void setSoundId(Integer soundId) {
        this.soundId = soundId;
    }

    public byte[] getSound() {
        return sound;
    }

    public void setSound(byte[] sound) {
        this.sound = sound;
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

    @XmlTransient
    public Set<Examplesound> getExamplesounds() {
        return examplesoundSet;
    }

    public void setExamplesoundSet(Set<Examplesound> examplesoundSet) {
        this.examplesoundSet = examplesoundSet;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Entrysound> getEntrysounds() {
        return entrysoundSet;
    }

    public void setEntrysoundSet(Set<Entrysound> entrysoundSet) {
        this.entrysoundSet = entrysoundSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (soundId != null ? soundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sound)) {
            return false;
        }
        Sound other = (Sound) object;
        if ((this.soundId == null && other.soundId != null) || (this.soundId != null && !this.soundId.equals(other.soundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Sound[ soundId=" + soundId + " ]";
    }

}
