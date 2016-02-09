/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedMeaningBO extends MeaningBO{

    private Integer oldMeaningId;
    private String oldDescription;
    private String oldSource;
    private String oldStatus = "Q";

    public UpdatedMeaningBO() {
        super();
    }

    public Integer getOldMeaningId ()
    {
        return oldMeaningId;
    }

    public void setOldMeaningId ( Integer oldMeaningId )
    {
        this.oldMeaningId = oldMeaningId;
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
