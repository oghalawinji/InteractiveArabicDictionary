/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedLinguisticBenefitBO extends LinguisticBenefitBO{

    private Integer oldLinguisticBenefitId;
    private String oldDescription;
    private String oldSource;
    private String oldStatus;

    public Integer getOldLinguisticBenefitId ()
    {
        return oldLinguisticBenefitId;
    }

    public void setOldLinguisticBenefitId ( Integer oldLinguisticBenefitId )
    {
        this.oldLinguisticBenefitId = oldLinguisticBenefitId;
    }

    public UpdatedLinguisticBenefitBO() {
        super();
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public String getOldSource() {
        return oldSource;
    }

    public void setOldSource(String oldSource) {
        this.oldSource = oldSource;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
