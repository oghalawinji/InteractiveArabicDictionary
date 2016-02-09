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
    @NamedQuery(name = "Number.findAll", query = "SELECT n FROM Number n"),
    @NamedQuery(name = "Number.findByNumberId", query = "SELECT n FROM Number n WHERE n.numberId = :numberId"),
    @NamedQuery(name = "Number.findByNumber", query = "SELECT n FROM Number n WHERE n.number = :number"),
    @NamedQuery(name = "Number.findByInfoStatus", query = "SELECT n FROM Number n WHERE n.infoStatus = :infoStatus"),
    @NamedQuery(name = "Number.findByChechStatus", query = "SELECT n FROM Number n WHERE n.chechStatus = :chechStatus")})
public class Number extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer numberId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String number;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String infoStatus;
    @Basic(optional = false)
    @NotNull
    private int chechStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numberId")
    private Set<Derivednoun> derivednounSet;
    @JoinColumn(name = "suggestionId", referencedColumnName = "suggestionId")
    @ManyToOne
    private Suggestion suggestionId;

    public Number() {
    }

    public Number(Integer numberId) {
        this.numberId = numberId;
    }

    public Number(String number) {
        this(new Suggestion(), number, "S", 0);
    }

    public Number(Suggestion suggestion, String number, String infoStatus, int chechStatus) {
        this.suggestionId = suggestion;
        this.number = number;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
    }

    public Number(Suggestion suggestion, String number, String infoStatus, int chechStatus, Set<Derivednoun> derivednouns) {
        this.suggestionId = suggestion;
        this.number = number;
        this.infoStatus = infoStatus;
        this.chechStatus = chechStatus;
        this.derivednounSet = derivednouns;
    }

    @Override
    public void execludeGeneralProperties() {
        this.setChechStatus(0);
        this.setSuggestion(null);
        this.setInfoStatus(null);
    }

    @Override
    public Integer getIdentity() {
        return numberId;
    }

    @Override
    public void setIdentity(Integer id) {
        this.setNumberId(id);
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
    public Set<Derivednoun> getDerivednouns() {
        return derivednounSet;
    }

    public void setDerivednounSet(Set<Derivednoun> derivednounSet) {
        this.derivednounSet = derivednounSet;
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
        hash += (numberId != null ? numberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Number)) {
            return false;
        }
        Number other = (Number) object;
        if ((this.numberId == null && other.numberId != null) || (this.numberId != null && !this.numberId.equals(other.numberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.Number[ numberId=" + numberId + " ]";
    }

}
