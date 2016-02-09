/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class VerbBO extends WordBO implements java.io.Serializable
{

    private Integer derivedVerbId;
    private String pattern;
    private String root;
    private String presentForm;
    private String phonetic;
    private String status;

    private List<SemanticVerbBO> semanticCases = new ArrayList<SemanticVerbBO>();

    public VerbBO()
    {
        super();
    }

    public Integer getDerivedVerbId()
    {
        return derivedVerbId;
    }

    public void setDerivedVerbId( Integer derivedVerbId )
    {
        this.derivedVerbId = derivedVerbId;
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

    public String getPresentForm()
    {
        return presentForm;
    }

    public void setPresentForm( String presentForm )
    {
        this.presentForm = presentForm;
    }

    public String getRoot()
    {
        return root;
    }

    public void setRoot( String root )
    {
        this.root = root;
    }

    public String getVocalizedVerb()
    {
        return this.getVocalizedString();
    }

    public void setVocalizedVerb( String vocalizedVerb )
    {
        this.setVocalizedString( vocalizedVerb );
    }

    public List<SemanticVerbBO> getSemanticCases()
    {
        return semanticCases;
    }

    public void setSemanticCases( List<SemanticVerbBO> semanticCases )
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
        return derivedVerbId;
    }

    @Override
    public void setId( int id )
    {
        this.derivedVerbId = id;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof VerbBO)
        {
            VerbBO newVerbBO = (VerbBO)obj;
            if(root.equals( newVerbBO.root) && pattern.equals( newVerbBO.getPattern()) && presentForm.equals( newVerbBO.presentForm) && super.equals( obj ))
            {
                return true;
            }
        }
        return false;
    }
}
