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
    @NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v"),
    @NamedQuery(name = "Video.findByVideoId", query = "SELECT v FROM Video v WHERE v.videoId = :videoId"),
    @NamedQuery(name = "Video.findByInfoStatus", query = "SELECT v FROM Video v WHERE v.infoStatus = :infoStatus"),
    @NamedQuery(name = "Video.findByChechStatus", query = "SELECT v FROM Video v WHERE v.chechStatus = :chechStatus")})
public class Video extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer videoId;
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] video;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoId")
    private Set<Entryvideo> entryvideoSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Video() {
    }

    public Video(Integer videoId) {
        this.videoId = videoId;
    }

    public Video(Integer videoId, byte[] video, String infoStatus, int chechStatus) {
        this.videoId = videoId;
        this.video = video;
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
        return videoId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setVideoId(id);
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
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
    public Set<Entryvideo> getEntryvideos() {
        return entryvideoSet;
    }

    public void setEntryvideoSet(Set<Entryvideo> entryvideoSet) {
        this.entryvideoSet = entryvideoSet;
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
        hash += (videoId != null ? videoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.videoId == null && other.videoId != null) || (this.videoId != null && !this.videoId.equals(other.videoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Video[ videoId=" + videoId + " ]";
    }

}
