/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class IdiomBO extends EntryBO implements java.io.Serializable
{

    private Integer idiomId;
    private String idiom;
    private SemanticEntryBO semanticCase;
    private String status;
    private String type = "تركيب لغوي";

    public String getType ()
    {
        return type;
    }

    public void setType ( String type )
    {
        this.type = type;
    }


    public IdiomBO()
    {
        super();
    }

    public String getIdiom()
    {
        return idiom;
    }

    public void setIdiom( String idiom )
    {
        this.idiom = idiom;
    }

    public Integer getIdiomId()
    {
        return idiomId;
    }

    public void setIdiomId( Integer idiomId )
    {
        this.idiomId = idiomId;
    }

    public SemanticEntryBO getSemanticCase()
    {
        return semanticCase;
    }

    public void setSemanticCase( SemanticEntryBO semanticCase )
    {
        this.semanticCase = semanticCase;
    }

    public String getVocalizedIdiom()
    {
        return this.getVocalizedString();
    }

    public void setVocalizedIdiom( String vocalizedIdiom )
    {
        this.setVocalizedString( vocalizedIdiom );
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    @Override
    public int getId()
    {
        return idiomId;
    }

    @Override
    public void setId(int idiomId)
    {
        this.idiomId = idiomId;
    }
}
