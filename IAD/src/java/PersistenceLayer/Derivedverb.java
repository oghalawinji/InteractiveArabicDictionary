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
    @NamedQuery(name = "Derivedverb.findAll", query = "SELECT d FROM Derivedverb d"),
    @NamedQuery(name = "Derivedverb.findByDerivedVerbId", query = "SELECT d FROM Derivedverb d WHERE d.derivedVerbId = :derivedVerbId"),
    @NamedQuery(name = "Derivedverb.findByVocalizedVerb", query = "SELECT d FROM Derivedverb d WHERE d.vocalizedVerb = :vocalizedVerb"),
    @NamedQuery(name = "Derivedverb.findByPresentForm", query = "SELECT d FROM Derivedverb d WHERE d.presentForm = :presentForm"),
    @NamedQuery(name = "Derivedverb.findByInfoStatus", query = "SELECT d FROM Derivedverb d WHERE d.infoStatus = :infoStatus"),
    @NamedQuery(name = "Derivedverb.findByChechStatus", query = "SELECT d FROM Derivedverb d WHERE d.chechStatus = :chechStatus")})
public class Derivedverb extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer derivedVerbId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String vocalizedVerb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String presentForm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "derivedVerbId")
    private Set<Contextualverb> contextualverbSet;
    @JoinColumn(name = "PronunciationId", referencedColumnName = "PronunciationId")
    @ManyToOne
    private Pronunciation pronunciationId;
    @JoinColumn(name = "patternId", referencedColumnName = "patternId")
    @ManyToOne(optional = false)
    private Pattern patternId;
    @JoinColumn(name = "rootId", referencedColumnName = "rootId")
    @ManyToOne(optional = false)
    private Root rootId;
    @JoinColumn(name = "rawWordId", referencedColumnName = "rawWordId")
    @ManyToOne(optional = false)
    private Rawword rawWordId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Derivedverb() {
    }

    public Derivedverb(Integer derivedVerbId) {
        this.derivedVerbId = derivedVerbId;
    }

    public Derivedverb(Pronunciation pronunciation, Rawword rawword, Pattern pattern, Root root, String vocalizedVerb, String presentForm) {
        this(pronunciation, rawword, pattern, root, new Suggestion(), vocalizedVerb, presentForm, "S", 0);
    }

    public Derivedverb(Pronunciation pronunciation, Rawword rawword, Pattern pattern, Root root, Suggestion suggestion, String vocalizedVerb, String presentForm, String infoStatus, int checkStatus) {
        this.pronunciationId = pronunciation;
        this.rawWordId = rawword;
        this.patternId = pattern;
        this.rootId = root;
        this.suggestionId = suggestion;
        this.vocalizedVerb = vocalizedVerb;
        this.presentForm = presentForm;
        this.infoStatus = infoStatus;
        this.chechStatus = checkStatus;
    }

    public Derivedverb(Pronunciation pronunciation, Rawword rawword, Pattern pattern, Root root, Suggestion suggestion, String vocalizedVerb, String presentForm, String infoStatus, int checkStatus, Set<Contextualverb> contextualverbs) {
        this.pronunciationId = pronunciation;
        this.rawWordId = rawword;
        this.patternId = pattern;
        this.rootId = root;
        this.suggestionId = suggestion;
        this.vocalizedVerb = vocalizedVerb;
        this.presentForm = presentForm;
        this.infoStatus = infoStatus;
        this.chechStatus = checkStatus;
        this.contextualverbSet = contextualverbs;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return derivedVerbId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setDerivedVerbId(id);
    }

    public void setDerivedVerbId(Integer derivedVerbId) {
        this.derivedVerbId = derivedVerbId;
    }

    public String getVocalizedVerb() {
        return vocalizedVerb;
    }

    public void setVocalizedVerb(String vocalizedVerb) {
        this.vocalizedVerb = vocalizedVerb;
    }

    public String getPresentForm() {
        return presentForm;
    }

    public void setPresentForm(String presentForm) {
        this.presentForm = presentForm;
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
    public Set<Contextualverb> getContextualverbs() {
        return contextualverbSet;
    }

    public void setContextualverbSet(Set<Contextualverb> contextualverbSet) {
        this.contextualverbSet = contextualverbSet;
    }

    public Pronunciation getPronunciation() {
        return pronunciationId;
    }

    public void setPronunciation(Pronunciation pronunciationId) {
        this.pronunciationId = pronunciationId;
    }

    public Pattern getPattern() {
        return patternId;
    }

    public void setPattern(Pattern patternId) {
        this.patternId = patternId;
    }

    public Root getRoot() {
        return rootId;
    }

    public void setRoot(Root rootId) {
        this.rootId = rootId;
    }

    public Rawword getRawword() {
        return rawWordId;
    }

    public void setRawword(Rawword rawWordId) {
        this.rawWordId = rawWordId;
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
        hash += (derivedVerbId != null ? derivedVerbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Derivedverb)) {
            return false;
        }
        Derivedverb other = (Derivedverb) object;
        if ((this.derivedVerbId == null && other.derivedVerbId != null) || (this.derivedVerbId != null && !this.derivedVerbId.equals(other.derivedVerbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Derivedverb[ derivedVerbId=" + derivedVerbId + " ]";
    }

}
