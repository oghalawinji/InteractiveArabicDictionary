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
    @NamedQuery(name = "Example.findAll", query = "SELECT e FROM Example e"),
    @NamedQuery(name = "Example.findByExampleId", query = "SELECT e FROM Example e WHERE e.exampleId = :exampleId"),
    @NamedQuery(name = "Example.findByExample", query = "SELECT e FROM Example e WHERE e.example = :example"),
    @NamedQuery(name = "Example.findByInfoStatus", query = "SELECT e FROM Example e WHERE e.infoStatus = :infoStatus"),
    @NamedQuery(name = "Example.findByChechStatus", query = "SELECT e FROM Example e WHERE e.chechStatus = :chechStatus")})
public class Example extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer exampleId;
    @Size(max = 300)
    private String example;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exampleId")
    private Set<Examplesound> examplesoundSet;
    @JoinColumn(name = "sourceId", referencedColumnName = "sourceId")
    @ManyToOne(optional = false)
    private Source sourceId;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exampleId")
    private Set<Entryexample> entryexampleSet;

    public Example() {
    }

    public Example(Integer exampleId) {
        this.exampleId = exampleId;
    }

    public Example(Source source, String example) {
        this(source, new Suggestion(), example, "S", 0);
    }

    public Example(Source source, Suggestion suggestion, String example, String infoStatus, int chechStatus) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.example = example;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Example(Source source, Suggestion suggestion, String example, String infoStatus, int chechStatus, Set<Examplesound> examplesounds, Set<Entryexample> entryexamples) {
        this.sourceId = source;
        this.suggestionId = suggestion;
        this.example = example;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.examplesoundSet = examplesounds;
        this.entryexampleSet = entryexamples;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return exampleId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setExampleId(id);
    }

    public void setExampleId(Integer exampleId) {
        this.exampleId = exampleId;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
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
    public Set<Examplesound> getExamplesounds() {
        return examplesoundSet;
    }

    public void setExamplesoundSet(Set<Examplesound> examplesoundSet) {
        this.examplesoundSet = examplesoundSet;
    }

    public Source getSource() {
        return sourceId;
    }

    public void setSource(Source sourceId) {
        this.sourceId = sourceId;
    }

    public Suggestion getSuggestion() {
        return suggestionId;
    }

    public void setSuggestion(Suggestion suggestionId) {
        this.suggestionId = suggestionId;
    }

    @XmlTransient
    public Set<Entryexample> getEntryexamples() {
        return entryexampleSet;
    }

    public void setEntryexampleSet(Set<Entryexample> entryexampleSet) {
        this.entryexampleSet = entryexampleSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exampleId != null ? exampleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Example)) {
            return false;
        }
        Example other = (Example) object;
        if ((this.exampleId == null && other.exampleId != null) || (this.exampleId != null && !this.exampleId.equals(other.exampleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Example[ exampleId=" + exampleId + " ]";
    }

}
