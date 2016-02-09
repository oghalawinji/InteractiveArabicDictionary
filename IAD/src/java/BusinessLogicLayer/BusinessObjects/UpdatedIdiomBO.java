package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class UpdatedIdiomBO extends IdiomBO
{

    private Integer newIdiomId;
    private String newIdiom;
    private String newStatus;

    public UpdatedIdiomBO() {
        super();
    }

    public String getNewIdiom() {
        return newIdiom;
    }

    public void setNewIdiom(String newIdiom) {
        this.newIdiom = newIdiom;
    }

    public Integer getNewIdiomId() {
        return newIdiomId;
    }

    public void setNewIdiomId(Integer newIdiomId) {
        this.newIdiomId = newIdiomId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

}
