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
    @NamedQuery(name = "Semanticrelationtype.findAll", query = "SELECT s FROM Semanticrelationtype s"),
    @NamedQuery(name = "Semanticrelationtype.findBySemanticRelationTypeId", query = "SELECT s FROM Semanticrelationtype s WHERE s.semanticRelationTypeId = :semanticRelationTypeId"),
    @NamedQuery(name = "Semanticrelationtype.findBySemanticRelationType", query = "SELECT s FROM Semanticrelationtype s WHERE s.semanticRelationType = :semanticRelationType"),
    @NamedQuery(name = "Semanticrelationtype.findByInfoStatus", query = "SELECT s FROM Semanticrelationtype s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticrelationtype.findByChechStatus", query = "SELECT s FROM Semanticrelationtype s WHERE s.chechStatus = :chechStatus")})
public class Semanticrelationtype extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer semanticRelationTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String semanticRelationType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semanticrelationtype")
    private Set<Semanticrelation> semanticrelationSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticrelationtype() {
    }

    public Semanticrelationtype(Integer semanticRelationTypeId) {
        this.semanticRelationTypeId = semanticRelationTypeId;
    }

    public Semanticrelationtype(Integer semanticRelationTypeId, String semanticRelationType, String infoStatus, int chechStatus) {
        this.semanticRelationTypeId = semanticRelationTypeId;
        this.semanticRelationType = semanticRelationType;
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
        return semanticRelationTypeId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSemanticRelationTypeId(id);
    }

    public void setSemanticRelationTypeId(Integer semanticRelationTypeId) {
        this.semanticRelationTypeId = semanticRelationTypeId;
    }

    public String getSemanticRelationType() {
        return semanticRelationType;
    }

    public void setSemanticRelationType(String semanticRelationType) {
        this.semanticRelationType = semanticRelationType;
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

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticRelationTypeId != null ? semanticRelationTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticrelationtype)) {
            return false;
        }
        Semanticrelationtype other = (Semanticrelationtype) object;
        if ((this.semanticRelationTypeId == null && other.semanticRelationTypeId != null) || (this.semanticRelationTypeId != null && !this.semanticRelationTypeId.equals(other.semanticRelationTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticrelationtype[ semanticRelationTypeId=" + semanticRelationTypeId + " ]";
    }

}
