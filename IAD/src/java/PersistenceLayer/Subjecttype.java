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
    @NamedQuery(name = "Subjecttype.findAll", query = "SELECT s FROM Subjecttype s"),
    @NamedQuery(name = "Subjecttype.findByVSId", query = "SELECT s FROM Subjecttype s WHERE s.vSId = :vSId"),
    @NamedQuery(name = "Subjecttype.findByInfoStatus", query = "SELECT s FROM Subjecttype s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Subjecttype.findByChechStatus", query = "SELECT s FROM Subjecttype s WHERE s.chechStatus = :chechStatus")})
public class Subjecttype extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer vSId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vSId")
    private Set<Firstobjecttype> firstobjecttypeSet;
    @JoinColumn(name = "seconObjectdActorId", referencedColumnName = "actorId")
    @ManyToOne
    private Actor seconObjectdActorId;
    @JoinColumn(name = "firstObjectActorId", referencedColumnName = "actorId")
    @ManyToOne
    private Actor firstObjectActorId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @JoinColumn(name = "subjectActorId", referencedColumnName = "actorId")
    @ManyToOne(optional = false)
    private Actor subjectActorId;
    @JoinColumn(name = "semanticVerbId", referencedColumnName = "semanticVerbId")
    @ManyToOne(optional = false)
    private Semanticverb semanticVerbId;
    @JoinColumn(name = "thirdObjectActorId", referencedColumnName = "actorId")
    @ManyToOne
    private Actor thirdObjectActorId;

    public Subjecttype() {
    }

    public Subjecttype(Integer vSId) {
        this.vSId = vSId;
    }

    public Subjecttype(Integer vSId, String infoStatus, int chechStatus) {
        this.vSId = vSId;
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
        return vSId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setId(id);
    }

    public void setVSId(Integer vSId) {
        this.vSId = vSId;
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
    public Set<Firstobjecttype> getFirstobjecttypes() {
        return firstobjecttypeSet;
    }

    public void setFirstobjecttypeSet(Set<Firstobjecttype> firstobjecttypeSet) {
        this.firstobjecttypeSet = firstobjecttypeSet;
    }

    public Actor getSeconObjectdActor() {
        return seconObjectdActorId;
    }

    public void setSeconObjectdActor(Actor seconObjectdActorId) {
        this.seconObjectdActorId = seconObjectdActorId;
    }

    public Actor getFirstObjectActor() {
        return firstObjectActorId;
    }

    public void setFirstObjectActor(Actor firstObjectActorId) {
        this.firstObjectActorId = firstObjectActorId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Actor getSubjectActor() {
        return subjectActorId;
    }

    public void setSubjectActor(Actor subjectActorId) {
        this.subjectActorId = subjectActorId;
    }

    public Semanticverb getSemanticVerb() {
        return semanticVerbId;
    }

    public void setSemanticVerb(Semanticverb semanticVerbId) {
        this.semanticVerbId = semanticVerbId;
    }

    public Actor getThirdObjectActor() {
        return thirdObjectActorId;
    }

    public void setThirdObjectActor(Actor thirdObjectActorId) {
        this.thirdObjectActorId = thirdObjectActorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vSId != null ? vSId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjecttype)) {
            return false;
        }
        Subjecttype other = (Subjecttype) object;
        if ((this.vSId == null && other.vSId != null) || (this.vSId != null && !this.vSId.equals(other.vSId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Subjecttype[ vSId=" + vSId + " ]";
    }

}
