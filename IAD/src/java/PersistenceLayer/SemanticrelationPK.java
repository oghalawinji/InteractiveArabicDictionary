/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Omar
 */
@Embeddable
public class SemanticrelationPK implements Serializable {

    private static final long serialVersionUID = 12L;
    @Column(name = "firstSemanticScopId", nullable = false)
    private int firstSemanticScopId;
    @Column(name = "secondSemanticScopId", nullable = false)
    private int secondSemanticScopId;
    @Column(name = "semanticRelationTypeId", nullable = false)
    private int semanticRelationTypeId;

    public SemanticrelationPK() {
    }

    public SemanticrelationPK(int firstSemanticScopId, int secondSemanticScopId, int semanticRelationTypeId) {
        this.firstSemanticScopId = firstSemanticScopId;
        this.secondSemanticScopId = secondSemanticScopId;
        this.semanticRelationTypeId = semanticRelationTypeId;
    }

    public int getFirstSemanticScopId() {
        return firstSemanticScopId;
    }

    public void setFirstSemanticScopId(int firstSemanticScopId) {
        this.firstSemanticScopId = firstSemanticScopId;
    }

    public int getSecondSemanticScopId() {
        return secondSemanticScopId;
    }

    public void setSecondSemanticScopId(int secondSemanticScopId) {
        this.secondSemanticScopId = secondSemanticScopId;
    }

    public int getSemanticRelationTypeId() {
        return semanticRelationTypeId;
    }

    public void setSemanticRelationTypeId(int semanticRelationTypeId) {
        this.semanticRelationTypeId = semanticRelationTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) firstSemanticScopId;
        hash += (int) secondSemanticScopId;
        hash += (int) semanticRelationTypeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemanticrelationPK)) {
            return false;
        }
        SemanticrelationPK other = (SemanticrelationPK) object;
        if (this.firstSemanticScopId != other.firstSemanticScopId) {
            return false;
        }
        if (this.secondSemanticScopId != other.secondSemanticScopId) {
            return false;
        }
        return this.semanticRelationTypeId == other.semanticRelationTypeId;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.SemanticrelationPK[ firstSemanticScopId=" + firstSemanticScopId + ", secondSemanticScopId=" + secondSemanticScopId + ", semanticRelationTypeId=" + semanticRelationTypeId + " ]";
    }

}
