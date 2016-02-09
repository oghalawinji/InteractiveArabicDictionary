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
    @NamedQuery(name = "Suffix.findAll", query = "SELECT s FROM Suffix s"),
    @NamedQuery(name = "Suffix.findById", query = "SELECT s FROM Suffix s WHERE s.id = :id"),
    @NamedQuery(name = "Suffix.findBySuffix", query = "SELECT s FROM Suffix s WHERE s.suffix = :suffix")})
public class Suffix extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    private String suffix;

    public Suffix() {
    }

    public Suffix(Integer id) {
        this.id = id;
    }

    public Suffix(Integer id, String suffix) {
        this.id = id;
        this.suffix = suffix;
    }

    @Override
    public void execludeGeneralProperties() {

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
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
        if (!(object instanceof Suffix)) {
            return false;
        }
        Suffix other = (Suffix) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Suffix[ id=" + id + " ]";
    }

}
