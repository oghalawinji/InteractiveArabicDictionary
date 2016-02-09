/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedSemanticVerbBO extends SemanticVerbBO
{

    private Integer newSemanticVerbId;
    private String newTransitivityCase;
    private String newDifficultydegree;
    private String newSemanticscop;
    private String newEpoch;
    private String newSpreadingdegree;
    private String newSpecialization;
    private String newRegion;

    public UpdatedSemanticVerbBO()
    {
        super();
    }

    public Integer getNewSemanticVerbId()
    {
        return newSemanticVerbId;
    }

    public void setNewSemanticVerbId( Integer newSemanticVerbId )
    {
        this.newSemanticVerbId = newSemanticVerbId;
    }

    public String getNewTransitivityCase()
    {
        return newTransitivityCase;
    }

    public void setNewTransitivityCase( String newTransitivityCase )
    {
        this.newTransitivityCase = newTransitivityCase;
    }

    public String getNewDifficultydegree()
    {
        return newDifficultydegree;
    }

    public void setNewDifficultydegree( String newDifficultydegree )
    {
        this.newDifficultydegree = newDifficultydegree;
    }

    public String getNewEpoch()
    {
        return newEpoch;
    }

    public void setNewEpoch( String newEpoch )
    {
        this.newEpoch = newEpoch;
    }

    public String getNewRegion()
    {
        return newRegion;
    }

    public void setNewRegion( String newRegion )
    {
        this.newRegion = newRegion;
    }

    public String getNewSemanticscop()
    {
        return newSemanticscop;
    }

    public void setNewSemanticscop( String newSemanticscop )
    {
        this.newSemanticscop = newSemanticscop;
    }

    public String getNewSpecialization()
    {
        return newSpecialization;
    }

    public void setNewSpecialization( String newSpecialization )
    {
        this.newSpecialization = newSpecialization;
    }

    public String getNewSpreadingdegree()
    {
        return newSpreadingdegree;
    }

    public void setNewSpreadingdegree( String newSpreadingdegree )
    {
        this.newSpreadingdegree = newSpreadingdegree;
    }
}
