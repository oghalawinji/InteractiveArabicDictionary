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
    @NamedQuery(name = "Source.findAll", query = "SELECT s FROM Source s"),
    @NamedQuery(name = "Source.findBySourceId", query = "SELECT s FROM Source s WHERE s.sourceId = :sourceId"),
    @NamedQuery(name = "Source.findBySource", query = "SELECT s FROM Source s WHERE s.source = :source"),
    @NamedQuery(name = "Source.findByInfoStatus", query = "SELECT s FROM Source s WHERE s.infoStatus = :infoStatus"),
    @NamedQuery(name = "Source.findByChechStatus", query = "SELECT s FROM Source s WHERE s.chechStatus = :chechStatus")})
public class Source extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer sourceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String source;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Set<Commonmistake> commonmistakeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Set<Linguisticbenefit> linguisticbenefitSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Set<Example> exampleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Set<Meaning> meaningSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Source() {
    }

    public Source(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Source(String source) {
        this(new Suggestion(), source, "S", 0);
    }

    public Source(Suggestion suggestion, String source, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.source = source;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Source(Suggestion suggestion, String source, String infoStatus, int chechStatus, Set<Meaning> meanings, Set<Linguisticbenefit> linguisticbenefits, Set<Example> examples, Set<Commonmistake> commonmistakes) {
        this.suggestionId = suggestion;
        this.source = source;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.meaningSet = meanings;
        this.linguisticbenefitSet = linguisticbenefits;
        this.exampleSet = examples;
        this.commonmistakeSet = commonmistakes;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return sourceId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setSourceId(id);
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
    public Set<Commonmistake> getCommonmistakes() {
        return commonmistakeSet;
    }

    public void setCommonmistakeSet(Set<Commonmistake> commonmistakeSet) {
        this.commonmistakeSet = commonmistakeSet;
    }

    @XmlTransient
    public Set<Linguisticbenefit> getLinguisticbenefits() {
        return linguisticbenefitSet;
    }

    public void setLinguisticbenefitSet(Set<Linguisticbenefit> linguisticbenefitSet) {
        this.linguisticbenefitSet = linguisticbenefitSet;
    }

    @XmlTransient
    public Set<Example> getExamples() {
        return exampleSet;
    }

    public void setExampleSet(Set<Example> exampleSet) {
        this.exampleSet = exampleSet;
    }

    @XmlTransient
    public Set<Meaning> getMeanings() {
        return meaningSet;
    }

    public void setMeaningSet(Set<Meaning> meaningSet) {
        this.meaningSet = meaningSet;
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
        hash += (sourceId != null ? sourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Source)) {
            return false;
        }
        Source other = (Source) object;
        if ((this.sourceId == null && other.sourceId != null) || (this.sourceId != null && !this.sourceId.equals(other.sourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Source[ sourceId=" + sourceId + " ]";
    }

}
