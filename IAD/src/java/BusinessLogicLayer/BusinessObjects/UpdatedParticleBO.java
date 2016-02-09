/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedParticleBO extends ParticleBO{

    private Integer newDerivedParticleId;
    private String newRoot;
    private String newParticletype;
    private String newPhonetic;
    private String newStatus;

    public UpdatedParticleBO() {
        super();
    }

    public Integer getNewDerivedParticleId() {
        return newDerivedParticleId;
    }

    public void setNewDerivedParticleId(Integer newDerivedParticleId) {
        this.newDerivedParticleId = newDerivedParticleId;
    }

    public String getNewParticletype() {
        return newParticletype;
    }

    public void setNewParticletype(String newParticletype) {
        this.newParticletype = newParticletype;
    }

    public String getNewPhonetic() {
        return newPhonetic;
    }

    public void setNewPhonetic(String newPhonetic) {
        this.newPhonetic = newPhonetic;
    }

    public String getNewRoot() {
        return newRoot;
    }

    public void setNewRoot(String newRoot) {
        this.newRoot = newRoot;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

}
