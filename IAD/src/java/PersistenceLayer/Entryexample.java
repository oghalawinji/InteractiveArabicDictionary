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
    @NamedQuery(name = "Entryexample.findAll", query = "SELECT e FROM Entryexample e"),
    @NamedQuery(name = "Entryexample.findByEntryExampleId", query = "SELECT e FROM Entryexample e WHERE e.entryExampleId = :entryExampleId"),
    @NamedQuery(name = "Entryexample.findByInfoStatus", query = "SELECT e FROM Entryexample e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Entryexample.findByChechStatus", query = "SELECT e FROM Entryexample e WHERE e.chechStatus = :chechStatus")})
public class Entryexample extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer entryExampleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @JoinColumn(name = "exampleId", referencedColumnName = "exampleId")
    @ManyToOne(optional = false)
    private Example exampleId;
    @JoinColumn(name = "semanticEntryId", referencedColumnName = "semanticEntryId")
    @ManyToOne(optional = false)
    private Semanticentry semanticEntryId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Entryexample() {
    }

    public Entryexample(Integer entryExampleId) {
        this.entryExampleId = entryExampleId;
    }

    public Entryexample(Semanticentry semanticentry, Example example) {
        this(semanticentry, example, new Suggestion(), "S", 0);
    }

    public Entryexample(Semanticentry semanticentry, Example example, Suggestion suggestion, String infoStatus, int chechStatus) {
        this.semanticEntryId = semanticentry;
        this.exampleId = example;
        this.suggestionId = suggestion;
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
        return entryExampleId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setEntryExampleId(id);
    }

    public void setEntryExampleId(Integer entryExampleId) {
        this.entryExampleId = entryExampleId;
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

    public Example getExample() {
        return exampleId;
    }

    public void setExample(Example exampleId) {
        this.exampleId = exampleId;
    }

    public Semanticentry getSemanticEntry() {
        return semanticEntryId;
    }

    public void setSemanticentry(Semanticentry semanticEntryId) {
        this.semanticEntryId = semanticEntryId;
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
        hash += (entryExampleId != null ? entryExampleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entryexample)) {
            return false;
        }
        Entryexample other = (Entryexample) object;
        if ((this.entryExampleId == null && other.entryExampleId != null) || (this.entryExampleId != null && !this.entryExampleId.equals(other.entryExampleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Entryexample[ entryExampleId=" + entryExampleId + " ]";
    }

}
