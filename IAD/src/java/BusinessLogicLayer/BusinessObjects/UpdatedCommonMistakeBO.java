/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedCommonMistakeBO extends CommonMistakeBO{

    private Integer oldCommonMistakeId;
    private String oldDescription;
    private String oldSource;
    private String oldStatus ="Q";

    public Integer getOldCommonMistakeId ()
    {
        return oldCommonMistakeId;
    }

    public void setOldCommonMistakeId ( Integer oldCommonMistakeId )
    {
        this.oldCommonMistakeId = oldCommonMistakeId;
    }

    public UpdatedCommonMistakeBO() {
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
