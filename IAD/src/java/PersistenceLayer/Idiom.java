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
    @NamedQuery(name = "Idiom.findAll", query = "SELECT i FROM Idiom i"),
    @NamedQuery(name = "Idiom.findByIdiomId", query = "SELECT i FROM Idiom i WHERE i.idiomId = :idiomId"),
    @NamedQuery(name = "Idiom.findByIdiom", query = "SELECT i FROM Idiom i WHERE i.idiom = :idiom"),
    @NamedQuery(name = "Idiom.findByVocalizedIdiom", query = "SELECT i FROM Idiom i WHERE i.vocalizedIdiom = :vocalizedIdiom"),
    @NamedQuery(name = "Idiom.findByInfoStatus", query = "SELECT i FROM Idiom i WHERE i.infoStatus = :infoStatus"),
    @NamedQuery(name = "Idiom.findByChechStatus", query = "SELECT i FROM Idiom i WHERE i.chechStatus = :chechStatus")})
public class Idiom extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idiomId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String idiom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String vocalizedIdiom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "PronunciationId", referencedColumnName = "PronunciationId")
    @ManyToOne
    private Pronunciation pronunciationId;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idiomId")
    private Set<Relatedidiom> relatedidiomSet;

    public Idiom() {
    }

    public Idiom(Integer idiomId) {
        this.idiomId = idiomId;
    }

    public Idiom(Semanticentry semanticentry, String idiom, String vocalizedIdiom) {
        this(semanticentry, new Suggestion(), idiom, vocalizedIdiom, "S", 0);
    }

    public Idiom(Semanticentry semanticentry, Suggestion suggestion, String idiom, String vocalizedIdiom, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.suggestionId = suggestion;
        this.idiom = idiom;
        this.vocalizedIdiom = vocalizedIdiom;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Idiom(Pronunciation pronunciation, Semanticentry semanticentry, Suggestion suggestion, String idiom, String vocalizedIdiom, String infoStatus, int chechStatus, Set<Relatedidiom> relatedidioms) {
        this.pronunciationId = pronunciation;
        this.semanticEntryId = semanticentry;
        this.suggestionId = suggestion;
        this.idiom = idiom;
        this.vocalizedIdiom = vocalizedIdiom;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.relatedidiomSet = relatedidioms;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return idiomId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setIdiomId(id);
    }

    public void setIdiomId(Integer idiomId) {
        this.idiomId = idiomId;
    }

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public String getVocalizedIdiom() {
        return vocalizedIdiom;
    }

    public void setVocalizedIdiom(String vocalizedIdiom) {
        this.vocalizedIdiom = vocalizedIdiom;
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

    public Pronunciation getPronunciation() {
        return pronunciationId;
    }

    public void setPronunciation(Pronunciation pronunciationId) {
        this.pronunciationId = pronunciationId;
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

    @XmlTransient
    public Set<Relatedidiom> getRelatedidioms() {
        return relatedidiomSet;
    }

    public void setRelatedidiomSet(Set<Relatedidiom> relatedidiomSet) {
        this.relatedidiomSet = relatedidiomSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idiomId != null ? idiomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idiom)) {
            return false;
        }
        Idiom other = (Idiom) object;
        if ((this.idiomId == null && other.idiomId != null) || (this.idiomId != null && !this.idiomId.equals(other.idiomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Idiom[ idiomId=" + idiomId + " ]";
    }

}
