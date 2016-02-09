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
    @NamedQuery(name = "Annexednoun.findAll", query = "SELECT a FROM Annexednoun a"),
    @NamedQuery(name = "Annexednoun.findByAnnexedNounId", query = "SELECT a FROM Annexednoun a WHERE a.annexedNounId = :annexedNounId"),
    @NamedQuery(name = "Annexednoun.findByInfoStatus", query = "SELECT a FROM Annexednoun a WHERE a.infoStatus = :infoStatus"),
    @NamedQuery(name = "Annexednoun.findByChechStatus", query = "SELECT a FROM Annexednoun a WHERE a.chechStatus = :chechStatus")})
public class Annexednoun extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer annexedNounId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "nounId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun nounId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @JoinColumn(name = "annexedId", referencedColumnName = "semanticNounId")
    @ManyToOne(optional = false)
    private Semanticnoun annexedId;

    public Annexednoun() {
    }

    public Annexednoun(Integer annexedNounId) {
        this.annexedNounId = annexedNounId;
    }

    public Annexednoun(Semanticnoun semanticAnnexedNoun, Semanticnoun semanticNoun) {
        this(semanticAnnexedNoun, semanticNoun, new Suggestion(), "S", 0);
    }

    public Annexednoun(Semanticnoun semanticAnnexedNoun, Semanticnoun semanticNoun, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.annexedId = semanticAnnexedNoun;
        this.nounId = semanticNoun;
        this.suggestionId = suggestion;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return annexedNounId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setAnnexedNounId(id);
    }

    public void setAnnexedNounId(Integer annexedNounId) {
        this.annexedNounId = annexedNounId;
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

    public Semanticnoun getNoun() {
        return nounId;
    }

    public void setNoun(Semanticnoun nounId) {
        this.nounId = nounId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Semanticnoun getSemanticAnnexedNoun() {
        return annexedId;
    }

    public void setAnnexed(Semanticnoun annexedId) {
        this.annexedId = annexedId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annexedNounId != null ? annexedNounId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annexednoun)) {
            return false;
        }
        Annexednoun other = (Annexednoun) object;
        if ((this.annexedNounId == null && other.annexedNounId != null) || (this.annexedNounId != null && !this.annexedNounId.equals(other.annexedNounId))) {
            return false;
        }
        return true;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Annexednoun[ annexedNounId=" + annexedNounId + " ]";
    }

}
