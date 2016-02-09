package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedNounBO extends NounBO
{

    private Integer newDerivedNounId;
    private String newNumber;
    private String newPattern;
    private String newType;
    private String newRoot;
    private String newOrigin;
    private String newGender;
    private String newPhonetic;
    private String newStatus;
    private String newVocalizedNoun;

    public UpdatedNounBO()
    {
        super();
    }

    public Integer getNewDerivedNounId()
    {
        return newDerivedNounId;
    }

    public void setNewDerivedNounId( Integer newDerivedNounId )
    {
        this.newDerivedNounId = newDerivedNounId;
    }

    public String getNewGender()
    {
        return newGender;
    }

    public void setNewGender( String newGender )
    {
        this.newGender = newGender;
    }

    public String getNewNumber()
    {
        return newNumber;
    }

    public void setNewNumber( String newNumber )
    {
        this.newNumber = newNumber;
    }

    public String getNewOrigin()
    {
        return newOrigin;
    }

    public void setNewOrigin( String newOrigin )
    {
        this.newOrigin = newOrigin;
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

    public String getNewRoot()
    {
        return newRoot;
    }

    public void setNewRoot( String newRoot )
    {
        this.newRoot = newRoot;
    }

    public String getNewStatus()
    {
        return newStatus;
    }

    public void setNewStatus( String newStatus )
    {
        this.newStatus = newStatus;
    }

    public String getNewType()
    {
        return newType;
    }

    public void setNewType( String newType )
    {
        this.newType = newType;
    }

    public String getNewVocalizedNoun()
    {
        return newVocalizedNoun;
    }

    public void setNewVocalizedNoun( String newVocalizedNoun )
    {
        this.newVocalizedNoun = newVocalizedNoun;
    }
}
