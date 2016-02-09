/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.querybyexample.jpa.Identifiable;

/**
 *
 * @author Omar
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semanticrelation.findAll", query = "SELECT s FROM Semanticrelation s"),
    @NamedQuery(name = "Semanticrelation.findByFirstSemanticScopId", query = "SELECT s FROM Semanticrelation s WHERE s.semanticrelationPK.firstSemanticScopId = :firstSemanticScopId"),
    @NamedQuery(name = "Semanticrelation.findBySecondSemanticScopId", query = "SELECT s FROM Semanticrelation s WHERE s.semanticrelationPK.secondSemanticScopId = :secondSemanticScopId"),
    @NamedQuery(name = "Semanticrelation.findBySemanticRelationTypeId", query = "SELECT s FROM Semanticrelation s WHERE s.semanticrelationPK.semanticRelationTypeId = :semanticRelationTypeId"),
    @NamedQuery(name = "Semanticrelation.findByInfoStatus", query = "SELECT s FROM Semanticrelation s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Semanticrelation.findByChechStatus", query = "SELECT s FROM Semanticrelation s WHERE s.chechStatus = :chechStatus")})
public class Semanticrelation implements Serializable, Identifiable<SemanticrelationPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SemanticrelationPK semanticrelationPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "semanticRelationTypeId", referencedColumnName = "semanticRelationTypeId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Semanticrelationtype semanticrelationtype;
    @JoinColumn(name = "secondSemanticScopId", referencedColumnName = "semanticScopId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Semanticscop semanticscop;
    @JoinColumn(name = "firstSemanticScopId", referencedColumnName = "semanticScopId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Semanticscop semanticscop1;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Semanticrelation() {
    }

    public Semanticrelation(SemanticrelationPK semanticrelationPK) {
        this.semanticrelationPK = semanticrelationPK;
    }

    public Semanticrelation(SemanticrelationPK semanticrelationPK, String infoStatus, int chechStatus) {
        this.semanticrelationPK = semanticrelationPK;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Semanticrelation(int firstSemanticScopId, int secondSemanticScopId, int semanticRelationTypeId) {
        this.semanticrelationPK = new SemanticrelationPK(firstSemanticScopId, secondSemanticScopId, semanticRelationTypeId);
    }

    public SemanticrelationPK getSemanticrelationPK() {
        return semanticrelationPK;
    }

    public void setSemanticrelationPK(SemanticrelationPK semanticrelationPK) {
        this.semanticrelationPK = semanticrelationPK;
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

    public Semanticrelationtype getSemanticrelationtype() {
        return semanticrelationtype;
    }

    public void setSemanticrelationtype(Semanticrelationtype semanticrelationtype) {
        this.semanticrelationtype = semanticrelationtype;
    }

    public Semanticscop getSemanticscopBySecondSemanticScopId() {
        return semanticscop;
    }

    public void setSemanticscop(Semanticscop semanticscop) {
        this.semanticscop = semanticscop;
    }

    public Semanticscop getSemanticscopByFirstSemanticScopId() {
        return semanticscop1;
    }

    public void setSemanticscopByFirstSemanticScopId(Semanticscop semanticscop1) {
        this.semanticscop1 = semanticscop1;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticrelationPK != null ? semanticrelationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanticrelation)) {
            return false;
        }
        Semanticrelation other = (Semanticrelation) object;
        if ((this.semanticrelationPK == null && other.semanticrelationPK != null) || (this.semanticrelationPK != null && !this.semanticrelationPK.equals(other.semanticrelationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Semanticrelation[ semanticrelationPK=" + semanticrelationPK + " ]";
    }

    @Override
    public SemanticrelationPK getId() {
        return this.getSemanticrelationPK();
    }

    @Override
    public void setId(SemanticrelationPK id) {
        this.setSemanticrelationPK(id);
    }

    @Override
    public boolean isIdSet() {
        return this.getSemanticrelationPK() != null;
    }

}
