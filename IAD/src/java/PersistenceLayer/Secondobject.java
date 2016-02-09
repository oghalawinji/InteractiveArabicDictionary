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
    @NamedQuery(name = "Secondobject.findAll", query = "SELECT s FROM Secondobject s"),
    @NamedQuery(name = "Secondobject.findByVSOOId", query = "SELECT s FROM Secondobject s WHERE s.vSOOId = :vSOOId")})
public class Secondobject extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer vSOOId;
    @JoinColumn(name = "VSOId", referencedColumnName = "VSOId")
    @ManyToOne(optional = false)
    private Firstobjecttype vSOId;
    @JoinColumn(name = "secondObjectActorId", referencedColumnName = "actorId")
    @ManyToOne(optional = false)
    private Actor secondObjectActorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vSOOId")
    private Set<Thirdobject> thirdobjectSet;

    public Secondobject() {
    }

    public Secondobject(Integer vSOOId) {
        this.vSOOId = vSOOId;
    }

    @Override
    public Integer getIdentity() {
        return vSOOId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setVSOOId(id);
    }

    public void setVSOOId(Integer vSOOId) {
        this.vSOOId = vSOOId;
    }

    public Firstobjecttype getVSO() {
        return vSOId;
    }

    public void setVSO(Firstobjecttype vSOId) {
        this.vSOId = vSOId;
    }

    @Override
    public void execludeGeneralProperties() {

    }

    public Actor getSecondObjectActor() {
        return secondObjectActorId;
    }

    public void setSecondObjectActor(Actor secondObjectActorId) {
        this.secondObjectActorId = secondObjectActorId;
    }

    @XmlTransient
    public Set<Thirdobject> getThirdobjects() {
        return thirdobjectSet;
    }

    public void setThirdobjectSet(Set<Thirdobject> thirdobjectSet) {
        this.thirdobjectSet = thirdobjectSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vSOOId != null ? vSOOId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secondobject)) {
            return false;
        }
        Secondobject other = (Secondobject) object;
        if ((this.vSOOId == null && other.vSOOId != null) || (this.vSOOId != null && !this.vSOOId.equals(other.vSOOId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Secondobject[ vSOOId=" + vSOOId + " ]";
    }

}
