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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Thirdobject.findAll", query = "SELECT t FROM Thirdobject t"),
    @NamedQuery(name = "Thirdobject.findByVSOOOId", query = "SELECT t FROM Thirdobject t WHERE t.vSOOOId = :vSOOOId")})
public class Thirdobject extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer vSOOOId;
    @JoinColumn(name = "VSOOId", referencedColumnName = "VSOOId")
    @ManyToOne(optional = false)
    private Secondobject vSOOId;
    @JoinColumn(name = "thirdObjectActorId", referencedColumnName = "actorId")
    @ManyToOne(optional = false)
    private Actor thirdObjectActorId;

    public Thirdobject() {
    }

    public Thirdobject(Integer vSOOOId) {
        this.vSOOOId = vSOOOId;
    }

    @Override
    public Integer getIdentity() {
        return vSOOOId;
    }

    @Override
    public void execludeGeneralProperties() {

    }

    @Override
    public void setIdentity(Integer id) {
        this.setVSOOOId(id);
    }

    public void setVSOOOId(Integer vSOOOId) {
        this.vSOOOId = vSOOOId;
    }

    public Secondobject getVSOO() {
        return vSOOId;
    }

    public void setVSOO(Secondobject vSOOId) {
        this.vSOOId = vSOOId;
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
        hash += (vSOOOId != null ? vSOOOId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thirdobject)) {
            return false;
        }
        Thirdobject other = (Thirdobject) object;
        if ((this.vSOOOId == null && other.vSOOOId != null) || (this.vSOOOId != null && !this.vSOOOId.equals(other.vSOOOId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Thirdobject[ vSOOOId=" + vSOOOId + " ]";
    }

}
