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
    @NamedQuery(name = "Root.findAll", query = "SELECT r FROM Root r"),
    @NamedQuery(name = "Root.findByRootId", query = "SELECT r FROM Root r WHERE r.rootId = :rootId"),
    @NamedQuery(name = "Root.findByRoot", query = "SELECT r FROM Root r WHERE r.root = :root"),
    @NamedQuery(name = "Root.findByInfoStatus", query = "SELECT r FROM Root r WHERE r.infoStatus = :infoStatus"),
    @NamedQuery(name = "Root.findByChechStatus", query = "SELECT r FROM Root r WHERE r.chechStatus = :chechStatus")})
public class Root extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer rootId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String root;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootId")
    private Set<Derivednoun> derivednounSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootId")
    private Set<Derivedparticle> derivedparticleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootId")
    private Set<Derivedverb> derivedverbSet;

    public Root() {
    }

    public Root(Integer rootId) {
        this.rootId = rootId;
    }

    public Root(String root) {
        this(new Suggestion(), root, "S", 0);
    }

    public Root(Suggestion suggestion, String root, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.root = root;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Root(Suggestion suggestion, String root, String infoStatus, int chechStatus, Set<Derivedparticle> derivedparticles, Set<Derivednoun> derivednouns, Set<Derivedverb> derivedverbs) {
        this.suggestionId = suggestion;
        this.root = root;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivedparticleSet = derivedparticles;
        this.derivednounSet = derivednouns;
        this.derivedverbSet = derivedverbs;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return rootId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setRootId(id);
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
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

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
    }

    @XmlTransient
    public Set<Derivedparticle> getDerivedparticles() {
        return derivedparticleSet;
    }

    public void setDerivedparticleSet(Set<Derivedparticle> derivedparticleSet) {
        this.derivedparticleSet = derivedparticleSet;
    }

    @XmlTransient
    public Set<Derivedverb> getDerivedverbs() {
        return derivedverbSet;
    }

    public void setDerivedverbSet(Set<Derivedverb> derivedverbSet) {
        this.derivedverbSet = derivedverbSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rootId != null ? rootId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Root)) {
            return false;
        }
        Root other = (Root) object;
        if ((this.rootId == null && other.rootId != null) || (this.rootId != null && !this.rootId.equals(other.rootId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Root[ rootId=" + rootId + " ]";
    }

}
