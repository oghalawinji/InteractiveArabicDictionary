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
public class SemanticNounBO extends SemanticEntryBO implements java.io.Serializable
{

    private Integer semanticNounId;
    private List<AnnexedNounBO> annexednouns = new ArrayList<AnnexedNounBO>();
    private List<VerbAccompanierBO> verbAccompaniers = new ArrayList<VerbAccompanierBO>();
    private List<AdjectiveAccompanierBO> adjectiveAccompaniers = new ArrayList<AdjectiveAccompanierBO>();
    private List<DiminutiveBO> diminutives = new ArrayList<DiminutiveBO>();
    private List<ProperAdjectiveBO> properAdjectives = new ArrayList<ProperAdjectiveBO>();
    private List<FemininityBO> femininities = new ArrayList<FemininityBO>();
    private String status;
    private boolean checked;
    private List<PluralNounBO> plurals = new ArrayList<PluralNounBO>();
    private NounBO noun;

    public SemanticNounBO()
    {
        super();
    }

    public List<AdjectiveAccompanierBO> getAdjectiveAccompaniers()
    {
        return adjectiveAccompaniers;
    }

    public void setAdjectiveAccompaniers( List<AdjectiveAccompanierBO> adjectiveAccompaniers )
    {
        this.adjectiveAccompaniers = adjectiveAccompaniers;
    }

    public List<AnnexedNounBO> getAnnexedNouns()
    {
        return annexednouns;
    }

    public void setAnnexedNouns( List<AnnexedNounBO> annexednouns )
    {
        this.annexednouns = annexednouns;
    }

    public List<DiminutiveBO> getDiminutives()
    {
        return diminutives;
    }

    public void setDiminutives( List<DiminutiveBO> diminutives )
    {
        this.diminutives = diminutives;
    }

    public List<FemininityBO> getFemininities()
    {
        return femininities;
    }

    public void setFemininities( List<FemininityBO> femininities )
    {
        this.femininities = femininities;
    }

    public List<PluralNounBO> getPlurals()
    {
        return plurals;
    }

    public void setPlurals( List<PluralNounBO> plurals )
    {
        this.plurals = plurals;
    }

    public List<ProperAdjectiveBO> getProperAdjectives()
    {
        return properAdjectives;
    }

    public void setProperAdjectives( List<ProperAdjectiveBO> properAdjectives )
    {
        this.properAdjectives = properAdjectives;
    }

    public Integer getSemanticNounId()
    {
        return semanticNounId;
    }

    public void setSemanticNounId( Integer semanticNounId )
    {
        this.semanticNounId = semanticNounId;
    }

    public List<VerbAccompanierBO> getVerbAccompaniers()
    {
        return verbAccompaniers;
    }

    public void setVerbAccompaniers( List<VerbAccompanierBO> verbAccompaniers )
    {
        this.verbAccompaniers = verbAccompaniers;
    }

    public NounBO getNoun()
    {
        return noun;
    }

    public void setNoun( NounBO noun )
    {
        this.noun = noun;
    }

    @Override
    public String getStatus()
    {
        return this.status;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked( boolean checked )
    {
        this.checked = checked;
    }

    @Override
    public void setStatus( String status )
    {
        this.status = status;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof SemanticNounBO )
        {
            SemanticNounBO newSemanticNounBO = ( SemanticNounBO ) obj;
            if ( this.getSemanticNounId() == newSemanticNounBO.getSemanticNounId() )
            {
                return true;
            }
        }
        return false;
    }
}
