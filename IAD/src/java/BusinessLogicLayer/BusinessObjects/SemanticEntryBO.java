/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

import PersistenceLayer.Difficultydegree;
import PersistenceLayer.Epoch;
import PersistenceLayer.Region;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticscop;
import PersistenceLayer.Specialization;
import PersistenceLayer.Spreadingdegree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class SemanticEntryBO implements java.io.Serializable
{

    private Integer semanticEntryId;
    private String difficultydegree;
    private String semanticscop;
    private String epoch;
    private String spreadingdegree;
    private String specialization;
    private String region;
    private String status;
    private boolean checked;
    private List<CommonMistakeBO> commonMistakes = new ArrayList<CommonMistakeBO>();
    private List<MeaningBO> meanings = new ArrayList<MeaningBO>();
    private List<LinguisticBenefitBO> linguisticBenefits = new ArrayList<LinguisticBenefitBO>();
    private List<ExampleBO> examples = new ArrayList<ExampleBO>();
    private List<String> relatedidioms = new ArrayList<String>();
    private List<byte[]> images = new ArrayList<byte[]>();
    private List<byte[]> videos = new ArrayList<byte[]>();
    private List<byte[]> sounds = new ArrayList<byte[]>();

    public String getDifficultydegree()
    {
        return difficultydegree;
    }

    public void setDifficultydegree( String difficultydegree )
    {
        this.difficultydegree = difficultydegree;
    }

    public String getEpoch()
    {
        return epoch;
    }

    public void setEpoch( String epoch )
    {
        this.epoch = epoch;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion( String region )
    {
        this.region = region;
    }

    public Integer getSemanticEntryId()
    {
        return semanticEntryId;
    }

    public void setSemanticEntryId( Integer semanticEntryId )
    {
        this.semanticEntryId = semanticEntryId;
    }

    public String getSemanticscop()
    {
        return semanticscop;
    }

    public void setSemanticscop( String semanticscop )
    {
        this.semanticscop = semanticscop;
    }

    public String getSpecialization()
    {
        return specialization;
    }

    public void setSpecialization( String specialization )
    {
        this.specialization = specialization;
    }

    public String getSpreadingdegree()
    {
        return spreadingdegree;
    }

    public void setSpreadingdegree( String spreadingdegree )
    {
        this.spreadingdegree = spreadingdegree;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public List<CommonMistakeBO> getCommonMistakes()
    {
        return commonMistakes;
    }

    public void setCommonMistakes( List<CommonMistakeBO> commonMistakes )
    {
        this.commonMistakes = commonMistakes;
    }

    public List<ExampleBO> getExamples()
    {
        return examples;
    }

    public void setExamples( List<ExampleBO> examples )
    {
        this.examples = examples;
    }

    public List<LinguisticBenefitBO> getLinguisticBenefits()
    {
        return linguisticBenefits;
    }

    public void setLinguisticBenefits( List<LinguisticBenefitBO> linguisticBenefits )
    {
        this.linguisticBenefits = linguisticBenefits;
    }

    public List<MeaningBO> getMeanings()
    {
        return meanings;
    }

    public void setMeanings( List<MeaningBO> meanings )
    {
        this.meanings = meanings;
    }

    public List<String> getRelatedidioms()
    {
        return relatedidioms;
    }

    public void setRelatedidioms( List<String> tempRelatedidioms )
    {
        List<String> relatedidioms1 = new ArrayList<String>();
        for ( int i = 0; i < tempRelatedidioms.size(); i++ )
        {
            String s = tempRelatedidioms.get( i );
            if ( !relatedidioms1.contains( s ) )
            {
                relatedidioms1.add( s );
            }
        }
        this.relatedidioms = relatedidioms1;
    }

    public List<byte[]> getImages()
    {
        return images;
    }

    public void setImages( List<byte[]> images )
    {
        this.images = images;
    }

    public List<byte[]> getSounds()
    {
        return sounds;
    }

    public void setSounds( List<byte[]> sounds )
    {
        this.sounds = sounds;
    }

    public List<byte[]> getVideos()
    {
        return videos;
    }

    public void setVideos( List<byte[]> videos )
    {
        this.videos = videos;
    }

    public Semanticentry getSemanticEntry()
    {
        Semanticentry newSemanticentry = new Semanticentry();
        newSemanticentry.setDifficultydegree( new Difficultydegree( this.difficultydegree ) );
        newSemanticentry.setEpoch( new Epoch( this.epoch ) );
        newSemanticentry.setRegion( new Region( this.region ) );
        newSemanticentry.setSemanticscop( new Semanticscop( semanticscop ) );
        newSemanticentry.setSpecialization( new Specialization( specialization ) );
        newSemanticentry.setSpreadingdegree( new Spreadingdegree( spreadingdegree ) );
        return newSemanticentry;
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
