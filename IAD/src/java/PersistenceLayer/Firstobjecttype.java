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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firstobjecttype.findAll", query = "SELECT f FROM Firstobjecttype f"),
    @NamedQuery(name = "Firstobjecttype.findByVSOId", query = "SELECT f FROM Firstobjecttype f WHERE f.vSOId = :vSOId")})
public class Firstobjecttype extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer vSOId;
    @JoinColumn(name = "VSId", referencedColumnName = "VSId")
    @ManyToOne(optional = false)
    private Subjecttype vSId;
    @JoinColumn(name = "objectActorId", referencedColumnName = "actorId")
    @ManyToOne(optional = false)
    private Actor objectActorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vSOId")
    private Set<Secondobject> secondobjectSet;

    public Firstobjecttype() {
    }

    public Firstobjecttype(Integer vSOId) {
        this.vSOId = vSOId;
    }

    @Override
    public void execludeGeneralProperties() {

    }

    @Override
    public Integer getIdentity() {
        return vSOId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setVSOId(id);
    }

    public void setVSOId(Integer vSOId) {
        this.vSOId = vSOId;
    }

    public Subjecttype getVS() {
        return vSId;
    }

    public void setVS(Subjecttype vSId) {
        this.vSId = vSId;
    }

    public Actor getObjectActor() {
        return objectActorId;
    }

    public void setObjectActor(Actor objectActorId) {
        this.objectActorId = objectActorId;
    }

    @XmlTransient
    public Set<Secondobject> getSecondobjects() {
        return secondobjectSet;
    }

    public void setSecondobjectSet(Set<Secondobject> secondobjectSet) {
        this.secondobjectSet = secondobjectSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vSOId != null ? vSOId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firstobjecttype)) {
            return false;
        }
        Firstobjecttype other = (Firstobjecttype) object;
        if ((this.vSOId == null && other.vSOId != null) || (this.vSOId != null && !this.vSOId.equals(other.vSOId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Firstobjecttype[ vSOId=" + vSOId + " ]";
    }

}
