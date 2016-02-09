package BusinessLogicLayer.BusinessObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class NounBO extends WordBO implements java.io.Serializable
{

    private Integer derivedNounId;
    private String number;
    private String pattern;
    private String type;
    private String root;
    private String origin;
    private String gender;
    private String phonetic;
    private String status;
    private List<SemanticNounBO> semanticCases = new ArrayList<SemanticNounBO>();

    public NounBO()
    {
        super();
    }

    public Integer getDerivedNounId()
    {
        return derivedNounId;
    }

    public void setDerivedNounId( Integer derivedNounId )
    {
        this.derivedNounId = derivedNounId;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender( String gender )
    {
        this.gender = gender;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin( String origin )
    {
        this.origin = origin;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern( String pattern )
    {
        this.pattern = pattern;
    }

    public String getPhonetic()
    {
        return phonetic;
    }

    public void setPhonetic( String phonetic )
    {
        this.phonetic = phonetic;
    }

    public String getRoot()
    {
        return root;
    }

    public void setRoot( String root )
    {
        this.root = root;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getVocalizedNoun()
    {
        return this.getVocalizedString();
    }

    public void setVocalizedNoun( String vocalizedNoun )
    {
        this.setVocalizedString( vocalizedNoun );
    }

    public List<SemanticNounBO> getSemanticCases()
    {
        return semanticCases;
    }

    public void setSemanticCases( List<SemanticNounBO> semanticCases )
    {
        this.semanticCases = semanticCases;
    }

    @Override
    public String getStatus()
    {
        return status;
    }

    @Override
    public void setStatus( String status )
    {
        this.status = status;
    }

    @Override
    public int getId()
    {
        return derivedNounId;
    }

    @Override
    public void setId(int id)
    {
        this.derivedNounId = id;
    }
}
