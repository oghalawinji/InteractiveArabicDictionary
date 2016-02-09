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
    @NamedQuery(name = "Semanticscop.findAll", query = "SELECT s FROM Semanticscop s"),
    @NamedQuery(name = "Semanticscop.findBySemanticScopId", query = "SELECT s FROM Semanticscop s WHERE s.semanticScopId = :semanticScopId"),
    @NamedQuery(name = "Semanticscop.findBySemanticScop", query = "SELECT s FROM Semanticscop s WHERE s.semanticScop = :semanticScop"),
    @NamedQuery(name = "Semanticscop.findByInfoStatus", query = "SELECT s FROM Semanticscop s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticscop.findByChechStatus", query = "SELECT s FROM Semanticscop s WHERE s.chechStatus = :chechStatus")})
public class Semanticscop extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticScopId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String semanticScop;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticscop")
    private Set<Semanticrelation> semanticrelationSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticscop1")
    private Set<Semanticrelation> semanticrelationSet1;
    @OneToMany(mappedBy = "semanticScopId")
    private Set<Semanticentry> semanticentrySet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticscop() {
    }

    public Semanticscop(Integer semanticScopId) {
        this.semanticScopId = semanticScopId;
    }

    public Semanticscop(String semanticScop) {
        this(new Suggestion(), semanticScop, "S", 0);
    }

    public Semanticscop(Suggestion suggestion, String semanticScop, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.semanticScop = semanticScop;
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
        return semanticScopId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticScopId(id);
    }

    public void setSemanticScopId(Integer semanticScopId) {
        this.semanticScopId = semanticScopId;
    }

    public String getSemanticScop() {
        return semanticScop;
    }

    public void setSemanticScop(String semanticScop) {
        this.semanticScop = semanticScop;
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
    public Set<Semanticrelation> getSemanticrelations() {
        return semanticrelationSet;
    }

    public void setSemanticrelationSet(Set<Semanticrelation> semanticrelationSet) {
        this.semanticrelationSet = semanticrelationSet;
    }

    @XmlTransient
    public Set<Semanticrelation> getSemanticrelationSet1() {
        return semanticrelationSet1;
    }

    public void setSemanticrelationSet1(Set<Semanticrelation> semanticrelationSet1) {
        this.semanticrelationSet1 = semanticrelationSet1;
    }

    @XmlTransient
    public Set<Semanticentry> getSemanticentries() {
        return semanticentrySet;
    }

    public void setSemanticentrySet(Set<Semanticentry> semanticentrySet) {
        this.semanticentrySet = semanticentrySet;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticScopId != null ? semanticScopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticscop)) {
            return false;
        }
        Semanticscop other = (Semanticscop) object;
        if ((this.semanticScopId == null && other.semanticScopId != null) || (this.semanticScopId != null && !this.semanticScopId.equals(other.semanticScopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticscop[ semanticScopId=" + semanticScopId + " ]";
    }

}
