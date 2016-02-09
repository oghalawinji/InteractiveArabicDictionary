/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhoneNumber", query = "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "User.findByWebSite", query = "SELECT u FROM User u WHERE u.webSite = :webSite"),
    @NamedQuery(name = "User.findByCountry", query = "SELECT u FROM User u WHERE u.country = :country"),
    @NamedQuery(name = "User.findByCity", query = "SELECT u FROM User u WHERE u.city = :city"),
    @NamedQuery(name = "User.findByEducationLevel", query = "SELECT u FROM User u WHERE u.educationLevel = :educationLevel"),
    @NamedQuery(name = "User.findByParticipationDate", query = "SELECT u FROM User u WHERE u.participationDate = :participationDate"),
    @NamedQuery(name = "User.findByNumOfInsertOperation", query = "SELECT u FROM User u WHERE u.numOfInsertOperation = :numOfInsertOperation"),
    @NamedQuery(name = "User.findByNumOfUpdateOperation", query = "SELECT u FROM User u WHERE u.numOfUpdateOperation = :numOfUpdateOperation"),
    @NamedQuery(name = "User.findByNumOfDeleteOperation", query = "SELECT u FROM User u WHERE u.numOfDeleteOperation = :numOfDeleteOperation"),
    @NamedQuery(name = "User.findByNumOfEnteredEntries", query = "SELECT u FROM User u WHERE u.numOfEnteredEntries = :numOfEnteredEntries"),
    @NamedQuery(name = "User.findByNumOfErrorsInfo", query = "SELECT u FROM User u WHERE u.numOfErrorsInfo = :numOfErrorsInfo"),
    @NamedQuery(name = "User.findByNumOfcheckedInfo", query = "SELECT u FROM User u WHERE u.numOfcheckedInfo = :numOfcheckedInfo"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")})
public class User extends JPAEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    private String email;
    @Size(max = 50)
    private String phoneNumber;
    @Size(max = 150)
    private String webSite;
    @Size(max = 45)
    private String country;
    @Size(max = 45)
    private String city;
    @Size(max = 45)
    private String educationLevel;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date participationDate;
    @Basic(optional = false)
    @NotNull
    private int numOfInsertOperation;
    @Basic(optional = false)
    @NotNull
    private int numOfUpdateOperation;
    @Basic(optional = false)
    @NotNull
    private int numOfDeleteOperation;
    @Basic(optional = false)
    @NotNull
    private int numOfEnteredEntries;
    @Basic(optional = false)
    @NotNull
    private int numOfErrorsInfo;
    @Basic(optional = false)
    @NotNull
    private int numOfcheckedInfo;
    @Size(max = 50)
    private String name;
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    @ManyToOne(optional = false)
    private Role roleId;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userName, String password, Date participationDate, int numOfInsertOperation, int numOfUpdateOperation, int numOfDeleteOperation, int numOfEnteredEntries, int numOfErrorsInfo, int numOfcheckedInfo) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.participationDate = participationDate;
        this.numOfInsertOperation = numOfInsertOperation;
        this.numOfUpdateOperation = numOfUpdateOperation;
        this.numOfDeleteOperation = numOfDeleteOperation;
        this.numOfEnteredEntries = numOfEnteredEntries;
        this.numOfErrorsInfo = numOfErrorsInfo;
        this.numOfcheckedInfo = numOfcheckedInfo;
    }

    @Override
    public Integer getIdentity() {
        return userId;
    }

    @Override
    public void execludeGeneralProperties() {

    }

    @Override
    public void setIdentity(Integer id) {
        this.setUserId(id);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Date getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(Date participationDate) {
        this.participationDate = participationDate;
    }

    public int getNumOfInsertOperation() {
        return numOfInsertOperation;
    }

    public void setNumOfInsertOperation(int numOfInsertOperation) {
        this.numOfInsertOperation = numOfInsertOperation;
    }

    public int getNumOfUpdateOperation() {
        return numOfUpdateOperation;
    }

    public void setNumOfUpdateOperation(int numOfUpdateOperation) {
        this.numOfUpdateOperation = numOfUpdateOperation;
    }

    public int getNumOfDeleteOperation() {
        return numOfDeleteOperation;
    }

    public void setNumOfDeleteOperation(int numOfDeleteOperation) {
        this.numOfDeleteOperation = numOfDeleteOperation;
    }

    public int getNumOfEnteredEntries() {
        return numOfEnteredEntries;
    }

    public void setNumOfEnteredEntries(int numOfEnteredEntries) {
        this.numOfEnteredEntries = numOfEnteredEntries;
    }

    public int getNumOfErrorsInfo() {
        return numOfErrorsInfo;
    }

    public void setNumOfErrorsInfo(int numOfErrorsInfo) {
        this.numOfErrorsInfo = numOfErrorsInfo;
    }

    public int getNumOfcheckedInfo() {
        return numOfcheckedInfo;
    }

    public void setNumOfcheckedInfo(int numOfcheckedInfo) {
        this.numOfcheckedInfo = numOfcheckedInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return roleId;
    }

    public void setRole(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersistenceLayer.User[ userId=" + userId + " ]";
    }

}
