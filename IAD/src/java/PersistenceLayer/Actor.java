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
    @NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a"),
    @NamedQuery(name = "Actor.findByActorId", query = "SELECT a FROM Actor a WHERE a.actorId = :actorId"),
    @NamedQuery(name = "Actor.findByActor", query = "SELECT a FROM Actor a WHERE a.actor = :actor"),
    @NamedQuery(name = "Actor.findByInfoStatus", query = "SELECT a FROM Actor a WHERE a.infoStatus = :infoStatus"),
    @NamedQuery(name = "Actor.findByChechStatus", query = "SELECT a FROM Actor a WHERE a.chechStatus = :chechStatus")})
public class Actor extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer actorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String actor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objectActorId")
    private Set<Firstobjecttype> firstobjecttypeSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secondObjectActorId")
    private Set<Secondobject> secondobjectSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thirdObjectActorId")
    private Set<Thirdobject> thirdobjectSet;
    @OneToMany(mappedBy = "seconObjectdActorId")
    private Set<Subjecttype> subjecttypeSet;
    @OneToMany(mappedBy = "firstObjectActorId")
    private Set<Subjecttype> subjecttypeSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectActorId")
    private Set<Subjecttype> subjecttypeSet2;
    @OneToMany(mappedBy = "thirdObjectActorId")
    private Set<Subjecttype> subjecttypeSet3;

    public Actor() {
    }

    public Actor(Integer actorId) {
        this.actorId = actorId;
    }

    public Actor(Integer actorId, String actor, String infoStatus, int chechStatus) {
        this.actorId = actorId;
        this.actor = actor;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    @Override
    public Integer getIdentity() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
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

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Secondobject> getSecondobjects() {
        return secondobjectSet;
    }

    public void setSecondobjectSet(Set<Secondobject> secondobjectSet) {
        this.secondobjectSet = secondobjectSet;
    }

    @XmlTransient
    public Set<Thirdobject> getThirdobjects() {
        return thirdobjectSet;
    }

    public void setThirdobjectSet(Set<Thirdobject> thirdobjectSet) {
        this.thirdobjectSet = thirdobjectSet;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypes() {
        return subjecttypeSet;
    }

    public void setSubjecttypeSet(Set<Subjecttype> subjecttypeSet) {
        this.subjecttypeSet = subjecttypeSet;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypeSet1() {
        return subjecttypeSet1;
    }

    public void setSubjecttypeSet1(Set<Subjecttype> subjecttypeSet1) {
        this.subjecttypeSet1 = subjecttypeSet1;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypeSet2() {
        return subjecttypeSet2;
    }

    public void setSubjecttypeSet2(Set<Subjecttype> subjecttypeSet2) {
        this.subjecttypeSet2 = subjecttypeSet2;
    }

    @XmlTransient
    public Set<Subjecttype> getSubjecttypeSet3() {
        return subjecttypeSet3;
    }

    public void setSubjecttypeSet3(Set<Subjecttype> subjecttypeSet3) {
        this.subjecttypeSet3 = subjecttypeSet3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actorId != null ? actorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actor)) {
            return false;
        }
        Actor other = (Actor) object;
        if ((this.actorId == null && other.actorId != null) || (this.actorId != null && !this.actorId.equals(other.actorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Actor[ actorId=" + actorId + " ]";
    }

    @Override
    public void setIdentity(Integer id) {
        this.setActorId(id);
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

}
