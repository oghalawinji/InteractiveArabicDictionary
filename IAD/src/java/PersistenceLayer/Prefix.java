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
    @NamedQuery(name = "Prefix.findAll", query = "SELECT p FROM Prefix p"),
    @NamedQuery(name = "Prefix.findById", query = "SELECT p FROM Prefix p WHERE p.id = :id"),
    @NamedQuery(name = "Prefix.findByPrefix", query = "SELECT p FROM Prefix p WHERE p.prefix = :prefix")})
public class Prefix extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    private String prefix;

    public Prefix() {
    }

    public Prefix(Integer id) {
        this.id = id;
    }

    public Prefix(Integer id, String prefix) {
        this.id = id;
        this.prefix = prefix;
    }

    @Override
    public Integer getIdentity() {
        return id;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setId(id);
    }

    @Override
    public void execludeGeneralProperties() {

    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prefix)) {
            return false;
        }
        Prefix other = (Prefix) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Prefix[ id=" + id + " ]";
    }

}
