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
public class SemanticVerbBO extends SemanticEntryBO
{

    private Integer semanticVerbId;
    private String transitivityCase;
    private String status;
    private boolean checked;
    private List<NounAccompanierBO> nounAccompaniers = new ArrayList<NounAccompanierBO>();
    private List<GerundBO> gerunds = new ArrayList<GerundBO>();
    private List<AssimilateAdjectiveBO> assimilateAdjectives = new ArrayList<AssimilateAdjectiveBO>();
    private List<ExaggerationBO> exaggerations = new ArrayList<ExaggerationBO>();
    private List<String[]> contextActors = new ArrayList<String[]>();

    public SemanticVerbBO()
    {
        super();
    }

    public Integer getSemanticVerbId()
    {
        return semanticVerbId;
    }

    public void setSemanticVerbId( Integer semanticVerbId )
    {
        this.semanticVerbId = semanticVerbId;
    }

    public String getTransitivityCase()
    {
        return transitivityCase;
    }

    public void setTransitivityCase( String TransitivityCase )
    {
        this.transitivityCase = TransitivityCase;
    }

    public List<AssimilateAdjectiveBO> getAssimilateAdjectives()
    {
        return assimilateAdjectives;
    }

    public void setAssimilateAdjectives( List<AssimilateAdjectiveBO> assimilateAdjectives )
    {
        this.assimilateAdjectives = assimilateAdjectives;
    }

    public List<String[]> getContextTypes()
    {
        return contextActors;
    }

    public void setContextTypes( List<String[]> contextActors )
    {
        this.contextActors = contextActors;
    }

    public List<ExaggerationBO> getExaggerations()
    {
        return exaggerations;
    }

    public void setExaggerations( List<ExaggerationBO> exaggerations )
    {
        this.exaggerations = exaggerations;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public List<GerundBO> getAlteredGerunds()
    {
        List<GerundBO> results = new ArrayList<GerundBO>();
        for ( int i = 0; i < gerunds.size(); i++ )
        {
            String s = gerunds.get( i ).getGerund().getVocalizedNoun();
            try
            {
                if ( s.charAt( s.length() - 1 ) == 'ة' || s.charAt( s.length() - 1 ) == 'ى' )
                {
                    s += "ً";
                }
                else if ( s.charAt( s.length() - 2 ) == 'ا' && s.charAt( s.length() - 1 ) == 'ء' )
                {
                    s += "ً";
                }
                else if ( s.charAt( s.length() - 1 ) == 'ل' )
                {
                    s += "اً";
                }
                else
                {
                    s += "ًا";
                }
                GerundBO newGerundBO = gerunds.get( i );
                newGerundBO.getGerund().setVocalizedNoun( s );
                results.add( newGerundBO );
            }
            catch ( Exception ex )
            {
                results.add( gerunds.get( i ) );
            }
        }
        return results;
    }

    public List<GerundBO> getGerunds()
    {
        return this.gerunds;
    }

    public void setGerunds( List<GerundBO> gerunds )
    {
        this.gerunds = gerunds;
    }

    public List<NounAccompanierBO> getNounAccompaniers()
    {
        return nounAccompaniers;
    }

    public void setNounAccompaniers( List<NounAccompanierBO> nounAccompaniers )
    {
        this.nounAccompaniers = nounAccompaniers;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked( boolean checked )
    {
        this.checked = checked;
    }
}
