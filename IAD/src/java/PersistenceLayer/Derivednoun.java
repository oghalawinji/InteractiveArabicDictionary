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
    @NamedQuery(name = "Derivednoun.findAll", query = "SELECT d FROM Derivednoun d"),
    @NamedQuery(name = "Derivednoun.findByDerivedNounId", query = "SELECT d FROM Derivednoun d WHERE d.derivedNounId = :derivedNounId"),
    @NamedQuery(name = "Derivednoun.findByVocalizedNoun", query = "SELECT d FROM Derivednoun d WHERE d.vocalizedNoun = :vocalizedNoun"),
    @NamedQuery(name = "Derivednoun.findByInfoStatus", query = "SELECT d FROM Derivednoun d WHERE d.infoStatus = :infoStatus"),
    @NamedQuery(name = "Derivednoun.findByChechStatus", query = "SELECT d FROM Derivednoun d WHERE d.chechStatus = :chechStatus")})
public class Derivednoun extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer derivedNounId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String vocalizedNoun;
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
    @JoinColumn(name = "rawNounId", referencedColumnName = "rawWordId")
    @ManyToOne(optional = false)
    private Rawword rawNounId;
    @JoinColumn(name = "numberId", referencedColumnName = "numberId")
    @ManyToOne(optional = false)
    private Number numberId;
    @JoinColumn(name = "typeId", referencedColumnName = "typeId")
    @ManyToOne(optional = false)
    private Type typeId;
    @JoinColumn(name = "patternId", referencedColumnName = "patternId")
    @ManyToOne(optional = false)
    private Pattern patternId;
    @JoinColumn(name = "rootId", referencedColumnName = "rootId")
    @ManyToOne(optional = false)
    private Root rootId;
    @JoinColumn(name = "originId", referencedColumnName = "originId")
    @ManyToOne(optional = false)
    private Origin originId;
    @JoinColumn(name = "genderId", referencedColumnName = "genderId")
    @ManyToOne(optional = false)
    private Gender genderId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "derivedNounId")
    private Set<Semanticnoun> semanticnounSet;

    public Derivednoun() {
    }

    public Derivednoun(Pronunciation pronunciation, Number number, Rawword rawword, Pattern pattern, Type type, Root root, Origin origin, Gender gender, String vocalizedNoun) {
        this(pronunciation, number, rawword, pattern, type, root, origin, new Suggestion(), gender, vocalizedNoun, "S", 0);
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    public Derivednoun(Pronunciation pronunciation, Number number, Rawword rawword, Pattern pattern, Type type, Root root, Origin origin, Suggestion suggestion, Gender gender, String vocalizedNoun, String infoStatus, int chechStatus) {
        this.pronunciationId = pronunciation;
        this.numberId = number;
        this.rawNounId = rawword;
        this.patternId = pattern;
        this.typeId = type;
        this.rootId = root;
        this.originId = origin;
        this.suggestionId = suggestion;
        this.genderId = gender;
        this.vocalizedNoun = vocalizedNoun;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Derivednoun(Pronunciation pronunciation, Number number, Rawword rawword, Pattern pattern, Type type, Root root, Origin origin, Suggestion suggestion, Gender gender, String vocalizedNoun, String infoStatus, int chechStatus, Set<Semanticnoun> semanticnouns) {
        this.pronunciationId = pronunciation;
        this.numberId = number;
        this.rawNounId = rawword;
        this.patternId = pattern;
        this.typeId = type;
        this.rootId = root;
        this.originId = origin;
        this.suggestionId = suggestion;
        this.genderId = gender;
        this.vocalizedNoun = vocalizedNoun;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.semanticnounSet = semanticnouns;
    }

    @Override
    public Integer getIdentity() {
        return derivedNounId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setDerivedNounId(id);
    }

    public void setDerivedNounId(Integer derivedNounId) {
        this.derivedNounId = derivedNounId;
    }

    public String getVocalizedNoun() {
        return vocalizedNoun;
    }

    public void setVocalizedNoun(String vocalizedNoun) {
        this.vocalizedNoun = vocalizedNoun;
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

    public Rawword getRawword() {
        return rawNounId;
    }

    public void setRawword(Rawword rawNounId) {
        this.rawNounId = rawNounId;
    }

    public Number getNumber() {
        return numberId;
    }

    public void setNumber(Number numberId) {
        this.numberId = numberId;
    }

    public Type getType() {
        return typeId;
    }

    public void setType(Type typeId) {
        this.typeId = typeId;
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

    public Origin getOrigin() {
        return originId;
    }

    public void setOrigin(Origin originId) {
        this.originId = originId;
    }

    public Gender getGender() {
        return genderId;
    }

    public void setGender(Gender genderId) {
        this.genderId = genderId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Semanticnoun> getSemanticnouns() {
        return semanticnounSet;
    }

    public void setSemanticnounSet(Set<Semanticnoun> semanticnounSet) {
        this.semanticnounSet = semanticnounSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (derivedNounId != null ? derivedNounId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Derivednoun)) {
            return false;
        }
        Derivednoun other = (Derivednoun) object;
        if ((this.derivedNounId == null && other.derivedNounId != null) || (this.derivedNounId != null && !this.derivedNounId.equals(other.derivedNounId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Derivednoun[ derivedNounId=" + derivedNounId + " ]";
    }

}
