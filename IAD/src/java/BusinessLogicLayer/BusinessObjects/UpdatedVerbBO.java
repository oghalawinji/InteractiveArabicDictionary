package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class UpdatedVerbBO extends VerbBO
{

    private Integer newDerivedVerbId;
    private String newPattern;
    private String newRoot;
    private String newPresentForm;
    private String newPhonetic;
    private String newVocalizedVerb;

    public UpdatedVerbBO()
    {
        super();
    }

    public Integer getNewDerivedVerbId()
    {
        return newDerivedVerbId;
    }

    public void setNewDerivedVerbId( Integer newDerivedVerbId )
    {
        this.newDerivedVerbId = newDerivedVerbId;
    }

    public String getNewPattern()
    {
        return newPattern;
    }

    public void setNewPattern( String newPattern )
    {
        this.newPattern = newPattern;
    }

    public String getNewPhonetic()
    {
        return newPhonetic;
    }

    public void setNewPhonetic( String newPhonetic )
    {
        this.newPhonetic = newPhonetic;
    }

    public String getNewPresentForm()
    {
        return newPresentForm;
    }

    public void setNewPresentForm( String newPresentForm )
    {
        this.newPresentForm = newPresentForm;
    }

    public String getNewRoot()
    {
        return newRoot;
    }

    public void setNewRoot( String newRoot )
    {
        this.newRoot = newRoot;
    }

    public String getNewVocalizedVerb()
    {
        return newVocalizedVerb;
    }

    public void setNewVocalizedVerb( String newVocalizedVerb )
    {
        this.newVocalizedVerb = newVocalizedVerb;
    }
}
